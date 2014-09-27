package me.andpay.apos.scm.event;

import me.andpay.apos.scm.activity.ScmFeedBackAlertActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class BackfeedCancelOnclickController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		ScmFeedBackAlertActivity feedbackActivity = (ScmFeedBackAlertActivity) activity;
		feedbackActivity.finish();
	}

}
