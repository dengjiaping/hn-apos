package me.andpay.apos.scm.event;

import me.andpay.apos.R;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.scm.ScmProvider;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ShowWeiboClickEventControl extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		String uString = ResourceUtil.getString(refActivty,
				(v.getId() == R.id.com_net_url_tv) ? R.string.config_company_url_str
						: R.string.config_weibo_url_str);
		Intent intent = new Intent(ScmProvider.SCM_ACTIVITY_WEIBO);
		intent.putExtra(ScmProvider.SCM_WEB_ACTIVITY_URL_KEY, uString);
		refActivty.startActivity(intent);
		
	}
}
