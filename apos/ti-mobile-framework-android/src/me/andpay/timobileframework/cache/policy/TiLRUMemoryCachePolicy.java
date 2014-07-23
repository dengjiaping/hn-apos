package me.andpay.timobileframework.cache.policy;

import me.andpay.timobileframework.cache.LruCache;
import me.andpay.timobileframework.cache.TiCacheInfo;
import android.app.ActivityManager;
import android.content.Context;

/**
 * LRU缓存策略实现
 * 
 * @author tinyliu
 * 
 */
public class TiLRUMemoryCachePolicy implements TiCachePolicy {
	
	private me.andpay.timobileframework.cache.LruCache<String, TiCacheInfo> memoryCache = null;
	
	private LRUCacheConfig config;
	
	private String tag;

	private int MaxSize;
	
	public TiLRUMemoryCachePolicy(String tag) {
		this.tag = tag;
	}

	public TiCacheInfo get(String cacheKey) {
		
		return memoryCache.get(cacheKey);
	}
	

	public void put(String cacheKey, TiCacheInfo cacheInfo) {
		memoryCache.put(cacheKey, cacheInfo);
	}

	public void configPolicy(TiCacheConfig config) {
		this.config = (LRUCacheConfig)config;
	}


	public void executeCacheFlush() {
	}

	public void build(Context context) {
		if(config != null) {
			this.MaxSize = config.maxCacheSize;
		} else {
			this.MaxSize = ((ActivityManager) context.getSystemService(
			            Context.ACTIVITY_SERVICE)).getMemoryClass();
		}
		 memoryCache = new LruCache<String, TiCacheInfo>(MaxSize);
		
	}

}
