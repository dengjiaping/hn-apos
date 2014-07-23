package me.andpay.apos.common.util;

import me.andpay.apos.common.receiver.AposMainBroadcastReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class APOSAlarmUtil {

	public static void startMainAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("me.andpay.AposMainBroadcastReceiver");
		intent.setClass(context, AposMainBroadcastReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		//3分钟执行一次
		long atTimeInMillis = System.currentTimeMillis() + 60*1000*3;
		am.set(AlarmManager.RTC_WAKEUP, atTimeInMillis, sender);
		
	}
	
}
