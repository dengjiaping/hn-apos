package me.andpay.apos.mopm.order;

import java.io.Serializable;

import me.andpay.orderpay.OrderPayRequest;
import me.andpay.orderpay.OrderPayResponse;

public class OrderPayContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8417849148629585480L;

	private OrderPayRequest orderPayRequest;

	private OrderPayResponse orderPayResponse;

	private boolean login;

	private boolean needAutoLogin;

	public OrderPayContext newInstance() {
		OrderPayContext newContext = new OrderPayContext();
		newContext.orderPayRequest = this.orderPayRequest;
		newContext.orderPayResponse = this.orderPayResponse;
		newContext.login = this.login;
		newContext.needAutoLogin = this.needAutoLogin;
		return newContext;
	}

	public OrderPayRequest getOrderPayRequest() {
		return orderPayRequest;
	}

	public void setOrderPayRequest(OrderPayRequest orderPayRequest) {
		this.orderPayRequest = orderPayRequest;
	}

	public OrderPayResponse getOrderPayResponse() {
		return orderPayResponse;
	}

	public void setOrderPayResponse(OrderPayResponse orderPayResponse) {
		this.orderPayResponse = orderPayResponse;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public boolean isNeedAutoLogin() {
		return needAutoLogin;
	}

	public void setNeedAutoLogin(boolean needAutoLogin) {
		this.needAutoLogin = needAutoLogin;
	}

}
