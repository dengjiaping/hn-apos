package me.andpay.timobileframework.mvc;

import android.app.Activity;

/**
 * 事件处理控制器，用于事件处理以及流程控制
 * 
 * @author tinyliu
 * 
 */
public interface EventController {

	/**
	 * 预处理方法 在event响应之前调用
	 * 
	 * @param refActivty
	 * @return 是否继续调用事件处理
	 */
	public Boolean preProcess(Activity refActivty);
}
