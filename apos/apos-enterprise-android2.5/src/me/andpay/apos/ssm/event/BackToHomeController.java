package me.andpay.apos.ssm.event;

import me.andpay.apos.common.CommonProvider;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class BackToHomeController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		Intent homePageIntent = new Intent(CommonProvider.COM_HOMEPAGE_ACTIVITY);
		refActivty.startActivity(homePageIntent);
		refActivty.finish();
	}
}
