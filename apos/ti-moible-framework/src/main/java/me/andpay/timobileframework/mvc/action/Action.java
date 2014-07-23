package me.andpay.timobileframework.mvc.action;

import me.andpay.timobileframework.mvc.ModelAndView;

/**
 *  事件处理控制器，用于事件处理以及流程控制
 * 
 * @author tinyliu
 *
 */
public interface Action {
	/**
	 * 处理事情请求
	 * @param event
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public ModelAndView handler(ActionRequest request) throws RuntimeException;
}
