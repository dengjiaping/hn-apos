package me.andpay.apos.tam.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.R;
import me.andpay.apos.tam.flow.model.SignContext;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class TxnFlowSignTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {
		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);
		SignContext signContext = new SignContext();
		TiFlowControlImpl.instanceControl().setFlowContextData(signContext);

		signContext.setShowAmt(txnContext.getAmtFomat());

		if (txnContext.getTxnType().equals(TxnTypes.PURCHASE)
				|| txnContext.getTxnType().equals(TxnTypes.INQUIRY_BALANCE)) {
			signContext.setAmtTextColor(activity.getResources().getColor(
					R.color.tqm_list_item_amount_col));
		} else {
			signContext.setAmtTextColor(activity.getResources().getColor(
					R.color.com_title_red_color));
		}

		signContext.setTermTraceNo(txnContext.getTxnActionResponse()
				.getTermTraceNo());
		signContext.setTermTxnTime(txnContext.getTxnActionResponse()
				.getTermTxnTime());
		return null;
	}

}
