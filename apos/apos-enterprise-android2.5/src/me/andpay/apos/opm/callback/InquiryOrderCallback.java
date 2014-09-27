package me.andpay.apos.opm.callback;

import me.andpay.ac.term.api.txn.order.InquiryOrderResponse;

public interface InquiryOrderCallback {

	public void querySuccess(InquiryOrderResponse inquiryOrderResponse);

	public void networkError();

	public void queryFaild(String msg);
}
