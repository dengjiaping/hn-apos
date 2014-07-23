package me.andpay.apos.ssm.event;

import me.andpay.apos.ssm.SsmProvider;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainHistoryController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		Intent homeIntent = new Intent(SsmProvider.SSM_ACTIVITY_INFO_LIST);
		refActivty.startActivity(homeIntent);
	}

}
