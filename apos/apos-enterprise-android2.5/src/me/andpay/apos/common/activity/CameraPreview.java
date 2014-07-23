package me.andpay.apos.common.activity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;

	@SuppressWarnings("deprecation")
	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// preview.
		try {

			mCamera.setPreviewDisplay(holder);
			// mCamera.startPreview();
		} catch (IOException e) {
			// Log.d(TAG, "Error setting camera preview: " + e.getMessage());
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// empty. Take care of releasing the Camera preview in your activity.
		mCamera.stopPreview();
//		mCamera.release();
		
	}

	@SuppressWarnings("deprecation")
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		try {
			Camera.Parameters parameters = mCamera.getParameters();
//			setParamters(parameters);
//			parameters.setPictureFormat(PixelFormat.JPEG);
//			List<Size> sizes = parameters.getSupportedPreviewSizes();
//			Size optimalSize = getBestPreviewSize(width, height, sizes);
//			parameters.setPreviewSize(optimalSize.width, optimalSize.height);
//			parameters.setPictureSize(optimalSize.width, optimalSize.height);
			mCamera.setParameters(parameters);
		} catch (Exception e) {
			Camera.Parameters parameters = mCamera.getParameters();
			setParamters(parameters);
			mCamera.setParameters(parameters);
		}

		mCamera.startPreview();
	}

	private Camera.Size getBestPreviewSize(int width, int height,
			List<Camera.Size> sizeList) {
		Camera.Size bestSize = null;
		bestSize = sizeList.get(0);

		int mySize = width * height;
		int difSize = 0;

		for (int i = 1; i < sizeList.size(); i++) {
			int thisSize = sizeList.get(i).width * sizeList.get(i).height;
			int thiDifSize = Math.abs(thisSize - mySize);
			if (difSize == 0 || thiDifSize < difSize) {
				difSize = thiDifSize;
				bestSize = sizeList.get(i);
			}
		}

		return bestSize;
	}

	private void setParamters(Camera.Parameters parameters) {
		if (Integer.parseInt(Build.VERSION.SDK) >= 8)
			setDisplayOrientation(mCamera, 90);
		else {
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				parameters.set("orientation", "portrait");
				parameters.set("rotation", 90);
			}
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				parameters.set("orientation", "landscape");
				parameters.set("rotation", 90);
			}
		}
		parameters.setFocusMode("auto");
	}

	protected void setDisplayOrientation(Camera camera, int angle) {
		Method downPolymorphic;
		try {
			downPolymorphic = camera.getClass().getMethod(
					"setDisplayOrientation", new Class[] { int.class });
			if (downPolymorphic != null)
				downPolymorphic.invoke(camera, new Object[] { angle });
		} catch (Exception e1) {
		}
	}

	// 获得 camera 大小
	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.2;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;
		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;
		int targetHeight = h;
		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			Log.d("dd", "Checking size " + size.width + "w " + size.height
					+ "h");
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}
		// Cannot find the one match the aspect ratio, ignore the
		// requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}
}