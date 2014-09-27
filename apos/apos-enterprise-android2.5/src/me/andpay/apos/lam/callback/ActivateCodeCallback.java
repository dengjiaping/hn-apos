package me.andpay.apos.lam.callback;

public interface ActivateCodeCallback {

	public void activateSuccess();

	public void activateFaild(String errorMsg);
}
