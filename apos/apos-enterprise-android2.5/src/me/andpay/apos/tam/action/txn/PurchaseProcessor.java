package me.andpay.apos.tam.action.txn;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.andpay.ac.consts.CurrencyCodes;
import me.andpay.ac.consts.GeoCooTypes;
import me.andpay.ac.consts.ProductCodes;
import me.andpay.ac.consts.ServiceEntryModes;
import me.andpay.ac.consts.TxnFlags;
import me.andpay.ac.term.api.txn.BizTypes;
import me.andpay.ac.term.api.txn.ParseBinResponse;
import me.andpay.ac.term.api.txn.PurchaseRequest;
import me.andpay.ac.term.api.txn.PurchaseResponse;
import me.andpay.ac.term.api.txn.TxnExtAttrNames;
import me.andpay.ac.term.api.txn.TxnExtAttrValues;
import me.andpay.ac.term.api.txn.TxnRequest;
import me.andpay.ac.term.api.txn.TxnResponse;
import me.andpay.ac.term.api.txn.order.OrderRecord;
import me.andpay.apos.R;
import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.common.constant.ResponseCodes;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.service.model.TiLocation;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.dao.ICCardInfoDao;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.dao.model.ICCardInfo;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.dao.model.OrderInfoCond;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.dao.model.QueryICCardInfoCond;
import me.andpay.apos.dao.model.QueryPayTxnInfoCond;
import me.andpay.apos.mopm.callback.impl.OrderPayUtil;
import me.andpay.apos.mopm.order.OrderPayContext;
import me.andpay.apos.tam.action.txn.model.PinData;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.orderpay.OrderPayRequest;
import me.andpay.orderpay.OrderPayResponse;
import me.andpay.ti.util.SleepUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.util.BeanUtils;
import android.util.Log;

import com.google.inject.Inject;

public class PurchaseProcessor extends GenTxnProcessor {

	private final String TAG = PurchaseProcessor.class.getName();

	@Inject
	private ICCardInfoDao idCardInfoDao;

	@Override
	public void reEntryTxn(TxnForm txnForm, TxnCallback callBack) {

		if (txnForm.getProcessStatus() == TxnForm.PRSTATUS_RESPONSE) {
			return;
		}
		SleepUtil.sleep(RE_ENTRY_SLEEPTIME);
		Log.i(this.getClass().getName(), "txn reEntry");
		try {
			PurchaseResponse purResponse = txnService
					.syncGetPurchaseResp((PurchaseRequest) txnForm
							.getReEntryTxnRequest());
			dealResponse(purResponse, txnForm, callBack, null);
		} catch (Exception ex) {
			dealException(ex, txnForm, callBack);
		}
	}

	@Override
	public void processTxn(ActionRequest request) {

		Log.e(this.getClass().getName(), "txn start");
		TxnForm txnForm = (TxnForm) request.getParameterValue("txnForm");
		txnForm.setTimeoutTime(System.currentTimeMillis());

		final TxnCallback callBack = (TxnCallback) request.getHandler();

		try {
			super.processTxn(request);
			Log.e(this.getClass().getName(), "start createTxn");
			PurchaseRequest purRequest = createRequest(txnForm);
			Log.e(this.getClass().getName(), "end createTxn");

			txnForm.setReEntryTxnRequest(purRequest);
			List<String> attachmentItemTypes = prepareUplaodImage(txnForm);
			purRequest.setAttachmentTypes(attachmentItemTypes);
			// 记录交易快照
			recordTxnSnapshot(purRequest, txnForm);
			sendTxn(txnForm, purRequest, callBack);

		} catch (Exception ex) {
			Log.i(this.getClass().getName(), "txn systemError");
			sysError(txnForm, callBack, ex);
		}

	}

	public void sendTxn(TxnForm txnForm, PurchaseRequest txnRequest,
			TxnCallback callBack) {
		try {
			PurchaseResponse purchaseResponse = txnService
					.syncPurchase(txnRequest);
			dealResponse(purchaseResponse, txnForm, callBack, null);
		} catch (Exception ex) {
			dealException(ex, txnForm, callBack);
		}
	}

