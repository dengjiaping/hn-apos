package me.andpay.apos.scm.event;

import me.andpay.apos.scm.activity.ScmFeedbackActivity;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class FeedBackTextWatcher extends AbstractEventController {

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {
		ScmFeedbackActivity sa = (ScmFeedbackActivity) activity;
		if (StringUtil.isEmpty(s.toString())) {
			sa.getSendButton().setEnabled(false);
		} else {
			sa.getSendButton().setEnabled(true);
		}

	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

}
