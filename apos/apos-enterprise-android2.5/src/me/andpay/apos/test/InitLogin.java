//package me.andpay.apos.test;
//
//import java.io.FileNotFoundException;
//import java.util.Map;
//
//import me.andpay.ac.term.srv.api.apos.LoginResponse;
//import me.andpay.ac.term.srv.api.apos.SecurityService;
//import me.andpay.ac.term.srv.api.apos.UserAuthService;
//import me.andpay.ac.term.srv.api.apos.UserService;
//import me.andpay.ac.term.srv.api.apos.txn.TxnService;
//import me.andpay.apos.cardreader.CardReaderTypes;
//import me.andpay.apos.common.CommonProvider;
//import me.andpay.apos.common.constant.ConfigAttrNames;
//import me.andpay.apos.common.constant.RuntimeAttrNames;
//import me.andpay.apos.common.contextdata.DeviceInfo;
//import me.andpay.apos.common.contextdata.LoginUserInfo;
//import me.andpay.apos.common.service.UpLoadFileServce;
//import me.andpay.ti.util.StringUtil;
//import me.andpay.timobileframework.lnk.TiRpcClient;
//import me.andpay.timobileframework.mvc.support.TiActivity;
//import me.andpay.timobileframework.util.BeanUtils;
//import me.andpay.timobileframework.util.ObjectMapConvertor;
//import android.content.Context;
//import android.content.pm.PackageManager.NameNotFoundException;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.telephony.TelephonyManager;
//
//import com.google.inject.Inject;
//
//public class InitLogin {
//
//	@Inject
//	private TiRpcClient tiRpcClient;
//
//	private UserService userService;
//
//	private UserAuthService authService;
//
//	private SecurityService securityService;
//
//	private TxnService txnService;
//	
//	@Inject
//	private UpLoadFileServce uploadService;
//	
//	
//	private boolean init=false;
//
//	public void testInit(TiActivity tiActivity) {
//		
//		if( init){
//			return;
//		}
//
//		DeviceInfo info = setDeviceInfo(tiActivity);
//		Map<String, String> deviceMap = ObjectMapConvertor.describe(info);
//
//		LoginResponse response = null;
////
////		try {
////			response = authService.login("15921279827", "11111111", deviceMap);
////
////			LoginUserInfo longUser = BeanUtils.copyProperties(
////					LoginUserInfo.class, response.getUser());
////			tiActivity.getAppContext().setAttribute(
////					RuntimeAttrNames.LOGIN_USER, longUser);
////		} catch (AppBizException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		uploadService.uploadFile();
//		// Test
//		tiActivity.getAppConfig().setAttribute(
//				ConfigAttrNames.CARD_READER_TYPE, CardReaderTypes.NEW_LAND);
//
//		// Map<String, FlexFieldDefine> flexDefine =
//		// txnService.bindTxnParty(party.getPartyId());
//
//		// 设置pin公钥
//		tiActivity.getAppContext().setAttribute(
//				RuntimeAttrNames.PIN_PUBLIC_KEY, response.getTxnPinKey());
//		LoginUserInfo longUser = BeanUtils.copyProperties(LoginUserInfo.class,
//				response.getUser());
//		tiActivity.getAppContext().setAttribute(RuntimeAttrNames.LOGIN_USER,
//				longUser);
//
//		deviveInit(response, tiActivity);
//
//		String keyPath = tiActivity.getFilesDir().getAbsolutePath() + "/"
//				+ getFileName(tiActivity);
//
//		tiRpcClient.configSSL(keyPath, response.getDevice()
//				.getKeyStorePassword(), response.getDevice()
//				.getKeyStorePassword());
//
//		init = true;
//	}
//
//	private void deviveInit(LoginResponse response, TiActivity tiActivity) {
//		String deviceId = (String) tiActivity.getAppConfig().getAttribute(
//				ConfigAttrNames.DEVICE_ID);
//		if (!isInit(deviceId, tiActivity)) {
//			initCert(response.getDevice().getDeviceId(), response.getDevice()
//					.getKeyStorePassword(), tiActivity);
//			tiActivity.getAppConfig().setAttribute(ConfigAttrNames.DEVICE_ID,
//					response.getDevice().getDeviceId());
//		}
//
//	}
//
//	private boolean isInit(String deviceId, TiActivity tiActivity) {
//
//		if (StringUtil.isBlank(deviceId)) {
//			return false;
//		}
//
//		try {
//			tiActivity.openFileInput(getFileName(tiActivity));
//		} catch (FileNotFoundException e) {
//			return false;
//		}
//
//		return true;
//	}
//
//	private String getFileName(TiActivity tiActivity) {
//		DeviceInfo deviceInfo = (DeviceInfo) tiActivity.getAppContext()
//				.getAttribute(RuntimeAttrNames.DEVICE_INFO);
//		String fileName = deviceInfo.getAppVersionCode() + "_"
//				+ CommonProvider.BKS_NAME;
//		return fileName;
//	}
//
//	/**
//	 * 设置设备信息
//	 * 
//	 * @throws NameNotFoundException
//	 */
//	private DeviceInfo setDeviceInfo(TiActivity tiActivity) {
//
//		TelephonyManager tpMg = (TelephonyManager) tiActivity
//				.getSystemService(Context.TELEPHONY_SERVICE);
//		DeviceInfo deviceInfo = new DeviceInfo();
//		deviceInfo.setImei(tpMg.getDeviceId());
//		deviceInfo.setOsVersionCode(android.os.Build.VERSION.SDK);
//		deviceInfo.setMac(getLocalMacAddress(tiActivity));
//		try {
//			Integer apkVersion = tiActivity.getPackageManager().getPackageInfo(
//					"me.andpay.apos", 0).versionCode;
//			deviceInfo.setAppVersionCode(apkVersion.toString());
//		} catch (Exception e) {
//			// TODO log
//		}
//		String deviceId = (String) tiActivity.getAppConfig().getAttribute(
//				ConfigAttrNames.DEVICE_ID);
//		if (StringUtil.isBlank(deviceId)) {
//			deviceId = null;
//		}
//		deviceInfo.setDeviceId(deviceId);
//
//		String cardBinVersion = (String) tiActivity.getAppConfig()
//				.getAttribute(ConfigAttrNames.CARD_BIN_DB_VERSION);
//
//		if (StringUtil.isBlank(cardBinVersion)) {
//			cardBinVersion = "0";
//			tiActivity.getAppConfig().setAttribute(
//					ConfigAttrNames.CARD_BIN_DB_VERSION, cardBinVersion);
//		}
//
//		return deviceInfo;
//	}
//
//	/**
//	 * 获取mac地址
//	 * 
//	 * @return
//	 */
//	public String getLocalMacAddress(TiActivity tiActivity) {
//		WifiManager wifi = (WifiManager) tiActivity
//				.getSystemService(Context.WIFI_SERVICE);
//		WifiInfo info = wifi.getConnectionInfo();
//		return info.getMacAddress();
//	}
//
//	private void initCert(String deviceId, String password,
//			TiActivity tiActivity) {
////		GenKeyAndCsrRequest genKeyAndCsrRequest = new GenKeyAndCsrRequest();
////		genKeyAndCsrRequest.setCommonName(deviceId);
////		genKeyAndCsrRequest.setCountryCode("CN");
////		genKeyAndCsrRequest.setCityName("SH");
////		genKeyAndCsrRequest.setKeySize(2048);
////		genKeyAndCsrRequest.setOrganizationalName("ANDPAY");
////		genKeyAndCsrRequest.setOrganizationCode("ANDPAY");
////		genKeyAndCsrRequest.setProvinceName("SH");
////		KeyAndCsr keyAndCsr = PkcsClient.genKeyAndCsr(genKeyAndCsrRequest);
////
////		EventRequest request = tiActivity.generateSubmitRequest(tiActivity);
////		SignCertResult keyandCert = securityService.signClientCert(deviceId,
////				keyAndCsr.getCsrData());
////
////		KeyAndCert keyAndCert = new KeyAndCert();
////		keyAndCert.setKeyPair(keyAndCsr.getKeyPair());
////		keyAndCert.setCertType(keyandCert.getCertType());
////		keyAndCert.setCertData(keyandCert.getCertData());
////
////		String keystorePath = tiActivity.getFilesDir().getAbsolutePath() + "/"
////				+ getFileName(tiActivity);
////
////		FileOutputStream fileOut = null;
////		try {
////			fileOut = tiActivity.openFileOutput(getFileName(tiActivity),
////					Context.MODE_PRIVATE);
////		} catch (FileNotFoundException e) {
////			// ignore
////		} finally {
////			try {
////				fileOut.close();
////			} catch (IOException e) {
////				// ignore
////			}
////		}
////
////		PkcsClient.storeKeyAndCert(keyAndCert, keystorePath,
////				CommonProvider.CERT_ALIAS, password);
//	}
// }
