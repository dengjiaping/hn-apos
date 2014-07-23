package me.andpay.apos.tam.action.txn.cloud;

import java.util.List;

import me.andpay.ac.consts.TxnFlags;
import me.andpay.ac.term.api.txn.PurchaseRequest;
import me.andpay.ac.term.api.txn.PurchaseResponse;
import me.andpay.ac.term.api.txn.TxnRequest;
import me.andpay.ac.term.api.txn.order.CloudOrderApply;
import me.andpay.ac.term.api.txn.order.CloudOrderPaymentResult;
import me.andpay.ac.term.api.txn.order.CloudOrderService;
import me.andpay.apos.R;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.tam.action.txn.PurchaseProcessor;
import me.andpay.apos.tam.callback.CloudPosCallback;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.ti.lnk.transport.websock.common.NetworkErrorException;
import me.andpay.ti.lnk.transport.websock.common.NetworkOpPhase;
import me.andpay.ti.lnk.transport.websock.common.WebSockTimeoutException;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.BeanUtils;
import android.util.Log;

public class SupportCloudPosPurchaseProcessor extends PurchaseProcessor {

	CloudOrderService cloudOrderService;

	private static int DEFAULT_APPLY_ORDER_WAIT_TIME = 2 * 1000;

	@Override
	public void sendTxn(TxnForm txnForm, PurchaseRequest txnRequest, TxnCallback callBack) {
		// 判断是否是云订单
		if (!txnForm.getIsCloudOrder()) {
			super.sendTxn(txnForm, txnRequest, callBack);
			return;
		}
		Log.i(this.getClass().getName(), "txn start send");
		// 将消费请求转换为云订单发送请求
		CloudOrderApply apply = CloudPosUtil.convert2CloudRequest(txnForm, txnRequest);
		String cloudOrderId = null;
		boolean continueFlag = false;
		do {
			try {
				cloudOrderId = cloudOrderService.processCloudOrderApply(apply);
				txnForm.setCloudOrderId(cloudOrderId);
				Thread.sleep(DEFAULT_APPLY_ORDER_WAIT_TIME);
				((CloudPosCallback) callBack).pushOrderSucc(cloudOrderId);
				continueFlag = false;
			} catch (Throwable ex) {
				Log.e(this.getClass().getName(), "sendTxn", ex);
				if (!isContinueWithCOApplyException(ex, txnForm)) {
					((CloudPosCallback) callBack).pushOrderNetworkError(
							ResourceUtil.getString(application,
									R.string.tam_networkerror_str));
					return;
				}
				continueFlag = true;
			}
		} while (continueFlag);
		getCloudOrderResult(txnRequest, txnForm, callBack);
	}
	
	/**
	 * 循环获取云订单支付结果
	 * 
	 * @param txnForm
	 * @param callBack
	 */
	protected void getCloudOrderResult(PurchaseRequest txnRequest, TxnForm txnForm,
			TxnCallback callBack) {
		CloudOrderPaymentResult txnResponse = null;
		do {

			try {
				txnResponse  = cloudOrderService.getCloudOrderPaymentResult(txnForm
						.getCloudOrderId());
			} catch (Throwable ex) {
				Log.e(this.getClass().getName(), "getCloudOrderResult", ex);
			}
			if (txnForm.getTxnCancelFlag().isCancelTxn()) {
				Log.e("SupportCloudPosPurchaseProcessor", "[" + txnForm.getCloudOrderId()
						+ "] is canceled");
				return;
			}
		} while (txnResponse == null);
		PurchaseResponse purResponse = new PurchaseResponse();
		BeanUtils.copyProperties(txnResponse, purResponse);
		super.recordTxnSnapshot(txnRequest, txnForm);
		dealResponse(purResponse, txnForm, callBack, null);
	}
	
	@Override
	protected void dealFailResponse(PurchaseResponse purResponse, TxnCallback callBack,
			TxnForm txnForm, ExceptionPayTxnInfo exPayTxnInfo) {
		if (!txnForm.getIsCloudOrder()) {
			super.dealFailResponse(purResponse, callBack, txnForm, exPayTxnInfo);
			return;
		}
		txnForm.getTxnCancelFlag().cancelTxn();
		exPayTxnInfo.setTxnStatus(PayTxnInfoStatus.STATUS_FAIL);
		exPayTxnInfo.setTxnFlag(TxnFlags.FAIL);
		txnAfter(purResponse, txnForm, exPayTxnInfo);
		txnForm.getResponse().setResponMsg(purResponse.getRespMessage());
		((CloudPosCallback) callBack).pushOrderNetworkError(
				purResponse.getRespMessage());
	}

//	/**
//	 * 只有IC交易才有ACK
//	 */
//	@Override
//	protected void confirmTxn(TxnForm txnForm, String txnId) {
//		if (txnForm.isIcCardTxn()) {
//			super.confirmTxn(txnId);
//		}
//	}

	/**
	 * 云订单不需要设置签名图片
	 */
	@Override
	protected List<String> prepareUplaodImage(TxnForm txnForm) {
		if (txnForm.getIsCloudOrder()) {
			txnForm.setSignUplaod(false);
		}
		return super.prepareUplaodImage(txnForm);

	}

	/**
	 * 云订单不需要恢复交易
	 */
	@Override
	protected void recordTxnSnapshot(TxnRequest txnRequest, TxnForm txnForm) {
		if (txnForm.getIsCloudOrder()) {
			return;
		}
		super.recordTxnSnapshot(txnRequest, txnForm);
	}

	

	/**
	 * 根据异常信息判断是否需要继续上送云订单
	 * 
	 * 只有(WebSockTimeoutException or NetworkErrorException).Phase=READ_WRITE
	 * 继续上送云订单
	 * 
	 * 其余状态直接报错
	 * 
	 * @param ex
	 * @param txnForm
	 * @return
	 */
	protected boolean isContinueWithCOApplyException(Throwable ex, TxnForm txnForm) {
		boolean isContinue = false;
		if (ex instanceof NetworkErrorException) {
			NetworkErrorException netExp = (NetworkErrorException) ex;
			isContinue = netExp.getPhase().equals(NetworkOpPhase.READ_WRITE);
		} else if (ex instanceof WebSockTimeoutException) {
			WebSockTimeoutException netException = (WebSockTimeoutException) ex;
			isContinue = netException.getPhase().equals(NetworkOpPhase.READ_WRITE);
		}
		if (!isContinue) {
			exceptionPayTxnInfoService.removeExceptionTxn(txnForm.getTermTraceNo(),
					txnForm.getTermTxnTime());
		}
		return isContinue;
	}

	@Override
	/**
	 * 云订单不需要设置GPS信息
	 */
	protected PayTxnInfo generatePayTxnInfoByResponse(PurchaseResponse purResponse,
			ExceptionPayTxnInfo exPayTxnInfo, TxnForm txnForm) {
		PayTxnInfo info = super.generatePayTxnInfoByResponse(purResponse, exPayTxnInfo,
				txnForm);
		if (txnForm.getIsCloudOrder()) {
			info.setTermTxnTime(StringUtil.format("yyyyMMddHHmmss",
					purResponse.getTermTxnTime()));
			info.setLatitude(null);
			info.setLongitude(null);
			info.setTxnAddress(null);
			info.setSpecCoordType(null);
			info.setSpecLatitude(null);
			info.setSpecLongitude(null);
			info.setIsCloudOrder(true);
			
		}
		return info;
	}

}
