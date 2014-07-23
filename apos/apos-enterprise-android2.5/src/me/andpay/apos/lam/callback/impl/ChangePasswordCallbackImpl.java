package me.andpay.apos.lam.callback.impl;

import me.andpay.apos.R;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.lam.callback.ChangePasswordCallback;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * 提醒修改密码（初次修改或密码过期修改）
 * 
 * @author cpz
 * 
 */

@CallBackHandler
public class ChangePasswordCallbackImpl implements ChangePasswordCallback {

	private Activity activity;

	public ChangePasswordCallbackImpl(Activity activity, FormBean formBean, View view) {
		this.activity = activity;
	}

	public void changeSuccess() {

		Toast.makeText(activity.getApplicationContext(),
				R.string.lam_change_password_success_str, Toast.LENGTH_LONG).show();
		TiActivity tiActivity = (TiActivity) activity;
		LoginCallBackHelper.nextPage(tiActivity);
	}

	public void changeFaild(String errorMsg) {

		final PromptDialog dialog = new PromptDialog(activity,
				ResourceUtil.getString(activity.getApplicationContext(),
						R.string.lam_change_password_faild_str), errorMsg);
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();

	}

}
