package me.andpay.apos.ssm.event;

import me.andpay.apos.ssm.activity.SettleMainActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class RefreshSettleController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {

		SettleMainActivity settleMainActivity = (SettleMainActivity) refActivty;
		settleMainActivity.loadUnCheckOutInfo();
	}
}
