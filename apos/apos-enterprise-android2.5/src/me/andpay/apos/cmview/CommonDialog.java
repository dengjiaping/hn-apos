package me.andpay.apos.cmview;

import android.app.Dialog;
import android.content.Context;

public class CommonDialog {

	private TiCustomProgressDialog dialog;

	public CommonDialog(Context context, String promptMsg) {
		dialog = TiCustomProgressDialog.createDialog(context);
		// 设置ProgressDialog 提示信息
		dialog.setMessage(promptMsg);
		dialog.setCancelable(false);
		
	}
	
	public void setCancelable(boolean flag) {
		dialog.setCancelable(flag);
	}

	public void show() {
		dialog.show();
	}

	public void cancel() {
		dialog.dismiss();
	}

	public Dialog getDialog() {
		return dialog;
	}
	
	public void setMsg(String strMessage) {
		dialog.setMessage(strMessage);
	}
	

	public boolean isShowing() {
		return dialog.isShowing();
	}

}
