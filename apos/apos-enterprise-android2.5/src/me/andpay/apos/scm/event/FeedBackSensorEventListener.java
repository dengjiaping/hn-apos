package me.andpay.apos.scm.event;

import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.scm.ScmProvider;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.support.TiApplication;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import me.andpay.timobileframework.util.FileUtil;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Vibrator;
import android.view.View;

import com.google.inject.Inject;

public class FeedBackSensorEventListener implements SensorEventListener {

	@Inject
	private Application application;

	private Vibrator mVibrator;

	private boolean init;

	private TiContext tiContext;

	private void initData() {
		if (init) {
			return;
		}
		mVibrator = (Vibrator) application
				.getSystemService(Context.VIBRATOR_SERVICE);
		tiContext = ((TiApplication) application).getContextProvider()
				.provider(TiContext.CONTEXT_SCOPE_APPLICATION);
		init = true;
	}

	public void onSensorChanged(SensorEvent event) {

		int sensorType = event.sensor.getType();
		float[] values = event.values;
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			if (Math.abs(values[0]) > 14 || Math.abs(values[1]) > 14
					|| Math.abs(values[2]) > 14) {
				initData();
				Object feedBack = tiContext
						.getAttribute(RuntimeAttrNames.IS_BACKFEED);
				if (feedBack != null) {
					return;
				}

				mVibrator.vibrate(100);

				Bitmap bitMap = takeScreenShot(TiAndroidRuntimeInfo
						.getCurrentActivity());
				String filePath = FileUtil.bitMapSaveFile(bitMap, application,
						FileUtil.getMyUUID() + "screen.jpg");
				if (!bitMap.isRecycled()) {
					bitMap.recycle();
				}

				Intent intent = new Intent();
				tiContext.setAttribute(RuntimeAttrNames.IS_BACKFEED,
						RuntimeAttrNames.IS_BACKFEED);
				intent.setAction(ScmProvider.SCM_ALERET_FEEDBACK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(ScmProvider.SCREEN_FILE_PATH, filePath);
				application.startActivity(intent);
			}
		}

	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	private static Bitmap takeScreenShot(Activity mActivity) {
		// View是你需要截图的View

		Activity activityParent = getParent(mActivity);

		View view = activityParent.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitMap = view.getDrawingCache();
		// //获取状态栏高度
		Rect frame = new Rect();
		activityParent.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		// System.out.println(statusBarHeight);
		// //获取屏幕长和高
		// int width =
		// activity.getWindowManager().getDefaultDisplay().getWidth();
		// int height =
		// activity.getWindowManager().getDefaultDisplay().getHeight();
		// //去掉标题栏
		// //Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(bitMap, 0, statusBarHeight,
				bitMap.getWidth(), bitMap.getHeight() - statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

	private static Activity getParent(Activity mActivity) {

		Activity activityParent = mActivity.getParent();
		if (activityParent == null) {
			return mActivity;
		}

		return getParent(activityParent);
	}


}
