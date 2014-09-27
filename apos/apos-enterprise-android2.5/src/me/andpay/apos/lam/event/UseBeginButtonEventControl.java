package me.andpay.apos.lam.event;

import me.andpay.apos.lam.LamProvider;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class UseBeginButtonEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		// 显示登录页面
		Intent intent = new Intent();
		intent.setAction(LamProvider.LAM_LOGIN_ACTIVITY);
		activity.startActivity(intent);
		activity.finish();
	}

}
