package me.andpay.apos.config;

import me.andpay.apos.tqrm.action.QueryCouponAction;
import me.andpay.apos.tqrm.event.QueryCouponRefreshController;
import me.andpay.timobileframework.config.TiMobileModule;

public class TqrmModule extends TiMobileModule {

	@Override
	protected void configure() {

		bindAction(QueryCouponAction.class);
		bindEventController(QueryCouponRefreshController.class);
	}
}
