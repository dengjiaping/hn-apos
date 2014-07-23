package me.andpay.apos.common.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.app.Activity;
import android.view.View;

public class PreviousClickEventController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		TiActivity activity = (TiActivity) refActivty;
		activity.getControl().previousSetup(activity);
	}
}
