package me.andpay.apos.lam.callback;

import me.andpay.ac.term.api.auth.LoginResponse;

public interface LoginCallback {
	
	/**
	 * 登录成功
	 * @param response
	 */
	public void loginSuccess(LoginResponse response);
	
	/**
	 * 登录失败
	 */
	public void loginFaild(String errorMsg);
	
	public void networkError(String errorMsg);
	
	public void updateApp(String errorCode);
	/**
	 * 激活证书
	 */
	public void goActivateCert();
	

}
