package me.andpay.apos.base.upimage;

import java.util.HashSet;
import java.util.Set;

/**
 * 上传图片对象
 * 
 * @author shanxiaoping
 *
 */
public class UpLoadImage {
	private int upCount = 0;// 上传次数
	private String title;// 标题描述
	private Set<UploadImageCallback> callBacks;// 上传回调
	private boolean isSuccess = false;// 是否上传成功
	private String id;// id
	private String type;// 类型
	private String fileName;// 文件路径
	private String httpName;// 网络路径

	public void addCallBack(UploadImageCallback callBack) {
		if (callBacks == null) {
			callBacks = new HashSet<UploadImageCallback>();
		}
		callBacks.add(callBack);

	}

	/**
	 * 增加上传次数
	 */
	public void addUpCount() {
		upCount++;
	}

	public int getUpCount() {
		return upCount;
	}

	public void setUpCount(int upCount) {
		this.upCount = upCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getHttpName() {
		return httpName;
	}

	public void setHttpName(String httpName) {
		this.httpName = httpName;
	}

	public boolean callBack() {

		if (callBacks == null || callBacks.isEmpty()) {
			return false;
		}

		for (UploadImageCallback callBack : callBacks) {
			callBack.callback(this);
		}
		return true;

	}

}
