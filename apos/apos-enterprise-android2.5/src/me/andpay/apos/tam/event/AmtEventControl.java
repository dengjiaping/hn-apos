package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.PurchaseFirstActivity;
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



		return true;

	}

	public boolean onFocusChange(Activity activity, FormBean formBean,
			View view, boolean hasFocus) {

		final PurchaseFirstActivity purAc = (PurchaseFirstActivity) activity;
		if (!hasFocus) {
			((PurchaseFirstActivity) activity).solfKeyBoard.hideKeyboard();
		} else {

			showKeyBoard(purAc);

		}
		return true;
	}

	private void showKeyBoard(PurchaseFirstActivity purAc) {

		InputMethodManager im = ((InputMethodManager) purAc
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		im.hideSoftInputFromWindow(purAc.amtEditText.getWindowToken(), 0);

		purAc.amtChangeEvent();

	}

}
