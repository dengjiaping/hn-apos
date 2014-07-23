package me.andpay.timobileframework.saf.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TiSafHandlerClazzInfo {
	
	private Object handler;
	
	private Map<String, Method> methodMappings = new HashMap<String, Method>();

	public Object getHandler() {
		return handler;
	}

	public void setHandler(Object handler) {
		this.handler = handler;
	}

	public Map<String, Method> getMethodMappings() {
		return methodMappings;
	}

	public void setMethodMappings(Map<String, Method> methodMappings) {
		this.methodMappings = methodMappings;
	}
	/**
	 * 提交事件请求
	 * @param event
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public boolean forwardEvent(TiSafEvent event) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return (Boolean) methodMappings.get(event.getEventType()).invoke(this.handler, event.getRefObj());
	}
	/**
	 * 判断是否支持此事件
	 * @param event
	 * @return
	 */
	public boolean isSupportEventType(TiSafEvent event) {
		return this.methodMappings.get(event.getEventType()) != null;
	}
}
