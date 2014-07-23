package me.andpay.apos.tcm;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class ViewfinderManager {
	private static float DISPLAY_WIDTH_PERCENT = 0.8f;
	private static float HEIGHT_WIDTH_RATIO = 1.33f;
	private static float VERTICAL_MOVEMENT_RATIO = 0.03f;
	private static float PADDING_RATIO = 0.01f;

	public static Rect getFramingRect(DisplayMetrics displayMetrics) {
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		int moveHeight = (int) (VERTICAL_MOVEMENT_RATIO * height);
		int midWidth = width / 2;
		int midHeight = height / 2;
		int rectWidth = (int) (width * DISPLAY_WIDTH_PERCENT);
		int rectHeight = (int) (rectWidth * HEIGHT_WIDTH_RATIO);
		return new Rect(midWidth - rectWidth / 2, midHeight - rectHeight / 2
				- moveHeight, midWidth + rectWidth / 2, midHeight + rectHeight
				/ 2 - moveHeight);
	}

	public static Rect getCrossFramingRect(DisplayMetrics displayMetrics,
			int width, int height) {
		int midWidth = width / 2;
		int midHeight = height / 2;
		float ratio = ((float) width / height)
				/ ((float) displayMetrics.heightPixels / displayMetrics.widthPixels);
		float heightPercent = ratio * DISPLAY_WIDTH_PERCENT;
		float movePercent = ratio * VERTICAL_MOVEMENT_RATIO;
		int moveWidth = (int) (movePercent * width);
		int padding = (int) (PADDING_RATIO * width);
		int rectHeight = (int) (height * heightPercent);
		int rectWidth = (int) (rectHeight * HEIGHT_WIDTH_RATIO);
		return new Rect(midWidth - rectWidth / 2 - moveWidth - padding,
				midHeight - rectHeight / 2, midWidth + rectWidth / 2, midHeight
						+ rectHeight / 2 - moveWidth + padding);
	}

	public static Bitmap bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	public static void bitMapRecycle(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}
}
