package me.andpay.apos.common.callback;

public interface SessionKeepCallback {

	public void loginFaild(String errorMsg);

	public void networkError(String errorMsg);
}
