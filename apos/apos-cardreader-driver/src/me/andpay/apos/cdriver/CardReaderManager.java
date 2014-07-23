package me.andpay.apos.cdriver;

import me.andpay.apos.cdriver.bluetooth.SearchBluetoothService;
import me.andpay.apos.cdriver.callback.CardReaderCallback;
import me.andpay.apos.cdriver.control.CardReaderControl;
import me.andpay.apos.cdriver.control.CardreaderControlFactory;
import me.andpay.apos.cdriver.listener.AposBluetoothSearchListener;
import me.andpay.apos.cdriver.model.AposICAppParam;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.cdriver.model.AposIcPublicKey;
import me.andpay.apos.cdriver.model.AposResultData;
import android.content.Context;

/**
 * 读卡器管理器
 * 
 * @author cpz
 * 
 */
public class CardReaderManager {

	/**
	 * 默认回调函数
	 */
	private static CardReaderCallback defaulCallback;
	/**
	 * 当前控制器
	 */
	private static CardReaderControl currCardReaderControl;

	/**
	 * 当前回调函数
	 */
	private static CardReaderCallback currCallback;

	/**
	 * 读卡器类型
	 */
	private static int cardReaderType;
	/**
	 * 密码初始化服务
	 */
	private static InitCardReaderService initMsrKeyService;

	private static SearchBluetoothService searchBluetoothService;
	/**
	 * 首次初始化
	 */
	public static boolean firstInit;
	/**
	 * 刷卡是否启动
	 */
	private static boolean startSwiper;
	/**
	 * 通讯类型
	 */
	private static int deviceCommType;

	private static AposSwiperContext currAposSwiperContext;

	/**
	 * 初始化读卡器
	 */
	public static void initCardReader(Context context,
			AposSwiperContext swiperContext) {
		currAposSwiperContext = swiperContext;
		if (searchBluetoothService == null) {
			searchBluetoothService = new SearchBluetoothService(context);
			searchBluetoothService
					.setBluetoothSearchListener(new AposBluetoothSearchListener());

		}

		if (swiperContext.getCardreaderType() == 0) {
			return;
		}

		if (currCardReaderControl != null) {
			firstInit = false;
			return;
		}
		firstInit = true;
		cardReaderType = swiperContext.getCardreaderType();
		CardReaderControl cardReaderControl = CardreaderControlFactory
				.getCardreaderControl(cardReaderType);
		cardReaderControl.initCardReader(context);
		currCardReaderControl = cardReaderControl;

	}

	/**
	 * 开始刷卡
	 * 
	 * @param swiperRequest
	 */
	public static void startSwiper(AposSwiperContext swiperRequest) {
		currAposSwiperContext = swiperRequest;
		currCardReaderControl.startSwiper(swiperRequest);
		startSwiper = true;

	}

	/**
	 * 搜索蓝牙设备
	 */
	public static void searchDevice() {
		currCardReaderControl.searchDevice();

	}

	/**
	 * 打开设备
	 * 
	 * @param defaultIdentifier
	 * @return
	 */
	public static OpenDeivceResult openDevice(String defaultIdentifier) {
		return currCardReaderControl.openDevice(defaultIdentifier);
	}

	/**
	 * 取消刷卡操作
	 */
	public static void stopSwiper() {
		if (currCardReaderControl == null) {
			return;
		}
		currCardReaderControl.stopSwiper();
		startSwiper = false;
	}

	/**
	 * 获取读卡器类型
	 * 
	 * @return
	 */
	public static int getCardReaderType() {
		return cardReaderType;
	}

	/**
	 * 获取刷卡状态
	 * 
	 * @return
	 */
	public static int getSwiperState() {
		return currCardReaderControl.getSwiperState();
	}

	/**
	 * 设备是否插入
	 * 
	 * @return
	 */
	public static boolean isInput() {
		if (currCardReaderControl != null) {
			return currCardReaderControl.isInput();
		} else {
			return false;
		}

	}

	/**
	 * 停止刷卡器
	 */
	public static void stopCardReader(boolean isDefault) {
		// 设置默认回调事件
		if (currCardReaderControl != null) {
			if (isDefault) {
				setCurrCallback(defaulCallback);
			}
			currCardReaderControl.stopCardReader();
		}

	}

