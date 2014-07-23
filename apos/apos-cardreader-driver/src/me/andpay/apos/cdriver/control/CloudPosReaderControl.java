package me.andpay.apos.cdriver.control;

import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.cdriver.DeviceInfo;
import me.andpay.apos.cdriver.OpenDeivceResult;
import me.andpay.apos.cdriver.callback.CardReaderCallback;
import me.andpay.apos.cdriver.model.AposICAppParam;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.cdriver.model.AposIcPublicKey;
import me.andpay.apos.cdriver.model.AposResultData;
import me.andpay.apos.cdriver.model.AposTerminalParam;
import android.content.Context;

import com.google.inject.Inject;

public class CloudPosReaderControl implements CardReaderControl {

	@Inject
	private CardReaderManager cardReaderManager;

	public void initCardReader(Context context) {
		CardReaderManager
				.setDeviceCommType(DeviceCommunicationTypes.COMM_CLOUD);
	}

	public String getKsn() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCardReaderType() {
		return CardReaderTypes.CLOUD_POS;
	}

	public boolean isInput() {
		return true;
	}

	public boolean startSwiper(AposSwiperContext txnContext) {
		CardReaderManager.getCurrCallback().onWaitingForCardSwipe();
		return true;
	}

	public void stopSwiper() {

		CardReaderManager.getCurrCallback().onCancel();
	}

	public int getSwiperState() {
		return 0;
	}

	public void setCardReaderCallBack(CardReaderCallback cardReaderCallback) {

	}

	public boolean isInitConnect() {
		return false;
	}

	public boolean isDevicePresent() {
		return false;
	}

	public void stopCardReader() {
	}

	public void setInitConnect(boolean initConnect) {
	}

	public void searchDevice() {
		// TODO Auto-generated method stub

	}

	public OpenDeivceResult openDevice(String defaultIdentifier) {
		// TODO Auto-generated method stub
		return null;
	}

	public DeviceInfo getDeviceInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean loadKey(String keyType, byte[] keyData, byte[] checkValue) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSupportDolby() {
		return true;
	}

	public void startRecord() {
		// TODO Auto-generated method stub

	}

	public void stopRecord() {
		// TODO Auto-generated method stub

	}

	public void startRecord(String traceNo) {
		// TODO Auto-generated method stub

	}

	public int getInputType() {
		// TODO Auto-generated method stub
		return 0;
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
