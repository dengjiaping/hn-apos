package me.andpay.timobileframework.mvc.action;

import java.util.HashMap;
import java.util.Map;

import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.context.TiContextProvider;

public class ActionRequest {

	private String domain;

	private String action = null;

	private Map submitData = null;

	private Object handler = null;

	private TiContextProvider provider = null;

	private ActionRequestDispatcher dispatcher = null;

	private Map attributes = null;

	public ActionRequest(EventRequest request, TiContextProvider provider) {
		this.domain = request.getDomain();
		this.action = request.getAction();
		this.submitData = request.getSubmitData();
		this.attributes = new HashMap();
		this.handler = request.getCallback();
		this.provider = provider;
		dispatcher = new DefaultRequestDispatcher();

	}

	public Object getParameterValue(String key) {
		return submitData.get(key);
	}

	public String getDomain() {
		return domain;
	}

	public String getAction() {
		return action;
	}

	public Object getHandler() {
		return handler;
	}

	public TiContext getContext(int contextScope) {
		return provider.provider(contextScope);
	}

	public ActionRequestDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Object getAttribute(String key) {
		return this.attributes.get(key);
	}

	public void setAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}

}
