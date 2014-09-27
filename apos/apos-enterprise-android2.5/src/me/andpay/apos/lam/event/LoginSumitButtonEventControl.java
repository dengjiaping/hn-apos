package me.andpay.apos.lam.event;

import me.andpay.apos.common.util.ValidateHelper;
import me.andpay.apos.lam.activity.LoginActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;

/**
 * 登录事件处理
 * 
 * @author cpz
 * 
 */
public class LoginSumitButtonEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		LoginActivity loginActivity = (LoginActivity) activity;
		login(formBean, loginActivity);
	}

	/**
	 * 登录
	 */
	private void login(FormBean formBean, LoginActivity activity) {

		if (ValidateHelper.validate(activity, formBean, null)) {
			return;
		}
		activity.submitLogin(formBean);
	}

	public boolean onKey(Activity activity, FormBean formBean, View v,
			int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_ENTER
				&& KeyEvent.ACTION_UP == event.getAction()) {
			LoginActivity loginActivity = (LoginActivity) activity;
			login(formBean, loginActivity);
		}
		return false;
	}

}
