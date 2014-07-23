package me.andpay.apos.scm.event;

import me.andpay.apos.scm.ScmProvider;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * 显示商户限额
 * @author cpz
 *
 */
public class ShowPartyLimitController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		Intent intent = new Intent();
		intent.setAction(ScmProvider.SCM_ACTIVITY_PARTY_LIMIT);
		activity.startActivity(intent);
	}
		
}
