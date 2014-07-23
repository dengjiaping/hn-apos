package me.andpay.apos.cmview;

import me.andpay.apos.R;
import me.andpay.apos.tcm.ViewfinderManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.View;

@SuppressLint("ViewConstructor")
public class Preview extends View {

	private final Paint paint;
	private Bitmap resultBitmap;
	private final int maskColor;
	private final int resultColor;
	private DisplayMetrics displayMetrics;

	public Preview(Context context, DisplayMetrics displayMetrics) {
		super(context);
		this.displayMetrics = displayMetrics;
		paint = new Paint();
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.viewfinder_mask1);
		resultColor = resources.getColor(R.color.transparent);
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		Rect frame = ViewfinderManager.getFramingRect(displayMetrics);
		if (frame == null) {
			return;
		}
		int width = canvas.getWidth();
		int height = canvas.getHeight();

		// Draw the exterior (i.e. outside the framing rect) darkened
		paint.setColor(resultBitmap != null ? resultColor : maskColor);
		canvas.drawRect(0, 0, width, frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
		canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
				paint);
		canvas.drawRect(0, frame.bottom + 1, width, height, paint);

		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.bankcard_focus_img);
		// canvas.drawBitmap(bitmap, frame.left, frame.top, paint);
		RectF dst = new RectF();
		dst.bottom = frame.bottom;
		dst.left = frame.left;
		dst.top = frame.top;
		dst.right = frame.right;
		canvas.drawBitmap(bitmap, null, dst, paint);
	}

}
