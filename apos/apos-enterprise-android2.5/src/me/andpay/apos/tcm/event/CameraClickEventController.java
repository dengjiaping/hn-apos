package me.andpay.apos.tcm.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tcm.activity.ViewfinderActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.CameraConfigurationUtils;
import me.andpay.timobileframework.util.FileUtil;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.view.View;

public class CameraClickEventController extends AbstractEventController
		implements PictureCallback {
	private ViewfinderActivity viewfinderActivity;

	public void onClick(Activity activity, FormBean formBean, View view) {
		viewfinderActivity = (ViewfinderActivity) activity;
		switch (view.getId()) {
		case R.id.fullscreen_camera_flashlight_img:
			onClickFlashLightButton();
			break;
		case R.id.fullscreen_cameratake_img:
			onTakePhotoButton();
			break;
		default:
			break;
		}

	}

	private void onTakePhotoButton() {
		viewfinderActivity.camera.takePicture(null, null, this);
	}

	private void onClickFlashLightButton() {
		if (!viewfinderActivity.status) {
			viewfinderActivity.flashLightImageView
					.setImageResource(R.drawable.camera_light_close_img);
			viewfinderActivity.status = true;
			new Thread(new TurnOnLight(viewfinderActivity)).start();
		} else {
			viewfinderActivity.status = false;
			viewfinderActivity.flashLightImageView
					.setImageResource(R.drawable.camera_light_img);
			Parameters params = viewfinderActivity.camera.getParameters();
			CameraConfigurationUtils.setTorch(params, false);
			// params.setFlashMode("off");
			viewfinderActivity.camera.setParameters(params);
		}
	}

	private class TurnOnLight implements Runnable {
		private ViewfinderActivity activity;

		private TurnOnLight(ViewfinderActivity activity) {
			this.activity = activity;
		}

		public void run() {
			Parameters params = activity.camera.getParameters();
			CameraConfigurationUtils.setTorch(params, true);
			// params.setFlashMode("torch");
			activity.camera.setParameters(params);
		}
	}

	public void onPictureTaken(byte[] data, Camera camera) {
		String fileName = FileUtil.getMyUUID() + ".jpg";
		String path = FileUtil.saveCameraData(viewfinderActivity.getFilesDir()
				.getAbsolutePath(), fileName, data);
		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("photo_path", path);
		TiFlowControlImpl.instanceControl().nextSetup(viewfinderActivity,
				FlowConstants.SUCCESS, intentData);
	}
}
