package me.andpay.timobileframework.cache.policy;

import android.content.Context;
import me.andpay.timobileframework.cache.TiCacheInfo;

/**
 * 缓存策略接口
 * @author tinyliu
 *
 */
public interface TiCachePolicy {
	
	  /**
	   * 获取缓存内容
	   * @param cacheKey
	   * @return
	   */
	  public void put(String cacheKey, TiCacheInfo cacheInfo);
	  /**
	   * 获取缓存内容
	   * @param cacheKey
	   * @return
	   */
	  public TiCacheInfo get(String cacheKey);
	  /**
	   * 执行缓存清理工作
	   * @return
	   */
	  public void executeCacheFlush();
	  /**
	   * 设置缓存
	   */
	  public void configPolicy(TiCacheConfig config);
	  /**
	   * 建立缓存
	   */
	  public void build(Context context);
}
