package me.andpay.apos.tam.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.flow.model.TxnDetailContext;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class TxnFlowDetailContextTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {

		TxnContext txnContext = null;
		if (subFlowContext != null) {
			txnContext = (TxnContext) subFlowContext.get(TxnContext.class
					.getName());
		} else {
			txnContext = TiFlowControlImpl.instanceControl()
					.getFlowContextData(TxnContext.class);
		}

		TxnActionResponse actionResponse = txnContext.getTxnActionResponse();

		TxnDetailContext txnDetailContext = new TxnDetailContext();
		TiFlowControlImpl.instanceControl()
				.setFlowContextData(txnDetailContext);
		TiFlowControlImpl.instanceControl().setFlowContextData(txnContext);

		txnDetailContext.setCardAssoc(txnContext.getParseBinResp()
				.getCardAssoc());
		txnDetailContext.setCardIssuerName(txnContext.getParseBinResp()
				.getCardIssuerName());
		if (actionResponse != null) {
			txnDetailContext.setCardNo(actionResponse.getShortCardNo());
			txnDetailContext.setTxnAddress(actionResponse.getTxnAddress());
			txnDetailContext.setTxnTime(actionResponse.getTxnTime());
			txnDetailContext.setTxnTypeName(actionResponse.getTxnTypeName());
		}
		txnDetailContext.setExtTraceNo(txnContext.getExtTraceNo());
		txnDetailContext.setMemo(txnContext.getMemo());
		txnDetailContext.setSalesAmtFomat(txnContext.getAmtFomat());
		txnDetailContext.setTxnType(txnContext.getTxnType());
		txnDetailContext.setSignFileURL(txnContext.getSignFileURL());
		txnDetailContext.setGoodsFileURL(txnContext.getGoodsFileURL());
		return null;

	}
}
