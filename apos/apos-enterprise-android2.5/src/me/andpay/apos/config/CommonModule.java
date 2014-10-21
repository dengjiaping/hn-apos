package me.andpay.apos.config;

import me.andpay.apos.common.AposTestConnector;
import me.andpay.apos.common.action.LbsAction;
import me.andpay.apos.common.action.UpdateAction;
import me.andpay.apos.common.bug.AposBugReporter;
import me.andpay.apos.common.bug.AposReportPersistenceThrowService;
import me.andpay.apos.common.bug.ThrowableReporter;
import me.andpay.apos.common.bug.handler.NetworkErrorExHandler;
import me.andpay.apos.common.bug.handler.SystemErrorHandler;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.event.BackHomeEventControl;
import me.andpay.apos.common.event.CameraCancelEventControl;
import me.andpay.apos.common.event.CameraSaveEventControl;
import me.andpay.apos.common.event.CaptureVcEventControl;
import me.andpay.apos.common.event.HomeTabEventControl;
import me.andpay.apos.common.event.QRScanBackEventControl;
import me.andpay.apos.common.event.ReCaptureEventControl;
import me.andpay.apos.common.event.ShowSliderControl;
import me.andpay.apos.common.listener.SessionTimeoutListener;
import me.andpay.apos.common.log.AposDebugLog;
import me.andpay.apos.common.log.AposOperationLog;
import me.andpay.apos.common.receiver.AposNetworkChangeReceiver;
import me.andpay.apos.common.receiver.BluetoothConnectActivityReceiver;
import me.andpay.apos.common.service.AudioFileUploadService;
import me.andpay.apos.common.service.CleanDataService;
import me.andpay.apos.common.service.DolbyService;
import me.andpay.apos.common.service.DownloadICCardParamsService;
import me.andpay.apos.common.service.ExceptionPayTxnInfoService;
import me.andpay.apos.common.service.ICCardSumbitSettleLListService;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.common.service.ProductSynchroner;
import me.andpay.apos.common.service.TxnConfirmService;
import me.andpay.apos.common.service.TxnReversalService;
import me.andpay.apos.common.service.UpLoadFileServce;
import me.andpay.apos.common.service.UploadLogFileHandler;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.common.util.MainhandlerFactory;
import me.andpay.apos.dao.ICCardInfoDao;
import me.andpay.apos.dao.ICCardParamsInfoDao;
import me.andpay.apos.dao.ICCardPublicKeyInfoDao;
import me.andpay.apos.dao.TxnConfirmDao;
import me.andpay.apos.dao.WaitUploadImageDao;
import me.andpay.apos.dao.provider.ICCardInfoDaoProvider;
import me.andpay.apos.dao.provider.ICCardParamsInfoDaoProvider;
import me.andpay.apos.dao.provider.ICCardPublicKeyInfoDaoProvider;
import me.andpay.apos.dao.provider.TxnConfirmDaoProvider;
import me.andpay.apos.dao.provider.WaitUploadImageDaoProvider;
import me.andpay.apos.lam.action.ClientInitHelper;
import me.andpay.ti.lnk.transport.wsock.client.NetworkStatusChecker;
import me.andpay.timobileframework.config.TiMobileModule;
import me.andpay.timobileframework.lnk.TiLnkServiceInvocation;
import me.andpay.timobileframework.lnk.TiLnkServiceTypeListener;
import me.andpay.timobileframework.lnk.TiNetworkStatusChecker;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.lnk.TiRpcClientProvider;

import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;

public class CommonModule extends TiMobileModule {

