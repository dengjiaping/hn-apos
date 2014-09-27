package me.andpay.apos.tam.callback;

import me.andpay.ac.term.api.txn.ParseBinResponse;

public interface ParseBinCallback {

	/**
	 * 成功处理
	 * 
	 * @param result
	 */
	public void dealSuccess(ParseBinResponse reponse);

	public void dealFailed(String msg);

}
