package me.andpay.apos.common.event;

import me.andpay.apos.common.activity.AposCameraActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class CameraCancelEventControl extends AbstractEventController {
	
	
	public void onClick(Activity activity, FormBean formBean, View view) {
		AposCameraActivity cameraActivity = (AposCameraActivity)activity;
		cameraActivity.finish();
		
	}
}
