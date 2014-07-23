package me.andpay.apos.tcm.event;

import me.andpay.apos.R;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tcm.activity.PhotoChallengeActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class OpenViewfinderEventController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		PhotoChallengeActivity photoActivity = (PhotoChallengeActivity) activity;
		switch (view.getId()) {
		case R.id.tcm_photochallenge_before_shoot_layout:
		case R.id.tcm_photochallenge_retake_photo_btn:
			TiFlowControlImpl.instanceControl().nextSetup(photoActivity,
					FlowConstants.SUCCESS);
			break;
		case R.id.tcm_photochallenge_next_btn:
			TiFlowControlImpl.instanceControl().nextSetup(photoActivity,
					FlowConstants.FINISH);
			break;
		default:
			break;
		}

		// Intent viewfinderIntent = new Intent(activity,
		// ViewfinderActivity.class);
		// activity.startActivity(viewfinderIntent);
	}
}
