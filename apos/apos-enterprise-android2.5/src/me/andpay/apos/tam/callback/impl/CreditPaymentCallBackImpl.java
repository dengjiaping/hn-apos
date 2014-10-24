package me.andpay.apos.tam.callback.impl;

import java.io.Serializable;
import java.util.Map;

import com.google.inject.Inject;

import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.apos.base.TxnType;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.CtResponseAdapterTxnActionResponse;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.cache.HashMap;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

/**
 * 信用卡还款回调
 * 
 * @author Administrator
 *
 */
@CallBackHandler
public class CreditPaymentCallBackImpl extends TxnCallbackImpl {
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
		Map<String, String> context = new HashMap<String,String>();
		context.put("isSuccess", cm.isSuccess()?"true":"false");
		context.put("txnType", TxnType.MPOS_PAY_CREDIT_CARD);
		TiFlowControlImpl.instanceControl().nextSetup(
				txnControl.getCurrActivity(),
				me.andpay.apos.common.flow.FlowConstants.FINISH,context);
	}
}