	/**
	 * 记录交易快照
	 * 
	 * @param txnRequest
	 * @param txnForm
	 */
	protected void recordTxnSnapshot(TxnRequest txnRequest, TxnForm txnForm) {
		ExceptionPayTxnInfo exPayTxnInfo = genPayTxnInfo(txnRequest, txnForm);
		Long idTxn = exceptionPayTxnInfoService.payTxnInfoSnapshot(
				exPayTxnInfo, txnRequest, txnForm.getAposICCardDataInfo());
		txnForm.getResponse().setExPayInfoIdTxn(idTxn.intValue());

	}

	// private void orderRequestSet(PurchaseRequest purchaseRequest) {
	// if (OrderPayUtil.isOrderPay(tiContext)) {
	//
	// if (purchaseRequest.getExtAttrs() == null) {
	// purchaseRequest.setExtAttrs(new HashMap<String, String>());
	// }
	// Map<String, String> extAttrs = purchaseRequest.getExtAttrs();
	// OrderPayRequest orderPayRequest = OrderPayUtil.getOrderPayContext()
	// .getOrderPayRequest();
	// extAttrs.put(TxnExtAttrNames.BIZ_TYPE, BizTypes.AGW_ORDER_PAYMENT);
	// extAttrs.put(TxnExtAttrNames.BIZ_PARTY_ID,
	// orderPayRequest.getPartyId());
	// extAttrs.put(TxnExtAttrNames.BIZ_VERSION,
	// orderPayRequest.getVersion());
	// purchaseRequest.setExtAttrs(extAttrs);
	// }
	//
	// }

	private PurchaseRequest createRequest(TxnForm txnForm) {

		PurchaseRequest purRequest = BeanUtils.copyProperties(
				PurchaseRequest.class, txnForm);

		if (locationService.hasLocation()){
			/**/
			TiLocation tiLocation = locationService.getLocation();

			purRequest.setLatitude(tiLocation.getLatitude());
			purRequest.setLongitude(tiLocation.getLongitude());
			if (tiLocation.getSpecLatitude() != 0) {
				purRequest.setSpecCoordType(GeoCooTypes.BD_09);
				purRequest.setSpecLatitude(tiLocation.getSpecLatitude());
				purRequest.setSpecLongitude(tiLocation.getSpecLongitude());
				txnForm.getResponse().setTxnAddress(tiLocation.getAddress());
			}

		}

		purRequest.setSalesAmt(txnForm.getSalesAmt());
		purRequest.setSalesCur(CurrencyCodes.CNY);
		purRequest.setProductCode(ProductCodes.APOS_ACQUIRE);
		purRequest.setTermTxnTime(new Date());
		purRequest.setTermTraceNo(TxnUtil.getTermTraceNo(tiConfig));
		TxnUtil.updateTermTraceNo(tiConfig);

		txnForm.setTermTraceNo(purRequest.getTermTraceNo());
		txnForm.setTermTxnTime(StringUtil.format("yyyyMMddHHmmss",
				purRequest.getTermTxnTime()));

		purRequest.setReceiptNo(txnForm.getReceiptNo());
		PinData pinData = setPinDate(txnForm, purRequest);
		setTracks(txnForm, purRequest);

		TxnHelper.setICCardInfo(txnForm.getAposICCardDataInfo(), purRequest);
		if (txnForm.isIcCardTxn()) {
			purRequest.setCardNo(txnForm.getAposICCardDataInfo().getCardNo());
		}
		setServiceEntryModes(txnForm, purRequest, pinData);

		setMac(txnForm.getMac(), purRequest);

		orderRequestSet(purRequest);
		return purRequest;
	}

