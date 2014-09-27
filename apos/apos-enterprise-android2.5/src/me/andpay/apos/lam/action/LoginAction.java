package me.andpay.apos.lam.action;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.term.api.auth.CookieAttributeNames;
import me.andpay.ac.term.api.auth.LoginRequest;
import me.andpay.ac.term.api.auth.LoginResponse;
import me.andpay.ac.term.api.auth.Party;
import me.andpay.ac.term.api.auth.TermAuthService;
import me.andpay.ac.term.api.auth.User;
import me.andpay.ac.term.api.auth.UserAuthService;
import me.andpay.ac.term.api.sec.PublicKey;
import me.andpay.ac.term.api.txn.order.AddOrderRequest;
import me.andpay.ac.term.api.txn.order.OrderService;
import me.andpay.apos.R;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.ResponseCodes;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.DeviceInfo;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.common.log.AposDebugLog;
import me.andpay.apos.common.service.CleanDataService;
import me.andpay.apos.common.service.ProductSynchroner;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.lam.callback.LoginCallback;
import me.andpay.apos.lam.form.LoginUserForm;
import me.andpay.apos.mopm.callback.MerchantOrderPayCallback;
import me.andpay.apos.mopm.callback.impl.OrderPayUtil;
import me.andpay.apos.mopm.order.OrderPayContext;
import me.andpay.apos.tam.action.txn.cloud.CloudPosUtil;
import me.andpay.orderpay.OrderPayRequest;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.action.MultiAction;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import me.andpay.timobileframework.util.BeanUtils;
import me.andpay.timobileframework.util.ObjectMapConvertor;
import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.inject.Inject;

/**
 * 登录事件处理
 * 
 * @author cpz
 * 
 */
@ActionMapping(domain = LoginAction.DOMAIN_NAME)
public class LoginAction extends MultiAction {
	public final static String DOMAIN_NAME = "/lam/login.action";
	public final static String ACTION_NAME_BIND = "bindParty";
	public final static String PARTYID = "party_id";
	public final static String FLEX_DEFINE = "flex_define";
	public final static String ACTION_NAME_LOGINOUT = "loginOut";
	public final static String EXCEPTION = "exception";

	@Deprecated
	public final static String ACTION_AUTOLOGIN_CHCEKORDER = "autoLoginAndCheckOrder";

	public final static String ACTION_ORDER_SUBMIT_CHCEK = "checkOrderSubmit";

	private TermAuthService termAuthService;

	private UserAuthService userAuthService;

	@Inject
	private TiRpcClient tiRpcClient;

	@Inject
	Application application;

	private TiContext tiConfig = null;

	private TiContext tiContext = null;

	@Inject
	private DynamicFieldHelper dynamicFieldHelper;

	@Inject
	private ProductSynchroner productSynchroner;

	@Inject
	private CleanDataService clearCleanPayTxnInfoService;

	private OrderService orderService;

	@Inject
	private AposDebugLog aposDebugLog;

	@Inject
	private ClientInitHelper clientInitHelper;

	public ModelAndView reLogin(ActionRequest request) throws Throwable {
		ModelAndView mv = new ModelAndView();
		tiContext = request.getContext(TiContext.CONTEXT_SCOPE_APPLICATION);
		String userName = (String) tiContext
				.getAttribute(RuntimeAttrNames.LOGIN_CURRENT_USER);
		String password = (String) tiContext
				.getAttribute(RuntimeAttrNames.LOGIN_CURRENT_PASSWORD);

		LoginUserForm loginUserForm = new LoginUserForm();
		loginUserForm.setAutoFlag(true);
		loginUserForm.setEncryptedPwd(true);
		loginUserForm.setUserName(userName);
		loginUserForm.setPassword(password);
		LoginResponse loginResponse = loginRequest(loginUserForm);
		if (loginResponse == null) {
			return null;
		}

		// loginRequest(loginUserForm, loginCallback)
		// try {
		// LoginResponse loginResponse = userAuthService
		// .login(createRequest(loginUserForm));
		// if (loginResponse == null) {
		// return mv;
		// }
		PartyInfo partyInfo = (PartyInfo) tiContext
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		clientInitHelper.configClient(loginResponse.getDevice()
				.getKeyStorePassword(), partyInfo.getPartyId());

		// } catch (AppBizException e) {
		// mv.addModelValue(EXCEPTION, e);
		// } catch (AppRtException e) {
		// mv.addModelValue(EXCEPTION, e);
		// } catch (Exception e) {
		// Log.e(this.getClass().getName(), "relogin error", e);
		// mv.addModelValue(EXCEPTION, e);
		// }

		return mv;
	}

