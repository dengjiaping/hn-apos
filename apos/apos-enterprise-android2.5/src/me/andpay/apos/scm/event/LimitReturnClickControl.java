package me.andpay.apos.scm.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class LimitReturnClickControl extends AbstractEventController {


	public void onClick(Activity refActivty, FormBean formBean, View v) {
		refActivty.finish();
	}

}
