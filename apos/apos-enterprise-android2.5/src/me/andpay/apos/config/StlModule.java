package me.andpay.apos.config;

import me.andpay.apos.stl.action.QuerySettleAction;
import me.andpay.timobileframework.config.TiMobileModule;

public class StlModule extends TiMobileModule {

	public static boolean statusHandlerinit = true;

	@Override
	protected void configure() {
		
		bindAction(QuerySettleAction.class);
		
	}

}
