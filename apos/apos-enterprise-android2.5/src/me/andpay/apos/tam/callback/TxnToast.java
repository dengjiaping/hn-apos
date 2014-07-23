package me.andpay.apos.tam.callback;

import me.andpay.apos.R;
import android.app.Activity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class TxnToast {
	
	
	public static void showSuccess(Activity activity) {

		Toast toast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		toastView.setBackgroundColor(activity.getResources().getColor(
				R.color.com_translucent_col));
		ImageView imageCodeProject = new ImageView(activity);
		imageCodeProject.setImageResource(R.drawable.com_tips_succeed_img);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		toastView.addView(imageCodeProject, params);
		toast.show();
	}
	
	
	public static void showFailed(Activity activity) {

		Toast toast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		toastView.setBackgroundColor(activity.getResources().getColor(
				R.color.com_translucent_col));
		ImageView imageCodeProject = new ImageView(activity);
		imageCodeProject.setImageResource(R.drawable.com_tips_unsuccessfully_img);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		toastView.addView(imageCodeProject, params);
		toast.show();
	}
}
