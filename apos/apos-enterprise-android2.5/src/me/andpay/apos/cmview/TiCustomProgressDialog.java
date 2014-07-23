package me.andpay.apos.cmview;

import me.andpay.apos.R;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TiCustomProgressDialog extends Dialog {
	private static TiCustomProgressDialog customProgressDialog = null;

	public TiCustomProgressDialog(Context context) {
		super(context);
	}

	public TiCustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static TiCustomProgressDialog createDialog(Context context) {
		customProgressDialog = new TiCustomProgressDialog(context,
				R.style.CustomProgressDialog);
		customProgressDialog
				.setContentView(R.layout.com_progress_dialog_layout);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {

		if (customProgressDialog == null) {
			return;
		}

		ProgressBar imageView = (ProgressBar) customProgressDialog
				.findViewById(R.id.progressBar1);
	}

	public TiCustomProgressDialog setTitile(String strTitle) {
		return customProgressDialog;
	}

	public TiCustomProgressDialog setMessage(String strMessage) {
		TextView tvMsg = (TextView) customProgressDialog
				.findViewById(R.id.id_tv_loadingmsg);

		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

		return customProgressDialog;
	}
}
