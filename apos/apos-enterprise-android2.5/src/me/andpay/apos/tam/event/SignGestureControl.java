package me.andpay.apos.tam.event;

import me.andpay.apos.cmview.signature.SignatureView;
import me.andpay.apos.tam.activity.SignActivity;
import me.andpay.apos.tam.flow.model.SignContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.MotionEvent;

public class SignGestureControl extends AbstractEventController {

	public void onGestureStarted(Activity activity, FormBean formBean,
			SignatureView signature, MotionEvent event) {
		SignContext signContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(SignContext.class);
		signContext.setGesturesCount(signContext.getGesturesCount() + 1);

	}

	public void onGesture(Activity activity, FormBean formBean,
			SignatureView signature, MotionEvent event) {
		SignContext signContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(SignContext.class);
		signContext.setGesturesLength(signContext.getGesturesLength() + 1);
	}

	public void onGestureEnded(Activity activity, FormBean formBean,
			SignatureView signature, MotionEvent event) {
		SignActivity signActivity = (SignActivity) activity;
		signActivity.clearBtn.setEnabled(true);
		signActivity.nextBtn.setEnabled(true);
	}

	public void onGestureCancelled(Activity activity, FormBean formBean,
			SignatureView signature, MotionEvent event) {
		// count3++;
		// Log.e("gesture error", "gesture count3="+count3);
	}
}
