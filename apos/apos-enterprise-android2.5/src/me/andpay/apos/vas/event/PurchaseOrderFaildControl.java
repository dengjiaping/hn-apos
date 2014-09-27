package me.andpay.apos.vas.event;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.vas.activity.PurchaseOrderFaildActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class PurchaseOrderFaildControl extends AbstractEventController {

	public void onClick(Activity refActivity, FormBean formBean, View v) {

		PurchaseOrderFaildActivity purchaseOrderDetailActivity = (PurchaseOrderFaildActivity) refActivity;
		if (v.getId() == purchaseOrderDetailActivity.retryBtn.getId()) {
			purchaseOrderDetailActivity.sumbitPurchaseOrder();
			return;
		}

		if (v.getId() == purchaseOrderDetailActivity.outButton.getId()) {

			TiFlowControlImpl.instanceControl().nextSetup(
					purchaseOrderDetailActivity, FlowConstants.FINISH);

			return;
		}
	}

}
