package me.andpay.timobileframework.mvc.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.exception.TiActionInvokeException;
/**
 * Action反射工具类
 * @author tinyliu
 *
 */
public class ActionReflectUtil {
	/**
	 * 获取action中指定方法
	 * @param action 
	 * @param actionName 方法名称
	 * @return
	 * @throws RuntimeException
	 */
	public static Method getActionMethod(Action action, String actionName)
			throws RuntimeException {
		try {
			return action.getClass().getDeclaredMethod(actionName,
					ActionRequest.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("the action method has not found actionName="+actionName+" action="+action.getClass().getName());
		}
	}
	/**
	 * 执行反射方法
	 * 
	 * @param action
	 * @param method
	 * @param request
	 * @return
	 * @throws RuntimeException
	 */
	public static ModelAndView invokeActionMethod(Action action, Method method,
			ActionRequest request) throws RuntimeException {
		try {

			return (ModelAndView) method.invoke(action, request);
			
		} catch (IllegalAccessException e) {
			throw new TiActionInvokeException(action, method, e);
		} catch (InvocationTargetException e) {
				Throwable throwable = e.getCause();
			throw new TiActionInvokeException(action, method, throwable);
		}
	}
}
