package me.andpay.apos.cmview;

import java.net.MalformedURLException;
import java.net.URL;

import me.andpay.apos.R;
import me.andpay.timobileframework.cache.TiImageCacheManager;
import me.andpay.timobileframework.cache.TiImageCacheManager.TiNetImageHttpStatusListener;
import me.andpay.timobileframework.cache.display.ImageDisplayer;
import me.andpay.timobileframework.cache.display.ResizeImageDisplayer;
import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

public class TiNetImageView {

	private ImageView view;

	private ImageDisplayer displayer = null;

	private TiImageCacheManager manager = null;
	
	private TiNetImageHttpStatusListener listener;
	
	public Context context;

	public TiNetImageView(Context context, ImageView view) {
		this.context = context;
		this.view = view;
		displayer = new ResizeImageDisplayer(view, context.getResources()
				.getDrawable(R.drawable.com_loading_img), context
				.getResources().getDrawable(R.drawable.com_loading_none_img));
		manager = TiImageCacheManager.getImageCacheService(context,
				TiImageCacheManager.MODE_LEAST_RECENTLY_USED,
				context.getApplicationInfo().name, null);
		displayer.displayLoadingImage();
	}
	
	public void setHttpStatusListener(TiNetImageHttpStatusListener listener) {
		this.listener = listener;
	}

	public void setNetUrl(final String url) {
		if (displayer.getCurrentBitmap() != null
				&& !displayer.getCurrentBitmap().isRecycled()) {
			displayer.destory();
			displayer.displayLoadingImage();
		}

	
		Runnable post = new Runnable() {
			public void run() {

				byte[] data = null;
				try {
					data = manager.downlaodImage(new URL(url), listener);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

				final byte[] dataMy = data;
				
				Activity activity = (Activity)context;
				activity.runOnUiThread(new Runnable() {
					
					public void run() {
				
						if (dataMy != null) {
							displayer.displayImage(dataMy);
						} else {
							displayer.displayLoadFailedImage();
						}
						
					}
				});
			
			}
		};
		
		Thread thread = new Thread(post);
		thread.start();

	}

	public ImageView getImageView() {
		return view;
	}

	public void destory() {
		if (displayer.getCurrentBitmap() != null
				&& !displayer.getCurrentBitmap().isRecycled()) {
			displayer.destory();
		}
	}

}
