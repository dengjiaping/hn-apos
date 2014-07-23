package me.andpay.apos.tcm.event;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tcm.activity.InfoChallengeActivity;
import me.andpay.apos.tcm.flow.ChallengeContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

/**
 * 
 * @author David.zhang
 * 
 */
public class SubmitChallengeInfoEventController extends AbstractEventController {
	// private static int count = 0;

	public void onClick(Activity activity, FormBean formBean, View view) {
		// for test
		// count++;
		// if (count % 2 == 1) {
		// Intent failedIntent = new Intent(activity,
		// InfoChallengeFailedActivity.class);
		// activity.startActivity(failedIntent);
		// } else {
		// Intent messageIntent = new Intent(activity,
		// SmsChallengeActivity.class);
		// activity.startActivity(messageIntent);
		// }
		InfoChallengeActivity infoChallengeActivity = (InfoChallengeActivity) activity;
		ChallengeContext challengeContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(ChallengeContext.class);
		challengeContext.setPhoneNumber(infoChallengeActivity.phoneEditText
				.getText().toString());
		challengeContext.setName(infoChallengeActivity.nameEditText.getText()
				.toString());
		challengeContext.setIdentityId(infoChallengeActivity.identityEditText
				.getText().toString());
		challengeContext.setCvv2Id(infoChallengeActivity.cvv2EditText.getText()
				.toString());
		TiFlowControlImpl.instanceControl().nextSetup(infoChallengeActivity,
				FlowConstants.SUCCESS);
	}
}
