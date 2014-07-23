package me.andpay.apos.tcm.event;

import me.andpay.apos.tcm.activity.SmsChallengeActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

/**
 * 
 * @author David.zhang
 * 
 */

public class RequestVerificationCodeEventController extends
		AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		final SmsChallengeActivity smsActivity = (SmsChallengeActivity) activity;
		// TODO send verification code request to server

		// set button disable and start wait timer
		smsActivity.codeButton.setEnabled(false);
		StringBuffer buffer = new StringBuffer();
		buffer.append(smsActivity.codeButton.getText().toString().trim());
		buffer.append(SmsChallengeActivity.TIME_SUFFIX);
		smsActivity.codeButton.setText(buffer.toString());
		smsActivity.codeButton.startTimer(60);
	}
}
