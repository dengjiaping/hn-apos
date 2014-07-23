package me.andpay.apos.cmview;

import me.andpay.apos.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class TiLazyLoadWebView extends WebView {

	private String loadImageSrc = null;

	private String netUrl = null;

	private String imageHtmlPath = "file:///android_asset/html/image.html";

	public TiLazyLoadWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ImageView view = new ImageView(context);
		view.scrollBy(this.getScrollX(), this.getScrollY());
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.com_net_image_view_attr);
		loadImageSrc = a
				.getString(R.styleable.com_net_image_view_attr_defaultImageSrc);
		this.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		this.getSettings().setJavaScriptEnabled(true);
		this.getSettings().setSupportZoom(false);
		this.getSettings().setBlockNetworkImage(false);
		this.getSettings().setBuiltInZoomControls(false);
		this.getSettings().setLoadsImagesAutomatically(true);
		this.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		this.setWebViewClient(new WebViewProcess());
	}

	public void loadNetImageUrl(String netImageUrl) {
		this.netUrl = netImageUrl;
		loadUrl(imageHtmlPath);
	}

	class WebViewProcess extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			String accessUrl = "javascript:changeImage('" + loadImageSrc + "')";
			loadUrl(accessUrl);
			accessUrl = "javascript:lazyLoad('" + netUrl + "')";
			loadUrl(accessUrl);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {

		}
	}
}
