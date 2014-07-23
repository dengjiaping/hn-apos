package me.andpay.apos.tam.event;

import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class SignBackControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		
		TiFlowControlImpl.instanceControl().previousSetup(activity);
		
	}

}
