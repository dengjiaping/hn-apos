package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.PurchaseFirstActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

public class PurMainLayoutTouchControl extends AbstractEventController {

	public boolean onTouch(Activity activity, FormBean formBean, View v,
			MotionEvent event) {

		PurchaseFirstActivity purActivity = (PurchaseFirstActivity) activity;

		return purActivity.mGestureDetector.onTouchEvent(event);
	}
}
