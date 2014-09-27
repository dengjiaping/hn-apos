package me.andpay.apos.tcm.activity;

import java.io.File;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.tcm.ViewfinderManager;
import me.andpay.apos.tcm.event.UsePhotoEventController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.util.BitMapUtil;
import me.andpay.timobileframework.util.FileUtil;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

@ContentView(R.layout.com_fullscreen_showphoto_layout)
public class ShowPictureActivity extends AposBaseActivity {

	@InjectView(R.id.fullscreen_show_photo_img)
	public ImageView showPhotoView;

	@InjectView(R.id.fullscreen_unuse_photo_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	public Button cancelButton;

	@InjectView(R.id.fullscreen_use_photo_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = UsePhotoEventController.class)
	public Button useButton;

	public DisplayMetrics displayMetrics;

	private static int PIC_WIDTH = 1024;
	private static int PIC_HEIGHT = 768;

	private static int STANDARD_WIDTH = 480;
	private static int STANDARD_HEIGHT = 360;

	public String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		Intent intent = this.getIntent();
		if (intent != null && intent.hasExtra("photo_path")) {
			String photoPath = intent.getStringExtra("photo_path");
			Bitmap bitMap = BitMapUtil.getBitmap(photoPath, PIC_WIDTH,
					PIC_HEIGHT);
			Bitmap cutBitmap = transformBitmap(bitMap);
			ViewfinderManager.bitMapRecycle(bitMap);
			String[] strs = photoPath.split(File.separator);
			filePath = FileUtil.bitMapSaveFile(cutBitmap,
					this.getApplicationContext(), strs[strs.length - 1], 95);
		}
	}

	public Bitmap transformBitmap(Bitmap sourceBitmap) {
		sourceBitmap.getWidth();
		sourceBitmap.getHeight();
		Rect tartgetRect = ViewfinderManager.getCrossFramingRect(
				displayMetrics, sourceBitmap.getWidth(),
				sourceBitmap.getHeight());
		float scaleWidth = ((float) STANDARD_WIDTH) / tartgetRect.width();
		float scaleHeight = ((float) STANDARD_HEIGHT) / tartgetRect.height();
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap cutBitmap = Bitmap.createBitmap(sourceBitmap, tartgetRect.left,
				tartgetRect.top, tartgetRect.width(), tartgetRect.height(),
				matrix, true);
		showPhotoView.setImageBitmap(cutBitmap);
		return cutBitmap;
	}
}
