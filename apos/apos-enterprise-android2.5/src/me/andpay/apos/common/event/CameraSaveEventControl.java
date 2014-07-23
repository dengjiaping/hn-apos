package me.andpay.apos.common.event;

import me.andpay.apos.common.activity.AposCameraActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class CameraSaveEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		AposCameraActivity cameraActivity = (AposCameraActivity) activity;

		Intent resultItent = new Intent();
		resultItent.putExtra("picturePath", cameraActivity.picturePath);
		cameraActivity.setResult(Activity.RESULT_OK, resultItent);
		cameraActivity.finish();
	}

}
