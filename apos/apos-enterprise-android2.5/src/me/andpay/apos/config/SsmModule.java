package me.andpay.apos.config;

import me.andpay.apos.ssm.action.SettleAction;
import me.andpay.apos.ssm.action.SettleQueryAction;
import me.andpay.apos.ssm.action.SettleSendAction;
import me.andpay.apos.ssm.event.MainDetailController;
import me.andpay.apos.ssm.event.MainHistoryController;
import me.andpay.apos.ssm.event.MainHomeController;
import me.andpay.apos.ssm.event.RefreshSettleController;
import me.andpay.apos.ssm.event.TextWatcherController;
import me.andpay.timobileframework.config.TiMobileModule;

public class SsmModule extends TiMobileModule {

	@Override
	protected void configure() {
		// bind controller
		bindEventController(MainHistoryController.class);
		bindEventController(MainHomeController.class);
		bindEventController(MainDetailController.class);
		bindEventController(RefreshSettleController.class);
		bindEventController(TextWatcherController.class);
		// bind action
		bindAction(SettleSendAction.class);
		// bindAction(MockSettleQueryAction.class);
		bindAction(SettleQueryAction.class);
		bindAction(SettleAction.class);

	}

}
