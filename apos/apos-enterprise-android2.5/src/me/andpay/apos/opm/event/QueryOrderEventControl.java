package me.andpay.apos.opm.event;

import me.andpay.apos.opm.activity.InputOrderNoActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class QueryOrderEventControl extends AbstractEventController {
	
	public void onClick(Activity activity, FormBean formBean, View view) {
			
		InputOrderNoActivity inputOrderNoActivity = (InputOrderNoActivity)activity;
		inputOrderNoActivity.queryOrder();
		
		
	}
	
	
}
