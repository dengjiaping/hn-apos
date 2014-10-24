package me.andpay.apos.tam.callback.impl;

import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.apos.base.TxnType;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.CtResponseAdapterTxnActionResponse;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.cache.HashMap;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

import com.google.inject.Inject;

/*转账响应*/
@CallBackHandler
public class TransferAccountCallBackImpl extends TxnCallbackImpl {
	@Inject
	TxnControl txnControl;
	@Override
	
	public void txnSuccess(TxnActionResponse actionResponse) {
		// TODO Auto-generated method stub
		if (txnControl.getTxnDialog().isShowing()) {
			TxnCallbackHelper.clearAc(txnControl);
		}
		
		
		CommonTermTxnResponse cm = ((CtResponseAdapterTxnActionResponse)actionResponse).getCommonTermResponse();
		HashMap<String,String> dataMap = new HashMap<String, String>();
		dataMap.put("isSuccess", cm.isSuccess()?"true":"false");
		dataMap.put("txnType",TxnType.MPOS_TRANSFER_ACCOUNT);
		TiFlowControlImpl.instanceControl().nextSetup(txnControl.getCurrActivity(), me.andpay.apos.common.flow.FlowConstants.FINISH,dataMap);
	}
}