	private void orderRequestSet(PurchaseRequest purchaseRequest) {
		if (OrderPayUtil.isOrderPay(tiContext)) {
			if (purchaseRequest.getExtAttrs() == null) {
				purchaseRequest.setExtAttrs(new HashMap<String, String>());
			}
			Map<String, String> extAttrs = purchaseRequest.getExtAttrs();
			OrderPayRequest orderPayRequest = OrderPayUtil.getOrderPayContext()
					.getOrderPayRequest();
			purchaseRequest.setExtTraceNo(orderPayRequest.getOrderId());
			extAttrs.put(TxnExtAttrNames.TRACE_NO, orderPayRequest.getOrderId());
			extAttrs.put(TxnExtAttrNames.BG_TNP_URL,
					orderPayRequest.getBgTnpUrl());
			extAttrs.put(TxnExtAttrNames.NOTIFY_ATTRS,
					orderPayRequest.getNotifyAttrs());
			extAttrs.put(TxnExtAttrNames.SIGN, orderPayRequest.getSign());
			extAttrs.put(TxnExtAttrNames.BIZ_TYPE, BizTypes.AGW_ORDER_PAYMENT);
			extAttrs.put(TxnExtAttrNames.BIZ_PARTY_ID,
					orderPayRequest.getPartyId());
			extAttrs.put(TxnExtAttrNames.BIZ_VERSION,
					orderPayRequest.getVersion());
			purchaseRequest.setExtAttrs(extAttrs);
		}
	}

	/**
	 * 设置服务条件代码
	 * 
	 * @param txnForm
	 * @param purRequest
	 * @param pinData
	 */
	private void setServiceEntryModes(TxnForm txnForm,
			PurchaseRequest purRequest, PinData pinData) {
		if (ExtTypes.EXT_TYPE_VALUE_CARD_TXN.equals(txnForm.getExtType())) {
			if (pinData.getPinblock() != null) {
				purRequest.setServiceEntryMode(ServiceEntryModes.OCR_WITH_PIN);
			} else {
				purRequest
						.setServiceEntryMode(ServiceEntryModes.OCR_WITHOUT_PIN);
			}
		}

	}

	/**
	 * 设置密码数据
	 * 
	 * @param txnForm
	 * @param purRequest
	 */
	private PinData setPinDate(TxnForm txnForm, PurchaseRequest purRequest) {
		// purRequest.setCardNo(txnForm.getParseBinResp().getCardNo());
		PinData pinData = genPinData(txnForm);
		purRequest.setPinblock(pinData.getPinblock());
		purRequest.setPinEncryptAdditionData(pinData
				.getPinEncryptAdditionData());
		purRequest.setPinEncryptMethod(pinData.getPinEncryptMethods());
		return pinData;

	}

	/**
	 * 设置磁道信息
	 * 
	 * @param txnForm
	 * @param purRequest
	 */
	private void setTracks(TxnForm txnForm, PurchaseRequest purRequest) {

		if (txnForm.getCardInfo() == null) {
			return;
		}
		if (ExtTypes.EXT_TYPE_VALUE_CARD_TXN.equals(txnForm.getExtType())) {
			purRequest.setTrack2(txnForm.getCardInfo().getEncTracks());
		} else {
			purRequest.setKsn(txnForm.getCardInfo().getKsn());
			purRequest.setTrackAll(txnForm.getCardInfo().getEncTracks());
			purRequest.setTrackRandomFactor(txnForm.getCardInfo()
					.getRandomNumber());
		}

	}

	private void updateTxn(PurchaseResponse purResponse,
			ExceptionPayTxnInfo exPayTxnInfo, TxnForm txnForm) {

		QueryPayTxnInfoCond cond = new QueryPayTxnInfoCond();
		cond.setTermTraceNo(exPayTxnInfo.getTermTraceNo());
		cond.setTermTxnTime(exPayTxnInfo.getTermTxnTime());
		List<PayTxnInfo> dbInfoList = payTxnInfoDao.query(cond, 0, -1);
		if (dbInfoList.size() > 0) {
			PayTxnInfo dbTxnInfo = dbInfoList.get(0);
			dbTxnInfo.setUpdateTime(StringUtil.format("yyyyMMddHHmmss",
					new Date()));
			payTxnInfoDao.update(dbTxnInfo);
			txnForm.getResponse().setGoodsFileURL(dbTxnInfo.getTranPicPath());
			return;
		}

		PayTxnInfo payTxnInfo = generatePayTxnInfoByResponse(purResponse,
				exPayTxnInfo, txnForm);

		storeImageItem(purResponse.getAttachmentItems(), payTxnInfo, txnForm,
				purResponse.isSignTxnFlag());

		payTxnInfoDao.insert(payTxnInfo);

		updateOrder(payTxnInfo, txnForm);
	}

