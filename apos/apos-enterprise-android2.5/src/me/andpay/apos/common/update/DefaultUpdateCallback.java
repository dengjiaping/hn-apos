package me.andpay.apos.common.update;

import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.bugsense.ThrowableInfo;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 默认下载更新接口
 * 
 * @author tinyliu
 * 
 */
public class DefaultUpdateCallback implements UpdateCallback {

	protected Activity activity;

	protected UpdateManager manager;

	protected ProgressDialog updateProgressDialog;

	protected String downLoadFailedTitle;

	protected String downLoadRedoStr;

	protected String downLoadCancelStr;

	public DefaultUpdateCallback(Activity activity, UpdateManager manager) {
		this.activity = activity;
		this.manager = manager;
		this.downLoadFailedTitle = activity.getResources().getString(
				R.string.com_net_update_error_str);
		this.downLoadRedoStr = activity.getResources().getString(
				R.string.com_net_update_error_redo_str);
		this.downLoadCancelStr = activity.getResources().getString(
				R.string.com_net_update_error_cancel_str);
	}

	public void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo) {
		if (!hasUpdate) {
			return;
		}

		final Dialog dialog = new Dialog(activity, R.style.Theme_dialog_style);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.com_update_dialog_layout);

		TextView updateInfoText = (TextView) dialog
				.findViewById(R.id.com_update_info_tv);
		if (StringUtil.isNotBlank(manager.getUpdateInfo())) {
			updateInfoText.setText(manager.getUpdateInfo());
			updateInfoText.setVisibility(View.VISIBLE);
		} else {
			updateInfoText.setVisibility(View.GONE);

		}

		((TextView) dialog.findViewById(R.id.com_update_size_tv))
				.setText(manager.getSize());
		((TextView) dialog.findViewById(R.id.com_update_version_tv))
				.setText(manager.getNewVersionName());
		((TextView) dialog.findViewById(R.id.com_update_time_tv))
				.setText(manager.getLastUpdateTime());
		((Button) dialog.findViewById(R.id.com_sure_btn))
				.setOnClickListener(new DownBtnClick(dialog));
		((Button) dialog.findViewById(R.id.com_cancel_btn))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						dialog.dismiss();
					}
				});
		if (!activity.isFinishing()) {
			dialog.show();
		}
	}

	public void downloadProgressChanged(int progress) {
		if (updateProgressDialog != null && updateProgressDialog.isShowing()) {
			updateProgressDialog.setProgress(progress);
		}
	}

	public void downloadCanceled() {
		// TODO Auto-generated method stub

	}

	public void downloadCompleted(Boolean sucess, CharSequence errorMsg) {
		if (updateProgressDialog != null && updateProgressDialog.isShowing()) {
			updateProgressDialog.dismiss();
		}
		if (sucess) {
			manager.update();
		} else {
			final OperationDialog redoDialog = new OperationDialog(activity,
					null, downLoadFailedTitle, downLoadRedoStr,
					downLoadCancelStr, false);
			redoDialog.setSureButtonOnclickListener(new DownBtnClick(redoDialog
					.getDialog()));
			redoDialog.show();
		}

	}

	public void processThrowable(ThrowableInfo info) {
		if (ErrorMsgUtil.isNetworkError(info.getEx())) {
			Toast.makeText(activity.getApplicationContext(),
					R.string.com_please_check_network_set_str,
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(activity.getApplicationContext(),
					R.string.tam_syserror_str, Toast.LENGTH_SHORT).show();
		}
	}

	protected class DownBtnClick implements OnClickListener {

		private Dialog preDialog = null;

		private OnCancelListener listener = null;

		public DownBtnClick(Dialog dialog) {
			preDialog = dialog;
		}

		public void setOnCancelListener(OnCancelListener listener) {
			this.listener = listener;
		}

		public void onClick(View v) {
			preDialog.dismiss();
			updateProgressDialog = new ProgressDialog(activity);
			updateProgressDialog
					.setProgressStyle(android.R.style.Widget_ProgressBar);
			updateProgressDialog.setMessage(activity.getResources().getString(
					R.string.com_net_update_str));
			updateProgressDialog.setIndeterminate(false);
			updateProgressDialog
					.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			updateProgressDialog.setMax(100);
			updateProgressDialog.setProgress(0);
			preDialog.setCancelable(false);
			updateProgressDialog.show();
			if (this.listener != null) {
				updateProgressDialog.setOnCancelListener(listener);
			}
			manager.downloadPackage();
		}

	}

}
