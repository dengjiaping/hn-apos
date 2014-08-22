package me.andpay.apos.lam.callback.impl;


import me.andpay.ac.term.api.auth.LoginResponse;
import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.update.DefaultUpdateCallback;
import me.andpay.apos.common.update.UpdateManager;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.lam.activity.LoginActivity;
import me.andpay.apos.lam.callback.LoginCallback;
import me.andpay.timobileframework.bugsense.ThrowableInfo;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.DateUtil;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 登录回调
 * 
 * @author cpz
 * 
 */
@CallBackHandler
public class LoginCallbackImpl implements LoginCallback {

	protected LoginActivity activity;

	public LoginCallbackImpl(LoginActivity activity) {
		this.activity = activity;
	}
	
	public void clear() {
		if(activity.loginDialog != null && activity.loginDialog.isShowing()) {
			activity.loginDialog.cancel();
		}
	}

	public void loginSuccess(LoginResponse response) {
		try {
			clear();

			LoginCallBackHelper.configImagetCache(activity);
			if (LoginCallBackHelper.recoverTxn(response, activity, true)) {
				return;
			}
			if (LoginCallBackHelper.passwordExpire(response, activity)) {
				return;
			}

		} catch (Exception e) {
		
			loginFaild(ResourceUtil.getString(activity, R.string.tam_syserror_str));
			return;
		}
		activity.getAppConfig().setAttribute(ConfigAttrNames.AUTOLOGIN_START_TIME, ""+DateUtil.getToday());
		LoginCallBackHelper.nextPage(activity);
	}

	/**
	 * 登录失败
	 */
	public void loginFaild(String errorCode) {
		clear();
		// 失败对话框
		PromptDialog dialog = new PromptDialog(this.activity, this.activity
				.getResources().getString(R.string.lam_login_faild_str), errorCode);
		dialog.show();

	}

	public void updateApp(String errorMsg) {
		clear();
		// 失败对话框
		final PromptDialog dialog = new PromptDialog(this.activity, this.activity
				.getResources().getString(R.string.lam_login_faild_str), errorMsg);

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
				pDialog.setSureButtonOnclickListener(new OnClickListener() {

					public void onClick(View v) {
						System.exit(0);
					}
				});
				pDialog.show();
				return;
			}
			super.checkUpdateCompleted(hasUpdate, updateInfo);
		}

	}

	public void goActivateCert() {
		clear();
	
		TiFlowControlImpl.instanceControl().nextSetup(activity, FlowConstants.START_ACTIVE);

	}

	public void networkError(String errorMsg) {
		loginFaild(errorMsg);
	}

}
