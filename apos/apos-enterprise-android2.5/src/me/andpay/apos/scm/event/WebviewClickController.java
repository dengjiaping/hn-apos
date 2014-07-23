package me.andpay.apos.scm.event;

import me.andpay.apos.R;
import me.andpay.apos.scm.activity.MdsWebViewActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class WebviewClickController extends AbstractEventController {
	
	
	
	public void onClick(Activity refActivty, FormBean formBean, View v) {
		
		MdsWebViewActivity mdsActivity = (MdsWebViewActivity)refActivty;
		if(v.getId() == R.id.scm_webview_back) {
			mdsActivity.mdsWebView.goBack();
		}else if(v.getId() == R.id.scm_webview_forward) {
			mdsActivity.mdsWebView.goForward();
		}else if(v.getId() == R.id.scm_webview_refresh) {
			mdsActivity.mdsWebView.reload();
		}

	}
}
