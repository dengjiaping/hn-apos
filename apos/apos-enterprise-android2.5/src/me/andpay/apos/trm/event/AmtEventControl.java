package me.andpay.apos.trm.event;

import me.andpay.apos.trm.activity.RefundInputActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class AmtEventControl extends AbstractEventController {

	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent motionEvent) {

		RefundInputActivity ac = (RefundInputActivity) activity;

		// ac.getFootLayout().setVisibility(View.VISIBLE);
		// ac.getSolfKeyBoard().showKeyboard((EditText)view);
		InputMethodManager im = ((InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		im.hideSoftInputFromWindow(ac.getAmtEditText().getWindowToken(), 0);
		ac.getAmtEditText().requestFocus();
		return true;
	}

	public boolean onFocusChange(Activity activity, FormBean formBean, View view,
			boolean hasFocus) {

		/*
		 * if(!hasFocus ) {
		 * ((RefundInputActivity)activity).getSolfKeyBoard().hideKeyboard(); }
		 */
		return true;
	}

}
