package me.andpay.apos.cdriver.control;

import java.util.ArrayList;
import java.util.List;

import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.CardReaderUtil;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.cdriver.DeviceInfo;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.cdriver.OpenDeivceResult;
import me.andpay.apos.cdriver.bluetooth.BluetoothKeepConnect;
import me.andpay.apos.cdriver.listener.LandiCommunicationListener;
import me.andpay.apos.cdriver.model.AposICAppParam;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.cdriver.model.AposIcPublicKey;
import me.andpay.apos.cdriver.model.AposResultData;
import me.andpay.apos.cdriver.model.AposTerminalParam;
import me.andpay.apos.cdriver.tlvmodel.GetOfflineTxnRequest;
import me.andpay.apos.cdriver.tlvmodel.GetPinRequest;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.HexUtils;
import me.andpay.timobileframework.util.SwiperRequest;
import me.andpay.timobileframework.util.tlv.TlvUtil;
import android.content.Context;
import android.util.Log;

import com.landicorp.android.mpay.commmanager.CommunicationManagerBase;
import com.landicorp.android.mpay.commmanager.CommunicationManagerBase.DeviceCommunicationChannel;

public class LandiCardReaderControl implements CardReaderControl {

	private final String TAG = LandiCardReaderControl.class.getName();

	private LandiCommunicationListener landiCommunicationListener;

	/**
	 * 发送通道
	 */
	private DeviceCommunicationChannel sendChannel;

	private BluetoothKeepConnect bluetoothKeepConnect;

	public void initCardReader(Context context) {
		CardReaderManager
				.setDeviceCommType(DeviceCommunicationTypes.COMM_BLUETOOTH);

		// 先默认设置为蓝牙
		sendChannel = DeviceCommunicationChannel.BLUETOOTH;
		CommunicationManagerBase.getInstance(sendChannel, context);

		if (landiCommunicationListener == null) {
			landiCommunicationListener = new LandiCommunicationListener();
		}

		if (bluetoothKeepConnect == null) {
			bluetoothKeepConnect = new BluetoothKeepConnect();
		}

	}

	private void closeAllDevice() {
		if (CommunicationManagerBase
				.getInstance(DeviceCommunicationChannel.BLUETOOTH) != null) {
			CommunicationManagerBase.getInstance(
					DeviceCommunicationChannel.BLUETOOTH).closeResource();
			CommunicationManagerBase.getInstance(
					DeviceCommunicationChannel.BLUETOOTH).closeDevice();
		}

	}

	private void searchDevice(AposSwiperContext txnContext) {

		CardReaderManager.getSearchBluetoothService()
				.getBluetoothSearchListener().onSeachDevice();
		CardReaderManager.getSearchBluetoothService().searchBluetooth();
	}

	public int getCardReaderType() {
		return CardReaderTypes.LANDI;
	}

	public boolean isInput() {
		return true;
	}

	public class SwiperCommitRunnable implements Runnable {

		private AposSwiperContext swiperRequest;

		public SwiperCommitRunnable(AposSwiperContext swiperRequest) {
			this.swiperRequest = swiperRequest;
		}

		public void run() {

			if (StringUtil.isNotBlank(swiperRequest.getBluetoothId())) {
				if (bluetoothKeepConnect.openDevice(
						swiperRequest.getBluetoothId()).isSuccess()) {
					sendToSwiper(swiperRequest);
				} else {
					landiCommunicationListener.onConnectError();
				}
			} else {
				searchDevice(swiperRequest);
			}

		}

	}

	public OpenDeivceResult tryOpenDevice(String identifier) {
		// 由于设备的原因，必须尝试两次
		int MAX_TRY_TIMES = 2;
		int tryTimes = 0;
		int status = 0;
		OpenDeivceResult oprDeivceResult = new OpenDeivceResult();
		while (tryTimes < MAX_TRY_TIMES) {
			tryTimes++;
			Log.e(TAG, "start open device");
			status = CommunicationManagerBase.getInstance(sendChannel)
					.openDevice(identifier);
			Log.e(TAG, "end open device");

			if (status == 0) {
				oprDeivceResult.setSuccess(true);
				return oprDeivceResult;
			}
		}
		return oprDeivceResult;
	}

	public boolean startSwiper(AposSwiperContext swiperContext) {

		Thread commitThread = new Thread(
				new SwiperCommitRunnable(swiperContext));
		commitThread.start();
		return true;
	}

