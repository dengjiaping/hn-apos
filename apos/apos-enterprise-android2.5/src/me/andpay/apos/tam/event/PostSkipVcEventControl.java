package me.andpay.apos.tam.event;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tam.activity.PostVoucherActivity;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class PostSkipVcEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		PostVoucherActivity postActivity = (PostVoucherActivity) activity;
		
		if (postActivity.postVoucherContext.isRePostFlag()) {
			TiFlowControlImpl.instanceControl().nextSetup(postActivity,
					FlowConstants.GOHOME);
		} else {
			if (StringUtil.isBlank(postActivity.postVoucherContext
					.getReceiptNo())) {
				postActivity.txnControl.backHomePage(activity);

			} else {
				postActivity.sumbitPurchaseOrder();
			}
		}
	}

}
