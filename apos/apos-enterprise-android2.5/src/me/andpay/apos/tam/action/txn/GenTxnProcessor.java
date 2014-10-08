package me.andpay.apos.tam.action.txn;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.andpay.ac.consts.AttachmentTypes;
import me.andpay.ac.consts.GeoCooTypes;
import me.andpay.ac.consts.PinEncryptMethods;
import me.andpay.ac.consts.TxnFlags;
import me.andpay.ac.term.api.sec.PublicKey;
import me.andpay.ac.term.api.txn.ParseBinResponse;
import me.andpay.ac.term.api.txn.TxnExtAttrNames;
import me.andpay.ac.term.api.txn.TxnRequest;
import me.andpay.ac.term.api.txn.TxnResponse;
import me.andpay.ac.term.api.txn.TxnService;
import me.andpay.apos.R;
import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.common.bug.ThrowableReporter;
import me.andpay.apos.common.constant.ResponseCodes;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.DeviceInfo;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.common.service.ExceptionPayTxnInfoService;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.common.service.TxnConfirmService;
import me.andpay.apos.common.service.TxnReversalService;
import me.andpay.apos.common.service.UpLoadFileServce;
import me.andpay.apos.common.service.model.TiLocation;
import me.andpay.apos.common.util.ItemConvertUtil;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.ExceptionPayTxnInfoDao;
import me.andpay.apos.dao.OrderInfoDao;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.dao.TxnConfirmDao;
import me.andpay.apos.dao.TxnFlagMapping;
import me.andpay.apos.dao.WaitUploadImageDao;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.dao.model.TxnConfirm;
import me.andpay.apos.dao.model.WaitUploadImage;
import me.andpay.apos.tam.action.txn.model.PinData;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.ti.lnk.transport.websock.common.NetworkErrorException;
import me.andpay.ti.lnk.transport.websock.common.NetworkOpPhase;
import me.andpay.ti.lnk.transport.websock.common.WebSockTimeoutException;
import me.andpay.ti.lnk.transport.wsock.client.light.http.Base64;
import me.andpay.ti.s3.client.PinblockAndProtectedSecretKey;
import me.andpay.ti.s3.client.S3Client;
import me.andpay.ti.util.AttachmentItem;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.bugsense.ThrowableRecorder;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.util.BitMapUtil;
import me.andpay.timobileframework.util.FileUtil;
import me.andpay.timobileframework.util.HexUtils;
import me.andpay.timobileframework.util.tlv.TlvUtil;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.inject.Inject;

public abstract class GenTxnProcessor implements TxnProcessor {

	@Inject
	protected TxnConfirmDao txnConfirmDao;
	@Inject
	protected TxnConfirmService txnConfirmService;

	protected TxnService txnService;

	@Inject
	protected PayTxnInfoDao payTxnInfoDao;
	@Inject
	protected WaitUploadImageDao waitUploadImageDao;

	@Inject
	protected ThrowableRecorder throwableRecorder;

	@Inject
	protected ExceptionPayTxnInfoService exceptionPayTxnInfoService;

	protected TiContext tiConfig;

	protected TiContext tiContext;

	@Inject
	protected Application application;
	@Inject
	protected LocationService locationService;
	@Inject
	protected ExceptionPayTxnInfoDao exceptionPayTxnInfoDao;
	@Inject
	protected OrderInfoDao orderInfoDao;

	@Inject
	protected TxnReversalService txnReversalService;

	@Inject
	protected ThrowableReporter throwableReporter;

	@Inject
	private UpLoadFileServce upLoadFileServce;

	// 重试提交睡眠时间
	public static final int RE_ENTRY_SLEEPTIME = 2000;

	public void processTxn(ActionRequest request) {
		initConfig(request);
	}

	public void initConfig(ActionRequest request) {
		tiContext = request.getContext(TiContext.CONTEXT_SCOPE_APPLICATION);
		tiConfig = request
				.getContext(TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);
	}

	protected void setMac(String mac, TxnRequest purRequest) {
		if (StringUtil.isBlank(mac)) {
			return;
		}
		Map<String, String> extAttrs = purRequest.getExtAttrs();
		if (extAttrs == null) {
			extAttrs = new HashMap<String, String>();
			purRequest.setExtAttrs(extAttrs);
		}
		extAttrs.put(TxnExtAttrNames.MAC, mac);
	}

