package me.andpay.apos.config;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.dao.ExceptionPayTxnInfoDao;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.provider.ExceptionPayTxnDaoProvider;
import me.andpay.apos.dao.provider.PayTxnDaoProvider;
import me.andpay.apos.tam.action.CardBinAction;
import me.andpay.apos.tam.action.CloudPosAction;
import me.andpay.apos.tam.action.CouponAction;
import me.andpay.apos.tam.action.PostVoucherAction;
import me.andpay.apos.tam.action.TxnAction;
import me.andpay.apos.tam.action.txn.CardBalanceProcessor;
import me.andpay.apos.tam.action.txn.RefundProcessor;
import me.andpay.apos.tam.action.txn.TxnProcessorFactory;
import me.andpay.apos.tam.action.txn.cloud.SupportCloudPosPurchaseProcessor;
import me.andpay.apos.tam.context.HandlerStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.handler.CardreaderWaitPasswordHandler;
import me.andpay.apos.tam.context.handler.GenChangeStatusHander;
import me.andpay.apos.tam.context.handler.InitStatusHandler;
import me.andpay.apos.tam.context.handler.SearchDeviceHandler;
import me.andpay.apos.tam.context.handler.ShowWaitSwiperHandler;
import me.andpay.apos.tam.context.handler.TimeoutDoQueryHandler;
import me.andpay.apos.tam.context.handler.TryConnDeviceHandler;
import me.andpay.apos.tam.context.handler.TxnSubmitWithPinHandler;
import me.andpay.apos.tam.context.handler.WaitCardReaderHandler;
import me.andpay.apos.tam.context.handler.WaitPasswordCardreaderHandler;
import me.andpay.apos.tam.context.handler.WaitPasswordInputHandler;
import me.andpay.apos.tam.context.handler.WaitSwiperHandler;
import me.andpay.apos.tam.event.AmtEventControl;
import me.andpay.apos.tam.event.CameraButtonEventControl;
import me.andpay.apos.tam.event.CameraGoodsButtonEventControl;
import me.andpay.apos.tam.event.CancelCouponControl;
import me.andpay.apos.tam.event.DetailNextButtonEventControl;
import me.andpay.apos.tam.event.InputValueCardTxnAmtEventControl;
import me.andpay.apos.tam.event.NewTxnEventControl;
import me.andpay.apos.tam.event.PasswordEditOnTouchEventControl;
import me.andpay.apos.tam.event.PasswordEditWatcherEventControl;
import me.andpay.apos.tam.event.PostSkipVcEventControl;
import me.andpay.apos.tam.event.PostVcEditWatcherEventControl;
import me.andpay.apos.tam.event.PostVcEventControl;
import me.andpay.apos.tam.event.PurClearAllButtonEventControl;
import me.andpay.apos.tam.event.PurClearButtonEventControl;
import me.andpay.apos.tam.event.PurMainLayoutEventControl;
import me.andpay.apos.tam.event.PurMainLayoutTouchControl;
import me.andpay.apos.tam.event.PurchaseBackEventControl;
import me.andpay.apos.tam.event.PurchaseNextButtonEventControl;
import me.andpay.apos.tam.event.PurshaseEditWatcherEventControl;
import me.andpay.apos.tam.event.QueryBalanceEventControl;
import me.andpay.apos.tam.event.RedeemCouponCouponControl;
import me.andpay.apos.tam.event.RepostVcEditWatcherEventControl;
import me.andpay.apos.tam.event.RepostVcEventControl;
import me.andpay.apos.tam.event.ShowQRScanViewControl;
import me.andpay.apos.tam.event.SignGestureControl;
import me.andpay.apos.tam.event.SignNextEventControl;
import me.andpay.apos.tam.event.SignPromptControl;
import me.andpay.apos.tam.event.SkipSignEventControl;
import me.andpay.apos.tam.event.TxnBackEventControl;
import me.andpay.apos.tam.event.TxnSubmitEventControl;
import me.andpay.apos.tam.event.TxnToBrowserEventControl;
import me.andpay.timobileframework.config.TiMobileModule;

import com.google.inject.Scopes;

public class TamModule extends TiMobileModule {

	public static boolean statusHandlerinit = true;

