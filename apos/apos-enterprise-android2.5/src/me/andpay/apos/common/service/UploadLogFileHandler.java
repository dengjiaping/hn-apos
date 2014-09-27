package me.andpay.apos.common.service;

import java.io.File;
import java.util.List;

import me.andpay.ac.term.api.ga.TermStatsDataTypes;
import me.andpay.ac.term.api.ga.TerminalStatsService;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.ti.util.AttachmentItem;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.mobile.dexlib.provider.HttpServiveProvider;
import android.app.Application;

import com.google.inject.Inject;

public class UploadLogFileHandler {

	private TerminalStatsService terminalStatsService;

	@Inject
	private AposContext aposContext;

	@Inject
	private Application application;

	@Inject
	private TiRpcClient tiRpcClient;

	/**
	 * 同步文件上传
	 * 
	 * @param file
	 * @return
	 */
	public boolean synUpload(File[] files, String dataType, String traceNo) {
		if (files == null || files.length == 0) {
			return true;
		}
		String deviceId = (String) aposContext.getAppConfig().getAttribute(
				ConfigAttrNames.DEVICE_ID);
		List<AttachmentItem> attachmentItems = terminalStatsService
				.submitStatsData(deviceId, TermStatsDataTypes.MSR_AUDIO_DATA,
						traceNo, files.length);
		for (int j = 0; j < attachmentItems.size(); j++) {

			AttachmentItem attachmentItem = attachmentItems.get(j);
			StringBuffer uploadUrl = new StringBuffer(
					tiRpcClient.getUploadUrl());
			if (uploadUrl.indexOf("?") == -1) {
				uploadUrl.append("?");
			} else {
				uploadUrl.append("&");
			}
			uploadUrl.append("type=")
					.append(attachmentItem.getAttachmentType());
			uploadUrl.append("&id=").append(attachmentItem.getIdUnderType());

			int code = HttpServiveProvider.get(application).simplUpload(
					uploadUrl.toString(), new File[] { files[j] });
			if (code == 200) {
				for (File file : files) {
					file.delete();
				}
			}

		}
		return true;
	}

	public void asynUpload(File[] files, String dataType, final String traceNo) {

		final File[] tempFiles = files;
		final String tempDataType = dataType;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				synUpload(tempFiles, tempDataType, traceNo);
			}
		});

		thread.start();
	}

}
