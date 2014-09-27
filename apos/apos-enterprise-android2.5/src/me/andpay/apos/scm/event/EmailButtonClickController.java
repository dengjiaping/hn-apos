package me.andpay.apos.scm.event;

import me.andpay.apos.R;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class EmailButtonClickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		Intent email = new Intent(android.content.Intent.ACTION_SEND);
		email.setType("text/plain");
		String[] emailReciver = new String[] { ResourceUtil.getString(
				refActivty, R.string.config_service_email_str) };

		// 设置邮件默认地址
		email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);

		refActivty.startActivity(email);
	}
}