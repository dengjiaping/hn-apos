package me.andpay.apos.lam.callback;

import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.update.DefaultUpdateCallback;
import me.andpay.apos.common.update.UpdateManager;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.bugsense.ThrowableInfo;
import me.andpay.timobileframework.util.ReflectUtil;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * 登陆更新callback接口
 * 
 * @author tinyliu
 * 
 */
public class LoginUpdateCallback extends DefaultUpdateCallback {

	public LoginUpdateCallback(AposBaseActivity activity, UpdateManager manager) {
		super(activity, manager);
	}

	@Override
	public void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo) {
		if (!hasUpdate) {
			if (ReflectUtil.isImplInterface(this.activity.getClass(),
					AfterUpdateCallback.class.getName())) {
				((AfterUpdateCallback) this.activity)
						.processAfterCancelUpdateOrNoUpdate();
			}
			return;
		}

		final Dialog dialog = new Dialog(activity, R.style.Theme_dialog_style);
		dialog.setCancelable(false);
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
		DownBtnClick click = new DownBtnClick(dialog);
		click.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				cancelUpdate();
			}
		});
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
						cancelUpdate();
					}
				});
		if (!activity.isFinishing()) {
			dialog.show();
		}
	}

	@Override
	public void processThrowable(ThrowableInfo info) {
		this.checkUpdateCompleted(false, "");
	}

	@Override
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
			DownBtnClick click = new DownBtnClick(redoDialog.getDialog());
			click.setOnCancelListener(new OnCancelListener() {

				public void onCancel(DialogInterface dialog) {
					dialog.dismiss();
					cancelUpdate();
				}
			});
			redoDialog.setSureButtonOnclickListener(click);
			redoDialog.setCancelButtonOnclickListener(new OnClickListener() {

				public void onClick(View v) {
					redoDialog.dismiss();
					cancelUpdate();

				}
			});
			redoDialog.show();
		}

	}

	protected void cancelUpdate() {
		if (ReflectUtil.isImplInterface(
				LoginUpdateCallback.this.activity.getClass(),
				AfterUpdateCallback.class.getName())) {
			((AfterUpdateCallback) LoginUpdateCallback.this.activity)
					.processAfterCancelUpdateOrNoUpdate();
		}
	}
}