	protected List<String> prepareUplaodImage(TxnForm txnForm) {

		List<String> attachmentItemTypes = new ArrayList<String>();
		if (txnForm.getGoodsUpload()) {
			attachmentItemTypes.add(AttachmentTypes.PRODUCT_PICTURE);
		}
		if (txnForm.isSignUplaod()) {
			attachmentItemTypes.add(AttachmentTypes.SIGNATURE_PICTURE);
		}

		attachmentItemTypes.add(AttachmentTypes.TXN_RECEIPT_PICTURE);
		attachmentItemTypes.add(AttachmentTypes.LOCATION_PICTURE);

		return attachmentItemTypes;
	}

	protected ExceptionPayTxnInfo genPayTxnInfo(TxnRequest txnRequest,
			TxnForm txnForm) {

		PartyInfo party = (PartyInfo) tiContext
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		DeviceInfo deviceInfo = (DeviceInfo) tiContext
				.getAttribute(RuntimeAttrNames.DEVICE_INFO);
		ExceptionPayTxnInfo payTxnInfo = new ExceptionPayTxnInfo();

		// 设置基本信息
		payTxnInfo.setExTraceNO(txnForm.getExtTraceNo());
		payTxnInfo.setTermTraceNo(txnRequest.getTermTraceNo());
		String termTxnTime = StringUtil.format("yyyyMMddHHmmss",
				txnRequest.getTermTxnTime());
		payTxnInfo.setTermTxnTime(termTxnTime);
		payTxnInfo.setMemo(txnForm.getMemo());
		payTxnInfo.setTxnType(txnForm.getTxnType());
		payTxnInfo.setTxnStatus(PayTxnInfoStatus.STATUS_PENDING);
		payTxnInfo.setTxnFlag(TxnFlags.PENDING);

		// 设置用户信息
		LoginUserInfo loginUser = (LoginUserInfo) tiContext
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		payTxnInfo.setOperNo(loginUser.getUserName());
		payTxnInfo.setOperName(loginUser.getPersonName());
		payTxnInfo.setTxnPartyId(party.getPartyId());
		payTxnInfo.setTxnPartyName(party.getPartyName());
		payTxnInfo.setDeviceId(deviceInfo.getDeviceId());
		// 设置位置信息
		if (locationService.hasLocation()) {

			TiLocation tiLocation = locationService.getLocation();
			payTxnInfo.setLatitude(tiLocation.getLatitude());
			payTxnInfo.setLongitude(tiLocation.getLongitude());
			if (tiLocation.getSpecLatitude() != 0) {
				payTxnInfo.setTxnAddress(tiLocation.getAddress());
				payTxnInfo.setSpecCoordType(GeoCooTypes.BD_09);
				payTxnInfo.setSpecLatitude(tiLocation.getSpecLatitude());
				payTxnInfo.setSpecLongitude(tiLocation.getSpecLongitude());
			}
		}
		txnForm.setTermTraceNo(txnRequest.getTermTraceNo());
		txnForm.setTermTxnTime(termTxnTime);

		if (txnForm.getCardInfo() != null) {
			payTxnInfo.setKsn(txnForm.getCardInfo().getKsn());
		}

		payTxnInfo.setSalesAmt(txnForm.getSalesAmt());

		return payTxnInfo;

	}

	public String getStringResource(int reid) {
		return application.getResources().getString(reid);

	}

	protected PinData genPinData(TxnForm txnForm) {
		PublicKey publicKey = (PublicKey) tiContext
				.getAttribute(RuntimeAttrNames.PIN_PUBLIC_KEY);
		PinData pinData = new PinData();

		if (CardReaderManager.getInputType() == AposSwiperContext.INPUT_CARD_READER) {
			if (txnForm.getCardInfo().getPin() != null) {
				pinData.setPinblock(txnForm.getCardInfo().getPin());
				pinData.setPinEncryptAdditionData(HexUtils
						.hexStringToBytes(txnForm.getCardInfo()
								.getPinRandNumber()));
				pinData.setPinEncryptMethods(PinEncryptMethods.PINPAD);
			}
		}

		if (CardReaderManager.getInputType() == AposSwiperContext.INPUT_KEY_BOARD){
			if (txnForm.getPin() != null) {
				PinblockAndProtectedSecretKey pinAndKey = S3Client.encryptPin(
						txnForm.getPin(),
						txnForm.getParseBinResp().getCardNo(),
						publicKey.getKeyVersion(), publicKey.getKeyData());

				pinData.setPinblock(pinAndKey.getPinblock());
				pinData.setPinEncryptAdditionData(pinAndKey
						.getProtectedSecretKey());
				pinData.setPinEncryptMethods(PinEncryptMethods.PKI);
			}
		}

		return pinData;

	}

