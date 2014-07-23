package me.andpay.apos.tam.event;

import me.andpay.apos.R;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;
import android.text.Selection;
import android.view.View;

public class PasswordEditWatcherEventControl extends AbstractEventController {

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {

		TxnAcitivty txnAcitivty = (TxnAcitivty) activity;
		int length = txnAcitivty.pwdTextView.length();
		if (length > 0) {
			txnAcitivty.pwdTextHint.setVisibility(View.GONE);
		} else {
			txnAcitivty.pwdTextHint.setVisibility(View.VISIBLE);
		}

		if (length == 0 || length == 4 || length == 6) {
			txnAcitivty.solfKeyBoard.getSure_btn().setEnabled(true);
			txnAcitivty.solfKeyBoard.getSure_btn().setBackgroundDrawable(
					activity.getResources().getDrawable(
							R.drawable.com_keyboard_button_blue_img));
		} else {
			txnAcitivty.solfKeyBoard.getSure_btn().setEnabled(false);
			txnAcitivty.solfKeyBoard.getSure_btn().setBackgroundDrawable(
					activity.getResources().getDrawable(
							R.drawable.com_keyboard_button_img));
		}
		
		txnAcitivty.pwdTextHint.requestFocus();
		Selection.setSelection(txnAcitivty.pwdTextHint.getEditableText(),0);


	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
