package me.andpay.timobileframework.mvc.exception;

import java.lang.reflect.Method;

import me.andpay.timobileframework.mvc.action.Action;

/**
 * action执行异常信息
 */
public class TiActionInvokeException extends RuntimeException {

	public TiActionInvokeException(Action action, Method method,
			Throwable causeException) {
		super("action:" + action.getClass().getName() + ",method:"
				+ method.getName() + ", invoke happed error", causeException);
	}
}
