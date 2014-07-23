package me.andpay.timobileframework.mvc.action;

import me.andpay.timobileframework.mvc.ModelAndView;

/**
 * 请求跳转器，用于调用其他action
 * 
 * @author tinyliu
 * 
 */
public interface ActionRequestDispatcher {
	/**
	 * 请求其他action，同步调用
	 * 
	 * @param domain
	 * @param action
	 * @param request
	 * @return
	 * @throws Throwable 
	 */

	public ModelAndView forward(String domain, String action,
			ActionRequest request);
}
