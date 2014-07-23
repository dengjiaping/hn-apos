package me.andpay.apos.tcm.event;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tcm.activity.InfoChallengeFailedActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class DealErrorMsgEventController extends AbstractEventController {
	public void onClick(Activity activity, FormBean formBean, View view) {
		InfoChallengeFailedActivity failedActivity = (InfoChallengeFailedActivity) activity;
		String errorMsg = failedActivity.failedInfoTextView.getText()
				.toString();
		if (errorMsg.equals("sms error")) {
			TiFlowControlImpl.instanceControl().nextSetup(failedActivity,
					FlowConstants.FAILED_SEPT1);
		} else if (errorMsg.equals("limit error")) {
			TiFlowControlImpl.instanceControl().nextSetup(failedActivity,
					FlowConstants.FAILED_SEPT2);
		} else if (errorMsg.equals("info error")) {
			TiFlowControlImpl.instanceControl().nextSetup(failedActivity,
					FlowConstants.FAILED);
		} else {

		}
	}
}
