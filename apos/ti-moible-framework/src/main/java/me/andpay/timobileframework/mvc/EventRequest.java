package me.andpay.timobileframework.mvc;

import java.util.Map;

import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.form.FormBean;

public interface EventRequest {
	
	public static enum Pattern {
		sync, async;
	}

	/**
	 * 建立后端连接
	 * @param domain
	 * @param action
	 * @param pattern
	 * @return
	 */
	public EventRequest open(String domain, String action, Pattern pattern);
	
	/**
	 * 建立后端连接
	 * @param formBean
	 * @parsdam pattern
	 * @return
	 */
	public EventRequest open(FormBean formBean, Pattern pattern);
	/**
	 * 设置callback对象
	 */
	public EventRequest callBack(Object callback);
	/**
	 * 提交Action请求
	 * @param submitData
	 * @return
	 * @throws RuntimeException
	 */
	public ModelAndView submit(Map submitData) throws RuntimeException;
	/**
	 * 提交Action请求
	 * @param submitData
	 * @return
	 * @throws RuntimeException
	 */
	public ModelAndView submit() throws RuntimeException;
	
	public String getDomain();

	public String getAction();

	public Map getSubmitData();

	public Object getCallback() ;

	public TiContext getRequestDataContext();

}
