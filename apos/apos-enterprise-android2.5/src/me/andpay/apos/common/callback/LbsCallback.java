package me.andpay.apos.common.callback;

import me.andpay.ac.term.api.lbs.LocateResult;

public interface LbsCallback {

	/**
	 * 处理结果
	 * 
	 * @param result
	 */
	public void dealResult(LocateResult result);

	/**
	 * 异常处理
	 */
	public void excptionHandle();
}
