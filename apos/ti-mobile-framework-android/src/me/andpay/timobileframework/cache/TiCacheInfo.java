package me.andpay.timobileframework.cache;

import java.io.Serializable;
import java.net.URL;
import java.util.UUID;

import android.graphics.Bitmap;

/**
 * 缓存信息
 * 
 * @author tinyliu
 * 
 */
public class TiCacheInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9206894660337551901L;

	private long creatAt = 0L;

	private URL url = null;

	private String fileName = null;

	private long fileSize = 0L;

	private byte[] value;

	private int usetimes;
	
	private int responseCode = -1;
	
	private boolean isCacheData = false;

	TiCacheInfo() {
	}

	TiCacheInfo(int responseCode, URL url, long fileSize, byte[] value) {
		this.creatAt = System.currentTimeMillis();
		this.responseCode = responseCode;
		this.usetimes = 0;
		this.fileName = UUID.randomUUID().toString();
		this.url = url;
		this.fileSize = fileSize;
		this.value = value;
	}
	
	TiCacheInfo(URL url, long fileSize, byte[] value) {
		this.creatAt = System.currentTimeMillis();
		isCacheData = true;
		this.usetimes = 0;
		this.fileName = UUID.randomUUID().toString();
		this.url = url;
		this.fileSize = fileSize;
		this.value = value;
	}

	public final long getCreatAt() {
		return this.creatAt;
	}

	public URL getUrl() {
		return this.url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setCreatAt(long creatAt) {
		this.creatAt = creatAt;
	}

	public long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public byte[] getValue() {
		return this.value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	public int getUsetimes() {
		return this.usetimes;
	}

	public void setUsetimes(int usetimes) {
		this.usetimes = usetimes;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public boolean isCacheData() {
		return isCacheData;
	}

	public void setCacheData(boolean isCacheData) {
		this.isCacheData = isCacheData;
	}
	
	
	
}