	// 重置读卡器
	public static void resetCardreader() {
		CardReaderManager.stopCardReader(true);
		currCardReaderControl = null;
	}

	/**
	 * 开始录音
	 * 
	 * @param traceNo
	 */
	public static void startRecord(String traceNo) {
		currCardReaderControl.startRecord(traceNo);
	}

	/**
	 * 停止录音
	 */
	public static void stopRecord() {
		currCardReaderControl.stopRecord();
	}

	/**
	 * 加载密钥
	 * 
	 * @param keyType
	 * @param keyData
	 * @param checkValue
	 * @return
	 */
	public static boolean loadKey(String keyType, byte[] keyData,
			byte[] checkValue) {

		return currCardReaderControl.loadKey(keyType, keyData, checkValue);
	}

	/**
	 * 获取设备信息
	 * 
	 * @return
	 */
	public static DeviceInfo getDeviceInfo() {
		DeviceInfo deviceInfo = currCardReaderControl.getDeviceInfo();
		if (deviceInfo.getKsn() == null) {
			deviceInfo.setSuccess(false);
		} else {
			deviceInfo.setSuccess(true);

		}
		return deviceInfo;
	}

	/**
	 * 增加IC卡参数
	 * 
	 * @return
	 */
	public static AposResultData addICAppParam(AposICAppParam aposICAppParam) {
		return currCardReaderControl.addICAppParam(aposICAppParam);
	}

	/**
	 * 增加公钥参数
	 * 
	 * @return
	 */
	public static AposResultData addICPublicKey(AposIcPublicKey aposIcPublicKey) {
		return currCardReaderControl.addICPublicKey(aposIcPublicKey);
	}

	/**
	 * IC卡二次授权
	 * 
	 * @param aposICCardDataInfo
	 */
	public static void secondIssuance(AposICCardDataInfo aposICCardDataInfo) {
		currCardReaderControl.secondIssuance(aposICCardDataInfo);
	}

	public static void setCurrCallback(CardReaderCallback callback) {
		currCallback = callback;
	}

	public static CardReaderCallback getCurrCallback() {
		return currCallback;
	}

	public static void setDefaultCallBack(CardReaderCallback defaulCallback) {
		CardReaderManager.defaulCallback = defaulCallback;
		setCurrCallback(defaulCallback);
	}

	public static void setDefaultCallBack() {
		CardReaderManager.currCallback = defaulCallback;
	}

	public static CardReaderControl getCurrCardReaderControl() {
		return currCardReaderControl;
	}

	public static boolean isFirstInit() {
		return firstInit;
	}

	public static boolean isStartSwiper() {
		return startSwiper;
	}

	public static boolean isSupportDolby() {
		if (currCardReaderControl != null) {
			return currCardReaderControl.isSupportDolby();
		}
		return true;
	}

	public static void setStartSwiper(boolean startSwiper) {
		CardReaderManager.startSwiper = startSwiper;
	}

	public static int getDeviceCommType() {
		return deviceCommType;
	}

	public static void setDeviceCommType(int deviceCommType) {
		CardReaderManager.deviceCommType = deviceCommType;
	}

	public static InitCardReaderService getInitMsrKeyService() {
		return initMsrKeyService;
	}

	public static void setInitMsrKeyService(
			InitCardReaderService initMsrKeyService) {
		CardReaderManager.initMsrKeyService = initMsrKeyService;
	}

	public static int getInputType() {
		if (currCardReaderControl != null) {
			return currCardReaderControl.getInputType();
		}
		return AposSwiperContext.INPUT_CARD_READER;
	}

	public static AposSwiperContext getCurrSwiperContext() {
		return currAposSwiperContext;
	}

	public static SearchBluetoothService getSearchBluetoothService() {
		return searchBluetoothService;
	}
	
	public static void showLCD(String showMsg, int showTime) {
		currCardReaderControl.showLCD(showMsg,showTime);
	}

	public static void clearScreen() {
		currCardReaderControl.clearScreen();
	}
	
	
	public static AposResultData calculateMac(String traceNo, String data) {
		return currCardReaderControl.calculateMac(traceNo, data);
	}
	
	public static String fetchEncryptSecTrackInfo(String traceNO) {
		return currCardReaderControl.fetchEncryptSecTrackInfo(traceNO);
	}


}
