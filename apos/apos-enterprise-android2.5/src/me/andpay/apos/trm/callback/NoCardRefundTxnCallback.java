package me.andpay.apos.trm.callback;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.callback.impl.TxnCallbackHelper;
import me.andpay.apos.tam.callback.impl.TxnCallbackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.apos.trm.activity.RefundInputActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

@CallBackHandler
public class NoCardRefundTxnCallback extends TxnCallbackImpl implements
		TxnCallback {

	public RefundInputActivity activity;

	private void clear() {
		if (txnControl.getTxnDialog() != null) {
			txnControl.getTxnDialog().cancel();
		}
	}

	@Override
	public void txnSuccess(TxnActionResponse txnActionResponse) {
		clear();
		TxnCallbackHelper.convertResponse(txnActionResponse);

		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("message", txnActionResponse.getResponMsg());
		intentData.put("buttonName", "查看交易");
		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.SUCCESS, intentData);
	}

	public void showReSwiperDialog(String msg) {
		// ignore
	}

	@Override
	public void showFaild(TxnActionResponse txnActionResponse) {

		clear();
		TxnCallbackHelper.convertResponse(txnActionResponse);

		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("errorMsg", txnActionResponse.getResponMsg());
		intentData.put("buttonName", "返回");
		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.FAILED, intentData);
	}

	@Override
	public void onTimeout(TxnActionResponse actionResponse) {
		clear();
		TxnCallbackHelper.convertResponse(actionResponse);

		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("buttonName", "查询结果");
		intentData.put("outButtonName", "退出交易");

		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.FAILED_SEPT1, intentData);
	}

	@Override
	public void showInputPassword(TxnActionResponse txnActionResponse) {
		// ignore
	}

	public void netWorkError(TxnActionResponse txnActionResponse) {
		// ignore
	}

	@Override
	public void initCallBack(TxnControl txnControl) {
		activity = (RefundInputActivity) txnControl.getCurrActivity();
		this.txnControl = txnControl;
	}

}