	@Override
	protected void configure(){

		// Log.e(this.getClass().getName(), "start commonCofig");

		java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
		java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");

		/**
		 * Ti Lnk Srv auto inject config Begin
		 * *********************************************************************
		 */
		bind(NetworkStatusChecker.class).to(TiNetworkStatusChecker.class)
				.asEagerSingleton();
		TiRpcClientProvider provider = TiRpcClientProvider
				.getProvider(AposTestConnector.class);

		SessionTimeoutListener sessionTimeoutListener = new SessionTimeoutListener();
		bind(SessionTimeoutListener.class).toInstance(sessionTimeoutListener);
		provider.registerExceptionListeners(Throwable.class,
				sessionTimeoutListener);
		requestInjection(sessionTimeoutListener);

		bind(TiLnkServiceInvocation.class).in(Scopes.SINGLETON);
		bind(TiRpcClient.class).toProvider(provider).asEagerSingleton();
		TiLnkServiceTypeListener listener = new TiLnkServiceTypeListener();
		requestInjection(listener);
		bindListener(Matchers.any(), listener);
		/**
		 * Throwable Handler define ************************************
		 * ***********************************
		 */
		bind(NetworkErrorExHandler.class).asEagerSingleton();
		bind(SystemErrorHandler.class).asEagerSingleton();
		bind(ThrowableReporter.class).to(AposBugReporter.class).in(
				Scopes.SINGLETON);
		bindEventController(BackHomeEventControl.class);
		bindEventController(HomeTabEventControl.class);
		bindEventController(ShowSliderControl.class);
		
		bind(UpLoadFileServce.class).in(Scopes.SINGLETON);
		bind(TxnReversalService.class).in(Scopes.SINGLETON);
		bind(AposReportPersistenceThrowService.class).in(Scopes.SINGLETON);
		bind(AposNetworkChangeReceiver.class).in(Scopes.SINGLETON);
		bind(BluetoothConnectActivityReceiver.class).in(Scopes.SINGLETON);

		bind(LocationService.class).in(Scopes.SINGLETON);
		bind(ExceptionPayTxnInfoService.class).in(Scopes.SINGLETON);

		requestInjection(WaitUploadImageDaoProvider.class);
		bind(WaitUploadImageDao.class).toProvider(
				WaitUploadImageDaoProvider.class).asEagerSingleton();

		requestInjection(TxnConfirmDaoProvider.class);
		bind(TxnConfirmDao.class).toProvider(TxnConfirmDaoProvider.class)
				.asEagerSingleton();

		requestInjection(ICCardPublicKeyInfoDaoProvider.class);
		bind(ICCardPublicKeyInfoDao.class).toProvider(
				ICCardPublicKeyInfoDaoProvider.class).asEagerSingleton();
		requestInjection(ICCardParamsInfoDaoProvider.class);
		bind(ICCardParamsInfoDao.class).toProvider(
				ICCardParamsInfoDaoProvider.class).asEagerSingleton();
		bind(ICCardInfoDao.class).toProvider(ICCardInfoDaoProvider.class)
				.asEagerSingleton();

		bindAction(LbsAction.class);
		bindAction(UpdateAction.class);

		bindAction(LbsAction.class);

		// requestInjection(DynamicFieldHelperProvider.class);
		bind(DynamicFieldHelper.class).in(Scopes.SINGLETON);

		bind(CleanDataService.class).in(Scopes.SINGLETON);
		// 增加同步产品数据服务
		bind(ProductSynchroner.class).in(Scopes.SINGLETON);
		// bind(SessionKeepService.class).in(Scopes.SINGLETON);

		bind(TxnConfirmService.class).in(Scopes.SINGLETON);

		bindEventController(CaptureVcEventControl.class);
		bindEventController(CameraCancelEventControl.class);
		bindEventController(ReCaptureEventControl.class);
		bindEventController(CameraSaveEventControl.class);
		bindEventController(QRScanBackEventControl.class);

		bind(UploadLogFileHandler.class).in(Scopes.SINGLETON);
		bind(AposDebugLog.class).in(Scopes.SINGLETON);
		bind(AposContext.class).in(Scopes.SINGLETON);
		bind(DolbyService.class).in(Scopes.SINGLETON);
		bind(AposOperationLog.class).in(Scopes.SINGLETON);
		bind(ClientInitHelper.class).in(Scopes.SINGLETON);
		bind(AudioFileUploadService.class).in(Scopes.SINGLETON);
		bind(MainhandlerFactory.class).in(Scopes.SINGLETON);
		bind(DownloadICCardParamsService.class).in(Scopes.SINGLETON);

		bind(ICCardSumbitSettleLListService.class).in(Scopes.SINGLETON);
		// Log.e(this.getClass().getName(), "end commonCofig");

	}

}
