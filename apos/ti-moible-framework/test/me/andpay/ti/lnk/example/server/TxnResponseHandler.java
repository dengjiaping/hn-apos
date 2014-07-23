package me.andpay.ti.lnk.example.server;

import me.andpay.ti.lnk.annotaion.LnkAbstractService;
import me.andpay.ti.lnk.annotaion.LnkMethod;

/**
 * 交易应答句柄类。
 * 
 * @author sea.bao
 */
@LnkAbstractService
public interface TxnResponseHandler {
	/**
	 * 交易应答处理
	 * @param resp
	 */
	@LnkMethod(async = true)
	void onResponse(TxnResponse resp);
}
