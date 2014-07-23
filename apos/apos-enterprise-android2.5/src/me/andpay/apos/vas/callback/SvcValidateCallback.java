package me.andpay.apos.vas.callback;

import me.andpay.ac.term.api.shop.GetSvcDepositCtrlsResponse;

/**
 * Svc验证接口
 * 
 * @author tinyliu
 * 
 */
public interface SvcValidateCallback {

	public void validateSucc(GetSvcDepositCtrlsResponse response);

	public void validateFailed(String errorMsg);

}