	/**
	 * 自动登陆并检验订单
	 * 
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	@Deprecated
	public ModelAndView autoLoginAndCheckOrder(ActionRequest request)
			throws Throwable {
		initContext(request);
		LoginCallback loginCallback = (LoginCallback) request.getHandler();
		MerchantOrderPayCallback orderCallback = (MerchantOrderPayCallback) request
				.getHandler();
		try {

			OrderPayContext orderPayContext = OrderPayUtil.getOrderPayContext();
			OrderPayRequest orderPayRequest = orderPayContext
					.getOrderPayRequest();
			if (!orderPayContext.isLogin()) {
				// 验证用户
				String userName = (String) tiConfig
						.getAttribute(ConfigAttrNames.LOGIN_HIS_USER);
				String password = (String) tiConfig
						.getAttribute(ConfigAttrNames.LOGIN_HIS_PASSWORD);
				if (StringUtil.isBlank(userName)
						|| StringUtil.isBlank(password)) {
					loginCallback.loginFaild("请先登陆APOS再完成订单支付。");
					return null;
				}
				LoginUserForm loginUserForm = new LoginUserForm();
				loginUserForm.setAutoFlag(true);
				loginUserForm.setEncryptedPwd(true);
				loginUserForm.setUserName(userName);
				loginUserForm.setPassword(password);
				LoginResponse loginResponse = loginRequest(loginUserForm);
				if (loginResponse == null) {
					return null;
				}
				PartyInfo partyInfo = (PartyInfo) tiContext
						.getAttribute(RuntimeAttrNames.PARTY_INFO);
				String partyId = null;
				if (StringUtil.isBlank(orderPayRequest.getTxnPartyId())) {
					partyId = orderPayRequest.getPartyId();
				} else {
					partyId = orderPayRequest.getTxnPartyId();
				}
				if (!partyInfo.getPartyId().equals(partyId)) {
					loginCallback.loginFaild("您提交的订单所属商户与登陆用户不匹配，请用正确用户重新登陆。");
					return null;
				}
				if (CloudPosUtil
						.isCloudPosCardReader(request
								.getContext(TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG))) {
					orderCallback
							.checkOrderFaild("移动POS暂不支持订单支付，请登陆系统选择其他读卡器类型");
					return null;
				}
				orderPayContext.setLogin(true);
			}

			AddOrderRequest addRequest = new AddOrderRequest();
			BeanUtils.copyProperties(orderPayRequest, addRequest);
			orderService.addOrder(addRequest);
			orderCallback.checkOrderSuccess();
		} catch (AppBizException ex) {
			Log.e(this.getClass().getName(), "check order error", ex);
			orderCallback.checkOrderFaild(ex.getLocalizedMessage());
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "check order error", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				loginCallback.networkError(ErrorMsgUtil.parseError(application,
						ex));
				return null;
			} else {
				Crashlytics.log("check orderError");
				Crashlytics.logException(ex);
			}
			orderCallback.checkOrderFaild("订单检验失败。");
		}

		return null;
	}

	public ModelAndView checkOrderSubmit(ActionRequest request) {
		initContext(request);
		LoginCallback loginCallback = (LoginCallback) request.getHandler();
		MerchantOrderPayCallback orderCallback = (MerchantOrderPayCallback) request
				.getHandler();
		OrderPayContext orderPayContext = OrderPayUtil.getOrderPayContext();
		OrderPayRequest orderPayRequest = orderPayContext.getOrderPayRequest();
		try {
			String userName = (String) tiConfig
					.getAttribute(ConfigAttrNames.LOGIN_HIS_USER);
			String password = (String) tiConfig
					.getAttribute(ConfigAttrNames.LOGIN_HIS_PASSWORD);
			if (StringUtil.isBlank(userName) || StringUtil.isBlank(password)) {
				loginCallback.loginFaild("请先登陆APOS再完成订单支付。");
				return null;
			}
			String partyId = (String) tiConfig
					.getAttribute(ConfigAttrNames.LOGIN_HIS_PARTY_ID);

			String orderPartyId = null;
			if (StringUtil.isBlank(orderPayRequest.getTxnPartyId())) {
				orderPartyId = orderPayRequest.getPartyId();
			} else {
				orderPartyId = orderPayRequest.getTxnPartyId();
			}
			if (!orderPartyId.equals(partyId)) {
				loginCallback.loginFaild("您提交的订单所属商户与登陆用户不匹配，请用正确用户重新登陆。");
				return null;
			}
			if (CloudPosUtil.isCloudPosCardReader(request
					.getContext(TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG))) {
				orderCallback.checkOrderFaild("移动POS暂不支持订单支付，请登陆系统选择其他读卡器类型");
				return null;
			}
			orderCallback.checkOrderSuccess();
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "submit order error", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				loginCallback.networkError(ErrorMsgUtil.parseError(application,
						ex));
				return null;
			} else {
				Crashlytics.log("submit orderError");
				Crashlytics.logException(ex);
			}
			orderCallback.checkOrderFaild("订单提交失败。");
		}
		// }
		return null;
	}

	private void initContext(ActionRequest request) {
		tiContext = request.getContext(TiContext.CONTEXT_SCOPE_APPLICATION);
		tiConfig = request
				.getContext(TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	Party party;

	public ModelAndView login(ActionRequest request) {

		initContext(request);
		ModelAndView mv = new ModelAndView();
		LoginUserForm loginUserForm = (LoginUserForm) request
				.getParameterValue("loginUserForm");
		LoginCallback loginCallback = (LoginCallback) request.getHandler();
		LoginResponse response = null;
		try {
			response = loginRequest(loginUserForm);
			if (response == null) {
				loginCallback.loginFaild("操作失败，请稍后再试");
				return null;
			}
			party = selectParty(response, loginCallback);
			saveTiConfigInfo(response, loginUserForm);
			// 设置cookies信息重建rpc
			setCookies(response, party);
			// 设备初始化
			deviveInit(response);

			if (!isInitCert(party.getPartyId())) {
				tiContext.setAttribute(RuntimeAttrNames.LOGIN_RESPONSE,
						response);
				termAuthService.sendTermActCode(party.getPartyId());
				loginCallback.goActivateCert();
				return null;
			}
			clientInitHelper.configClient(response.getDevice()

			.getKeyStorePassword(), party.getPartyId());
			productSynchroner.sync(application, true);
			clearCleanPayTxnInfoService.cleanTxn();
			loginCallback.loginSuccess(response);
		} catch (AppBizException e) {
			// TERM.017
			if (ResponseCodes.MUST_UPDATE_SOFEWARE_VERSION.equals(e.getCode())) {
				loginCallback.updateApp(e.getLocalizedMessage());
				// TiFlowControlImpl.instanceControl().nextSetup(TiFlowControlImpl.instanceControl().getNodeContrl().getLastActivity(),FlowConstants.GOHOME);

				// loginCallback.loginSuccess(response);
				return mv;
			}
			loginCallback.loginFaild(e.getLocalizedMessage());
			Log.e(this.getClass().getName(), "app error", e);
		} catch (Throwable ex) {
			Log.e(this.getClass().getName(), "system error", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				loginCallback.networkError(ErrorMsgUtil.parseError(application,
						ex));
				return null;
			} else {
				Crashlytics.log("login error");
				Crashlytics.logException(ex);
			}
			loginCallback.loginFaild(ErrorMsgUtil.parseError(application, ex));
		}
		return mv;

	}

	private synchronized LoginResponse loginRequest(LoginUserForm loginUserForm)
			throws Throwable {
		LoginResponse loginResponse = null;
		loginResponse = userAuthService.login(createRequest(loginUserForm));
		if (loginResponse == null) {
			return null;
		}

		// 记录登录用户
		saveTiContextInfo(loginResponse, loginUserForm);

		// 用户信息保留
		setUserInfo(loginResponse);

		Party party = loginResponse.getParties().get(0);
		if (party == null) {
			return null;
		}
		return loginResponse;
	}

	@Deprecated
	private LoginResponse loginReques_old(LoginUserForm loginUserForm,
			LoginCallback loginCallback) {

		LoginResponse loginResponse = null;
		try {

			Log.i(this.getClass().getName(), "txn login start");

			loginResponse = userAuthService.login(createRequest(loginUserForm));
			Log.i(this.getClass().getName(), "txn login end");

			if (loginResponse == null) {
				loginCallback.loginFaild(ResourceUtil.getString(application,
						R.string.tam_syserror_str));
				return null;
			}

			// 记录登录用户
			saveLoginUser(loginResponse, loginUserForm);
			// 设置交易pin密码
			// setpublicKey(termSecurityService.getTxnPinPublicKey());
			// 用户信息保留
			setUserInfo(loginResponse);

			// 机构选择
			Party party = selectParty(loginResponse, loginCallback);
			if (party == null) {
				return null;
			}

			// 设置cookies信息重建rpc
			setCookies(loginResponse, party);
			// 设备初始化
			deviveInit(loginResponse);

			if (!isInitCert(party.getPartyId())) {
				tiContext.setAttribute(RuntimeAttrNames.LOGIN_RESPONSE,
						loginResponse);
				termAuthService.sendTermActCode(party.getPartyId());
				loginCallback.goActivateCert();
				return null;
			}

			clientInitHelper.configClient(loginResponse.getDevice()
					.getKeyStorePassword(), party.getPartyId());
			productSynchroner.sync(application, true);
			clearCleanPayTxnInfoService.cleanTxn();

		} catch (AppBizException e) {
			// TERM.017
			if (ResponseCodes.MUST_UPDATE_SOFEWARE_VERSION.equals(e.getCode())) {
				loginCallback.updateApp(e.getLocalizedMessage());
				return null;
			}
			loginCallback.loginFaild(e.getLocalizedMessage());
			Log.e(this.getClass().getName(), "app error", e);
		} catch (Throwable ex) {
			Log.e(this.getClass().getName(), "system error", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				loginCallback.networkError(ErrorMsgUtil.parseError(application,
						ex));
				return null;
			} else {
				Crashlytics.log("login error");
				Crashlytics.logException(ex);
			}

			loginCallback.loginFaild(ErrorMsgUtil.parseError(application, ex));
		}
		return loginResponse;

	}

	private Party selectParty(LoginResponse loginResponse,
			LoginCallback loginCallback) throws AppBizException {
		Party party = null;
		if (loginResponse.getParties().size() >= 1) {
			party = loginResponse.getParties().get(0);
		}
		if (party == null) {
			loginCallback.loginFaild(ResourceUtil.getString(application,
					R.string.lam_login_no_roles_str));
			return party;
		}

		return party;

	}

	private void setCookies(LoginResponse loginResponse, Party party) {

		Map<String, String> cookies = new HashMap<String, String>();
		cookies.put(CookieAttributeNames.APP_LANGUAGE, getDeviceInfo()
				.getAppLanguage());
		cookies.put(CookieAttributeNames.DEVICE_ID, loginResponse.getDevice()
				.getDeviceId());
		cookies.put(CookieAttributeNames.ENC_PASSWORD, loginResponse.getUser()
				.getEncPassword());
		cookies.put(CookieAttributeNames.TXN_PARTY_ID, party.getPartyId());
		cookies.put(CookieAttributeNames.USER_NAME, loginResponse.getUser()
				.getUserName());
		tiRpcClient.setCookies(cookies);
	}

	/**
	 * 创建Loginrequest
	 * 
	 * @param loginUserForm
	 * @param request
	 * @return
	 */
	private LoginRequest createRequest(LoginUserForm loginUserForm) {

		DeviceInfo deviceInfo = getDeviceInfo();
		Map<String, String> deviceMap = ObjectMapConvertor.describe(deviceInfo);

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword(loginUserForm.getPassword());
		loginRequest.setUserName(loginUserForm.getUserName());
		loginRequest.setDeviceEnv(deviceMap);
		loginRequest.setEncryptedPwd(loginUserForm.isEncryptedPwd());
		return loginRequest;
	}

