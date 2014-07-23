package me.andpay.apos.cdriver.control;

import java.math.BigDecimal;

import me.andpay.ac.term.api.sec.MsrKeyTypes;
import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.cdriver.DeviceInfo;
import me.andpay.apos.cdriver.InitIcCardResult;
import me.andpay.apos.cdriver.InitMsrKeyResult;
import me.andpay.apos.cdriver.OpenDeivceResult;
import me.andpay.apos.cdriver.bluetooth.BluetoothKeepConnect;
import me.andpay.apos.cdriver.bluetooth.BluetoothUtils;
import me.andpay.apos.cdriver.listener.NewlandOperateListener;
import me.andpay.apos.cdriver.model.AposICAppParam;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.cdriver.model.AposIcPublicKey;
import me.andpay.apos.cdriver.model.AposResultData;
import me.andpay.apos.cdriver.model.AposTerminalParam;
import me.andpay.apos.cdriver.model.ModelConverter;
import me.andpay.timobileframework.util.BeanUtils;
import me.andpay.timobileframework.util.HexUtils;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;

import com.NLmpos.API.Hefu.LoadKeyResult;
import com.NLmpos.API.Hefu.MacDataResult;
import com.NLmpos.API.Hefu.OpenResult;
import com.NLmpos.API.Hefu.OperateRequest;
import com.NLmpos.Controller.ICCardTxnController;
import com.NLmpos.Data.ErrorCode;
import com.NLmpos.Model.AddICAppParamRequest;
import com.NLmpos.Model.AddICAppParamResult;
import com.NLmpos.Model.AddPublicKeyRequest;
import com.NLmpos.Model.AddPublicKeyResult;
import com.NLmpos.Model.AddTerminalParamsRequest;
import com.NLmpos.Model.AddTerminalParamsResult;
import com.newland.andpay.controller.SwiperCommandController;

/**
 * 新大陆蓝牙控制器
 * 
 * @author cpz
 * 
 */
public class NewlandBluetoothCardReaderControl implements CardReaderControl {

	public static final String TAG = NewlandBluetoothCardReaderControl.class
			.getName();

	private NewlandOperateListener newlandOperateListener;

	private SwiperCommandController swiperCommandController;

	private BluetoothAdapter adapter;

	private BluetoothKeepConnect bluetoothKeepConnect;

	/**
	 * IC卡控制器
	 */
	private ICCardTxnController icCardTxnController;

	public void initCardReader(Context context) {
		CardReaderManager
				.setDeviceCommType(DeviceCommunicationTypes.COMM_BLUETOOTH);

		if (bluetoothKeepConnect == null) {
			bluetoothKeepConnect = new BluetoothKeepConnect();
		}

		if (newlandOperateListener == null) {
			newlandOperateListener = new NewlandOperateListener();
			newlandOperateListener.setNewlandBluetoothCardReaderControl(this);
		}

		if (swiperCommandController == null) {
			swiperCommandController = new SwiperCommandController(context);
			swiperCommandController
					.setCommunication(OperateRequest.MODEL_BLUETOOTH);
			swiperCommandController
					.setUserOperateListener(newlandOperateListener);
			adapter = BluetoothAdapter.getDefaultAdapter();

		}

	}

	public int getCardReaderType() {
		return CardReaderTypes.NEW_LAND_BL;
	}

	public boolean isInput() {
		return true;
	}

	public boolean startSwiper(AposSwiperContext txnContext) {
		Thread commitThread = new Thread(new SwiperCommitRunnable(txnContext));
		commitThread.start();
		return true;

	}

	public class SwiperCommitRunnable implements Runnable {

		private AposSwiperContext swiperContext;

		public SwiperCommitRunnable(AposSwiperContext swiperContext) {
			this.swiperContext = swiperContext;
		}

		public void run() {

			String defaultIdentifier = swiperContext.getBluetoothId();
			// 自动匹配
			Log.e(this.getClass().getName(), "start swiper");
			if (me.andpay.ti.util.StringUtil.isNotBlank(defaultIdentifier)) {
				if (bluetoothKeepConnect.openDevice(defaultIdentifier)
						.isSuccess()) {
					InitMsrKeyResult initMsrKeyResult = CardReaderManager
							.getInitMsrKeyService().initMsrKey(
									defaultIdentifier);
					if (initMsrKeyResult.isSuccess()) {
						//暂时去掉IC灌装
//						InitIcCardResult result = CardReaderManager
//								.getInitMsrKeyService().initIcCard(
//										defaultIdentifier);
//						if (result.isSuccess()) {
//							sendToSwiper(swiperContext);
//						} else {
//							
//							newlandOperateListener.initKeyError(result
//									.getErrorMsg());
//						}
						sendToSwiper(swiperContext);	
					} else {
						newlandOperateListener.initKeyError(initMsrKeyResult
								.getErrorMsg());
						return;
					}

				} else {
					newlandOperateListener.onConnectError();
				}
			} else {
				searchDevice(swiperContext);
			}
		}

	}

