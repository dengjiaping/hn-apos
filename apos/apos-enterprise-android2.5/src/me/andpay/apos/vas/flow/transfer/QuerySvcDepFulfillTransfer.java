package me.andpay.apos.vas.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import android.app.Activity;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;

/**
 * 查询交易履约
 * 
 * @author tinyliu
 * 
 */
public class QuerySvcDepFulfillTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {
		Long purchaseOrderId = Long.parseLong(data
				.get(VasProvider.VAS_INTENT_PURCHASE_INFO_ID_KEY));
		SvcDepositeContext dContext = new SvcDepositeContext();
		dContext.setPurchaseOrderId(purchaseOrderId);
		((AposBaseActivity) activity).setFlowContextData(dContext);
		return null;
	}

}
