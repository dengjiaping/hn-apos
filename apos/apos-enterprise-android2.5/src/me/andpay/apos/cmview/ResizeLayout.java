package me.andpay.apos.cmview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ResizeLayout extends RelativeLayout {

	public int oldB = 0;

	public interface OnResizeListener {
		void OnResize(int w, int h, int oldw, int oldh);
	}

	public ResizeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (oldw != 0) {
			return;
		}
		super.onSizeChanged(w, h, oldw, oldh);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		if (oldB == 0) {
			oldB = b;
		}

		if (b == oldB) {
			super.onLayout(changed, l, t, r, b);
		} else {
			// super.onLayout(changed, l, -100, r, 100);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
