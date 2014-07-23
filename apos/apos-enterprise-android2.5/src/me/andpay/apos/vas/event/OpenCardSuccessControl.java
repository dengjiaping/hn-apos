package me.andpay.apos.vas.event;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.vas.activity.OpenCardSuccessActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class OpenCardSuccessControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		
		OpenCardSuccessActivity openCardSuccessActivity = (OpenCardSuccessActivity)activity;
		
		if(view.getId() == openCardSuccessActivity.sureBtn.getId() ) {
			TiFlowControlImpl.instanceControl().nextSetup(openCardSuccessActivity, FlowConstants.SUCCESS);
			return;
		}
		
		if(view.getId() == openCardSuccessActivity.resendBtn.getId()) {
			openCardSuccessActivity.sendSvcEcard();
			return;
		}
		
	}
		

}
