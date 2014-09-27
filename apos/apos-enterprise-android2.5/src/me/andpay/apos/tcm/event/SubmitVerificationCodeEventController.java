package me.andpay.apos.tcm.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tcm.activity.SmsChallengeActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class SubmitVerificationCodeEventController extends
		AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		SmsChallengeActivity smsChallengeActivity = (SmsChallengeActivity) activity;
		// TODO submit verification code to server

		// Mock Code
		// int num = (int) (Math.random() * 100);
		// if (num % 2 == 0)
		// TiFlowControlImpl.instanceControl().nextSetup(smsChallengeActivity,
		// FlowConstants.FINISH);
		// else {
		// int num1 = (int) (Math.random() * 100);
		Map<String, String> intentData = new HashMap<String, String>();
		// switch (num1 % 3) {
		// case 0:
		// intentData.put("errorMsg", "sms error");
		// break;
		// case 1:
		intentData.put("errorMsg", "limit error");
		// break;
		// default:
		// intentData.put("errorMsg", "info error");
		// break;
		// }

		TiFlowControlImpl.instanceControl().nextSetup(smsChallengeActivity,
				FlowConstants.FAILED, intentData);
		// }
	}
}
