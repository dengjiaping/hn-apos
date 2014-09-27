package me.andpay.apos.config;

import me.andpay.apos.dao.ProductInfoDao;
import me.andpay.apos.dao.PurchaseOrderInfoDao;
import me.andpay.apos.dao.provider.ProductInfoDaoProvider;
import me.andpay.apos.dao.provider.PurchaseOrderInfoDaoProvider;
import me.andpay.apos.vas.action.OpenCardAction;
import me.andpay.apos.vas.action.ProductAction;
import me.andpay.apos.vas.action.PurchaseOrderAction;
import me.andpay.apos.vas.action.QueryPoAction;
import me.andpay.apos.vas.action.QueryRemotePoAction;
import me.andpay.apos.vas.action.SvcDepositeAction;
import me.andpay.apos.vas.event.AmtEventControl;
import me.andpay.apos.vas.event.ProductEditBackControl;
import me.andpay.apos.vas.event.ProductEditEventControl;
import me.andpay.apos.vas.event.ProductItemClickControl;
import me.andpay.apos.vas.event.PurQueryCleanController;
import me.andpay.apos.vas.event.PurQueryCodButtonClickController;
import me.andpay.apos.vas.event.PurchaseOrderItemClickController;
import me.andpay.apos.vas.event.QueryPoRefreshController;
import me.andpay.timobileframework.config.TiMobileModule;

public class VasModule extends TiMobileModule {

	@Override
	protected void configure() {

		requestInjection(ProductInfoDaoProvider.class);
		bind(ProductInfoDao.class).toProvider(ProductInfoDaoProvider.class)
				.asEagerSingleton();

		requestInjection(PurchaseOrderInfoDaoProvider.class);
		bind(PurchaseOrderInfoDao.class).toProvider(
				PurchaseOrderInfoDaoProvider.class).asEagerSingleton();

		bindAction(QueryPoAction.class);
		bindAction(QueryRemotePoAction.class);
		bindAction(PurchaseOrderAction.class);
		bindAction(OpenCardAction.class);
		bindAction(ProductAction.class);
		bindAction(SvcDepositeAction.class);

		bindEventController(AmtEventControl.class);
		bindEventController(PurchaseOrderItemClickController.class);
		bindEventController(PurQueryCleanController.class);
		bindEventController(PurQueryCodButtonClickController.class);
		bindEventController(QueryPoRefreshController.class);
		bindEventController(ProductEditEventControl.class);
		bindEventController(ProductItemClickControl.class);
		bindEventController(ProductEditBackControl.class);
	}

}
