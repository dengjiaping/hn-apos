package me.andpay.apos.vas.event;

import me.andpay.apos.cmview.SolfKeyBoardDialog;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.BeanUtils;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;

public class CardNoTextEventController extends AbstractEventController {

	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent motionEvent) {
		ScrollView scrollView = BeanUtils.getFieldValue(activity,
				ScrollView.class, "scrollView");
		SolfKeyBoardDialog solfKeyBoardDialog = BeanUtils.getFieldValue(
				activity, SolfKeyBoardDialog.class, "solfKeyBoardDialog");
		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

			DisplayMetrics metric = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
			if (scrollView != null)
				scrollView.layout(0, -300, metric.widthPixels,
						Float.valueOf((420 * metric.density)).intValue());
		}

		solfKeyBoardDialog.showKeyboard((EditText) view);
		InputMethodManager im = ((InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		im.hideSoftInputFromWindow(view.getWindowToken(), 0);
		view.requestFocus();
		return true;
	}

	public boolean onFocusChange(Activity activity, FormBean formBean,
			View view, boolean hasFocus) {
		SolfKeyBoardDialog solfKeyBoardDialog = BeanUtils.getFieldValue(
				activity, SolfKeyBoardDialog.class, "solfKeyBoardDialog");
		if (solfKeyBoardDialog == null) {
			return true;
		}
		if (!hasFocus) {
			solfKeyBoardDialog.hideKeyboard();
		}
		return true;
	}
}
