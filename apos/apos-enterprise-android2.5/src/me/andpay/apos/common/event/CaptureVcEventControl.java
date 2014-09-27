package me.andpay.apos.common.event;

import java.io.File;
import java.io.FileOutputStream;

import me.andpay.apos.common.activity.AposCameraActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.BitMapUtil;
import me.andpay.timobileframework.util.FileUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.view.Surface;
import android.view.View;

@SuppressLint("NewApi")
public class CaptureVcEventControl extends AbstractEventController implements
		PictureCallback {

	private AposCameraActivity cameraActivity;

	public void onClick(Activity activity, FormBean formBean, View view) {
		cameraActivity = (AposCameraActivity) activity;
		cameraActivity.camera.takePicture(null, null, this);
		cameraActivity.cameraLay.setVisibility(View.GONE);
		cameraActivity.savepicLay.setVisibility(View.VISIBLE);
	}

	public void onPictureTaken(byte[] data, Camera camera) {

		setCameraDisplayOrientation(cameraActivity, 0, camera);
		String fileName = FileUtil.getMyUUID() + ".jpg";

		File photo = new File(cameraActivity.getFilesDir().getAbsolutePath(),
				fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(photo);
			fos.write(data);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String path = photo.getAbsolutePath();
		// int degree = BitMapUtil.readPictureDegree(path);
		Bitmap bitMap = BitMapUtil.getBitmap(path, 1280, 720);
		bitMap = BitMapUtil.rotate(bitMap, 90);
		cameraActivity.picturePath = FileUtil.bitMapSaveFile(bitMap,
				cameraActivity.getApplicationContext(), fileName);
		camera.stopPreview();

	}

	public static void setCameraDisplayOrientation(Activity activity,
			int cameraId, android.hardware.Camera camera) {
		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else { // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}
}
