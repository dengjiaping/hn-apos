package me.andpay.timobileframework.cache;

import java.net.URL;

import me.andpay.timobileframework.cache.policy.TiCacheConfig;
import me.andpay.timobileframework.cache.policy.TiCachePolicy;
import me.andpay.timobileframework.cache.policy.TiLRUMemoryCachePolicy;
import android.content.Context;

public class TiImageCacheManager {

	private static TiImageCacheManager instance = null;
	private Context mContext;

	private TiCachePolicy policy;

	private TiCacheFilesUtils fileUtils;

	public static final int MODE_LEAST_RECENTLY_USED = 0;
	public static final int MODE_FIXED_TIMED_USED = 1;
	public static final int MODE_FIXED_MEMORY_USED = 2;
	public static final int MODE_NO_CACHE_USED = 3;

	private TiImageCacheManager(Context context, int mode, String tag,
			TiCacheConfig config) {
		this.mContext = context;
		this.fileUtils = TiCacheFilesUtils.getInstance(context);
		if (mode == 0)
			policy = new TiLRUMemoryCachePolicy("least_recently_used");
		policy.configPolicy(config);
		policy.build(context);

	}

	public static synchronized TiImageCacheManager getImageCacheService(
			Context context, int mode, String tag, TiCacheConfig config) {
		if (instance == null) {
			instance = new TiImageCacheManager(context, mode, tag, config);
		}
		return instance;
	}

	/**
	 * 下载图片，请注意downloadImage是同步方法，需要在异步线程中调用
	 * 
	 * @param url
	 * @return
	 */
	public byte[] downlaodImage(URL url, TiNetImageHttpStatusListener listener) {
		byte[] imageCache = getBytesFormCache(url);
		if (imageCache != null) {
			return imageCache;
		}
		TiCacheInfo cacheInfo = fileUtils.downloadImage(url);
		if (cacheInfo == null) {
			return null;
		}
		if(!cacheInfo.isCacheData() && listener != null) {
			listener.responseStatus(url, cacheInfo.getResponseCode());
		}
		if(cacheInfo.getValue() == null) {
			return null;
		}
		policy.put(url.toString(), cacheInfo);
		return cacheInfo.getValue();
	}

	public byte[] getBytesFormCache(URL url) {
		TiCacheInfo cacheInfo = policy.get(url.toString());
		if (cacheInfo != null) {
			return cacheInfo.getValue();
		}
		return null;
	}
	
	
	public interface TiNetImageHttpStatusListener {
		public void responseStatus(URL url, int statusCode);
	}

}