	protected PayTxnInfo generatePayTxnInfoByResponse(
			PurchaseResponse purResponse, ExceptionPayTxnInfo exPayTxnInfo,
			TxnForm txnForm) {
		PayTxnInfo payTxnInfo = BeanUtils.copyProperties(PayTxnInfo.class,
				exPayTxnInfo);

		payTxnInfo.setRespMsg(purResponse.getRespMessage());
		payTxnInfo.setRespCode(purResponse.getRespCode());
		payTxnInfo.setMerchantId(purResponse.getMerchantId());
		payTxnInfo.setMerchantName(purResponse.getMerchantName());
		payTxnInfo.setTxnTypeDesc(purResponse.getTxnTypeMessage());
		payTxnInfo.setTxnId(purResponse.getTxnId());
		payTxnInfo.setSalesCur(CurrencyCodes.CNY);
		// payTxnInfo.setLatitude(purResponse.)
		payTxnInfo.setTxnFlag(purResponse.getTxnFlag());
		payTxnInfo.setTxnFlagMessage(purResponse.getTxnFlagMessage());
		if (purResponse.getTxnTime() != null) {
			payTxnInfo.setTxnTime(StringUtil.format("yyyyMMddHHmmss",
					purResponse.getTxnTime()));
		}
		payTxnInfo.setRefNo(purResponse.getRefNo());
		payTxnInfo.setBankAuthNo(purResponse.getAuthCode());

		ParseBinResponse parseRespon = txnForm.getParseBinResp();
		payTxnInfo.setTermTraceNo(purResponse.getTermTraceNo());
		// payTxnInfo.setTermTxnTime(purResponse.gett)
		payTxnInfo.setDeviceId(purResponse.getTerminalId());
		payTxnInfo.setIssuerId(parseRespon.getCardIssuerId());
		payTxnInfo.setIssuerName(parseRespon.getCardIssuerName());
		payTxnInfo.setCardOrg(parseRespon.getCardAssoc());
		payTxnInfo.setCardType(parseRespon.getCardType());
		payTxnInfo.setShortPan(TxnUtil.hidePan(parseRespon.getShortCardNo()));
		payTxnInfo.setSalesCur(CurrencyCodes.CNY);
		payTxnInfo.setIsRefundEnable(true);
		payTxnInfo.setIdTxn(null);
		payTxnInfo.setUpdateTime(StringUtil
				.format("yyyyMMddHHmmss", new Date()));
		return payTxnInfo;
	}

	protected void updateOrder(PayTxnInfo payTxnInfo, TxnForm txnForm) {

		if (ExtTypes.EXT_TYPE_ORDER_QUERY.equals(txnForm.getExtType())
				&& payTxnInfo.getTxnStatus().equals(
						PayTxnInfoStatus.STATUS_SUCCESS)) {
			OrderInfoCond cond = new OrderInfoCond();
			cond.setOrderId(payTxnInfo.getExTraceNO());
			cond.setOrderStatus(OrderRecord.STATUS_WAITING_PAY);
			List<OrderInfo> orderInfos = orderInfoDao.query(cond, 0, 1);
			if (orderInfos.size() == 0) {
				return;
			}
			OrderInfo orderInfo = orderInfos.get(0);
			orderInfo.setOrderStatus(OrderRecord.STATUS_PAID);
			orderInfo.setTxnId(payTxnInfo.getTxnId());
			orderInfoDao.update(orderInfo);
		}
	}

