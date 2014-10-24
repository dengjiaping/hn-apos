package me.andpay.apos.tam.callback.impl;

import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

@CallBackHandler
public class QueryBalanceCallBackImpl extends TxnCallbackImpl implements
		TxnCallback {

	@Override
	public void txnSuccess(TxnActionResponse txnActionResponse){
		if (txnControl.getTxnDialog().isShowing()) {
			TxnCallbackHelper.clearAc(txnControl);
		}

		// IC卡二次授权
		if (txnActionResponse.isIcCardTxn()) {
			CardReaderManager.secondIssuance(txnActionResponse
					.getAposICCardDataInfo());
			CardReaderManager.setDefaultCallBack();
		}

		TxnCallbackHelper.convertResponse(txnActionResponse);
		txnControl.getTxnContext().setSalesAmt(
				txnActionResponse.getBalanceAmt());

		TiFlowControlImpl.instanceControl().nextSetup(activity,
				me.andpay.apos.common.flow.FlowConstants.FINISH);

	}

}
