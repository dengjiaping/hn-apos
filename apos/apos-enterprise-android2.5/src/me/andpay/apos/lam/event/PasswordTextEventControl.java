package me.andpay.apos.lam.event;

import me.andpay.apos.lam.activity.LoginActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class PasswordTextEventControl extends AbstractEventController {

	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent motionEvent) {

		LoginActivity loginAc = (LoginActivity) activity;
		// InputMethodManager im = ((InputMethodManager) activity
		// .getSystemService(Context.INPUT_METHOD_SERVICE));
		// im.showSoftInput(loginAc.passwordText, 0);
		// loginAc.passwordText.requestFocus();
		// loginAc.loginContent.layout(0, 20, 5000, 5000);

		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

			DisplayMetrics metric = new DisplayMetrics();
			loginAc.getWindowManager().getDefaultDisplay().getMetrics(metric);

			loginAc.mainScroll.layout(0, -200, metric.widthPixels, Float
					.valueOf((360 * metric.density)).intValue());
		}
		return false;
	}
}
