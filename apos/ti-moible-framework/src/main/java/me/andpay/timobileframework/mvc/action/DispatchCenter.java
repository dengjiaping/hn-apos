package me.andpay.timobileframework.mvc.action;

import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.context.TiContextProvider;
import me.andpay.timobileframework.mvc.exception.NoSuchMappingException;

public class DispatchCenter {

	public ModelAndView dipatchEvent(EventRequest request,
			TiContextProvider provider)
			throws RuntimeException {
		Action action = ActionMappingHandler.getAction(request.getDomain());
		if (action == null) {
			throw new NoSuchMappingException(request);
		}
		ActionRequest actionRequest = new ActionRequest(request, provider);
		ModelAndView mv = action.handler(actionRequest);
		return mv;
	}

}