	protected void storeImageItem(List<AttachmentItem> items,
			PayTxnInfo payTxnInfo, TxnForm txnForm, boolean isSignFlag) {
		if (items == null || items.isEmpty()
				|| !ResponseCodes.SUCCESS.equals(payTxnInfo.getRespCode())) {
			return;
		}

		TxnActionResponse actionResponse = txnForm.getResponse();

		for (AttachmentItem item : items) {

			WaitUploadImage waitImg = new WaitUploadImage();
			waitImg.setCreateDate(StringUtil.format("yyyyMMddHHmmss",
					new Date()));
			String itemType = item.getAttachmentType();
			waitImg.setItemType(itemType);
			waitImg.setTermTraceNo(payTxnInfo.getTermTraceNo());
			waitImg.setTermTxnTime(payTxnInfo.getTermTxnTime());
			waitImg.setItemId(item.getIdUnderType());
			waitImg.setTimes(0);

			if (itemType.equals(AttachmentTypes.PRODUCT_PICTURE)) {

				if (StringUtil.isNotBlank(payTxnInfo.getTranPicPath())) {

					File file = new File(payTxnInfo.getTranPicPath());
					// 压缩
					Bitmap bitmap = BitMapUtil.getBitmap(
							payTxnInfo.getTranPicPath(), 800, 600);
					FileUtil.bitMapSaveFile(bitmap,
							application.getApplicationContext(),
							file.getName(), 70);
					bitmap.recycle();
					waitImg.setReadyUpload(true);
					waitImg.setItemType(AttachmentTypes.PRODUCT_PICTURE);

					String tempFilePath = FileUtil.getFilePath(
							FileUtil.getMyUUID(), application);
					FileUtil.doCopyFile(new File(payTxnInfo.getTranPicPath()),
							new File(tempFilePath));
					actionResponse.setGoodsFileURL(tempFilePath);
					waitImg.setFilePath(tempFilePath);
					waitImg.setFilePath(payTxnInfo.getTranPicPath());
					waitUploadImageDao.insert(waitImg);
					upLoadFileServce.uploadFile();
					payTxnInfo.setTranPicPath(tempFilePath);
					payTxnInfo.setTranPic(ItemConvertUtil.appendItemId(
							payTxnInfo.getTranPic(), item.getIdUnderType()));
				}

			} else if (itemType.equals(AttachmentTypes.SIGNATURE_PICTURE)
					&& isSignFlag) {
				waitImg.setFilePath(txnForm.getSignFileURL());
				waitImg.setItemType(AttachmentTypes.SIGNATURE_PICTURE);
				waitImg.setReadyUpload(false);
				waitUploadImageDao.insert(waitImg);
				payTxnInfo.setSignPic(ItemConvertUtil.appendItemId(
						payTxnInfo.getSignPic(), item.getIdUnderType()));
			} else if (itemType.equals(AttachmentTypes.TXN_RECEIPT_PICTURE)
					|| itemType.equals(AttachmentTypes.LOCATION_PICTURE)) {
				// 忽略图片
				continue;
			}

		}

	}

	/**
	 * 系统异常
	 */
	public void sysError(TxnForm txnForm, TxnCallback callback, Throwable ex) {
		Log.e(this.getClass().getName(), "txn error", ex);

		Crashlytics.log("txn system error");
		Crashlytics.logException(ex);

		if (txnForm.isNeedRetry()) {
			txnTimeout(txnForm, callback);
		} else {
			exceptionPayTxnInfoService.removeExceptionTxn(
					txnForm.getTermTraceNo(), txnForm.getTermTxnTime());
			dealResponse(null, txnForm, callback, ResourceUtil.getString(
					application, R.string.tam_txn_syserror_str));
		}

	}

