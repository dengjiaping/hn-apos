package me.andpay.apos.tam.callback.impl;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

@CallBackHandler
public class ValueCardTxnCallbackImpl extends TxnCallbackImpl implements
		TxnCallback {


	@Override
	public void showFaild(TxnActionResponse txnActionResponse) {
		if (txnControl.getTxnDialog().isShowing()) {
			TxnCallbackHelper.clearAc(txnControl);
		}
		TxnCallbackHelper.convertResponse(txnActionResponse);
		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("errorMsg", txnActionResponse.getResponMsg());
		intentData.put("buttonName", "重新扫描");
		TiFlowControlImpl.instanceControl().nextSetup(
				txnControl.getCurrActivity(),
				me.andpay.apos.common.flow.FlowConstants.FAILED_SEPT2,
				intentData);

	}

}
