package me.andpay.apos.common.event;

import me.andpay.apos.common.activity.HomePageActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class HomeTabEventControl extends AbstractEventController {

		public void onClick(Activity activity, FormBean formBean, View view) {
			HomePageActivity tabActivity = (HomePageActivity) activity;
//			tabActivity.changeView(view,false);
		}
}
