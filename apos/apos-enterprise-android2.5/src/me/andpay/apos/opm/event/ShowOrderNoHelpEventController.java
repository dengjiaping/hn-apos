package me.andpay.apos.opm.event;

import me.andpay.apos.opm.OpmProvider;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;


public class ShowOrderNoHelpEventController  extends AbstractEventController {
	
	
	public void onClick(Activity activity, FormBean formBean, View view) {
		
		Intent intent = new Intent();
		intent.setAction(OpmProvider.OPM_ORDER_NO_HELP_ACTIVITY);
		activity.startActivity(intent);
		
	}

}
