package me.andpay.apos.config;

import me.andpay.apos.dao.OrderInfoDao;
import me.andpay.apos.dao.provider.OrderInfoDaoProvider;
import me.andpay.apos.opm.action.QueryOrderAction;
import me.andpay.apos.opm.event.OrderDetailBackController;
import me.andpay.apos.opm.event.OrderDetailPayController;
import me.andpay.apos.opm.event.QueryClickItemController;
import me.andpay.apos.opm.event.QueryOrderRefreshController;
import me.andpay.apos.opm.event.QueryStatusSelectorController;
import me.andpay.timobileframework.config.TiMobileModule;

import com.google.inject.Scopes;

public class OpmModule extends TiMobileModule {

	@Override
	protected void configure() {

		requestInjection(OrderInfoDaoProvider.class);
		bind(OrderInfoDao.class).toProvider(OrderInfoDaoProvider.class).in(
				Scopes.SINGLETON);

		bindEventController(QueryOrderRefreshController.class);
		bindEventController(QueryClickItemController.class);
		bindEventController(QueryStatusSelectorController.class);
		bindEventController(OrderDetailBackController.class);
		bindEventController(OrderDetailPayController.class);
		bindAction(QueryOrderAction.class);

	}
}
