package me.andpay.timobileframework.mvc;


/**
 * Form Process生命接口，所有callback都必须实现该接口
 * @author tinyliu
 * 所有callback都运行在主线程中
 *
 */
public interface AfterProcessCallBackHandler {
	public void afterRequest(ModelAndView mv);	
}


