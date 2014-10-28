package me.andpay.apos.base.upimage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import me.andpay.timobileframework.lnk.TiRpcClient;
import me.mobile.dexlib.provider.HttpServiveProvider;
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

/**
 * 删除图片管理器
 * 
 * @author shanxiaoping
 *
 */
public class UploadImageManager {
	@Inject
	private TiRpcClient tiRpcClient;

	@Inject
	private Application application;
	/**
	 * 全部图片上传完响应器
	 */
	private Set<UploadAllImageCallback> callBacks;

	public void addUploadFileCallBack(UploadAllImageCallback callBack) {
		if (callBacks == null) {
			callBacks = new HashSet<UploadAllImageCallback>();
		}
		callBacks.add(callBack);

	}

	/**
	 * 上传图片集合
	 */
	private Set<UpLoadImage> images;

	public void addImage(UpLoadImage image) {
		if (images == null){
			images = new HashSet<UpLoadImage>();
		}
		images.add(image);
	}

	public void startUpload() {
		new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				if(images==null&&images.isEmpty()){
					return;
				}
				for (UpLoadImage image : images) {
					if(image.isSuccess()){
						continue;
					}else{
						doUpload(image);
					}
				}
				if(callBacks!=null&&!callBacks.isEmpty()){
					for(UploadAllImageCallback callBack:callBacks){
						callBack.callback(images);
						
					}
				}
				

			}
		}).start();

	}

	private void doUpload(UpLoadImage image){

		StringBuffer uploadUrl = new StringBuffer(tiRpcClient.getUploadUrl());

		if (uploadUrl.indexOf("?") == -1) {
			uploadUrl.append("?");
		} else {
			uploadUrl.append("&");
		}

		uploadUrl.append("type=").append(image.getType());
		uploadUrl.append("&id=").append(image.getId());

		int code = HttpServiveProvider.get(application.getApplicationContext())
				.simplUpload(uploadUrl.toString(),
						new File[] { new File(image.getFileName())});
		Log.e("上传的图片地址:", uploadUrl.toString());
		if (code == 200) {//上传成功
			image.setSuccess(true);
			image.setHttpName(uploadUrl.toString());
			image.callBack();
			
		
		} else {
			image.callBack();
		}
		image.addUpCount();

	}
	/**
	 * 清理
	 */
	public void clear(){
	    images.clear();
		callBacks.clear();
	}

}
