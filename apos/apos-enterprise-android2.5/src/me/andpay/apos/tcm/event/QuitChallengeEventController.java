package me.andpay.apos.tcm.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

/**
 * 
 * @author David.zhang
 *
 */
public class QuitChallengeEventController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		// TODO send cancel trading message to sever
		// TODO back to home page
	}

}
