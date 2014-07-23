package me.andpay.apos.cdriver.control;

import java.math.BigDecimal;

import me.andpay.ac.term.api.sec.MsrKeyTypes;
import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.CardReaderUtil;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.cdriver.DeviceInfo;
import me.andpay.apos.cdriver.InitMsrKeyResult;
import me.andpay.apos.cdriver.OpenDeivceResult;
import me.andpay.apos.cdriver.listener.NewlandOperateListener;
import me.andpay.apos.cdriver.model.AposICAppParam;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.cdriver.model.AposIcPublicKey;
import me.andpay.apos.cdriver.model.AposResultData;
import me.andpay.apos.cdriver.model.AposTerminalParam;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.NLmpos.API.Hefu.LoadKeyResult;
import com.NLmpos.API.Hefu.OpenResult;
import com.NLmpos.API.Hefu.OperateRequest;
import com.NLmpos.API.Hefu.SwiperCommandController;

public class NewlandAudioCardReaderControl implements CardReaderControl {

	private static final String TAG = NewlandAudioCardReaderControl.class
			.getName();

	private SwiperCommandController swiperCommandController;

	private NewlandOperateListener newlandAudioOperateListener;

	private Context context;


	public void initCardReader(Context context) {

		this.context = context;
		CardReaderManager
				.setDeviceCommType(DeviceCommunicationTypes.COMM_AUDIO);
		if (newlandAudioOperateListener == null) {
			newlandAudioOperateListener = new NewlandOperateListener();
		}
		if (newlandAudioOperateListener == null) {
			newlandAudioOperateListener = new NewlandOperateListener();
		}

		if (swiperCommandController == null) {
			swiperCommandController = new SwiperCommandController(context);
			swiperCommandController
					.setUserOperateListener(newlandAudioOperateListener);
		}
		swiperCommandController.setCommunication(OperateRequest.MODEL_AUDIO);

	}

	public String getKsn() {
		return null;
	}

	public int getCardReaderType() {
		return CardReaderTypes.NEW_LAND_AD;

	}

	public boolean isInput() {
		AudioManager localAudioManager = (AudioManager) context
				.getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		return localAudioManager.isWiredHeadsetOn();
	}

	public boolean startSwiper(AposSwiperContext txnContext) {
		Thread commitThread = new Thread(new SwiperCommitRunnable(txnContext));
		commitThread.start();
		return true;

	}

	public class SwiperCommitRunnable implements Runnable {

		private AposSwiperContext txnContext;

		public SwiperCommitRunnable(AposSwiperContext txnContext) {
			this.txnContext = txnContext;
		}

		public void run() {

			OpenDeivceResult openDeivceResult = openDevice(null);

			if (openDeivceResult.isSuccess()) {
				InitMsrKeyResult initMsrKeyResult = CardReaderManager
						.getInitMsrKeyService().initMsrKey(
								openDeivceResult.getKsn());

				if (initMsrKeyResult.isSuccess()) {
					sendToSwiper(txnContext);
				} else {
					newlandAudioOperateListener.initKeyError(initMsrKeyResult
							.getErrorMsg());
				}

			} else {
				newlandAudioOperateListener.onConnectError();
			}

		}

	}

	public void sendToSwiper(AposSwiperContext aposSwiperContext) {

		OperateRequest operateRequest = null;

		if (aposSwiperContext.isNeedPin()
				&& aposSwiperContext.getPinErrorCount() < 3
				&& aposSwiperContext.getPinErrorCount() > 0) {
			operateRequest = new OperateRequest();
			BigDecimal amt = BigDecimal.ZERO;
			if (aposSwiperContext.getSalesAmt() != null) {
				amt = aposSwiperContext.getSalesAmt();
			}
			amt = amt.multiply(new BigDecimal("100")).setScale(0);
			operateRequest.setamt(amt.toString());
			operateRequest.setinputMaxLen(6);
			operateRequest.setmode(OperateRequest.GET_PIN);
			operateRequest.setinputPinTimeout(180);
			operateRequest.settraceNo(aposSwiperContext.getTermTraceNo());
			operateRequest.setpan(CardReaderUtil.panConvert(aposSwiperContext
					.getCardNo()));
		} else {
			operateRequest = new OperateRequest();
			BigDecimal amt = BigDecimal.ZERO;
			if (aposSwiperContext.getSalesAmt() != null) {
				amt = aposSwiperContext.getSalesAmt();
			}
			amt = amt.multiply(new BigDecimal("100")).setScale(0);
			operateRequest.setamt(amt.toString());
			operateRequest.setinputMaxLen(6);
			operateRequest.setmode(OperateRequest.GET_GROUP);
			operateRequest.setinputPinTimeout(180);
			operateRequest.setswiperTimeout(180);
			operateRequest.settraceNo(aposSwiperContext.getTermTraceNo());

		}

		swiperCommandController.startUserOperate(operateRequest);

	}