	private DeviceInfo getDeviceInfo() {

		DeviceInfo deviceInfo = (DeviceInfo) tiContext
				.getAttribute(RuntimeAttrNames.DEVICE_INFO);
		return deviceInfo;
	}

	private void setpublicKey(PublicKey txnPinKey) {
		tiContext.setAttribute(RuntimeAttrNames.PIN_PUBLIC_KEY, txnPinKey);

	}

	/**
	 * 设备初始化
	 * 
	 * @param response
	 * @throws AppBizException
	 */
	private String deviveInit(LoginResponse response) throws AppBizException {
		String deviceId = (String) tiConfig
				.getAttribute(ConfigAttrNames.DEVICE_ID);
		DeviceInfo deviceInfo = getDeviceInfo();
		if (StringUtil.isBlank(deviceId)) {
			deviceId = response.getDevice().getDeviceId();
			tiConfig.setAttribute(ConfigAttrNames.DEVICE_ID, deviceId);
			deviceInfo.setDeviceId(deviceId);
		}

		deviceInfo.setKeyStorePassword(response.getDevice()
				.getKeyStorePassword());
		TiAndroidRuntimeInfo.setDeviceId(deviceId);
		return deviceId;

	}

	@Deprecated
	private void saveLoginUser(LoginResponse response, LoginUserForm userForm) {
		User user = response.getUser();
		Crashlytics.setString("Username", user.getUserName());
		tiContext.setAttribute(RuntimeAttrNames.OLD_PASSWORD,
				userForm.getPassword());
		if (LoginResponse.NEXT_STEP_CHANGE_PWD.equals(response.getNextStep())) {
			tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_PASSWORD, "");
		}

