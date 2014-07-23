package me.andpay.apos.common.event;

import me.andpay.apos.common.activity.HomePageActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class ShowSliderControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		HomePageActivity homeActivity = (HomePageActivity) activity.getParent();
		homeActivity.showSlider();
	}
}
