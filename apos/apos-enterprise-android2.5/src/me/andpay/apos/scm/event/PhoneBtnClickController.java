package me.andpay.apos.scm.event;

import me.andpay.apos.R;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class PhoneBtnClickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		Intent myIntentDial = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ ResourceUtil.getString(refActivty, R.string.config_service_phonenumber_str)));
		
		refActivty.startActivity(myIntentDial);
		
	}
}
