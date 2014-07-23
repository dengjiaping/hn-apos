package me.andpay.timobileframework.util;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.audiofx.AudioEffect;
import android.media.audiofx.AudioEffect.Descriptor;

import android.media.AudioManager;
import android.media.audiofx.AudioEffect;
import android.media.audiofx.AudioEffect.Descriptor;

public class AudioUtil {

	// 关闭
	public static final String DB_STATUS_CLOSE = "close";
	// 打开
	public static final String DB_STATUS_OPEN = "open";
	// 未知
	public static final String DB_STATUS_UNKNOW = "unknow";

	private static Boolean isDolbyMobile;

	/**
	 * 耳机线是否插入
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isHeadsetInsert(Context context) {

		AudioManager localAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		return localAudioManager.isWiredHeadsetOn();
	}

	/**
	 * 是否是dobly手机
	 * 
	 * @return
	 */
	public static boolean isDolbymobile() {
		if (isDolbyMobile != null) {
			return isDolbyMobile;
		}

		Descriptor[] des = AudioEffect.queryEffects();
		for (Descriptor descriptor : des) {
			if (descriptor.implementor.toUpperCase().indexOf("DOLBY") >= 0) {
				isDolbyMobile = true;
				return isDolbyMobile;
			}

		}
		isDolbyMobile = false;

		return isDolbyMobile;
	}

	public static String dolbyStatus() {

		try {
			Class<?> localClass = Class.forName("android.os.SystemProperties");

			Method localMethod = localClass.getMethod("get",
					new Class[] { String.class });

			String status = (String) localMethod.invoke(localClass,
					new Object[] { "dolby.ds.state" });

			if ("on".equalsIgnoreCase(status)) {
				return DB_STATUS_OPEN;
			} else if ("off".equalsIgnoreCase(status)) {
				return DB_STATUS_CLOSE;
			} else {
				return DB_STATUS_UNKNOW;
			}

		} catch (Exception e) {
			return DB_STATUS_UNKNOW;
		}

	}
	
	public static Integer  headsetStatus(Context context) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.HEADSET_PLUG");
		Intent stickyIntent = context.registerReceiver(null, intentFilter);		
		if(stickyIntent == null) {
			return 0;
		}
		Integer headsetState = stickyIntent.getExtras().getInt("state");
		return headsetState;
	}
	
	
	public static Integer  microphoneState(Context context) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.HEADSET_PLUG");
		Intent stickyIntent = context.registerReceiver(null, intentFilter);		
		if(stickyIntent == null) {
			return 0;
		}
		Integer headsetState = stickyIntent.getExtras().getInt("state");
		if(headsetState == 0) {
			return 0;
		}
		
		Integer microphoneState = stickyIntent.getExtras().getInt("microphone");
		return microphoneState;
	}
	
	
	

}
