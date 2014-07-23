package me.andpay.apos.common.event;

import java.io.File;

import me.andpay.apos.common.activity.AposCameraActivity;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class ReCaptureEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		AposCameraActivity cameraActivity = (AposCameraActivity) activity;
		cameraActivity.camera.startPreview();

		cameraActivity.cameraLay.setVisibility(View.VISIBLE);
		cameraActivity.savepicLay.setVisibility(View.GONE);

		if (!StringUtil.isBlank(cameraActivity.picturePath)) {
			File file = new File(cameraActivity.picturePath);
			file.delete();
		}

	}
}
