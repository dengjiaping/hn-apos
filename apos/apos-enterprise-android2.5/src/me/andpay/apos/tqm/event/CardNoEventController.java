package me.andpay.apos.tqm.event;

import me.andpay.apos.tqm.activity.TxnQueryConditionActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class CardNoEventController extends AbstractEventController {

	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent motionEvent) {

		TxnQueryConditionActivity purAc = (TxnQueryConditionActivity) activity;

		purAc.getFootLayout().setVisibility(View.VISIBLE);
		purAc.getSolfKeyBoard().showKeyboard((EditText) view);
		InputMethodManager im = ((InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		im.hideSoftInputFromWindow(purAc.getCardNoEv().getWindowToken(), 0);
		purAc.getCardNoEv().requestFocus();
		return true;
	}

	public boolean onFocusChange(Activity activity, FormBean formBean,
			View view, boolean hasFocus) {

		if (!hasFocus) {
			((TxnQueryConditionActivity) activity).getSolfKeyBoard()
					.hideKeyboard();
		}
		return true;
	}
}
