package me.andpay.apos.cdriver.control;

import java.math.BigDecimal;

import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.cdriver.DeviceInfo;
import me.andpay.apos.cdriver.OpenDeivceResult;
import me.andpay.apos.cdriver.listener.ItronCommandStateChangedListener;
import me.andpay.apos.cdriver.model.AposICAppParam;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.cdriver.model.AposIcPublicKey;
import me.andpay.apos.cdriver.model.AposResultData;
import me.andpay.apos.cdriver.model.AposTerminalParam;
import me.andpay.ti.util.SleepUtil;
import me.andpay.timobileframework.util.HexUtils;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.itron.android.ftf.Util;
import com.itron.protol.android.CommandController;
import com.itron.protol.android.CommandReturn;

/**
 * 艾创刷卡控制器
 * 
 * @author cpz
 * 
 */
public class ItronCardReaderControl implements CardReaderControl {

	public final static String TAG = ItronCardReaderControl.class.getName();

	private ItronCommandStateChangedListener commandListener;

	private CommandController commandController;
	private Thread commitThread;

	public volatile boolean startSwiper;

	private Context context;

	public void initCardReader(Context context) {

		this.context = context;
		CardReaderManager
				.setDeviceCommType(DeviceCommunicationTypes.COMM_AUDIO);

		if (commandListener == null) {
			commandListener = new ItronCommandStateChangedListener();
		}

		if (commandController == null) {
			commandController = new CommandController(context, commandListener);
			commandController.Init(null);
		}

	}

	public int getCardReaderType() {
		return CardReaderTypes.ITRON;
	}

	/**
	 * 只判断设备是否插入
	 */
	public boolean isInput() {

		AudioManager localAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		return localAudioManager.isWiredHeadsetOn();

	}

	public boolean startSwiper(AposSwiperContext txnContext) {
		
		this.stopSwiper();

		startSwiper = true;
		while(commitThread!=null && commitThread.isAlive()) {
			SleepUtil.sleep(500);
		}
		commitThread = new Thread(new SwiperCommitRunnable(txnContext));

		commitThread.start();
		return true;

	}

	public class SwiperCommitRunnable implements Runnable {

		private byte[] ranNum;

		CardInfo cardInfo;

		private AposSwiperContext swiperRequest;

		public SwiperCommitRunnable(AposSwiperContext swiperRequest) {
			this.swiperRequest = swiperRequest;
			
			if(swiperRequest.getCardInfo() !=null)  {
				cardInfo = swiperRequest.getCardInfo();
				cardInfo.setPin(null);
				cardInfo.setPinRandNumber(null);
			}else {
				cardInfo = new CardInfo();

			}
		}

		public void run() {

			CommandReturn cmdReturn = null;
			long start = System.currentTimeMillis();

			try {
				ranNum = swiperRequest.getBase64TermTraceNo();
				Integer intAmt = null;
				if (swiperRequest.getSalesAmt() == null) {
					intAmt = 0;
				} else {
					intAmt = swiperRequest.getSalesAmt()
							.multiply(new BigDecimal(100)).intValue();
				}
				if (swiperRequest.isNeedPin()
						&& swiperRequest.getPinErrorCount() < 3
						&& swiperRequest.getPinErrorCount() > 0) {

					cardInfo.setPin(null);
					cardInfo.setPinRandNumber(null);

					cmdReturn = commandController.Get_PIN(0, 0, intAmt
							.toString().getBytes(), ranNum, swiperRequest
							.getBcdCardNo(), 180);

					
				} else {

					// commandListener.waitSend(System.currentTimeMillis());
					cardInfo = new CardInfo();
					cmdReturn = commandController.Get_ExtConOperator(1, ranNum,
							intAmt.toString().getBytes(), null, 180);
				}

			} catch (Exception e) {
				Log.e(TAG, "cardreader error", e);
				return;
			}
			if(startSwiper) {
				processCmdReturn(cmdReturn, start);
			}
		}

		private void processCmdReturn(final CommandReturn cmdReturn,
				final long startTime) {

			if (cmdReturn == null) {
				startSwiper = false;
				return;
			}

			byte result = cmdReturn.Return_Result;
			if (result == 10) {
				if (startSwiper) {
					startSwiper = false;
					commandListener.onCancel();
				}
				return;
			}
			startSwiper = false;

			if (result == -100) {
				commandListener.onDeviceError();
				return;
			}

			if (result == 1) {
				if (!isInput()) {
					return;
				}
				long endTime = System.currentTimeMillis() - startTime;
				if (endTime < 60000) {
					commandListener.onSendDataError();
				} else {
					commandListener.OnTimeout();
				}

				return;
			}
			if (result == 0) {
				commandListener.onReceiveDataStart();

				if (cmdReturn.Return_PSAMNo != null) {
					Log.e("ksn", HexUtils
							.bytesToHexString(cmdReturn.Return_PSAMNo));
					byte[] ksnBtye = cmdReturn.Return_PSAMNo;
					cardInfo.setKsn(HexUtils.bytesToHexString(ksnBtye));
				}
				if (cmdReturn.Return_PSAMTrack != null) {

				

					byte[] trackBtye = cmdReturn.Return_PSAMTrack;
					cardInfo.setRandomNumber(HexUtils
							.bytesToHexString(ranNum));
					String trackHex = HexUtils
							.bytesToHexString(trackBtye);
					cardInfo.setEncTracks(trackHex);
				}
				if (cmdReturn.Return_PSAMPIN != null
						&& !HexUtils.bytesToHexString(
								cmdReturn.Return_PSAMPIN)
								.equalsIgnoreCase(
										"ffffffffffffffffffffffff")) {

				
					byte[] pinBtye = cmdReturn.Return_PSAMPIN;
					cardInfo.setPin(pinBtye);
					cardInfo.setPinRandNumber(HexUtils
							.bytesToHexString(ranNum));
				}

				commandListener.onDecodeCompleted(cardInfo);
			} else {
				commandListener.onDeviceError();
			}

			
		}

	}

	public void stopSwiper() {

		if (startSwiper) {
			startSwiper = false;
			if (!isInput()) {
				return;
			}
			try {
				commandController.Get_CommExit();
			} catch (Exception ex) {

				Log.e(TAG, "exit", ex);
			}
		}

	}

	public int getSwiperState() {
		return 0;
	}

	public Context getContext() {
		return null;
	}

	public boolean isInitConnect() {
		return false;
	}

	public void setInitConnect(boolean initConnect) {

	}

	public boolean isDevicePresent() {

		return commandController.isDevicePresent();
	}

	public void stopCardReader() {
		commandController.ReleaseDevice();

	}

	public void searchDevice() {
		// TODO Auto-generated method stub

	}

	public OpenDeivceResult openDevice(String defaultIdentifier) {
		// TODO Auto-generated method stub
		return null;
	}

	public DeviceInfo getDeviceInfo() {
		DeviceInfo deviceInfo = new DeviceInfo();

		CommandReturn cmdret = commandController.Get_PsamNo();
		if ((cmdret != null) && (cmdret.Return_PSAMNo != null)) {
			String ksn = Util.BcdToString(cmdret.Return_PSAMNo);
			ksn = "02" + ksn.substring(0, 14);
			deviceInfo.setKsn(ksn);
		} else {
			deviceInfo.setResponseCode(cmdret == null ? null : String
					.valueOf(cmdret.Return_Result));
		}

		return deviceInfo;
	}

	public boolean loadKey(String keyType, byte[] keyData, byte[] checkValue) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSupportDolby() {
		return false;
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
		return AposSwiperContext.INPUT_CARD_READER;
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