	private void sendToSwiper(AposSwiperContext swiperContext) {

		if (swiperContext.isNeedPin() && swiperContext.getPinErrorCount() < 3
				&& swiperContext.getPinErrorCount() > 0&&!swiperContext.isIcCardTxn()) {
			OperateRequest operateRequest = new OperateRequest();
			BigDecimal amt = BigDecimal.ZERO;
			if (swiperContext.getSalesAmt() != null) {
				amt = swiperContext.getSalesAmt();
				amt = amt.multiply(new BigDecimal("100")).setScale(0);

			}
			operateRequest.setamt(amt == null ? null : amt.toString());
			operateRequest.setinputMaxLen(6);
			operateRequest.setmode(OperateRequest.GET_PIN);
			operateRequest.setinputPinTimeout(120);
			operateRequest.settraceNo(swiperContext.getTermTraceNo());
			operateRequest.setpan(swiperContext.getCardNo());
			swiperCommandController.startUserOperate(operateRequest);
		} else {
			
			Log.e(this.getClass().getName(), "start swiper");
			OperateRequest operateRequest = new OperateRequest();
			BigDecimal amt = BigDecimal.ZERO;
			if (swiperContext.getSalesAmt() != null) {
				amt = swiperContext.getSalesAmt();
				amt = amt.multiply(new BigDecimal("100")).setScale(0);

			}
			operateRequest.setamt(amt == null ? null : amt.toString());
			operateRequest.setinputMaxLen(6);
			operateRequest.setswiperTimeout(60);
			operateRequest.setinputPinTimeout(120);
			operateRequest.setmode(OperateRequest.GET_GROUP);
			operateRequest.settraceNo(swiperContext.getTermTraceNo());

			swiperCommandController.startUserOperate(operateRequest);
		}
	}

	private OpenDeivceResult tryOpenDevice(String defaultIdentifier) {

		Log.e(this.getClass().getName(), "try open device");

		int MAX_TRY_TIMES = 1;
		int tryTimes = 0;
		OpenResult openResult = null;
		OpenDeivceResult openDeivceResult = new OpenDeivceResult();
		while (tryTimes < MAX_TRY_TIMES) {
			tryTimes++;
			Log.e(TAG, "start open device");
			openResult = swiperCommandController.openDevice(defaultIdentifier);
			Log.e(TAG, "end open device");

			if (ErrorCode.CMD_SUCCESS.equals(openResult.geterrcode())) {
				openDeivceResult.setSuccess(true);
				return openDeivceResult;
			}
		}

		openDeivceResult.setErrorCode(Integer.valueOf(
				openResult.geterrcode().getErrorCode()).toString());
		openDeivceResult.setSuccess(false);
		return openDeivceResult;

	}

	private void searchDevice(AposSwiperContext swiperContext) {

		swiperCommandController.checkDevice();
		CardReaderManager.getSearchBluetoothService()
				.getBluetoothSearchListener().onSeachDevice();
		CardReaderManager.getSearchBluetoothService().searchBluetooth();
	}

	public void stopSwiper() {
		CardReaderManager.getSearchBluetoothService().stopSearch();
		if (bluetoothKeepConnect.isConnect()) {
			swiperCommandController.stopUserOperate();
		}
	}

	public int getSwiperState() {
		return 0;
	}

	public boolean isInitConnect() {
		return false;
	}

	public boolean isDevicePresent() {
		return false;
	}

	public void stopCardReader() {
		Log.e(this.getClass().getName(), "stopBluetooth");
		CardReaderManager.getSearchBluetoothService().stopSearch();
		swiperCommandController.closeDevice();
	}