	/**
	 * 
	 */
	@Override
	public synchronized void dealResponse(TxnResponse txnResponse,
			TxnForm txnForm, TxnCallback callBack, String hasDealErrorMsg) {

		if (txnForm.getProcessStatus() == TxnForm.PRSTATUS_RESPONSE) {
			return;
		} else {
			txnForm.setProcessStatus(TxnForm.PRSTATUS_RESPONSE);
		}
		// 错误结果已经给出
		if (StringUtil.isNotBlank(hasDealErrorMsg)) {
			txnForm.getResponse().setResponMsg(hasDealErrorMsg);
			callBack.showFaild(txnForm.getResponse());
			return;
		}

		PurchaseResponse purResponse = filterResponse(txnResponse);
		txnForm.getResponse().setTxnResponse(purResponse);
		responseProcessresponseProcess(purResponse, callBack, txnForm);
	}

	private PurchaseResponse filterResponse(TxnResponse txnResponse) {
		PurchaseResponse purResponse = null;
		if (txnResponse == null) {
			purResponse = new PurchaseResponse();
		}
		purResponse = (PurchaseResponse) txnResponse;
		String msg = getStringResource(R.string.tam_syserror_str);
		if (purResponse.getRespCode() == null) {
			purResponse.setRespMessage(msg);
			purResponse.setRespCode(ResponseCodes.SYS_ERROR);
		}
		if (StringUtil.isBlank(purResponse.getRespMessage())) {
			purResponse.setRespMessage(msg);
		}
		return purResponse;
	}

	private void responseProcessresponseProcess(PurchaseResponse purResponse,
			TxnCallback callBack, TxnForm txnForm) {

		// 交易超时的处理
		if (purResponse.getRespCode().equals(ResponseCodes.TXN_TIMEOUT)) {
			callBack.onTimeout(txnForm.getResponse());
			return;
		}

		TxnActionResponse actionResponse = txnForm.getResponse();

		Log.i(this.getClass().getName(), "txn response");
		actionResponse.setTxnTypeName(purResponse.getTxnTypeMessage());
		actionResponse.setTxnTime(purResponse.getTxnTime());
		actionResponse.setSettlementTime(purResponse.getSettleValueDate());
		actionResponse.setTxnId(purResponse.getTxnId());
		actionResponse.setAposICCardDataInfo(txnForm.getAposICCardDataInfo());
		actionResponse.setIcCardTxn(txnForm.isIcCardTxn());
		actionResponse.setAposICCardDataInfo(covertAposICCardDataInfo(
				purResponse, txnForm));

		setParseBinInfo(purResponse, txnForm);

		ExceptionPayTxnInfo exPayTxnInfo = exceptionPayTxnInfoService
				.getExceptionTxn(txnForm.getTermTraceNo(),
						txnForm.getTermTxnTime());

		if (purResponse.getRespCode().equals(ResponseCodes.SUCCESS)) {

			exPayTxnInfo.setTxnStatus(PayTxnInfoStatus.STATUS_SUCCESS);
			genOrderPayResponse(purResponse);

			tiContext.setAttribute(RuntimeAttrNames.FRESH_SETTLE_FLAG,
					RuntimeAttrNames.FRESH_SETTLE_FLAG);
			// 发送交易确认
			txnAfter(purResponse, txnForm, exPayTxnInfo);
			// 设置已完成一笔交易
			tiContext.setAttribute(RuntimeAttrNames.NEXT_TXN,
					RuntimeAttrNames.NEXT_TXN);
			txnForm.getResponse().setResponMsg(purResponse.getRespMessage());
			callBack.txnSuccess(txnForm.getResponse());

		} else {
			// 处理失败交易
			confirmTxn(purResponse.getTxnId());
			dealFailResponse(purResponse, callBack, txnForm, exPayTxnInfo);
		}

	}

