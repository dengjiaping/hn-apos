package me.andpay.apos.tam.callback.impl;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.apos.base.TxnType;
import me.andpay.apos.tam.form.CtResponseAdapterTxnActionResponse;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;

/**
 * 信用卡还款回调
 * 
 * @author Administrator
 *
 */
public class CreditPaymentCallBackImpl extends TxnCallbackImpl {
	@Override
	public void txnSuccess(TxnActionResponse actionResponse) {
		// TODO Auto-generated method stub
		if (txnControl.getTxnDialog().isShowing()) {
			TxnCallbackHelper.clearAc(txnControl);
		}

		CommonTermTxnResponse cm = ((CtResponseAdapterTxnActionResponse) actionResponse)
				.getCommonTermResponse();
		Map<String, Serializable> context = TiFlowControlImpl.instanceControl()
				.getFlowContext();
		context.put(CommonTermTxnResponse.class.getName(), cm);
		context.put("txnType", TxnType.MPOS_PAY_CREDIT_CARD);
		TiFlowControlImpl.instanceControl().nextSetup(
				txnControl.getCurrActivity(),
				me.andpay.apos.common.flow.FlowConstants.FINISH);
	}
}
