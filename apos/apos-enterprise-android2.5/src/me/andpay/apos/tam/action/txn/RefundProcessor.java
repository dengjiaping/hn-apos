package me.andpay.apos.tam.action.txn;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.andpay.ac.consts.CurrencyCodes;
import me.andpay.ac.consts.GeoCooTypes;
import me.andpay.ac.consts.ProductCodes;
import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.RefundRequest;
import me.andpay.ac.term.api.txn.RefundResponse;
import me.andpay.ac.term.api.txn.TxnExtAttrNames;
import me.andpay.ac.term.api.txn.TxnExtAttrValues;
import me.andpay.ac.term.api.txn.TxnRequest;
import me.andpay.ac.term.api.txn.TxnResponse;
import me.andpay.apos.R;
import me.andpay.apos.common.constant.ResponseCodes;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.service.model.TiLocation;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.dao.model.QueryPayTxnInfoCond;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.ti.util.SleepUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.util.BeanUtils;
import android.util.Log;

public class RefundProcessor extends GenTxnProcessor {

	@Override
	public void reEntryTxn(TxnForm txnForm, TxnCallback callBack) {
		if (txnForm.getProcessStatus() == TxnForm.PRSTATUS_RESPONSE) {
			return;
		}

		SleepUtil.sleep(RE_ENTRY_SLEEPTIME);
		try {
			RefundResponse refundResponse = txnService
					.syncGetRefundResp((RefundRequest) txnForm
							.getReEntryTxnRequest());
			dealResponse(refundResponse, txnForm, callBack, null);
		} catch (Exception ex) {
			dealException(ex, txnForm, callBack);
		}
	}

	@Override
	public void processTxn(ActionRequest request) {

		TxnCallback callBack = (TxnCallback) request.getHandler();
		TxnForm txnForm = (TxnForm) request.getParameterValue("txnForm");
		txnForm.setTimeoutTime(System.currentTimeMillis());
		// registerTimeout(txnForm, callBack);
		try {
			super.processTxn(request);

			RefundRequest refundRequest = createRequest(txnForm);
			txnForm.setReEntryTxnRequest(refundRequest);

			ExceptionPayTxnInfo exPayTxnInfo = genPayTxnInfo(refundRequest,
					txnForm);
			exceptionPayTxnInfoService.payTxnInfoSnapshot(exPayTxnInfo,
					refundRequest);
			sendTxn(txnForm, refundRequest, callBack);

		} catch (Throwable e) {
			sysError(txnForm, callBack, e);
		}
	}

	public void sendTxn(TxnForm txnForm, RefundRequest refundRequest,
			TxnCallback callBack) {
		try {
			RefundResponse purchaseResponse = txnService
					.syncRefund(refundRequest);
			dealResponse(purchaseResponse, txnForm, callBack, null);
		} catch (Exception ex) {
			dealException(ex, txnForm, callBack);
		}
	}

	private RefundRequest createRequest(TxnForm txnForm) {

		RefundRequest refundRequest = BeanUtils.copyProperties(
				RefundRequest.class, txnForm);

		refundRequest.setRefundAmt(txnForm.getSalesAmt());
		refundRequest.setTermTxnTime(new Date());
		refundRequest.setTermTraceNo(TxnUtil.getTermTraceNo(tiConfig));
		TxnUtil.updateTermTraceNo(tiConfig);

		txnForm.setTermTraceNo(refundRequest.getTermTraceNo());
		txnForm.setTermTxnTime(StringUtil.format("yyyyMMddHHmmss",
				refundRequest.getTermTxnTime()));

		refundRequest.setOrigTxnId(txnForm.getOrigTxnId());
		refundRequest.setProductCode(ProductCodes.APOS_ACQUIRE);
		refundRequest.setRefundCur(CurrencyCodes.CNY);

		if (locationService.hasLocation()) {
			TiLocation tiLocation = locationService.getLocation();

			refundRequest.setLatitude(tiLocation.getLatitude());
			refundRequest.setLongitude(tiLocation.getLongitude());
			if (tiLocation.getSpecLatitude() != 0) {
				refundRequest.setSpecCoordType(GeoCooTypes.BD_09);
				refundRequest.setSpecLatitude(tiLocation.getSpecLatitude());
				refundRequest.setSpecLongitude(tiLocation.getSpecLongitude());
			}
			txnForm.getResponse().setTxnAddress(tiLocation.getAddress());
		}
		// refundRequest.setServiceEntryMode(ServiceEntryModes.IC_WITHOUT_PIN);
		return refundRequest;
	}

