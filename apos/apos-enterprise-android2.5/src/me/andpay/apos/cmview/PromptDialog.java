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

public class PromptDialog {

	private Dialog dialog;

	private Button sureButton;

	public PromptDialog(Context context, String title, String promptMsg) {
		dialog = new Dialog(context, R.style.Theme_dialog_style);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.com_prompt_dialog_layout);

		sureButton = (Button) dialog.findViewById(R.id.com_prompt_sure_btn);
		sureButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
			}
		});
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
	}

	public void setSureButtonOnclickListener(OnClickListener onclickListener) {
		sureButton.setOnClickListener(onclickListener);
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
		
	}
	public void setCancelable(boolean flag)  {
		dialog.setCancelable(flag);
	}
	
	public void setButtonText(String text) {
		sureButton.setText(text);
	}
}
