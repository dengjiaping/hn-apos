package me.andpay.apos.tam.event;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import me.andpay.apos.tam.activity.PostVoucherActivity;
import me.andpay.apos.tam.activity.SettlementRulesActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;

public class ShowRulesEventControl extends AbstractEventController {
	public void onClick(Activity activity, FormBean formBean, View view) {
		PostVoucherActivity postVoucherActivity = (PostVoucherActivity) activity;
		final Intent rulesIntent = new Intent(postVoucherActivity,
				SettlementRulesActivity.class);
		postVoucherActivity.startActivity(rulesIntent);
	}
}
