package me.andpay.apos.vas.event;

import me.andpay.apos.vas.activity.PurchaseQueryCondActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class AmtEventControl extends AbstractEventController {

	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent motionEvent) {

		PurchaseQueryCondActivity purAc = (PurchaseQueryCondActivity) activity;

		purAc.getFootLayout().setVisibility(View.VISIBLE);
		purAc.getSolfKeyBoard().showKeyboard((EditText) view);
		InputMethodManager im = ((InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		im.hideSoftInputFromWindow(purAc.getAmtEditText().getWindowToken(), 0);
		purAc.getAmtEditText().requestFocus();
		return true;
	}

	public boolean onFocusChange(Activity activity, FormBean formBean, View view,
			boolean hasFocus) {

		if (!hasFocus) {
			((PurchaseQueryCondActivity) activity).getSolfKeyBoard().hideKeyboard();
		}
		return true;
	}
}