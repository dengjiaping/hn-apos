package me.andpay.apos.vas.callback;

import java.math.BigDecimal;

public interface SvcDepositeCallback {

	public void depositeSucc(BigDecimal balance);

	public void depositeFail(String errorMsg);

}
