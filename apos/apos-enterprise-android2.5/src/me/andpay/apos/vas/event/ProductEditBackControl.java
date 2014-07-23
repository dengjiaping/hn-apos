package me.andpay.apos.vas.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class ProductEditBackControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		activity.finish();
	}
		

}
