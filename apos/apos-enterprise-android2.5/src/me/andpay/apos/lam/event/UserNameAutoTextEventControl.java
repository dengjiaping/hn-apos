package me.andpay.apos.lam.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

public class UserNameAutoTextEventControl extends AbstractEventController {

	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent motionEvent) {
		//
		// LoginActivity loginAc = (LoginActivity) activity;
		//
		// loginAc.getLoginFoot().setVisibility(View.VISIBLE);
		// // loginAc.getSolfKeyBoard().showKeyboard((EditText) view);
		// InputMethodManager im = ((InputMethodManager) activity
		// .getSystemService(Activity.INPUT_METHOD_SERVICE));
		// im.hideSoftInputFromWindow(loginAc.getUserNameText().getWindowToken(),
		// 0);
		// //loginAc.getSolfKeyBoard().showPasswordEdit(loginAc);
		// loginAc.getUserNameText().requestFocus();

		return true;
	}

	// public void onFocusChange(Activity activity, FormBean formBean, View v,
	// boolean hasFocus) {
	// if (!hasFocus) {
	// ((LoginActivity) activity).solfKeyBoard.hideKeyboard();
	// }
	//
	// }
}
