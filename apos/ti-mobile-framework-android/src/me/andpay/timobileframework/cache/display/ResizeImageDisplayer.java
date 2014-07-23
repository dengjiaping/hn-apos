package me.andpay.timobileframework.cache.display;

import me.andpay.timobileframework.util.BitMapUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

/**
 * 按照申请的比例压缩图片
 * 
 * @author tinyliu
 * 
 */
public class ResizeImageDisplayer implements ImageDisplayer {

	private ImageView imageView;

	private Drawable loadingResId = null;

	private Drawable loadFailResId = null;

	private Bitmap currentBitMap = null;

	public ResizeImageDisplayer(ImageView view, Drawable loadingResId,
			Drawable loadFailResId) {
		this.imageView = view;
		this.loadingResId = loadingResId;
		this.loadFailResId = loadFailResId;
	}

	public void displayLoadingImage() {
		if (loadingResId != null) {
			imageView.setImageDrawable(loadingResId);
		}
	}

	public void displayImage(byte[] imageBuffer) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(imageBuffer, 0, imageBuffer.length,
				options);
		options.inSampleSize = BitMapUtil.calculateInSampleSize(options,
				imageView.getLayoutParams().width,
				imageView.getLayoutParams().height);
		options.inJustDecodeBounds = false;
		currentBitMap = BitmapFactory.decodeByteArray(imageBuffer, 0,
				imageBuffer.length, options);
		imageView.setImageBitmap(currentBitMap);
	}

	public void displayLoadFailedImage() {
		if (loadFailResId != null) {
			imageView.setImageDrawable(loadFailResId);
		}
	}

	public Bitmap getCurrentBitmap() {
		return currentBitMap;
	}

	public void destory() {
		if (currentBitMap != null && !currentBitMap.isRecycled()) {
			currentBitMap.recycle();
			currentBitMap = null;
		}

	}

}
