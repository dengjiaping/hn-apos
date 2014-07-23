package me.andpay.apos.cdriver.control;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.cdriver.DeviceInfo;
import me.andpay.apos.cdriver.OpenDeivceResult;
import me.andpay.apos.cdriver.SwiperStatus;
import me.andpay.apos.cdriver.listener.NewLandSwiperStateChangedListener;
import me.andpay.apos.cdriver.model.AposICAppParam;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.cdriver.model.AposIcPublicKey;
import me.andpay.apos.cdriver.model.AposResultData;
import me.andpay.apos.cdriver.model.AposTerminalParam;
import android.content.Context;

import com.lakala.cswiper3.CSwiperController;
import com.lakala.cswiper3.CSwiperController.CSwiperControllerState;
import com.landicorp.android.mpay.commdriver.util.StringUtil;

/**
 * 新大录刷卡控制器
 * 
 * @author cpz
 * 
 */
public class NewlandCardReaderControl implements CardReaderControl {

	private CSwiperController msr;

	private NewLandSwiperStateChangedListener newlandListener;

	/**
	 * 初始化时是否连接读卡器
	 */
	private boolean initConnect;

	/**
	 * 开始设备关闭状态
	 */
	private volatile int status = 1;


	private static Map<CSwiperControllerState, Integer> stateMap = new HashMap<CSwiperControllerState, Integer>();
	static {
		stateMap.put(CSwiperControllerState.STATE_IDLE, SwiperStatus.STATE_IDLE);
		stateMap.put(CSwiperControllerState.STATE_RECORDING,
				SwiperStatus.STATE_RECORDING);
		stateMap.put(CSwiperControllerState.STATE_WAITING_FOR_DEVICE,
				SwiperStatus.STATE_WAITING_FOR_DEVICE);
		stateMap.put(CSwiperControllerState.STATE_DECODING,
				SwiperStatus.STATE_DECODING);
	}

	public void initCardReader(Context context) {

		CardReaderManager
				.setDeviceCommType(DeviceCommunicationTypes.COMM_AUDIO);

		if (newlandListener == null) {
			newlandListener = new NewLandSwiperStateChangedListener(this);
		}

		newlandListener.setCardReaderControl(this);
		if (msr == null) {
			msr = new CSwiperController(context, newlandListener);
		}
		if (msr.isDevicePresent()) {
			initConnect = true;
		}
	}

	public boolean startSwiper(AposSwiperContext swiperContext) {

		try {
			msr.startCSwiper();

			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public String getKsn() {
		return msr.getCSwiperKsn();
	}

	public int getCardReaderType() {
		return CardReaderTypes.NEW_LAND;
	}

	public boolean isInput() {
		return msr.isDevicePresent();
	}

	public void stopSwiper() {

		if (getSwiperState() != SwiperStatus.STATE_IDLE) {
			msr.stopCSwiper();
		}

	}

	public void stopCardReader() {
		msr.deleteCSwiper();
		msr = null;
	}

	public int getSwiperState() {

		return stateMap.get(msr.getCSwiperState());
	}

	public boolean isInitConnect() {
		return initConnect;
	}

	public void setInitConnect(boolean initConnect) {
		this.initConnect = initConnect;
	}

	public boolean isDevicePresent() {
		return msr.isDevicePresent();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public OpenDeivceResult openDevice(String defaultIdentifier) {
		// TODO Auto-generated method stub
		return null;

	}

	public DeviceInfo getDeviceInfo() {
		DeviceInfo deviceInfo = new DeviceInfo();
		String ksn = this.getKsn();
		if (StringUtil.isEmpty(ksn)) {
			deviceInfo.setResponseCode("SWiperStatus_" + msr.getCSwiperState());
			return deviceInfo;
		}
		deviceInfo.setKsn(ksn);
		return deviceInfo;
	}

	public boolean isSupportDolby() {
		return false;
	}



	public boolean loadKey(String keyType, byte[] keyData, byte[] checkValue) {
		// TODO Auto-generated method stub
		return false;
	}

	public void startRecord(String traceNo) {
		// TODO Auto-generated method stub

	}

	public void stopRecord() {
		// TODO Auto-generated method stub

	}

	public void searchDevice() {
		// TODO Auto-generated method stub

	}
	public int getInputType() {
		return AposSwiperContext.INPUT_KEY_BOARD;
	}

	public AposSwiperContext getCurrSwiperContext() {
		// TODO Auto-generated method stub
		return null;
	}


	public AposResultData addICAppParam(AposICAppParam aposICAppParam) {
		// TODO Auto-generated method stub
		return null;
	}

	public AposResultData addICPublicKey(AposIcPublicKey aposIcPublicKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public void secondIssuance(AposICCardDataInfo aposICCardDataInfo) {
		// TODO Auto-generated method stub
		
	}

	public AposResultData addTerminalParams(AposTerminalParam aposTerminalParam) {
		// TODO Auto-generated method stub
		return null;
	}

	public void showLCD(String showMsg, int showTime) {
		// TODO Auto-generated method stub
		
	}

	public void clearScreen() {
		// TODO Auto-generated method stub
		
	}

	public AposResultData calculateMac(String traceNo, String data) {
		// TODO Auto-generated method stub
		return null;
	}

	public String fetchEncryptSecTrackInfo(String traceNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
