package me.andpay.timobileframework.mvc.exception;

import me.andpay.timobileframework.mvc.EventRequest;

public class NoSuchMappingException  extends RuntimeException {
	
	public NoSuchMappingException(EventRequest request) {
		super("domain:" + request.getDomain() + ",action:" + request.getAction() + ", has not found mapping action");
	}
}
