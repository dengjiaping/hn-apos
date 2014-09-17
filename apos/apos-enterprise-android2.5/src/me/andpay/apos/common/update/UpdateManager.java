package me.andpay.apos.common.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import me.andpay.ac.term.api.app.ApplicationInfo;
import me.andpay.apos.R;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.bugsense.ThrowableInfo;
import me.andpay.timobileframework.mvc.EventGenerate;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.util.FileUtil;
import me.andpay.timobileframework.util.ReflectUtil;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UpdateManager extends AfterProcessWithErrorHandler {

	private UpdateCallback callback;
	private Activity ctx;
	private ApplicationInfo info;

	private String newVersion;
	private String size;
	private String updateInfo;
	private String lastUpdateTime;
	private String UPDATE_SAVENAME = "apos.apk";
	public String updateDownloadUrl;
	// 从服务器上下载apk存放文件夹
	private String savefolder = null;
	@SuppressWarnings("unused")
	private String curVersion = null;
	private String dateStr = null;

	private int progress;
	private Boolean hasNewVersion = false;
	private Boolean canceled;

	private int curVersionCode;

	private static final int UPDATE_CHECKCOMPLETED = 1;
	private static final int UPDATE_DOWNLOADING = 2;
	private static final int UPDATE_DOWNLOAD_ERROR = 3;
	private static final int UPDATE_DOWNLOAD_COMPLETED = 4;
	private static final int UPDATE_DOWNLOAD_CANCELED = 5;

	// private String savefolder = "/sdcard/";
	// public static final String SAVE_FOLDER =Storage. // "/mnt/innerDisk";
	public UpdateManager(Activity activity) {
		super(activity);
		ctx = activity;
		canceled = false;
		dateStr = activity.getResources().getString(R.string.com_time_str);
		getCurVersion();
		savefolder = FileUtil.getExtDirPath(Environment.DIRECTORY_DOWNLOADS);
	}

	public void checkUpdate() {
		hasNewVersion = false;
		if (info != null
				&& !StringUtil.isEmpty(info.getAppVersionCode())
				&& Integer.parseInt(info.getAppVersionCode()) > this.curVersionCode){

			this.newVersion = info.getAppName();
			this.updateInfo = info.getDescription();
			this.updateDownloadUrl = info.getAppUpgradeURL();

			this.size = new BigDecimal(info.getAppSize())
					.divide(new BigDecimal(1024 * 1024))
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString()
					+ "M";
			this.lastUpdateTime = String.format(dateStr, info.getReleaseTime());
			callback.checkUpdateCompleted(true, updateInfo);
		}
		if (!ReflectUtil.isImplInterface(ctx.getClass(),
				EventGenerate.class.getName())) {
			updateHandler.sendEmptyMessage(UPDATE_CHECKCOMPLETED);
			return;
		}
		EventRequest request = ((EventGenerate) ctx).generateSubmitRequest(ctx);
		request.open(CommonProvider.COM_DOMAIN_UPDATE, null, Pattern.async);
		request.getSubmitData().put("currentVersionCode", curVersionCode);
		request.callBack(this);
		request.submit();
	}

	@Override
	public void afterRequest(ModelAndView mv) {
		Boolean isUpdate = (Boolean) mv.getValue("isUpdate");
		if (!isUpdate) {
			updateHandler.sendEmptyMessage(UPDATE_CHECKCOMPLETED);
			return;
		}

		this.newVersion = (String) mv.getValue("newVersion");
		this.updateInfo = (String) mv.getValue("updateInfo");
		this.updateDownloadUrl = (String) mv.getValue("updateDownloadUrl");
		this.size = ((BigDecimal) mv.getValue("size")).toString() + "M";
		this.lastUpdateTime = String.format(dateStr,
				((Date) mv.getValue("time")));
		callback.checkUpdateCompleted(isUpdate, updateInfo);
	}

	@Override
	public void processThrowable(ThrowableInfo info) {
		callback.processThrowable(info);
	}

	private void getCurVersion() {
		try {
			PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			curVersion = pInfo.versionName;
			curVersionCode = pInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.e("update", e.getMessage());
			curVersion = "1.1.1000";
			curVersionCode = 111000;
		}

	}

	public void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.setDataAndType(
				Uri.fromFile(new File(savefolder, UPDATE_SAVENAME)),
				"application/vnd.android.package-archive");
		ctx.startActivity(intent);
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public void downloadPackage() {

		new Thread() {
			@Override
			public void run() {
				try {
					URL url = new URL(updateDownloadUrl);

					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();

					File ApkFile = new File(savefolder, UPDATE_SAVENAME);

					if (ApkFile.exists()) {

						ApkFile.delete();
					}

					FileOutputStream fos = new FileOutputStream(ApkFile);

					int count = 0;
					byte buf[] = new byte[512];

					do {

						int numread = is.read(buf);
						count += numread;
						progress = (int) (((float) count / length) * 100);

						updateHandler.sendMessage(updateHandler
								.obtainMessage(UPDATE_DOWNLOADING));
						if (numread <= 0) {

							updateHandler
									.sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED);
							break;
						}
						fos.write(buf, 0, numread);
					} while (!canceled);
					if (canceled) {
						updateHandler
								.sendEmptyMessage(UPDATE_DOWNLOAD_CANCELED);
					}
					fos.close();
					is.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();

					updateHandler.sendMessage(updateHandler.obtainMessage(
							UPDATE_DOWNLOAD_ERROR, e.getMessage()));
				} catch (IOException e) {
					e.printStackTrace();

					updateHandler.sendMessage(updateHandler.obtainMessage(
							UPDATE_DOWNLOAD_ERROR, e.getMessage()));
				}

			}
		}.start();
	}

	public void cancelDownload() {
		canceled = true;
	}

	Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case UPDATE_CHECKCOMPLETED:

				callback.checkUpdateCompleted(hasNewVersion, newVersion);
				break;
			case UPDATE_DOWNLOADING:

				callback.downloadProgressChanged(progress);
				break;
			case UPDATE_DOWNLOAD_ERROR:
				
				callback.downloadCompleted(false, msg.obj.toString());
				break;
			case UPDATE_DOWNLOAD_COMPLETED:
				
				callback.downloadCompleted(true, "");
				break;
			case UPDATE_DOWNLOAD_CANCELED:

				callback.downloadCanceled();
			default:
				break;
			}
		}
	};

	public void setApplicationInfo(ApplicationInfo info) {
		this.info = info;
	}

	public void setCallBack(UpdateCallback callback) {
		this.callback = callback;
	}

	public String getNewVersionName() {
		return newVersion;
	}

	public String getUpdateInfo() {
		return updateInfo;
	}

	public String getSize() {
		return size;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

}