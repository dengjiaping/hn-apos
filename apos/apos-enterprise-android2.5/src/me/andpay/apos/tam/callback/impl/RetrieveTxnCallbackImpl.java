package me.andpay.apos.tam.callback.impl;

import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.TxnResponse;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.service.ExceptionPayTxnInfoService;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.tam.activity.TxnTimeoutActivity;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.cache.HashMap;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import me.andpay.timobileframework.util.RoboGuiceUtil;

@CallBackHandler
public class RetrieveTxnCallbackImpl implements TxnCallback {

	private TxnTimeoutActivity txnTimeoutActivity;

	public TxnControl txnControl;

	public RetrieveTxnCallbackImpl(TxnTimeoutActivity txnTimeoutActivity) {
		this.txnTimeoutActivity = txnTimeoutActivity;
	}

	public void txnSuccess(TxnActionResponse actionResponse) {
		clear();
		TxnCallbackHelper.convertResponse(actionResponse);

		// IC卡二次授权
		if (actionResponse.isIcCardTxn() && txnControl != null) {
			CardReaderManager.secondIssuance(actionResponse
					.getAposICCardDataInfo());
			CardReaderManager.setCurrCallback(txnControl
					.getSwipCardReaderCallBack());
			return;
		}

		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("message", actionResponse.getResponMsg());
		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);
		if (TxnTypes.PURCHASE.equals(txnContext.getTxnType())) {

			ExceptionPayTxnInfoService exceptionPayTxnInfoService = RoboGuiceUtil
					.getInjectObject(ExceptionPayTxnInfoService.class,
							txnTimeoutActivity);
			TxnResponse txnResponse = actionResponse.getTxnResponse();
			if (txnResponse.isSignTxnFlag()) {

				exceptionPayTxnInfoService.changeExceptionStatus(txnContext
						.getTxnActionResponse().getTermTraceNo(), txnContext
						.getTxnActionResponse().getTermTxnTime(),
						ExceptionPayTxnInfo.EXP_STATUS_WAIT_SIGN);
				TiFlowControlImpl.instanceControl().nextSetup(
						txnTimeoutActivity,
						me.andpay.apos.common.flow.FlowConstants.SUCCESS);
			} else {
				// 无签名
				exceptionPayTxnInfoService.removeExceptionTxn(txnContext
						.getTxnActionResponse().getTermTraceNo(), txnContext
						.getTxnActionResponse().getTermTxnTime());
				TiFlowControlImpl.instanceControl().nextSetup(
						txnTimeoutActivity,
						me.andpay.apos.common.flow.FlowConstants.SUCCESS_STEP2);
			}
		} else {
			intentData.put("buttonName", "进入主页");
			TiFlowControlImpl.instanceControl().nextSetup(txnTimeoutActivity,
					FlowConstants.SUCCESS_STEP3, intentData);
		}

	}

	public void showFaild(TxnActionResponse actionResponse) {
		clear();
		TxnCallbackHelper.convertResponse(actionResponse);
		secondIssuance(null);
		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("errorMsg", actionResponse.getResponMsg());

		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);
		if (TxnTypes.PURCHASE.equals(txnContext.getTxnType())) {
			intentData.put("buttonName", "重新刷卡");
		} else {
			intentData.put("buttonName", "查看交易");
		}

		TiFlowControlImpl.instanceControl().nextSetup(txnTimeoutActivity,
				FlowConstants.FAILED, intentData);

	}

	public void networkError(TxnActionResponse actionResponse) {
		txnTimeoutActivity.retrySubmit("网络不稳定,");
	}

	public void clear() {
		if (!txnTimeoutActivity.isFinishing()
				&& txnTimeoutActivity.retryDialog.isShowing()) {
			txnTimeoutActivity.retryDialog.cancel();
		}
	}

	public void onTimeout(TxnActionResponse actionResponse) {
		// ignore
	}

	public void showInputPassword(TxnActionResponse actionResponse) {
		showFaild(actionResponse);
	}

	public void initCallBack(TxnControl txnControl) {
		// ignore
	}

	private void secondIssuance(TxnActionResponse actionResponse) {
		// IC卡二次授权
		if (actionResponse.getAposICCardDataInfo() != null) {
			CardReaderManager.setDefaultCallBack();
			CardReaderManager.secondIssuance(actionResponse
					.getAposICCardDataInfo());
		} else {
			CardReaderManager.clearScreen();
		}
	}

}
