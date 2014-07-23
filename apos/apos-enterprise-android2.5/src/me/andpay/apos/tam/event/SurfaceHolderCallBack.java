package me.andpay.apos.tam.event;

import java.io.IOException;

import me.andpay.apos.common.activity.AposCameraActivity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;

public class SurfaceHolderCallBack implements SurfaceHolder.Callback {

	private AposCameraActivity activity;

	public static final int MAX_WIDTH = 200;
	public static final int MAX_HEIGHT = 200;

	public SurfaceHolderCallBack(AposCameraActivity activity) {
		this.activity = activity;
	}

	private boolean mPreviewRunning;

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (mPreviewRunning) {
			activity.camera.stopPreview();
		}
		Parameters params = activity.camera.getParameters();
		params.setPreviewSize(width, height);
		activity.camera.setParameters(params);

		try {
			activity.camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		activity.camera.startPreview();
		mPreviewRunning = true;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		activity.camera = Camera.open(); // 获取activity.camera实例

		// 启动预览功能
		activity.camera.startPreview();

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		activity.camera.stopPreview();
		activity.camera.release();

	}

}