	@Override
	public synchronized void dealResponse(TxnResponse txnResponse,
			TxnForm txnForm, TxnCallback callBack, String errorMsg) {

		if (txnForm.getProcessStatus() == TxnForm.PRSTATUS_RESPONSE) {
			return;
		} else {
			txnForm.setProcessStatus(TxnForm.PRSTATUS_RESPONSE);
		}

		if (StringUtil.isNotBlank(errorMsg)) {
			txnForm.getResponse().setResponMsg(errorMsg);
			callBack.showFaild(txnForm.getResponse());
			return;
		}

		RefundResponse refundResponse = filterRefundResponse(txnResponse);
		txnForm.getResponse().setTxnResponse(refundResponse);
		responseProcess(refundResponse, callBack, txnForm);

	}

	private RefundResponse filterRefundResponse(TxnResponse txnResponse) {

		RefundResponse refundResponse = null;
		if (txnResponse == null) {
			refundResponse = new RefundResponse();
		}
		refundResponse = (RefundResponse) txnResponse;

		String msg = getStringResource(R.string.tam_syserror_str);
		if (refundResponse.getRespCode() == null) {
			refundResponse.setRespMessage(msg);
			refundResponse.setRespCode(ResponseCodes.SYS_ERROR);
		}

		if (StringUtil.isBlank(refundResponse.getRespMessage())) {
			refundResponse.setRespMessage(msg);
		}
		return refundResponse;

	}

	private void insertTxn(RefundResponse refundResponse,
			ExceptionPayTxnInfo exPayTxnInfo, TxnForm txnForm) {

		PayTxnInfo payTxnInfo = BeanUtils.copyProperties(PayTxnInfo.class,
				exPayTxnInfo);

		payTxnInfo.setRespMsg(refundResponse.getRespMessage());
		payTxnInfo.setRespCode(refundResponse.getRespCode());
		payTxnInfo.setMerchantId(refundResponse.getMerchantId());
		payTxnInfo.setMerchantName(refundResponse.getMerchantName());
		payTxnInfo.setTxnTypeDesc(refundResponse.getTxnTypeMessage());
		payTxnInfo.setTxnId(refundResponse.getTxnId());
		payTxnInfo.setTxnFlag(refundResponse.getTxnFlag());
		payTxnInfo.setTxnFlagMessage(refundResponse.getTxnFlagMessage());
		payTxnInfo.setTxnType(refundResponse.getTxnType());
		if (refundResponse.getTxnTime() != null) {
			payTxnInfo.setTxnTime(StringUtil.format("yyyyMMddHHmmss",
					refundResponse.getTxnTime()));
		}
		payTxnInfo.setRefNo(refundResponse.getRefNo());
		payTxnInfo.setBankAuthNo(refundResponse.getAuthCode());

		if (StringUtil.isNotBlank(refundResponse.getTxnType())) {
			txnForm.setTxnType(refundResponse.getTxnType());
		}
		// 更新卡信息
		payTxnInfo.setSalesCur(CurrencyCodes.CNY);
		payTxnInfo.setIsRefundEnable(true);
		payTxnInfo.setIdTxn(null);
		payTxnInfo.setUpdateTime(StringUtil
				.format("yyyyMMddHHmmss", new Date()));
		payTxnInfoDao.insert(payTxnInfo);
	}