		// 自动登陆
		if (userForm.isAutoFlag()) {
			tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_PASSWORD,
					user.getEncPassword());
		} else {
			tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_PASSWORD, "");
		}
		tiContext.setAttribute(RuntimeAttrNames.HIS_AUTOLOGIN_FLAG,
				userForm.isAutoFlag());
		tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_USER,
				user.getUserName());

		Party party = response.getParties().get(0);
		if (party != null && StringUtil.isNotBlank(party.getPartyId()))
			tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_PARTY_ID,
					party.getPartyId());

		// 后台重新登陆
		tiContext.setAttribute(RuntimeAttrNames.LOGIN_CURRENT_USER,
				userForm.getUserName());
		tiContext.setAttribute(RuntimeAttrNames.LOGIN_CURRENT_PASSWORD,
				user.getEncPassword());
	}

	private void saveTiConfigInfo(LoginResponse response, LoginUserForm userForm) {
		User user = response.getUser();
		Crashlytics.setString("Username", user.getUserName());

		if (LoginResponse.NEXT_STEP_CHANGE_PWD.equals(response.getNextStep())) {
			tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_PASSWORD, "");
		}

		// 自动登陆
		if (userForm.isAutoFlag()) {
			tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_PASSWORD,
					user.getEncPassword());
		} else {
			tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_PASSWORD, "");
		}

		tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_USER,
				user.getUserName());

		Party party = response.getParties().get(0);
		if (party != null && StringUtil.isNotBlank(party.getPartyId()))
			tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_PARTY_ID,
					party.getPartyId());
	}

	private void saveTiContextInfo(LoginResponse response,
			LoginUserForm userForm) {
		User user = response.getUser();
		Crashlytics.setString("Username", user.getUserName());
		tiContext.setAttribute(RuntimeAttrNames.OLD_PASSWORD,
				userForm.getPassword());
		tiContext.setAttribute(RuntimeAttrNames.HIS_AUTOLOGIN_FLAG,
				userForm.isAutoFlag());
		tiContext.setAttribute(RuntimeAttrNames.LOGIN_CURRENT_USER,
				userForm.getUserName());
		tiContext.setAttribute(RuntimeAttrNames.LOGIN_CURRENT_PASSWORD,
				user.getEncPassword());
	}

	private boolean isInitCert(String partyId) {

		try {
			application.openFileInput(getFileName(partyId));
		} catch (FileNotFoundException e) {
			return false;
		}

		return true;
	}

	// /**
	// * 开启sll链接
	// *
	// * @param password
	// */
	// private void configClient(LoginResponse loginResponse, String partyId)
	// throws AppBizException {
	// // 初始化日志
	// aposDebugLog.init();
	// AposOperationLog.init(application.getApplicationContext());
	// openSSL(
	// partyId,loginResponse.getDevice().getKeyStorePassword());
	//
	// bindParty(partyId);
	//
	// //动态字段
	// List<FlexFieldDefine> defineList = partyInfoService
	// .getFlexFieldDefines();
	// tiContext.setAttribute(RuntimeAttrNames.FIELD_DEFINE, defineList);
	// dynamicFieldHelper.setFieldDefines(defineList);
	// //party 设置公共密码
	// setpublicKey(termSecurityService.getTxnPinPublicKey());
	// }

	// private void openSSL(String partyId, String password) {
	// tiRpcClient.configSSL(getKeyPath(partyId), password, password);
	// }

	private void setUserInfo(LoginResponse loginResponse) {

		LoginUserInfo longUser = BeanUtils.copyProperties(LoginUserInfo.class,
				loginResponse.getUser());
		tiContext.setAttribute(RuntimeAttrNames.LOGIN_USER, longUser);
		tiContext.setAttribute(RuntimeAttrNames.EXT_TERM_PARA,
				loginResponse.getExtTermParams());
	}

	// /**
	// * 选择商户 以后有用户选择
	// *
	// * @param response
	// * @throws AppBizException
	// */
	// private PartyInfo bindParty(String partyId) throws AppBizException {
	//
	// Party party = partyAuthService.bindTxnParty(partyId);
	// return partyInfoInit(party);
	//
	// }

	private PartyInfo partyInfoInit(Party party) {

		// 设置刷卡器配合
		// List<MsrQuota> mrsList = partyInfoService.getMsrQuotas();
		PartyInfo partyInfo = new PartyInfo();

		partyInfo.setPartyId(party.getPartyId());
		partyInfo.setPartyName(party.getPartyName());
		partyInfo.setRoles(party.getRoles());
		partyInfo.setPrivileges(party.getPrivileges());
		partyInfo.setExtFuncConfigs(party.getExtFuncConfigs());
		// if(mrsList==null ||mrsList.isEmpty()) {
		// Set<String> msrTypes = new HashSet<String>();
		// msrTypes.add("1");
		// msrTypes.add("2");
		// msrTypes.add("3");
		// msrTypes.add("4");
		// msrTypes.add("5");
		// msrTypes.add("6");
		// partyInfo.setMsrTypes(msrTypes);
		// }else {
		// partyInfo.setMsrTypes(party.getMsrTypes());
		// }
		tiContext.setAttribute(RuntimeAttrNames.PARTY_INFO, partyInfo);
		return partyInfo;
	}

	// private String getKeyPath(String partyId) {
	// return application.getFilesDir().getAbsolutePath() + "/"
	// + getFileName(partyId);
	// }

	private String getFileName(String partyId) {
		DeviceInfo deviceInfo = (DeviceInfo) tiContext
				.getAttribute(RuntimeAttrNames.DEVICE_INFO);
		String fileName = deviceInfo.getAppCode() + "_" + partyId + "_"
				+ CommonProvider.BKS_NAME;
		return fileName;
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView loginOut(ActionRequest request) {

		userAuthService.logout();
		return null;
	}

}
