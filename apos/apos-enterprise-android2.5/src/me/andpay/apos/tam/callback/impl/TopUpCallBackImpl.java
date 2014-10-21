package me.andpay.apos.tam.callback.impl;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.consts.VasTxnTypes;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.apos.base.TxnType;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.CtResponseAdapterTxnActionResponse;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

/**
 * 充值交易回调
 * @author Administrator
 *
 */
@CallBackHandler
public class TopUpCallBackImpl extends TxnCallbackImpl{

	public void txnSuccess(TxnActionResponse actionResponse) {
		// TODO Auto-generated method stub
		if (txnControl.getTxnDialog().isShowing()) {
			TxnCallbackHelper.clearAc(txnControl);
		}
		
		
		CommonTermTxnResponse cm = ((CtResponseAdapterTxnActionResponse)actionResponse).getCommonTermResponse();
		Map<String,Serializable> context=TiFlowControlImpl.instanceControl().getFlowContext();
		context.put(CommonTermTxnResponse.class.getName(),cm);
		context.put("txnType",TxnType.MPOS_TOPUP);
		TiFlowControlImpl.instanceControl().nextSetup(txnControl.getCurrActivity(), me.andpay.apos.common.flow.FlowConstants.FINISH);
		
		
	}

	public void showFaild(TxnActionResponse actionResponse) {
		// TODO Auto-generated method stub
		
	}

	public void onTimeout(TxnActionResponse actionResponse) {
		// TODO Auto-generated method stub
		
	}

	public void showInputPassword(TxnActionResponse actionResponse) {
		// TODO Auto-generated method stub
		
	}

	public void networkError(TxnActionResponse actionResponse) {
		// TODO Auto-generated method stub
		
	}

	public void initCallBack(TxnControl txnControl) {
		// TODO Auto-generated method stub
		
	}
	

}