	public void searchDevice() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				searchDevice(null);
			}
		});

		thread.start();

	}

	public OpenDeivceResult openDevice(String defaultIdentifier) {
		BluetoothUtils.startBluetooth(adapter);

		return this.tryOpenDevice(defaultIdentifier);
	}

	public DeviceInfo getDeviceInfo() {

		DeviceInfo deviceInfo = new DeviceInfo();

		com.NLmpos.API.Hefu.DeviceInfo newlandDev = swiperCommandController
				.getDeviceInfo();
		if (newlandDev == null) {
			return deviceInfo;
		}

		if (newlandDev.getdatakeystat() == 0) {
			deviceInfo.setInitDataKey(true);
		} else {
			deviceInfo.setInitDataKey(false);
		}

		if (newlandDev.getpinkeystat() == 0) {
			deviceInfo.setInitPinKey(true);
		} else {
			deviceInfo.setInitPinKey(false);
		}

		if (newlandDev.getinitstat() == 0) {
			deviceInfo.setInitMasterKey(true);
		} else {
			deviceInfo.setInitMasterKey(false);

		}
		
		if (newlandDev.getMacKInit() == 0) {
			deviceInfo.setInitMacKey(true);
		} else {
			deviceInfo.setInitMacKey(false);

		}
		
		deviceInfo.setAppVersion(newlandDev.getAppVer());
		deviceInfo.setKsn(newlandDev.getKSN());

		Log.e(TAG, "ksn=" + deviceInfo.getKsn());

		return deviceInfo;
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

	public AposResultData addICAppParam(AposICAppParam aposICAppParam) {

		AddICAppParamRequest addReqeust = BeanUtils.copyProperties(
				AddICAppParamRequest.class, aposICAppParam);
		AddICAppParamResult result = swiperCommandController
				.addICAppParam(addReqeust);

		AposResultData aposResultData = new AposResultData();
		if (result.getErrorCode() != null) {
			aposResultData.setErrorCode(String.valueOf(result.getErrorCode()
					.getErrorCode()));
		}
		if (result.getSuccess() == 0) {
			aposResultData.setSuccess(true);
		}

		return aposResultData;
	}

	public AposResultData addICPublicKey(AposIcPublicKey aposIcPublicKey) {

		AddPublicKeyRequest addPublicKeyRequest = BeanUtils.copyProperties(
				AddPublicKeyRequest.class, aposIcPublicKey);

		AddPublicKeyResult result = swiperCommandController
				.addICPublicKey(addPublicKeyRequest);

		AposResultData aposResultData = new AposResultData();
		if (result.getErrorCode() != null) {
			aposResultData.setErrorCode(String.valueOf(result.getErrorCode()
					.getErrorCode()));
		}
		if (result.getSuccess() == 0) {
			aposResultData.setSuccess(true);
		}

		return aposResultData;
	}

	public AposResultData addTerminalParams(AposTerminalParam aposTerminalParam) {

		AddTerminalParamsRequest addTerminalParamsRequest = BeanUtils
				.copyProperties(AddTerminalParamsRequest.class,
						aposTerminalParam);

		AddTerminalParamsResult result = swiperCommandController
				.addTerminalParams(addTerminalParamsRequest);

		AposResultData aposResultData = new AposResultData();
		if (result.getErrorCode() != null) {
			aposResultData.setErrorCode(String.valueOf(result.getErrorCode()
					.getErrorCode()));
		}
		if (result.getSuccess() == 1) {
			aposResultData.setSuccess(true);
		}

		return aposResultData;

	}

	public void secondIssuance(AposICCardDataInfo aposICCardDataInfo) {
		if (icCardTxnController != null) {
			icCardTxnController.secondIssuance(ModelConverter
					.covertIcCardDataInfo(aposICCardDataInfo));
		}

	}

	private int covertKeyTypes(String keyType) {

		if (keyType.equals(MsrKeyTypes.DATA_KEY)) {
			return 1;
		}

		if (keyType.equals(MsrKeyTypes.PIN_KEY)) {
			return 2;

		}
		
		if (keyType.equals(MsrKeyTypes.MAC_KEY)) {
			return 3;

		}

		if (keyType.equals(MsrKeyTypes.MASTER_KEY)) {
			return 0;
		}
		return 0;
	}

	public void setInitConnect(boolean initConnect) {
		// TODO Auto-generated method stub

	}

	public boolean isSupportDolby() {
		return true;
	}

	public void startRecord(String traceNo) {
		// TODO Auto-generated method stub

	}

	public void stopRecord() {
		// TODO Auto-generated method stub

	}

	public int getInputType() {
		return AposSwiperContext.INPUT_CARD_READER;
	}

	public void setIcCardTxnController(ICCardTxnController icCardTxnController) {
		this.icCardTxnController = icCardTxnController;
	}

	public void showLCD(String showMsg, int showTime) {
		swiperCommandController.showLCD(showMsg,showTime);
	}

	public void clearScreen() {
		Log.e(this.getClass().getName(), "clearScreen");
		swiperCommandController.clearScreen();
	}
	
	public String fetchEncryptSecTrackInfo(String traceNo) {
		byte[] data = swiperCommandController.fetchEncryptSecTrackInfo(traceNo);
		if(data == null) {
			return null;
		}
		
		return HexUtils.bytesToHexString(data);
	}
	

	public AposResultData calculateMac(String traceNo, String data) {
		MacDataResult result = swiperCommandController.calculateMac(traceNo, data);
		
		AposResultData aposResultData = new AposResultData();
		if (result.getErrorCode() != null) {
			aposResultData.setErrorCode(String.valueOf(result.getErrorCode()
					.getErrorCode()));
		}
		aposResultData.setSuccess(result.isSuccess());
		aposResultData.setData(result.getMac());
		return aposResultData;
	}

}