	private void responseProcess(RefundResponse refundResponse,
			TxnCallback callBack, TxnForm txnForm) {

		if (refundResponse.getRespCode().equals(ResponseCodes.TXN_TIMEOUT)) {
			callBack.onTimeout(txnForm.getResponse());
			return;
		}

		TxnActionResponse actionResponse = txnForm.getResponse();

		ExceptionPayTxnInfo exPayTxnInfo = exceptionPayTxnInfoService
				.getExceptionTxn(txnForm.getTermTraceNo(),
						txnForm.getTermTxnTime());

		actionResponse.setTxnTypeName(refundResponse.getTxnTypeMessage());
		actionResponse.setTxnTime(refundResponse.getTxnTime());
		actionResponse.setSettlementTime(refundResponse.getSettleValueDate());
		actionResponse.setTxnId(refundResponse.getTxnId());

		setParseBinInfo(refundResponse, txnForm);

		if (refundResponse.getRespCode().equals(ResponseCodes.SUCCESS)) {

			exPayTxnInfo.setTxnStatus(PayTxnInfoStatus.STATUS_SUCCESS);
			txnForm.setTxnType(refundResponse.getTxnType());
			tiContext.setAttribute(RuntimeAttrNames.FRESH_SETTLE_FLAG,
					RuntimeAttrNames.FRESH_SETTLE_FLAG);
			// 发送交易确认
			// confirmTxn(txnForm, refundResponse.getTxnId());
			afertRefund(refundResponse, exPayTxnInfo, txnForm);
			updateOrigTxn(refundResponse, txnForm);
			actionResponse.setResponMsg(refundResponse.getRespMessage());
			callBack.txnSuccess(actionResponse);
		} else {
			// 退款失败
			exPayTxnInfo.setTxnStatus(PayTxnInfoStatus.STATUS_FAIL);
			afertRefund(refundResponse, exPayTxnInfo, txnForm);
			actionResponse.setResponMsg(refundResponse.getRespMessage());
			callBack.showFaild(actionResponse);
		}

	}

	private void afertRefund(RefundResponse refundResponse,
			ExceptionPayTxnInfo exPayTxnInfo, TxnForm txnForm) {
		insertTxn(refundResponse, exPayTxnInfo, txnForm);
		if (ResponseCodes.SUCCESS.equals(refundResponse.getRespCode())) {
			tiContext.setAttribute(RuntimeAttrNames.FRESH_REFUND_FLAG,
					RuntimeAttrNames.FRESH_REFUND_FLAG);
			tiContext.setAttribute(RuntimeAttrNames.FRESH_TXN_FLAG,
					RuntimeAttrNames.FRESH_TXN_FLAG);
		}
		exceptionPayTxnInfoService.removeExceptionTxn(
				exPayTxnInfo.getTermTraceNo(), exPayTxnInfo.getTermTxnTime());
	}

	/**
	 * 更新本地的原交易
	 * 
	 * @param refundResponse
	 */
	private void updateOrigTxn(RefundResponse refundResponse, TxnForm txnForm) {

		if (txnForm.getOrigPayTxnInfo() == null) {
			return;
		}

		PayTxnInfo origTxnInfo = queryOrigTxn(txnForm.getOrigTxnId());
		if (origTxnInfo != null) {
			if (TxnTypes.isVoidTxnType(refundResponse.getTxnType())) {
				origTxnInfo.setTxnFlag(refundResponse.getOrigTxnFlag());
				origTxnInfo.setTxnFlagMessage(refundResponse
						.getOrigTxnFlagMessage());
				origTxnInfo.setIsRefundEnable(false);
			} else {
				BigDecimal amtBig = new BigDecimal(0);
				if (origTxnInfo.getRefundAmt() != null) {
					amtBig = origTxnInfo.getRefundAmt();
				}

				origTxnInfo.setRefundAmt(amtBig.add(txnForm.getSalesAmt()));
				if (origTxnInfo.getRefundAmt().compareTo(
						origTxnInfo.getSalesAmt()) >= 0) {
					origTxnInfo.setIsRefundEnable(false);
				}
			}
			payTxnInfoDao.update(origTxnInfo);

		}
	}

