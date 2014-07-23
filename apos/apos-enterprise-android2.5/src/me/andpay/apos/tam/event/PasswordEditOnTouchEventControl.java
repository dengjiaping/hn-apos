package me.andpay.apos.tam.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

public class PasswordEditOnTouchEventControl extends AbstractEventController {

	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent motionEvent) {

		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

		}

		return true;
	}
}