	public void stopSwiper() {

		swiperCommandController.stopUserOperate();
	}

	public int getSwiperState() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isInitConnect() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isDevicePresent() {
		// TODO Auto-generated method stub
		return false;
	}

	public void stopCardReader() {
		swiperCommandController.closeDevice();
	}

	public void setInitConnect(boolean initConnect) {
		// TODO Auto-generated method stub

	}

	public void searchDevice() {
		// TODO Auto-generated method stub

	}

	public OpenDeivceResult openDevice(String defaultIdentifier) {

		OpenDeivceResult openDeivceResult = new OpenDeivceResult();
		Log.e(TAG, "open device start");
		OpenResult openResult = swiperCommandController.openDevice(null);
		Log.e(TAG, "open device end");

		if (openResult.getsuccess()) {
			openDeivceResult.setSuccess(true);
			openDeivceResult.setKsn(openResult.getksn());
			return openDeivceResult;
		}

		openDeivceResult.setErrorCode(Integer.valueOf(
				openResult.geterrcode().getErrorCode()).toString());
		return openDeivceResult;
	}

	public DeviceInfo getDeviceInfo() {

		DeviceInfo deviceInfoReturn = new DeviceInfo();

		OpenDeivceResult openDeivceResult = openDevice(null);

		if (!openDeivceResult.isSuccess()) {
			deviceInfoReturn.setResponseCode("openError_"
					+ openDeivceResult.getErrorCode());

			return deviceInfoReturn;
		}

		com.NLmpos.API.Hefu.DeviceInfo deviceInfo = swiperCommandController
				.getDeviceInfo();
		if (deviceInfo == null) {
			return deviceInfoReturn;
		}

		deviceInfoReturn.setKsn(deviceInfo.getKSN());

		if (deviceInfo.getdatakeystat() == 0) {
			deviceInfoReturn.setInitDataKey(true);
		} else {
			deviceInfoReturn.setInitDataKey(false);
		}

		if (deviceInfo.getpinkeystat() == 0) {
			deviceInfoReturn.setInitPinKey(true);
		} else {
			deviceInfoReturn.setInitPinKey(false);
		}

		if (deviceInfo.getinitstat() == 0) {
			deviceInfoReturn.setInitMasterKey(true);
		} else {
			deviceInfoReturn.setInitMasterKey(false);
		}
		return deviceInfoReturn;
	}

	public boolean loadKey(String keyType, byte[] keyData, byte[] checkValue) {
		LoadKeyResult loadKeyResult = swiperCommandController.loadKey(
				covertKeyTypes(keyType), keyData, checkValue);
		if (loadKeyResult.getsuccess()) {
			return true;
		} else {
			return false;
		}
	}

	private int covertKeyTypes(String keyType) {

		if (keyType.equals(MsrKeyTypes.DATA_KEY)) {
			return 1;
		}

		if (keyType.equals(MsrKeyTypes.PIN_KEY)) {
			return 2;

		}

		if (keyType.equals(MsrKeyTypes.MASTER_KEY)) {
			return 0;

		}
		return 0;
	}

	public boolean isSupportDolby() {
		return false;
	}

	public void startRecord(String traceNo) {
		swiperCommandController.startRecord(traceNo);
	}

	public void stopRecord() {
		swiperCommandController.stopRecord();
	}

	public int getInputType() {
		return AposSwiperContext.INPUT_CARD_READER;
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