	/**
	 * 发送刷卡指令
	 * 
	 * @param txnContext
	 */
	private void sendToSwiper(AposSwiperContext swiperRequest) {

		if (ExtTypes.EXT_TYPE_TXN_GET.equals(swiperRequest.getExtType())) {

			GetOfflineTxnRequest offlineTxnRequest = new GetOfflineTxnRequest();
			offlineTxnRequest.setTerminalTraceNo(swiperRequest
					.getBase64TermTraceNo());

			StringBuffer cmd = new StringBuffer();

			String tlvStr = TlvUtil.encodeTvl(offlineTxnRequest);
			cmd.append("FE06000000")
					.append(HexUtils.bytesToHexString(new byte[] { new Integer(
							tlvStr.length() / 2).byteValue() })).append(tlvStr)
					.append("00");
			sendCmd2Device(cmd.toString(), false, 0);
			landiCommunicationListener.waitSend(System.currentTimeMillis());
			return;
		}

		if (swiperRequest.isNeedPin() && swiperRequest.getPinErrorCount() < 3
				&& swiperRequest.getPinErrorCount() > 0) {
			GetPinRequest getPinRequest = new GetPinRequest();
			getPinRequest.setAmt(swiperRequest.getSalesAmt());
			getPinRequest.setPan(CardReaderUtil.panConvert(swiperRequest
					.getCardNo()));
			getPinRequest.setPinTimeout(180);
			getPinRequest.setTerminalTraceNo(swiperRequest
					.getBase64TermTraceNo());

			StringBuffer cmd = new StringBuffer();

			String tlvStr = TlvUtil.encodeTvl(getPinRequest);
			cmd.append("FE02000000")
					.append(HexUtils.bytesToHexString(new byte[] { new Integer(
							tlvStr.length() / 2).byteValue() })).append(tlvStr)
					.append("00");
			landiCommunicationListener.waitSend(System.currentTimeMillis());
			sendCmd2Device(cmd.toString(), false, 0);

		} else {
			SwiperRequest swRequest = new SwiperRequest();
			swRequest.setAmt(swiperRequest.getSalesAmt());
			swRequest.setOpModel(1);

			swRequest.setTerminalTraceNo(swiperRequest.getTermTraceNo());
			swRequest.setPinTimeout(60);
			swRequest.setSwiperTimeout(180);

			StringBuffer cmd = new StringBuffer();

			String tlvStr = TlvUtil.encodeTvl(swRequest);
			cmd.append("FE04000000")
					.append(HexUtils.bytesToHexString(new byte[] { new Integer(
							tlvStr.length() / 2).byteValue() })).append(tlvStr)
					.append("00");
			landiCommunicationListener.waitSend(System.currentTimeMillis());
			sendCmd2Device(cmd.toString(), false, 0);
		}

	}

	private String sendCmd2Device(String cmdWant2Send, boolean isReceive,
			long timeOut) {

		Log.i(this.getClass().getName(), "cmd=" + cmdWant2Send);

		byte[] cmd = HexUtils.hexString2Bytes(cmdWant2Send);
		List<Byte> commandArray = new ArrayList<Byte>();
		for (int i = 0; i < cmd.length; i++) {
			commandArray.add(cmd[i]);
		}
		LandiReceiveCallback landiReceiveCallback = null;
		if (isReceive) {
			landiReceiveCallback = new LandiReceiveCallback(timeOut);
			landiCommunicationListener
					.setLandiReceiveCallback(landiReceiveCallback);
		}
		if (0 == CommunicationManagerBase.getInstance(sendChannel)
				.exchangeData(commandArray, 300 * 1000,
						landiCommunicationListener)) {
		} else {
			System.out.println("");
		}

		if (isReceive) {
			String data = landiReceiveCallback.getData();
			landiCommunicationListener.setLandiReceiveCallback(null);
			return data;
		}

		return null;
	}

	public void stopSwiper() {
		CardReaderManager.getSearchBluetoothService().stopSearch();

		if (!ExtTypes.EXT_TYPE_TXN_GET.equals(CardReaderManager
				.getCurrSwiperContext().getExtType())) {
			CommunicationManagerBase.getInstance(sendChannel).cancelExchange();

		}
	}

	public void searchDevice() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				closeAllDevice();
				searchDevice(null);
			}
		});

		thread.start();
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
		CommunicationManagerBase.getInstance(sendChannel).cancelExchange();
		closeAllDevice();
	}

	public void setInitConnect(boolean initConnect) {
		// TODO Auto-generated method stub

	}

	public OpenDeivceResult openDevice(String defaultIdentifier) {

		return tryOpenDevice(defaultIdentifier);
	}

	public DeviceInfo getDeviceInfo() {

		DeviceInfo deviceInfo = new DeviceInfo();

		if (StringUtil.isNotBlank(CardReaderManager.getCurrSwiperContext()
				.getBluetoothId())) {
			OpenDeivceResult openDeivceResult = bluetoothKeepConnect
					.openDevice(CardReaderManager.getCurrSwiperContext()
							.getBluetoothId());
			if (!openDeivceResult.isSuccess()) {
				deviceInfo.setResponseCode(openDeivceResult.getErrorCode());
				return deviceInfo;
			}
		} else {
			return deviceInfo;
		}

		StringBuffer cmd = new StringBuffer();
		cmd.append("FE01000000");
		String mydata = sendCmd2Device(cmd.toString(), true, 2000l);

		if (mydata == null) {
			return deviceInfo;
		}
		if (mydata.length() != 26) {
			deviceInfo.setResponseCode(mydata);
			return deviceInfo;
		}

		String ksn = mydata.substring(6, mydata.length() - 4);

		deviceInfo.setKsn(ksn);
		return deviceInfo;
	}

	public boolean loadKey(String keyType, byte[] keyData, byte[] checkValue) {
		// TODO Auto-generated method stub
		return false;
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
