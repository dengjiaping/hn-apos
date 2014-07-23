package me.andpay.apos.vas.event;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.vas.activity.OpenCardFaildActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class OpenCardFaildControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		
		OpenCardFaildActivity openCardFaildActivity = (OpenCardFaildActivity)activity;
		
		if(openCardFaildActivity.backBtn.getId() == view.getId()) {
			TiFlowControlImpl.instanceControl().previousSetup(openCardFaildActivity);
			return;
		}
		
		if(openCardFaildActivity.outBtn.getId() == view.getId()) {
			TiFlowControlImpl.instanceControl().nextSetup(openCardFaildActivity, FlowConstants.FINISH);
			return;
		}
	}

}
