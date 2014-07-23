package me.andpay.apos.opm.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.opm.action.QueryOrderAction;
import me.andpay.apos.opm.activity.InputOrderNoActivity;
import me.andpay.apos.opm.callback.impl.InquiryOrderCallbackImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class QueryOrderEventControl extends AbstractEventController {
	
	public void onClick(Activity activity, FormBean formBean, View view) {
			
		InputOrderNoActivity inputOrderNoActivity = (InputOrderNoActivity)activity;
		inputOrderNoActivity.queryOrder();
		
		
	}
	
	
}
