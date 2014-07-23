package me.andpay.timobileframework.cache.display;

import android.graphics.Bitmap;


/**
 * 图片显示接口
 * 
 * @author tinyliu
 * 
 */
public interface ImageDisplayer {
	/**
	 * 显示Loading图片
	 */
	public void displayLoadingImage();

	public void displayImage(byte[] imageBuffer);
	/**
	 * 显示Loadingfailed图片
	 */
	public void displayLoadFailedImage();
	/**
	 * 获取当前显示图片的bitmap
	 * 
	 * @return
	 */
	public Bitmap getCurrentBitmap();
	
	public void destory();
}
