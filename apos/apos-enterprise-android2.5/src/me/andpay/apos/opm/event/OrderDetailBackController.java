package me.andpay.apos.opm.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class OrderDetailBackController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		refActivty.finish();
	}

}
