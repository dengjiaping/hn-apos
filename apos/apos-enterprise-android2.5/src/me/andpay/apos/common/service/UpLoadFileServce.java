package me.andpay.apos.common.service;

import java.io.File;
import java.util.List;

import me.andpay.apos.common.bug.ThrowableReporter;
import me.andpay.apos.dao.WaitUploadImageDao;
import me.andpay.apos.dao.model.QueryWaitUploadImageCond;
import me.andpay.apos.dao.model.WaitUploadImage;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.util.NetWorkUtil;
import me.mobile.dexlib.provider.HttpServiveProvider;
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

public class UpLoadFileServce {

	/**
	 * 上传次数限制
	 */
	public static final int UPLOAD_TIMES = 30;

	@Inject
	private WaitUploadImageDao waitImageDao;

	@Inject
	private Application application;

	@Inject
	private ThrowableReporter throwableReporter;

	@Inject
	private TiRpcClient tiRpcClient;

	// 图片高清标志
	boolean highDefinition;

	public void uploadFile() {

		new Thread(new Runnable() {
			public void run() {
				startUpload();
			}
		}).start();
	}

	public void startUpload() {

		try {
			QueryWaitUploadImageCond cond = new QueryWaitUploadImageCond();
			cond.setReadyUpload(true);
			cond.setSorts("id-");
			List<WaitUploadImage> imges = waitImageDao.query(cond, 0, -1);
			if (imges.size() == 0) {
				return;
			}
			highDefinition = NetWorkUtil.isHighNetWork(application
					.getApplicationContext());
			for(WaitUploadImage waitUploadImage : imges){

				// 垃圾数据
				if (waitUploadImage.getItemId() == null
						|| StringUtil.isBlank(waitUploadImage.getFilePath())) {
					waitImageDao.delete(waitUploadImage.getId());
					continue;
				}
				File file = new File(waitUploadImage.getFilePath());
				if (!file.exists()){
					waitImageDao.delete(waitUploadImage.getId());
					continue;
				}
				try {
					doUpload(waitUploadImage);
				} catch (Exception ex) {
					// upload igore ex
				}
			}

		} catch (Exception e) {

			Log.e(this.getClass().getName(), "file upload error", e);
		}
	}

	private void doUpload(WaitUploadImage waitUploadImage) throws Exception {

		StringBuffer uploadUrl = new StringBuffer(tiRpcClient.getUploadUrl());
		

		if (uploadUrl.indexOf("?") == -1) {
			uploadUrl.append("?");
		} else {
			uploadUrl.append("&");
		}
		uploadUrl.append("type=").append(waitUploadImage.getItemType());
		uploadUrl.append("&id=").append(waitUploadImage.getItemId());
		Log.e("上传的地址:", uploadUrl.toString());

		int code = HttpServiveProvider.get(application.getApplicationContext())
				.simplUpload(uploadUrl.toString(),
						new File[] { new File(waitUploadImage.getFilePath()) });
		if (code == 200 || code == 403) {
			waitImageDao.delete(waitUploadImage.getId());
			File file = new File(waitUploadImage.getFilePath());
			file.delete();
		} else {
			waitUploadImage.setTimes(waitUploadImage.getTimes() + 1);
			waitImageDao.update(waitUploadImage);
		}

	}
}