	@Override
	protected ExceptionPayTxnInfo genPayTxnInfo(TxnRequest txnRequest,
			TxnForm txnForm) {

		ExceptionPayTxnInfo txnInfo = super.genPayTxnInfo(txnRequest, txnForm);

		PayTxnInfo origTxnInfo = txnForm.getOrigPayTxnInfo();

		txnInfo.setOrigRefNo(origTxnInfo.getRefNo());
		txnInfo.setCardOrg(origTxnInfo.getCardOrg());
		txnInfo.setCardType(origTxnInfo.getCardType());
		txnInfo.setIssuerId(origTxnInfo.getIssuerId());
		txnInfo.setIssuerName(origTxnInfo.getIssuerName());
		txnInfo.setExTraceNO(txnRequest.getExtTraceNo());
		txnInfo.setShortPan(TxnUtil.hidePan(origTxnInfo.getShortPan()));
		txnInfo.setIsRefundEnable(false);
		txnInfo.setOrigTxnId(origTxnInfo.getTxnId());

		txnInfo.setIssuerId(origTxnInfo.getIssuerId());
		txnInfo.setIssuerName(origTxnInfo.getIssuerName());
		txnInfo.setCardOrg(origTxnInfo.getCardOrg());
		txnInfo.setCardType(origTxnInfo.getCardType());
		txnInfo.setShortPan(origTxnInfo.getShortPan());

		return txnInfo;
	}

	public PayTxnInfo queryOrigTxn(String origTxnId) {
		QueryPayTxnInfoCond cond = new QueryPayTxnInfoCond();
		cond.setTxnId(origTxnId);
		List<PayTxnInfo> list = payTxnInfoDao.query(cond, 0, 1);
		if (list == null || list.size() <= 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public void txnTimeout(TxnForm txnForm, TxnCallback txnCallback) {
		Log.i(this.getClass().getName(), "txn timeout");
		RefundResponse refundResponse = new RefundResponse();
		refundResponse.setRespCode(ResponseCodes.TXN_TIMEOUT);
		dealResponse(refundResponse, txnForm, txnCallback, null);
		return;

	}

	public void retrieveTxn(ActionRequest request) {

		super.initConfig(request);

		TxnCallback txnCallback = (TxnCallback) request.getHandler();

		TxnForm txnForm = (TxnForm) request.getParameterValue("txnForm");
		TxnActionResponse txnActionResponse = txnForm.getResponse();

		try {
			RefundRequest refundRequest = new RefundRequest();
			Map<String, String> extAttrs = new HashMap<String, String>();
			extAttrs.put(TxnExtAttrNames.RETRIEVE_ONLY, TxnExtAttrValues.TRUE);
			refundRequest.setExtAttrs(extAttrs);

			refundRequest.setProductCode(ProductCodes.APOS_ACQUIRE);
			refundRequest.setTermTraceNo(txnActionResponse.getTermTraceNo());
			refundRequest.setTermTxnTime(StringUtil.parseToDate(
					"yyyyMMddHHmmss", txnActionResponse.getTermTxnTime()));
			refundRequest.setRefundAmt(txnForm.getSalesAmt());
			refundRequest.setRefundCur(CurrencyCodes.CNY);
			refundRequest.setOrigTxnId(txnForm.getOrigTxnId());
			RefundResponse refundResponse = null;
			try {

				refundResponse = txnService.syncGetRefundResp(refundRequest);

			} catch (Exception ex) {
				Log.e(this.getClass().getName(), "txn systemError", ex);
				txnCallback.networkError(txnForm.getResponse());
				return;
			}
			dealResponse(refundResponse, txnForm, txnCallback, null);
		} catch (Exception ex) {
			sysError(txnForm, txnCallback, ex);
		}
	}

}
