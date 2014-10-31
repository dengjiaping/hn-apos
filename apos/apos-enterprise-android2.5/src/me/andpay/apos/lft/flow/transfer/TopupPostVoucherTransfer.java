package me.andpay.apos.lft.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import android.app.Activity;
import me.andpay.apos.tam.flow.model.PostVoucherContext;
import me.andpay.apos.tam.flow.model.SignContext;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;

/**
 * 充值成功凭证发送界面 数据转化器
 * 
 * @author xiaoping.shan
 *
 */
public class TopupPostVoucherTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {
		// TODO Auto-generated method stub
		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);

		SignContext signContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(SignContext.class);
		if(signContext != null) {
			txnContext.setSignFileURL(signContext.getSignFileURL());
		}
		PostVoucherContext postVoucherContext = new PostVoucherContext();
		postVoucherContext.setFormatedAmt(txnContext.getAmtFomat());
		TiFlowControlImpl.instanceControl().setFlowContextData(
				postVoucherContext);
	
//		postVoucherContext.setRePostFlag(txnContext.isRePostFlag());
//		postVoucherContext.setTxnId(txnContext.getTxnActionResponse()
//				.getTxnId());
//		postVoucherContext.setHasNewTxnButton(txnContext.isHasNewTxnButton());
//		postVoucherContext.setReceiptNo(txnContext.getReceiptNo());
//		postVoucherContext.setShoppingCart(txnContext.getShoppingCart());
//		postVoucherContext.setMessage(txnContext.getTxnActionResponse()
//				.getTxnResponse().getComment());
		
//		postVoucherContext.setSettlementTime(txnContext.getTxnActionResponse()
//				.getSettlementTime());

		
		
		
		return null;
	}

}
