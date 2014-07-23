package me.andpay.timobileframework.mvc.action;

import java.lang.reflect.Method;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.exception.TiActionInvokeException;

/**
 * 默认action跳转实现类
 * 
 * @author tinyliu
 * 
 */
public class DefaultRequestDispatcher implements ActionRequestDispatcher {

	public ModelAndView forward(String domain, String action,
			ActionRequest request) {
		Action actionObj = ActionMappingHandler.getAction(domain);
		if (StringUtil.isEmpty(action)) {
			return actionObj.handler(request);
		}

		Method actionMethod = ActionReflectUtil.getActionMethod(actionObj,
				action);

		return ActionReflectUtil.invokeActionMethod(actionObj, actionMethod,
				request);

	}
}
