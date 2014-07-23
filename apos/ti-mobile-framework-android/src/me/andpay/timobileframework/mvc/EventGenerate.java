package me.andpay.timobileframework.mvc;

import android.app.Activity;

/**
 * event action 生成接口
 * @author tinyliu
 *
 */
public interface EventGenerate {
	public EventRequest generateSubmitRequest(Activity refActivty);
}
