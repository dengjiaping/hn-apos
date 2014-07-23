package me.andpay.apos.ssm.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class MainSettleBackController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		refActivty.finish();

	}
}
