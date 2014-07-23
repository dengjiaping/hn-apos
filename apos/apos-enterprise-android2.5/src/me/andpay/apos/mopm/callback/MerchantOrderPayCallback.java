package me.andpay.apos.mopm.callback;



public interface MerchantOrderPayCallback {
	
	public void checkOrderSuccess();
	
	public void checkOrderFaild(String errorMSg);
}
