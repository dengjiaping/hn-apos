package me.andpay.apos.vas.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.vas.flow.model.CashPaymentContext;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import android.app.Activity;

/**
 * 交易完成后，获取PurchaseOrderId
 * @author tinyliu
 *
 */
public class SvcDepositeAfterTxnTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity, Map<String, String> data,
			TiFlowNodeComplete complete, Map<String, Serializable> subFlowContext) {
		AposBaseActivity baseActivity = (AposBaseActivity) activity;
		String paymentMethod = baseActivity.getFlowContextData(SvcDepositeContext.class)
				.getPaymentMethod();
		Long orderId = null;
		if (PaymentMethods.CASH.equals(paymentMethod)) {
			orderId = baseActivity.getFlowContextData(CashPaymentContext.class)
					.getOrderId();
		}
		if (PaymentMethods.SWIPING.equals(paymentMethod)) {
			orderId = baseActivity.getFlowContextData(TxnContext.class).getOrderId();
		}
		baseActivity.getFlowContextData(SvcDepositeContext.class).setPurchaseOrderId(
				orderId);
		return null;
	}

}