	public void txnAfter(PurchaseResponse purchaseResponse, TxnForm txnForm,
			ExceptionPayTxnInfo exPayTxnInfo) {
		updateTxn(purchaseResponse, exPayTxnInfo, txnForm);
		tiContext.setAttribute(RuntimeAttrNames.FRESH_TXN_FLAG,
				RuntimeAttrNames.FRESH_TXN_FLAG);
		if (!purchaseResponse.getRespCode().equals(ResponseCodes.SUCCESS)) {

			exceptionPayTxnInfoService.removeExceptionTxn(
					exPayTxnInfo.getTermTraceNo(),
					exPayTxnInfo.getTermTxnTime());
		} else {
			tiContext.setAttribute(RuntimeAttrNames.FRESH_REFUND_FLAG,
					RuntimeAttrNames.FRESH_REFUND_FLAG);
		}
	}

	/**
	 * 生成订单支付响应
	 * 
	 * @param purResponse
	 */
	private void genOrderPayResponse(PurchaseResponse purResponse) {
		if (OrderPayUtil.isOrderPay(tiContext)) {
			OrderPayContext orderPayContext = OrderPayUtil.getOrderPayContext();
			OrderPayRequest orderPayRequest = orderPayContext
					.getOrderPayRequest();
			OrderPayResponse orderPayResponse = new OrderPayResponse();
			orderPayContext.setOrderPayResponse(orderPayResponse);
			BeanUtils.copyProperties(orderPayRequest, orderPayResponse);
			orderPayResponse.setTxnId(purResponse.getTxnId());
			orderPayResponse.setTxnTime(purResponse.getTxnTime());
			Map<String, String> exAttrs = purResponse.getExtAttrs();
			orderPayResponse.setSign(exAttrs.get(TxnExtAttrNames.SIGN));
			orderPayResponse.setNotifyAttrs(exAttrs
					.get(TxnExtAttrNames.NOTIFY_ATTRS));
			orderPayResponse.setAmt(purResponse.getSalesAmt());
		}

	}

	/**
	 * 处理失败交易访问
	 */
	protected void dealFailResponse(PurchaseResponse purResponse,
			TxnCallback callBack, TxnForm txnForm,
			ExceptionPayTxnInfo exPayTxnInfo) {
		TxnActionResponse actionResponse = txnForm.getResponse();
		if (purResponse.getRespCode().equals(ResponseCodes.PIN_ERROR)) {

			if (txnForm.getPinErrorCount() == 2) {
				actionResponse.setPinErrorCount(0);
				String msg = getStringResource(R.string.tam_pinerror_three_str);
				exPayTxnInfo.setTxnStatus(PayTxnInfoStatus.STATUS_FAIL);
				txnAfter(purResponse, txnForm, exPayTxnInfo);
				actionResponse.setResponMsg(msg);
				callBack.showFaild(actionResponse);
			} else {
				int pinCount = txnForm.getPinErrorCount();
				actionResponse.setPinErrorCount(pinCount + 1);
				String msg = getStringResource(R.string.tam_pin_error1_str)
						+ (2 - pinCount)
						+ getStringResource(R.string.tam_pin_error2_str);
				exPayTxnInfo.setTxnStatus(PayTxnInfoStatus.STATUS_FAIL);
				txnAfter(purResponse, txnForm, exPayTxnInfo);
				txnForm.getResponse().setResponMsg(msg);
				callBack.showInputPassword(txnForm.getResponse());
			}
		} else {
			exPayTxnInfo.setTxnStatus(PayTxnInfoStatus.STATUS_FAIL);
			exPayTxnInfo.setTxnFlag(TxnFlags.FAIL);
			txnAfter(purResponse, txnForm, exPayTxnInfo);
			txnForm.getResponse().setResponMsg(purResponse.getRespMessage());
			callBack.showFaild(txnForm.getResponse());
		}
	}

