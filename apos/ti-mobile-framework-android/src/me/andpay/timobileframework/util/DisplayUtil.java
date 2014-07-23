package me.andpay.timobileframework.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class DisplayUtil {

	public static DisplayMetrics getDisplayMetrics(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);
		return displayMetrics;
	}

	public static ViewRect getViewRect(View view) {

		DisplayMetrics displayMetrics = getDisplayMetrics(view.getContext());

		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);

		ViewRect reViewRect = new ViewRect();
		reViewRect.setHeight(view.getMeasuredHeight());
		reViewRect.setWidth(view.getMeasuredWidth());

		return reViewRect;
	}
}
