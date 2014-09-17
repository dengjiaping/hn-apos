package me.andpay.apos.scm.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.scm.event.WebviewBackClickController;
import me.andpay.apos.scm.event.WebviewClickController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

@ContentView(R.layout.scm_mds_webview_layout)
public class MdsWebViewActivity extends AposBaseActivity {

	@InjectView(R.id.scm_mds_webview)
	public WebView mdsWebView;
	
	@InjectView(R.id.scm_top_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = WebviewBackClickController.class)
	public ImageView backButton;
	
	@InjectView(R.id.scm_webview_back)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = WebviewClickController.class)
	public ImageView webBackButton;
	@InjectView(R.id.scm_webview_forward)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = WebviewClickController.class)
	public ImageView webForwardButton;
	@InjectView(R.id.scm_webview_refresh)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = WebviewClickController.class)
	public ImageView webRefreshButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		webBackButton.setEnabled(false);
		webForwardButton.setEnabled(false);
		mdsWebView.loadUrl("http://www.baidu.com");
		mdsWebView.getSettings().setJavaScriptEnabled(true);
		mdsWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;
			}
			@Override
			public void onPageFinished (WebView view, String url) {
				
				if(view.canGoBack()) {
					webBackButton.setEnabled(true);
				}else {
					webBackButton.setEnabled(false);
				}
				if(view.canGoForward()) {
					webForwardButton.setEnabled(true);
				}else {
					webForwardButton.setEnabled(false);
				}
			}
			
		});
		

	}

}
