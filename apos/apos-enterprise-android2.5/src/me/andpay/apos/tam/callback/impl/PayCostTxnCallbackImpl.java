package me.andpay.apos.tam.callback.impl;

import java.io.Serializable;
import java.util.Map;

import com.google.inject.Inject;

import me.andpay.ac.consts.VasTxnTypes;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.CtResponseAdapterTxnActionResponse;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;

/**
 * 缴费
 * 
 * @author Administrator
 *
 */
public class PayCostTxnCallbackImpl extends TxnCallbackImpl {
	@Inject
	TxnControl txnControl;

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

		context.put("txnType", txnControl.getTxnContext().getTxnType());
		TiFlowControlImpl.instanceControl().nextSetup(
				txnControl.getCurrActivity(),
				me.andpay.apos.common.flow.FlowConstants.FINISH);

	}
}
