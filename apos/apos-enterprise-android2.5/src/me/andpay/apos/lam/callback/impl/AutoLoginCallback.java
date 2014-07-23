package me.andpay.apos.lam.callback.impl;

import me.andpay.ac.term.api.auth.LoginResponse;
import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.update.DefaultUpdateCallback;
import me.andpay.apos.common.update.UpdateManager;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.lam.callback.LoginCallback;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.bugsense.ThrowableInfo;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

@CallBackHandler
public class AutoLoginCallback implements LoginCallback {

	protected TiActivity activity;

	public AutoLoginCallback(TiActivity activity) {
		this.activity = activity;
	}

	public void loginSuccess(LoginResponse response) {
		try {
			LoginCallBackHelper.configImagetCache(activity);
			if (LoginCallBackHelper.recoverTxn(response, activity, false)) {
				return;
			}

			if (LoginCallBackHelper.passwordExpire(response, activity)) {
				return;
			}
			
		

		} catch (Exception e) {
			Log.e("loginError", "login callback error!", e);
			loginFaild(ResourceUtil.getString(activity, R.string.tam_syserror_str));
			return;
		}
		// 直接进入主页
		LoginCallBackHelper.nextPage(activity);
	}

	public void loginFaild(String errorMsg) {
		if(activity.isFinishing()) {
			return;
		}
		final PromptDialog promptDialog = new PromptDialog(activity,
				ResourceUtil.getString(activity, R.string.lam_login_faild_str), errorMsg);
		promptDialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				promptDialog.dismiss();
				TiFlowControlImpl.instanceControl().nextSetup(activity,
						FlowConstants.SUCCESS);
			}
		});
		promptDialog.show();

	}

	public void networkError(String errorMsg) {
		loginFaild(errorMsg);
	}

	public void updateApp(String errorCode) {
		// 失败对话框
		final PromptDialog dialog = new PromptDialog(this.activity, this.activity
				.getResources().getString(R.string.lam_login_faild_str), errorCode);

		dialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				dialogUpdate = new CommonDialog(activity, "检测更新");
				UpdateManager manager = new UpdateManager(activity);
				manager.setCallBack(new ScmUpdateCallback(activity, manager));
				manager.checkUpdate();
				dialogUpdate.show();

			}
		});

		dialog.show();
	}

	public CommonDialog dialogUpdate;

	class ScmUpdateCallback extends DefaultUpdateCallback {

		private TiActivity activity;

		public ScmUpdateCallback(TiActivity activity, UpdateManager manager) {
			super(activity, manager);
			this.activity = activity;
		}
		
		@Override
		public void processThrowable(ThrowableInfo info) {
			dialogUpdate.cancel();
			super.processThrowable(info);
		}

		@Override
		public void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo) {
			dialogUpdate.cancel();
			if (!hasUpdate) {
				PromptDialog pDialog = new PromptDialog(activity, null, "当前版本为测试版本,请等待此版本开通再使用。");
				pDialog.setCancelable(false);
				pDialog.setButtonText("点击退出应用");
				pDialog.show();
				pDialog.setSureButtonOnclickListener(new OnClickListener() {

					public void onClick(View v) {
						System.exit(0);
					}
				});
				return;
			}
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

			((TextView) dialog.findViewById(R.id.com_update_size_tv)).setText(manager
					.getSize());
			((TextView) dialog.findViewById(R.id.com_update_version_tv)).setText(manager
					.getNewVersionName());
			((TextView) dialog.findViewById(R.id.com_update_time_tv)).setText(manager
					.getLastUpdateTime());
			((Button) dialog.findViewById(R.id.com_sure_btn))
					.setOnClickListener(new DownBtnClick(dialog));
			((Button) dialog.findViewById(R.id.com_cancel_btn))
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							dialog.dismiss();
							LoginCallBackHelper.goHome(activity);
						}
					});
			if (!activity.isFinishing()) {
				dialog.show();
			}
		}

	}

	public void goActivateCert() {
		TiFlowControlImpl.instanceControl().nextSetup(activity, FlowConstants.START_ACTIVE);
	}

}
