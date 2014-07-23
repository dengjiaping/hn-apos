package me.andpay.timobileframework.cache.policy;

public class LRUCacheConfig extends TiCacheConfig{
	/**
	 * 最大文件存储数量
	 * 默认为100张图片
	 */
	public static int maxSize = 100;
	/**
	 * 最大文件存储空间
	 * 默认为20M
	 */
	public static int maxCacheSize = 0;

}