	@Override
	protected void configure() {

		bindEventController(PurchaseNextButtonEventControl.class);
		bindEventController(CameraButtonEventControl.class);
		bindEventController(TxnBackEventControl.class);
		bindEventController(TxnToBrowserEventControl.class);
		bindEventController(PurchaseBackEventControl.class);
		bindEventController(AmtEventControl.class);
		bindEventController(SignNextEventControl.class);
		bindEventController(SignGestureControl.class);
		bindEventController(DetailNextButtonEventControl.class);
		bindEventController(PurshaseEditWatcherEventControl.class);
		bindEventController(PostVcEditWatcherEventControl.class);
		bindEventController(PostVcEventControl.class);
		bindEventController(RepostVcEditWatcherEventControl.class);
		bindEventController(RepostVcEventControl.class);
		bindEventController(CameraGoodsButtonEventControl.class);
		bindEventController(PurClearButtonEventControl.class);
		bindEventController(PurClearAllButtonEventControl.class);
		bindEventController(QueryBalanceEventControl.class);
		bindEventController(SignPromptControl.class);
		bindEventController(TxnSubmitEventControl.class);
		bindEventController(PasswordEditWatcherEventControl.class);
		bindEventController(PasswordEditOnTouchEventControl.class);
		bindEventController(PurMainLayoutEventControl.class);
		bindEventController(SkipSignEventControl.class);
		bindEventController(PostSkipVcEventControl.class);
		bindEventController(PurMainLayoutTouchControl.class);
		bindEventController(ShowQRScanViewControl.class);
		bindEventController(RedeemCouponCouponControl.class);
		bindEventController(CancelCouponControl.class);
		bindEventController(InputValueCardTxnAmtEventControl.class);
		bindEventController(NewTxnEventControl.class);
		bind(TxnControl.class).in(Scopes.SINGLETON);

		if (statusHandlerinit == true) {
			bindStatusHandler(InitStatusHandler.class);
			bindStatusHandler(WaitCardReaderHandler.class);
			bindStatusHandler(WaitPasswordInputHandler.class);
			bindStatusHandler(WaitSwiperHandler.class);
			bindStatusHandler(TxnSubmitWithPinHandler.class);
			bindStatusHandler(TimeoutDoQueryHandler.class);
			bindStatusHandler(CardreaderWaitPasswordHandler.class);
			bindStatusHandler(WaitPasswordCardreaderHandler.class);
			bindStatusHandler(SearchDeviceHandler.class);
			bindStatusHandler(TryConnDeviceHandler.class);
			bindStatusHandler(ShowWaitSwiperHandler.class);
			statusHandlerinit = false;
		}

		bindAction(TxnAction.class);
		bindAction(CardBinAction.class);
		bindAction(PostVoucherAction.class);
		bindAction(CouponAction.class);
		bindAction(CloudPosAction.class);

		bind(TxnProcessorFactory.class).in(Scopes.SINGLETON);
		TxnProcessorFactory.registerProcessor(TxnTypes.PURCHASE,
				SupportCloudPosPurchaseProcessor.class);
		TxnProcessorFactory.registerProcessor(TxnTypes.REFUND,
				RefundProcessor.class);

		TxnProcessorFactory.registerProcessor(TxnTypes.INQUIRY_BALANCE,
				CardBalanceProcessor.class);

		requestInjection(PayTxnDaoProvider.class);
		bind(PayTxnInfoDao.class).toProvider(PayTxnDaoProvider.class).in(
				Scopes.SINGLETON);
		requestInjection(ExceptionPayTxnDaoProvider.class);
		bind(ExceptionPayTxnInfoDao.class).toProvider(
				ExceptionPayTxnDaoProvider.class).asEagerSingleton();

	}

	private void bindStatusHandler(
			Class<? extends GenChangeStatusHander> handlerClass) {
		try {
			GenChangeStatusHander handler = handlerClass.newInstance();
			HandlerStatus handlerStatus = handler.getClass().getAnnotation(
					HandlerStatus.class);
			TxnControl.registerHandler(handlerStatus.status(), handler);
		} catch (Exception e) {
			throw new RuntimeException("register satus handler error,class="
					+ handlerClass.getName(), e);
		}

	}

}
