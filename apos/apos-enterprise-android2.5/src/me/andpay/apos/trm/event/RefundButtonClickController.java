package me.andpay.apos.trm.event;

import me.andpay.apos.R;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class RefundButtonClickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		
		final TiActivity tiActivity = (TiActivity) refActivty;
		final Dialog pwdDialog = new Dialog(refActivty,
				R.style.Theme_dialog_style);

		LoginUserInfo info = (LoginUserInfo) tiActivity.getAppContext()
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		pwdDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		pwdDialog.setContentView(R.layout.trm_refund_dialog_password_layout);
		TextView userid = (TextView) pwdDialog
				.findViewById(R.id.trm_refund_dialog_userid_tv);
		userid.setText(info.getUserName());
		pwdDialog.findViewById(R.id.trm_txn_refund_sure_btn)
				.setOnClickListener(
						new RefundPwdDialogSureClick(pwdDialog, tiActivity));
		pwdDialog.findViewById(R.id.trm_txn_refund_cancel_btn)
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						pwdDialog.dismiss();
					}
				});
		
		EditText editText = (EditText)pwdDialog.findViewById(R.id.trm_refund_dialog_pwd_ev);
		
		
		pwdDialog.show();
		editText.requestFocus();
		InputMethodManager inputMethodManager = ((InputMethodManager)tiActivity.getSystemService(Context.INPUT_METHOD_SERVICE));
		inputMethodManager.showSoftInput(editText, 0);
	}

//	public Dialog createRefundDialog(Activity refActivty) {
//		// final Dialog dialog = new Dialog(refActivty,
//		// R.style.Theme_dialog_style);
//		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		// dialog.setContentView(R.layout.trm_refund_dialog_layout);
//		// //
//		// dialog.findViewById(R.id.trm_txn_refund_sure_btn).setOnClickListener(
//		// // new RefundDialogSureClick((TiActivity) refActivty, dialog));
//		//
//		// dialog.findViewById(R.id.trm_txn_refund_sure_btn).setOnClickListener(
//		// new RefundDialogSureClick((TiActivity) refActivty, dialog));
//		// dialog.findViewById(R.id.trm_txn_refund_cancel_btn).setOnClickListener(
//		// new OnClickListener() {
//		//
//		// public void onClick(View v) {
//		// dialog.dismiss();
//		// }
//		// });
//		// return dialog;
//
//	
//	}
}
