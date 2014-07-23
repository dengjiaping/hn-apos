package me.andpay.apos.vas.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.activity.PurchaseOrderDetailActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class DetailFulfullBtnClickController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		PurchaseOrderDetailActivity pActivity = (PurchaseOrderDetailActivity) activity;
		Map<String, String> sendData = new HashMap<String, String>();
		PurchaseOrderInfo info = pActivity.getFlowContextData(PurchaseOrderInfo.class);
		sendData.put(VasProvider.VAS_INTENT_PURCHASE_INFO_ID_KEY, pActivity
				.getFlowContextData(PurchaseOrderInfo.class).getOrderId().toString());
		sendData.put(VasProvider.VAS_INTENT_SHOWBACK_FLAG_KEY, Boolean.TRUE.toString());
		pActivity.nextSetup(FlowConstants.NEXT_STEP_PREFIX
				+ info.getItems().get(0).getProductType(), sendData);

	}
}
