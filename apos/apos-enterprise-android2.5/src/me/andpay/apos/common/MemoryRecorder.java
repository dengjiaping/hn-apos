package me.andpay.apos.common;

import me.andpay.apos.lam.activity.FirstPageActivity;
import android.app.Activity;
import android.content.Intent;

/**
 * jvm内存记录器
 * 
 * @author tinyliu
 * 
 */
public class MemoryRecorder {

	public static final int MEMORY_NOT_RECORD = 0;

	public static final int MEMORY_RECORD = 1;

	public static int MEMORY_FLAG = MEMORY_NOT_RECORD;

	public static void startRecordMemory() {
		MEMORY_FLAG = MEMORY_RECORD;
	}

	public static boolean isRecordMemory() {
		return MEMORY_FLAG == MEMORY_RECORD;
	}

	public static void reset(Activity activity) {
		Intent intent = new Intent(activity, FirstPageActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}
}
