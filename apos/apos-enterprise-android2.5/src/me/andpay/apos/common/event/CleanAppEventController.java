package me.andpay.apos.common.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.FileUtil;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class CleanAppEventController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		TiActivity tiActivity = (TiActivity) activity;
		tiActivity.getAppConfig().inValidate();
		FileUtil.deleteDirectory(activity.getFilesDir());
		FileUtil.deleteDirectory(activity.getCacheDir());
		FileUtil.deleteDirectory(activity.getExternalCacheDir());

		Intent intent = new Intent("BROADCAST_COUNTER_ACTION");
		activity.sendBroadcast(intent);// 传递过去

		activity.finish();

	}
}
