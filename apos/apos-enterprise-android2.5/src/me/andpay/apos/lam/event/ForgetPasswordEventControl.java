package me.andpay.apos.lam.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ForgetPasswordEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		final PromptDialog dialog = new PromptDialog(activity,
				ResourceUtil.getString(activity, R.string.com_show_str),
				ResourceUtil.getString(activity,
						R.string.lam_contect_reset_password_str));
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
}
