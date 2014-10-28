package me.andpay.apos.base.upimage;

import java.util.Set;

/**
 * 上传所有图片响应接口
 * @author shanxiaoping
 *
 */
public interface UploadAllImageCallback {
	
	public void callback(Set<UpLoadImage> images);
}
