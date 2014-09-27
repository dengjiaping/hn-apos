package me.andpay.apos.lam.callback;

public interface ChangePasswordCallback {

	/**
	 * 修改成功
	 * 
	 * @param response
	 */
	public void changeSuccess();

	/**
	 * 修改失败
	 */
	public void changeFaild(String errorCode);
}
