package me.andpay.apos.tcm;

import me.andpay.apos.R;
import me.andpay.apos.tcm.activity.ViewfinderActivity;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * 
 * @author David.zhang
 */
public final class ViewfinderActivityHandler extends Handler {

	private final ViewfinderActivity activity;

	public ViewfinderActivityHandler(ViewfinderActivity activity) {
		this.activity = activity;
	}

	@Override
	public void handleMessage(Message message) {
		switch (message.what) {
		case R.id.auto_focus:
			activity.requestAutoFocus(this, R.id.auto_focus);
			break;
		default:
			break;
		}
	}

}
