package me.andpay.apos.common.activity;

import java.io.IOException;

import me.andpay.apos.R;
import me.andpay.apos.common.event.CameraCancelEventControl;
import me.andpay.apos.common.event.CameraSaveEventControl;
import me.andpay.apos.common.event.CaptureVcEventControl;
import me.andpay.apos.common.event.ReCaptureEventControl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

@ContentView(R.layout.com_camera_layout)
public class AposCameraActivity extends AposBaseActivity {
	
	
	
	@InjectView(R.id.com_camera_preview)
	public FrameLayout preView;  
	
	@InjectView(R.id.com_button_capture)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CaptureVcEventControl.class)
	public Button captureBtn; 
	
	
	@InjectView(R.id.com_button_cancel)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CameraCancelEventControl.class)
	public Button cancelBtn; 
	
	
	@InjectView(R.id.com_savepic_lay)
	public RelativeLayout savepicLay; 
	
	@InjectView(R.id.com_camera_lay)
	public RelativeLayout cameraLay; 
	
	@InjectView(R.id.com_save_button)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CameraSaveEventControl.class)
	public Button saveBtn; 
	
	@InjectView(R.id.com_recapture_button)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ReCaptureEventControl.class)
	public Button recaptureBtn; 

	
	public Camera camera; 
	public CameraPreview mPreview;
	public String picturePath;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
//	@Override
//	protected void onResumeProcess() {
//		super.onResumeProcess();
//
//	}
//	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		camera.release();
	}
	
	
	
	
	@Override
	protected void onResumeProcess() {
		// TODO Auto-generated method stub
		super.onResumeProcess();
		if(camera == null) {
			camera = getCameraInstance();
		}else {
			try {
				camera.reconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mPreview = new CameraPreview(this, camera);
		preView.removeAllViews();
        preView.addView(mPreview);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
	}




	/** A safe way to get an instance of the Camera object. */
	public  Camera getCameraInstance(){
		
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	    	Log.e(this.getClass().getName(), "camera error", e);
	    }
	    return c; // returns null if camera is unavailable
	}
	

	

}
