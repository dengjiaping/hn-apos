package me.andpay.apos.lam.event;

import me.andpay.apos.lam.LamProvider;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class ShowHelpClickControl extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View view) {
		Intent intent = new Intent();
		intent.setAction(LamProvider.LAM_HELP_ACTIVITY);
		refActivty.startActivity(intent);
	}

	public boolean onTouch(Activity refActivty, FormBean formBean, View v,
			MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP
				|| event.getAction() == MotionEvent.ACTION_OUTSIDE
				|| event.getAction() == MotionEvent.ACTION_CANCEL) {

			v.setPressed(false);
			RelativeLayout rel = (RelativeLayout) v;
			for (int i = 0; i < rel.getChildCount(); i++) {
				rel.getChildAt(i).setPressed(false);
			}

			Intent intent = new Intent();
			intent.setAction(LamProvider.LAM_HELP_ACTIVITY);
			refActivty.startActivity(intent);

		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			RelativeLayout rel = (RelativeLayout) v;
			v.setPressed(true);
			for (int i = 0; i < rel.getChildCount(); i++) {
				rel.getChildAt(i).setPressed(true);
			}
		}
		return true;
	}

}
