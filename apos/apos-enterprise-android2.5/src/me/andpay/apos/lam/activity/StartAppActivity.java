package me.andpay.apos.lam.activity;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.InitMsrKeyServiceImpl;
import me.andpay.apos.cardreader.callback.DefaultCardReaderCallBack;
import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.ConfigAttrValues;
import me.andpay.apos.common.contextdata.DeviceInfo;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.receiver.AposNetworkChangeReceiver;
import me.andpay.apos.common.service.DolbyService;
import me.andpay.apos.common.update.UpdateManager;
import me.andpay.apos.common.util.APOSAlarmUtil;
import me.andpay.apos.common.util.MainhandlerFactory;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.lam.LamProvider;
import me.andpay.apos.lam.callback.AfterUpdateCallback;
import me.andpay.apos.lam.callback.LoginUpdateCallback;
import me.andpay.apos.lam.callback.impl.AutoLoginCallback;
import me.andpay.apos.lam.form.LoginUserForm;
import me.andpay.apos.lam.util.DeviceInfoUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import me.andpay.timobileframework.util.AudioUtil;
import me.andpay.timobileframework.util.DateUtil;
import me.andpay.timobileframework.util.NetWorkUtil;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import android.os.Bundle;
import android.util.Log;

import com.google.inject.Inject;

/**
 * 应用启动
 * 
 * @author cpz
 * 
 */
@ContentView(R.layout.lam_firstpage_layout)
public class StartAppActivity extends AposBaseActivity implements
		ValueContainer, AfterUpdateCallback {

	@Inject
	private DefaultCardReaderCallBack callback;

	@Inject
	private TiRpcClient tiRpcClient;

	@Inject
	private AposNetworkChangeReceiver aposNetworkChangeReceiver;

	// @Inject
	// private TiNetworkStatusChecker tiNetworkStatusChecker;

	@Inject
	private DolbyService dolbyService;

	@Inject
	private MainhandlerFactory mainhandlerFactory;

	@Inject
	private InitMsrKeyServiceImpl initMsrKeyServiceImpl;
	@Inject
	private DefaultCardReaderCallBack defaultCardReaderCallBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		Log.e(this.getClass().getName(), "start robguice");

		super.onCreate(savedInstanceState);
		Log.e(this.getClass().getName(), "end robguice");

		APOSAlarmUtil.startMainAlarm(this.getApplicationContext());

		if (AudioUtil.isDolbymobile()) {
			dolbyService.createDolbyAudioProcessing();
		}
		mainhandlerFactory.createMainHandler();
		aposNetworkChangeReceiver.register(getApplicationContext());

		// 初始化读卡器
		CardReaderManager.initCardReader(getApplicationContext(),
				AposCardReaderUtil.genAposSwiperContext(getAppConfig()));
		CardReaderManager.setInitMsrKeyService(initMsrKeyServiceImpl);
		CardReaderManager.setDefaultCallBack(defaultCardReaderCallBack);

		// 初始化设备信息
		DeviceInfo deviceInfo = DeviceInfoUtil.setDeviceInfo(this);

		// 不同版本初始化

		String verionOnceInstallKey = ConfigAttrNames.ONCE_INSTALL_USE
				+ deviceInfo.getAppVersionCode();
		String verionOnceInstall = (String) getAppConfig().getAttribute(
				verionOnceInstallKey);
		if (StringUtil.isBlank(verionOnceInstall)) {

			if (deviceInfo.getAppVersionCode().equals("15")) {
				getAppConfig().removeAttribute(
						ConfigAttrNames.LOGIN_HIS_PASSWORD);
			}
			getAppConfig().setAttribute(verionOnceInstallKey,
					verionOnceInstallKey);
		}

		String onceInstall = (String) getAppConfig().getAttribute(
				ConfigAttrNames.ONCE_INSTALL_USE);

		// 是否安装
		if (ConfigAttrValues.ONCE_INSTALL_USE_VALUE.equals(onceInstall)) {
			checkUpdate();
		} else {

			this.nextSetup(FlowConstants.SUCCESS_STEP2);
		}
		System.gc();
	}

	@SuppressWarnings("unchecked")
	private boolean autoLogin() {
		String hisUser = (String) getAppConfig().getAttribute(
				ConfigAttrNames.LOGIN_HIS_USER);
		String hisPassword = (String) getAppConfig().getAttribute(
				ConfigAttrNames.LOGIN_HIS_PASSWORD);
		long autoLoginDate = StringConvertor.parseInt((String) getAppConfig()
				.getAttribute(ConfigAttrNames.AUTOLOGIN_START_TIME));

		long betweenDays = 0;
		if (autoLoginDate > 0) {
			long currentDate = DateUtil.getToday();
			betweenDays = currentDate - autoLoginDate;
		}

		if (StringUtil.isEmpty(hisUser) || StringUtil.isEmpty(hisPassword)
				|| betweenDays >= 15) {
			return false;
		}
		LoginUserForm form = new LoginUserForm();
		form.setUserName(hisUser);
		form.setPassword(hisPassword);
		form.setAutoFlag(true);
		form.setEncryptedPwd(true);
		EventRequest request = this.generateSubmitRequest(this);
		request.open(LamProvider.LAM_DOMAIN_LOGIN,
				LamProvider.LAM_ACTION_LOGIN_DEFAULT, Pattern.async);
		request.getSubmitData().put(LoginUserForm.FORM_NAME, form);
		AutoLoginCallback loginCallBack = new AutoLoginCallback(this);
		if (!NetWorkUtil.isNetworkConnected(this.getApplicationContext())) {
			loginCallBack.loginFaild(ResourceUtil.getString(this,
					R.string.com_please_check_network_set_str));
			return true;
		}
		request.callBack(loginCallBack);
		request.submit();
		return true;
	}

	/**
	 * 取消或者无更新后续处理流程
	 */
	public void processAfterCancelUpdateOrNoUpdate() {
		String loginOutFlag = getIntent().getStringExtra(
				CommonProvider.LOGIN_OUT);
		// checkUpdate();
		// 显示登录页面
		if (StringUtil.isEmpty(loginOutFlag) && autoLogin()) {
			return;
		}

		getAppConfig().setAttribute(ConfigAttrNames.ONCE_INSTALL_USE,
				ConfigAttrValues.ONCE_INSTALL_USE_VALUE);
		TiAndroidRuntimeInfo.setup(null);
		this.nextSetup(FlowConstants.SUCCESS);
	}

	/**
	 * 检查更新
	 */
	private void checkUpdate() {
		UpdateManager manager = new UpdateManager(this);
		manager.setCallBack(new LoginUpdateCallback(this, manager));
		manager.checkUpdate();
	}

}