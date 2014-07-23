package me.andpay.timobileframework.mvc.action;

import java.lang.reflect.Method;

import me.andpay.timobileframework.mvc.ModelAndView;

public class MultiAction implements Action {

	public ModelAndView handler(ActionRequest request) throws RuntimeException {
		Method actionMethod = ActionReflectUtil.getActionMethod(this,
				request.getAction());
		return ActionReflectUtil
				.invokeActionMethod(this, actionMethod, request);
	}

}
