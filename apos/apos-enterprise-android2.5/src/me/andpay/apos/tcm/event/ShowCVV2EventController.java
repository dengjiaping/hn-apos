package me.andpay.apos.tcm.event;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import me.andpay.apos.tcm.activity.ShowCVV2Activity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;

/**
 * 
 * @author David.zhang
 *
 */
public class ShowCVV2EventController extends AbstractEventController {
	public void onClick(Activity activity, FormBean formBean, View view) {
		Intent showImageIntent = new Intent(activity, ShowCVV2Activity.class);
		activity.startActivity(showImageIntent);
	}
}