	/**
	 * 网络异常
	 */
	public void netWorkerror(TxnForm txnForm, Exception ex, TxnCallback callBack) {
		Log.i(this.getClass().getName(), "txn networkException");
		Log.e(this.getClass().getName(), "txn networkException", ex);
		if (txnForm.isNeedRetry()) {
			txnTimeout(txnForm, callBack);
		} else {
			exceptionPayTxnInfoService.removeExceptionTxn(
					txnForm.getTermTraceNo(), txnForm.getTermTxnTime());
			dealResponse(null, txnForm, callBack, ResourceUtil.getString(
					application, R.string.tam_networkerror_str));
		}

	}

	abstract public void dealResponse(TxnResponse purResponse, TxnForm txnForm,
			TxnCallback callBack, String errorMsg);

	/**
	 * 交易是否成功
	 * 
	 * @param txnFlag
	 * @return
	 */
	protected boolean isTxnSusscess(String txnFlag) {
		if (StringUtil.isBlank(txnFlag)) {
			return false;
		}
		if (TxnFlagMapping.getSuccessFlags().contains(txnFlag)) {
			return true;
		}

		return false;

	}

	public void txnTimeout(ActionRequest request) {
		// TODO Auto-generated method stub

	}

	protected void confirmTxn(String txnId) {

		TxnConfirm txnConfirm = new TxnConfirm();
		txnConfirm.setCreateTime(DateUtil.format("yyyyMMddHHmmss", new Date()));
		txnConfirm.setTxnId(txnId);
		txnConfirm.setRetryCount(0);
		txnConfirmDao.insert(txnConfirm);
		txnConfirmService.sendConfirmTxn();
	}

	protected void dealException(Exception ex, TxnForm txnForm,
			TxnCallback callBack) {
		// 超时判断
		if ((System.currentTimeMillis() - txnForm.getTimeoutTime()) > TxnContext.TXN_TIMEOUT_TIME) {
			txnTimeout(txnForm, callBack);
			return;
		}

		if (ex instanceof NetworkErrorException) {
			NetworkErrorException netExp = (NetworkErrorException) ex;
			processNetworkError(netExp.getPhase(), ex, txnForm, callBack);
		} else if (ex instanceof WebSockTimeoutException) {
			WebSockTimeoutException netException = (WebSockTimeoutException) ex;
			processNetworkError(netException.getPhase(), ex, txnForm, callBack);
		} else {
			sysError(txnForm, callBack, ex);
		}
	}

	protected void processNetworkError(NetworkOpPhase phase, Exception ex,
			TxnForm txnForm, TxnCallback callBack) {

		if (phase.equals(NetworkOpPhase.READ_WRITE) || txnForm.isNeedRetry()) {
			txnForm.setNeedRetry(true);
			reEntryTxn(txnForm, callBack);
		} else {
			netWorkerror(txnForm, ex, callBack);

		}
	}

	abstract public void reEntryTxn(TxnForm txnForm, TxnCallback callBack);

	protected void setParseBinInfo(TxnResponse txnResponse, TxnForm txnForm) {
		ParseBinResponse parseBinResponse = txnForm.getParseBinResp();
		if (parseBinResponse.getCardNo() == null) {
			parseBinResponse.setCardNo(txnResponse.getEncCardNo());
		}
		parseBinResponse.setShortCardNo(txnResponse.getShortCardNo());
		parseBinResponse.setCardAssoc(txnResponse.getCardAssoc());
		parseBinResponse.setCardIssuerName(txnResponse.getIssuerName());
		txnForm.getResponse().setShortCardNo(txnResponse.getShortCardNo());

	}

	protected AposICCardDataInfo covertAposICCardDataInfo(
			TxnResponse txnResponse, TxnForm txnForm) {

		if (!txnForm.isIcCardTxn()) {
			return null;
		}

		AposICCardDataInfo aposICCardDataInfo = null;
		Map<String, String> extAttrs = txnResponse.getExtAttrs();
		if (extAttrs == null
				|| extAttrs.get(TxnExtAttrNames.IC_DATA_BASE64) == null) {
			aposICCardDataInfo = txnForm.getAposICCardDataInfo();
		} else {
			aposICCardDataInfo = TlvUtil.decodeTlv(HexUtils
					.bytesToHexString(Base64.decode(extAttrs
							.get(TxnExtAttrNames.IC_DATA_BASE64))),
					AposICCardDataInfo.class);

		}
		aposICCardDataInfo.setAutoCode(txnResponse.getIso8583RespCode());

		return aposICCardDataInfo;
	}

	abstract public void txnTimeout(TxnForm txnForm, TxnCallback txnCallback);

}
