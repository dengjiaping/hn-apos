package me.andpay.apos.cmview;

import me.andpay.apos.R;
import me.andpay.ti.util.StringUtil;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * 公用dialog
 * 
 * @author tinyliu
 * 
 */
public class OperationDialog {

	private Dialog dialog = null;

	private Button sureButton = null;

	private Button cancelButton = null;
	


	public OperationDialog(Context context, String title, String promptMsg) {
		this(context, title, promptMsg, false);
	}
	

	public OperationDialog(Context context, String title, String promptMsg,
			boolean isSureleft) {
		this(context, title, promptMsg, null, null, isSureleft);
	}


	public OperationDialog(Context context, String title, String promptMsg,
			String sureButtonStr, String cancelButtonStr, boolean isSureleft) {
		dialog = new Dialog(context, R.style.Theme_dialog_style);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (isSureleft) {
			dialog.setContentView(R.layout.com_operation_dialog_sureleft_layout);
		} else {
			dialog.setContentView(R.layout.com_operation_dialog_layout);
		}
		sureButton = (Button) dialog.findViewById(R.id.com_sure_btn);
		cancelButton = (Button) dialog.findViewById(R.id.com_cancel_btn);
		if (!StringUtil.isEmpty(sureButtonStr)) {
			sureButton.setText(sureButtonStr);
		}
		if (!StringUtil.isEmpty(cancelButtonStr)) {
			cancelButton.setText(cancelButtonStr);
		}
		if (!StringUtil.isEmpty(title)) {
			((TextView) dialog.findViewById(R.id.com_prompt_dialog_title_tv))
					.setText(title);
		} else {
			((TextView) dialog.findViewById(R.id.com_prompt_dialog_title_tv))
					.setVisibility(View.GONE);
		}
		if (!StringUtil.isEmpty(promptMsg)) {
			((TextView) dialog.findViewById(R.id.com_prompt_dialog_tv))
					.setText(promptMsg);
		} else {
			((TextView) dialog.findViewById(R.id.com_prompt_dialog_tv))
					.setVisibility(View.GONE);
		}
		this.setCancelButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();

			}
		});
	}

	public void setSureButtonOnclickListener(OnClickListener onclickListener) {
		sureButton.setOnClickListener(onclickListener);
	}

	public void setCancelButtonOnclickListener(OnClickListener onclickListener) {
		cancelButton.setOnClickListener(onclickListener);
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
	}

	public Dialog getDialog() {
		return this.dialog;
	}
	
	public void setSureButtonName(String buttonName) {
		sureButton.setText(buttonName);
	}
	
	
	public void setCancelButtonName(String buttonName) {
		cancelButton.setText(buttonName);
		
	}

	public void setCancelable(boolean flag) {
		dialog.setCancelable(flag);
	}
	
	
}
