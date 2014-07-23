package me.andpay.apos.common.event;

import me.andpay.apos.common.activity.AposQRActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class QRScanBackEventControl extends AbstractEventController {
	
	public void onClick(Activity activity, FormBean formBean, View view) {
		AposQRActivity refActivity = (AposQRActivity)activity;
		refActivity.finishSelf();
	}

}
