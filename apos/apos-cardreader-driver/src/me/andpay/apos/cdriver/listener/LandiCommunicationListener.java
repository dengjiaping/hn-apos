package me.andpay.apos.cdriver.listener;

import java.io.UnsupportedEncodingException;

import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.cdriver.control.LandiReceiveCallback;
import me.andpay.apos.cdriver.tlvmodel.GetOffileTxnResponse;
import me.andpay.apos.cdriver.tlvmodel.GetPinResponse;
import me.andpay.apos.cdriver.tlvmodel.SwiperResonse;
import me.andpay.ti.util.SleepUtil;
import me.andpay.timobileframework.util.HexUtils;
import me.andpay.timobileframework.util.tlv.TlvUtil;

import com.landicorp.android.mpay.commmanager.CommunicationCallBack;

public class LandiCommunicationListener implements CommunicationCallBack {

	private long waitTokenTime;

	private LandiReceiveCallback landiReceiveCallback;

	public void waitSend(long time) {
		waitTokenTime = time;
		Thread thread = new Thread(new WaitTokenRunnable(time));
		thread.start();
	}

	public class WaitTokenRunnable implements Runnable {
		private long token;

		public WaitTokenRunnable(long token) {
			this.token = token;
		}

		public void run() {
			SleepUtil.sleep(4 * 1000l);
			if (token == waitTokenTime) {
				CardReaderManager.getCurrCardReaderControl().stopCardReader();
				CardReaderManager.getCurrCallback().onSendDataError();
			}
		}

	}

	public void onError(int arg0, String arg1) {
		if ("Cancel success.".equals(arg1) || arg0 == 13) {
			return;
		}
		CardReaderManager.getCurrCallback().onDeviceError(null,
				String.valueOf(arg0));
	}

	public void onProgress(byte[] arg0) {

		String msg;
		try {
			msg = new String(arg0, "gb2312");
			if (msg.equals("OK") && waitTokenTime > 0) {
				waitTokenTime += 1;

				CardReaderManager.getCurrCallback().onShowSwiper();

			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onReceive(byte[] arg0) {
		String hexStr = HexUtils.bytesToHexString(arg0);

		if (landiReceiveCallback != null) {
			landiReceiveCallback.onReceiveData(hexStr);
			return;
		}

		if ("8102".equals(hexStr)) {
			CardReaderManager.getCurrCallback().onTimeout();
			return;
		} else if ("8101".equals(hexStr)) {
			CardReaderManager.getCurrCallback().onCancel();
			return;
		} else if (hexStr.length() < 5) {
			CardReaderManager.getCurrCallback().onDeviceError(null, hexStr);
			return;
		}

		String data = hexStr.substring(0, hexStr.length() - 4);
		AposSwiperContext txnContext = CardReaderManager.getCurrSwiperContext();

		if (ExtTypes.EXT_TYPE_TXN_GET.equals(txnContext.getExtType())) {

			GetOffileTxnResponse getOffileTxnResponse = TlvUtil.decodeTlv(data,
					GetOffileTxnResponse.class);

			if ("00".equals(getOffileTxnResponse.getRespnseCode())) {
				CardReaderManager.getCurrCallback().getTxnError();
				return;
			}

			txnContext.setSalesAmt(getOffileTxnResponse.getAmt());
			CardInfo cardInfo = new CardInfo();
			cardInfo.setEncTracks(getOffileTxnResponse.getEncTracks());

			if (!"FFFFFFFFFFFFFFFFFFFFFFFF".equals(HexUtils.bytesToHexString(
					getOffileTxnResponse.getEncPin()).toUpperCase())) {
				cardInfo.setPin(getOffileTxnResponse.getEncPin());
				cardInfo.setPinRandNumber(txnContext.getTermTraceNo());
			}
			cardInfo.setRandomNumber(txnContext.getTermTraceNo());
			cardInfo.setKsn(getOffileTxnResponse.getKsn());

			CardReaderManager.getCurrCallback().onDecodeCompleted(cardInfo);

			return;
		}

		if (txnContext.isNeedPin() && txnContext.getPinErrorCount() < 3
				&& txnContext.getPinErrorCount() > 0) {
			GetPinResponse getPinResponse = TlvUtil.decodeTlv(data,
					GetPinResponse.class);
			CardInfo cardInfo = null;
			cardInfo.setPin(null);
			cardInfo.setPinRandNumber(null);

			if (!"FFFFFFFFFFFFFFFFFFFFFFFF".equals(HexUtils.bytesToHexString(
					getPinResponse.getEncPin()).toUpperCase())) {
				cardInfo.setPin(getPinResponse.getEncPin());
				cardInfo.setPinRandNumber(txnContext.getTermTraceNo());
			}
			CardReaderManager.getCurrCallback().onDecodeCompleted(cardInfo);

		} else {
			SwiperResonse siSwiperResonse = TlvUtil.decodeTlv(data,
					SwiperResonse.class);

			CardInfo cardInfo = new CardInfo();
			cardInfo.setEncTracks(siSwiperResonse.getEncTracks());

			if (!"FFFFFFFFFFFFFFFFFFFFFFFF".equals(HexUtils.bytesToHexString(
					siSwiperResonse.getEncPin()).toUpperCase())) {
				cardInfo.setPin(siSwiperResonse.getEncPin());
				cardInfo.setPinRandNumber(txnContext.getTermTraceNo());
			}
			cardInfo.setRandomNumber(txnContext.getTermTraceNo());
			cardInfo.setKsn(siSwiperResonse.getKsn());

			CardReaderManager.getCurrCallback().onDecodeCompleted(cardInfo);
		}

	}

	// 蓝牙暂时无效
	public void onSendOK() {
		CardReaderManager.getCurrCallback().onWaitingForCardSwipe();
	}

	public void onTimeout() {
		System.out.println("time out");
	}

	public void onConnectError() {
		CardReaderManager.getCurrCallback().onConnectError();
	}

	public void setLandiReceiveCallback(LandiReceiveCallback landiReceiveCallback) {
		this.landiReceiveCallback = landiReceiveCallback;
	}
	
	

}
