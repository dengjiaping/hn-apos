package me.andpay.timobileframework.cache;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;

import android.content.Context;
import android.util.Log;

/**
 * 缓存文件操作类
 * 
 * @author tinyliu
 * 
 */
public class TiCacheFilesUtils {
	private Context mContext;

	private static TiCacheFilesUtils instance = null;

	private FileCache fileCache;
	/**
	 * 文件最大存储数量
	 */
	private Integer maxFileCount = 100;

	/**
	 * 默认2分钟
	 */
	private Integer deleteTaskSleepMills = 2 * 60 * 1000;

	private boolean isTaskRunning = false;

	private TiCacheFilesUtils(Context context) {
		this.mContext = context;
		try {
			fileCache = FileCache.getFileCache(context);
		} catch (IOException e) {
			Log.e(this.mContext.getPackageName(), e.getMessage());
		}
	}

	public static synchronized TiCacheFilesUtils getInstance(Context context) {
		if (instance == null) {
			instance = new TiCacheFilesUtils(context);
			if (!instance.isTaskRunning) {
				instance.startImageClearTask();
			}
		}
		return instance;
	}

	TiCacheInfo downloadImage(URL url) {
		TiCacheInfo cacheInfo = new TiCacheInfo();
		byte[] imageBuffer = readImage(url.toString());
		if (imageBuffer != null) {
			Log.i(this.mContext.getPackageName(), url.toString()
					+ ", cache size is " + imageBuffer.length);
			return new TiCacheInfo(url, imageBuffer.length, imageBuffer);
		}
		BufferedInputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			URLConnection conn = url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(10000);
			HttpURLConnection httpConnection  = (HttpURLConnection)conn;
			cacheInfo.setResponseCode(httpConnection.getResponseCode());
			is = new BufferedInputStream(conn.getInputStream());
			baos = new ByteArrayOutputStream();
			long size = 0L;
			int b = -1;
			while ((b = is.read()) != -1) {
				baos.write(b);
			}
			imageBuffer = baos.toByteArray();
			size = imageBuffer.length;
			Log.i(this.mContext.getPackageName(), url.toString()
					+ ", size is " + size);
			cacheInfo = new TiCacheInfo(cacheInfo.getResponseCode(), url, size, imageBuffer);
		} catch (IOException e) {
			Log.i(this.mContext.getPackageName(), url.toString()
					+ " is unavailable");
			return cacheInfo;
		} finally {
			try {
				baos.close();
			} catch (Exception e) {
				return cacheInfo;
			}
		}
		saveImage(imageBuffer, url.toString());
		return cacheInfo;
	}

	boolean saveImage(byte[] fileBytes, String key) {
		return fileCache.put(key, new ByteArrayInputStream(fileBytes));
	}

	byte[] readImage(String key) {
		byte[] caches = null;
		InputStream input = fileCache.get(key);
		if (input != null) {
			ByteArrayOutputStream baos = null;
			try {
				int b = -1;
				baos = new ByteArrayOutputStream();
				while ((b = input.read()) != -1) {
					baos.write(b);
				}
				caches = baos.toByteArray();
			} catch (IOException e) {
				Log.i(this.mContext.getPackageName(), key
						+ " read cache happend error");
				return null;
			} finally {
				try {
					baos.close();
				} catch (Exception e) {
					return null;
				}
			}
		}
		return caches;
	}

	void startImageClearTask() {
		new Thread(new Runnable() {
			public void run() {
				if(fileCache == null) {
					return;
				}
				int deleteFileCount = fileCache.numFiles() - maxFileCount;
				if (deleteFileCount <= 0) {
					try {
						Thread.sleep(deleteTaskSleepMills);
					} catch (InterruptedException e) {
						isTaskRunning = false;
					}
				} else {
					for (int i = 0; i < deleteFileCount - 1; i++) {
						fileCache.deleteOldest();
					}
				}
			}
		}).start();
		isTaskRunning = true;
	}
}