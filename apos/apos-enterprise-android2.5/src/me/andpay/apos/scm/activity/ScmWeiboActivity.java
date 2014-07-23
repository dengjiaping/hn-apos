package me.andpay.apos.scm.activity;

import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.scm.ScmProvider;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class ScmWeiboActivity extends AposBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url = this.getIntent().getStringExtra(ScmProvider.SCM_WEB_ACTIVITY_URL_KEY);
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		intent.setClassName("com.android.browser",
				"com.android.browser.BrowserActivity");
		startActivity(intent);
		this.finish();
	}

}
