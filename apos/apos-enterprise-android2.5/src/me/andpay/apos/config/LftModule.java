package me.andpay.apos.config;

import me.andpay.apos.lft.controller.CreditCardPaymentController;
import me.andpay.apos.lft.controller.TransferAccountVertyController;
import me.andpay.timobileframework.config.TiMobileModule;

public class LftModule extends TiMobileModule{

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		bindEventController(TransferAccountVertyController.class);
		bindEventController(CreditCardPaymentController.class);
		
		
	}

}
