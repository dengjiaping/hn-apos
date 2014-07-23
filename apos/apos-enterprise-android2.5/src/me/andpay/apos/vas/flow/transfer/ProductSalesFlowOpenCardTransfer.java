package me.andpay.apos.vas.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.flow.model.ProductSalesContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class ProductSalesFlowOpenCardTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {

		ProductSalesContext productSalesContext =	TiFlowControlImpl.instanceControl().getFlowContextData(ProductSalesContext.class);
		data.put(VasProvider.VAS_INTENT_PURCHASE_INFO_ID_KEY, productSalesContext.getPurchaseOrder().getOrderId().toString());
		return data;
	}


}