	@Override
	protected ExceptionPayTxnInfo genPayTxnInfo(TxnRequest txnRequest,
			TxnForm txnForm) {

		ExceptionPayTxnInfo exPayTxnInfo = super.genPayTxnInfo(txnRequest,
				txnForm);
		// 设置卡信息
		ParseBinResponse parseRespon = txnForm.getParseBinResp();
		CardInfo cardInfo = txnForm.getCardInfo();
		exPayTxnInfo.setIssuerId(parseRespon.getCardIssuerId());
		exPayTxnInfo.setIssuerName(parseRespon.getCardIssuerName());
		exPayTxnInfo.setCardOrg(parseRespon.getCardAssoc());
		exPayTxnInfo.setCardType(parseRespon.getCardType());
		exPayTxnInfo.setShortPan(cardInfo.getMaskedPAN());
		exPayTxnInfo.setSalesCur(CurrencyCodes.CNY);
		exPayTxnInfo.setIsRefundEnable(true);
		exPayTxnInfo.setSignPicPath(txnForm.getSignFileURL());
		exPayTxnInfo.setTranPicPath(txnForm.getGoodsFileURL());
		exPayTxnInfo.setIsICCardTxn(txnForm.isIcCardTxn());
		return exPayTxnInfo;
	}

	@Override
	public void txnTimeout(TxnForm txnForm, TxnCallback txnCallback) {

		Log.i(this.getClass().getName(), "txn timeout");
		PurchaseResponse purResponse = new PurchaseResponse();
		purResponse.setRespCode(ResponseCodes.TXN_TIMEOUT);
		dealResponse(purResponse, txnForm, txnCallback, null);
		return;

	}

	public void retrieveTxn(ActionRequest request) {
		super.initConfig(request);

		TxnCallback txnCallback = (TxnCallback) request.getHandler();
		TxnForm txnForm = (TxnForm) request.getParameterValue("txnForm");
		TxnActionResponse txnActionResponse = txnForm.getResponse();
		txnForm.setTermTraceNo(txnActionResponse.getTermTraceNo());
		txnForm.setTermTxnTime(txnActionResponse.getTermTxnTime());

		try {
			PurchaseRequest purchaseRequest = new PurchaseRequest();
			Map<String, String> extAttrs = new HashMap<String, String>();
			purchaseRequest.setExtAttrs(extAttrs);
			extAttrs.put(TxnExtAttrNames.RETRIEVE_ONLY, TxnExtAttrValues.TRUE);
			purchaseRequest.setProductCode(ProductCodes.APOS_ACQUIRE);
			purchaseRequest.setTermTraceNo(txnActionResponse.getTermTraceNo());
			purchaseRequest.setTermTxnTime(StringUtil.parseToDate(
					"yyyyMMddHHmmss", txnActionResponse.getTermTxnTime()));
			purchaseRequest.setSalesAmt(txnForm.getSalesAmt());
			purchaseRequest.setSalesCur(CurrencyCodes.CNY);
			orderRequestSet(purchaseRequest);

			if (txnForm.isIcCardTxn()
					&& txnActionResponse.getExPayInfoIdTxn() != 0) {

				QueryICCardInfoCond cond = new QueryICCardInfoCond();

				List<ICCardInfo> icCardInfos = idCardInfoDao.query(cond, 0, 1);
				if (icCardInfos.size() > 0) {
					ICCardInfo icCardInfo = icCardInfos.get(0);
					AposICCardDataInfo aposICCardDataInfo = BeanUtils
							.copyProperties(AposICCardDataInfo.class,
									icCardInfo);
					TxnHelper
							.setICCardInfo(aposICCardDataInfo, purchaseRequest);
				}

			}
			PurchaseResponse purchaseResponse = null;
			try {
				purchaseResponse = txnService
						.syncGetPurchaseResp(purchaseRequest);
			} catch (Exception ex) {
				Log.e(this.getClass().getName(), "txn systemError", ex);
				txnCallback.networkError(txnForm.getResponse());
				return;
			}

			dealResponse(purchaseResponse, txnForm, txnCallback, null);
		} catch (Exception ex) {
			sysError(txnForm, txnCallback, ex);
		}
	}

}
