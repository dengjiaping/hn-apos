package me.andpay.apos.vas.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.apos.vas.flow.model.CashPaymentContext;
import me.andpay.apos.vas.flow.model.ProductSalesContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class CardSalesCashPaymentTransfer implements TiFlowNodeDataTransfer {
	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {

		ProductSalesContext productSalesContext = TiFlowControlImpl
				.instanceControl()
				.getFlowContextData(ProductSalesContext.class);
		productSalesContext.setPaymeneMethed(PaymentMethods.CASH);
		CashPaymentContext cashPaymentContext = new CashPaymentContext();
		TiFlowControlImpl.instanceControl().setFlowContextData(
				cashPaymentContext);
		cashPaymentContext.setShoppingCart(productSalesContext
				.getShoppingCart());
		return null;
	}

}
