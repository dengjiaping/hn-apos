package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.InputValueCardTxnAmtActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputValueCardTxnAmtEventControl extends AbstractEventController {

	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent motionEvent) {

		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			InputValueCardTxnAmtActivity purAc = (InputValueCardTxnAmtActivity) activity;
			purAc.amtEditText.requestFocus();
			showKeyBoard(purAc);
			return true;
		}

		return true;

	}

	public boolean onFocusChange(Activity activity, FormBean formBean,
			View view, boolean hasFocus) {

		final InputValueCardTxnAmtActivity purAc = (InputValueCardTxnAmtActivity) activity;
		if (!hasFocus) {
			//((PurchaseFirstActivity) activity).solfKeyBoard.hideKeyboard();
		} else {
			showKeyBoard(purAc);

		}
		return true;
	}
	
	private void showKeyBoard(InputValueCardTxnAmtActivity purAc) {
		
		InputMethodManager im = ((InputMethodManager) purAc
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		im.hideSoftInputFromWindow(purAc.amtEditText.getWindowToken(), 0);

		purAc.solfKeyBoard.showKeyboard(purAc.amtEditText);

	}
}
