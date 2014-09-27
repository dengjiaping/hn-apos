package me.andpay.apos.cmview;

import me.andpay.apos.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastTools {

	public static final int LIST_VIEW_TOAST_HEIGHT = 45;

	private static Toast toast;

	private static TextView tv = null;

	public static void toast(Context context, String msg, String timeTag,
			Integer gravity, int xOffset, int yOffset) {
		int time = Toast.LENGTH_SHORT;
		if (timeTag == null || "l".equals(timeTag)) {
			time = Toast.LENGTH_LONG;
		}
		if (toast == null) {
			toast = Toast.makeText(context, null, time);
			LinearLayout layout = (LinearLayout) toast.getView();
			layout.setBackgroundResource(R.drawable.com_toast_shape);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			tv = new TextView(context);
			tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			tv.setGravity(Gravity.CENTER_VERTICAL);
			tv.setTextColor(Color.parseColor("#ffffffff"));
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			tv.setPadding(0, 0, 0, 0);
			layout.addView(tv);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		tv.setText(msg);
		if (gravity != null)
			toast.setGravity(gravity, 0, 0);
		else
			toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void topToast(Activity activity, String msg, int topHigh) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.com_toast_layout,
				(ViewGroup) activity.findViewById(R.id.com_toast_layout_root));
		TextView textView = (TextView) layout
				.findViewById(R.id.com_toast_textview);
		textView.setText(msg);
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		Toast toast = new Toast(activity);

		toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, Float
				.valueOf((topHigh * metric.density)).intValue());
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	public static void toastMsg(Activity activity, String msg, int topHigh) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.com_toast_layout,
				(ViewGroup) activity.findViewById(R.id.com_toast_layout_root));
		TextView textView = (TextView) layout
				.findViewById(R.id.com_toast_textview);
		textView.setText(msg);
		textView.setBackgroundColor(Color.parseColor("#e34d39"));
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		Toast toast = new Toast(activity);

		toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, Float
				.valueOf((topHigh * metric.density)).intValue());
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

}
