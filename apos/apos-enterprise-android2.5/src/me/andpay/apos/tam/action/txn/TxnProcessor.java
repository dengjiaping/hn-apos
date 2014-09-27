package me.andpay.apos.tam.action.txn;

import me.andpay.timobileframework.mvc.action.ActionRequest;

public interface TxnProcessor {

	public void processTxn(ActionRequest request);

	public void retrieveTxn(ActionRequest request);
}
