package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.SignActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SignPromptControl extends AbstractEventController {

	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent event) {
		Log.e(this.getClass().getName(), "EventAction=" + event.getAction());
		SignActivity signActivity = (SignActivity) activity;
		signActivity.signPromptLay.setVisibility(View.GONE);
		return false;
	}
}
