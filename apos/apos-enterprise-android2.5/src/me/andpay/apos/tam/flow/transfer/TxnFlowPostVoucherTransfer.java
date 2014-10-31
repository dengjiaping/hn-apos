package me.andpay.apos.tam.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.apos.tam.flow.model.PostVoucherContext;
import me.andpay.apos.tam.flow.model.SignContext;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class TxnFlowPostVoucherTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext){

		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);

		SignContext signContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(SignContext.class);
		if (signContext != null) {
			txnContext.setSignFileURL(signContext.getSignFileURL());
		}

		PostVoucherContext postVoucherContext = new PostVoucherContext();
		TiFlowControlImpl.instanceControl().setFlowContextData(
				postVoucherContext);
		postVoucherContext.setContactInfos(txnContext.getTxnActionResponse()
				.getTxnResponse().getContactInfos());
		postVoucherContext.setRePostFlag(txnContext.isRePostFlag());
		postVoucherContext.setTxnId(txnContext.getTxnActionResponse()
				.getTxnId());
		postVoucherContext.setHasNewTxnButton(txnContext.isHasNewTxnButton());
		postVoucherContext.setReceiptNo(txnContext.getReceiptNo());
		postVoucherContext.setShoppingCart(txnContext.getShoppingCart());
		postVoucherContext.setMessage(txnContext.getTxnActionResponse()
				.getTxnResponse().getComment());
		postVoucherContext.setFormatedAmt(txnContext.getAmtFomat());
		postVoucherContext.setSettlementTime(txnContext.getTxnActionResponse()
				.getSettlementTime());

		return null;
	}

}
