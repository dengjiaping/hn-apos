package me.andpay.apos.tcm.activity;

import java.io.IOException;

import me.andpay.apos.R;
import me.andpay.apos.cmview.Preview;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.tcm.AutoFocusCallback;
import me.andpay.apos.tcm.ViewfinderActivityHandler;
import me.andpay.apos.tcm.event.CameraClickEventController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.util.CameraConfigurationUtils;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author David.zhang
 * 
 */

@ContentView(R.layout.com_fullscreen_camera_layout)
public class ViewfinderActivity extends AposBaseActivity implements
		SurfaceHolder.Callback {

	@InjectView(R.id.fullscreen_camera_txt)
	public TextView hintTextView;

	@InjectView(R.id.fullscreen_camera_framView_framelayout)
	FrameLayout frameLayout;

	public Preview preview;

	private SurfaceView surfaceView;

	@InjectView(R.id.fullscreen_camera_flashlight_img)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = CameraClickEventController.class)
	public ImageView flashLightImageView;

	@InjectView(R.id.fullscreen_cameratake_img)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = CameraClickEventController.class)
	public ImageButton takePhotoButton;

	@InjectView(R.id.fullscreen_cancel_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	public Button cancelButton;

	private Animation mAnimationRight;

	public Camera camera;
	private AutoFocusCallback autoFocusCallback;
	private ViewfinderActivityHandler handler;
	private DisplayMetrics displayMetrics;
	public boolean status;

	public String photoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAnimationRight = AnimationUtils.loadAnimation(getBaseContext(),
				R.anim.rotate_right);
		mAnimationRight.setFillAfter(true);
		hintTextView.setAnimation(mAnimationRight);
		surfaceView = new SurfaceView(getApplicationContext());
		surfaceView.getHolder()
				.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().setSizeFromLayout();
		surfaceView.getHolder().setKeepScreenOn(true);
		surfaceView.getHolder().addCallback(this);
		frameLayout.addView(surfaceView);
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		preview = new Preview(getApplicationContext(), displayMetrics);
		frameLayout.addView(preview);
		status = false;
		autoFocusCallback = new AutoFocusCallback();
	}

	public void sentPhotoToNext(byte[] data) {
		Intent intent = new Intent();
		intent.setClass(this, ShowPictureActivity.class);
		intent.putExtra("photo", data);
		startActivity(intent);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (camera == null)
			camera = Camera.open();
		try {
			camera.setDisplayOrientation(90);
			camera.setPreviewDisplay(holder);
			camera.startPreview();
			initCamera();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if (camera != null) {
			autoFocusCallback.setHandler(null, 0);
			camera.stopPreview();
			camera.release();
			camera = null;
			flashLightImageView.setImageResource(R.drawable.camera_light_img);
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

	}

	public void requestAutoFocus(Handler handler, int message) {
		if (camera != null) {
			autoFocusCallback.setHandler(handler, message);
			camera.autoFocus(autoFocusCallback);
		}
	}

	private void initCamera() {
		Parameters parameters = camera.getParameters();
		// parameters.setPictureFormat(parameters.getSupportedPictureFormats()
		// .get(0));
		parameters.getSupportedPreviewSizes();
		Point point = CameraConfigurationUtils.findBestPreviewSizeValue(
				parameters, new Point(displayMetrics.heightPixels,
						displayMetrics.widthPixels));
		parameters.setPreviewSize(point.x, point.y);
		CameraConfigurationUtils.setBestPreviewFPS(parameters);
		// parameters.setPreviewFpsRange(parameters.getSupportedPreviewFpsRange()
		// .get(0)[0], parameters.getSupportedPreviewFpsRange().get(0)[1]);
		// // parameters.setPictureSize(PIC_WIDTH, PIC_HEIGHT);
		// parameters.setJpegQuality(80);
		camera.setParameters(parameters);
		if (handler == null) {
			handler = new ViewfinderActivityHandler(this);
		}
		requestAutoFocus(handler, R.id.auto_focus);
	}

}
