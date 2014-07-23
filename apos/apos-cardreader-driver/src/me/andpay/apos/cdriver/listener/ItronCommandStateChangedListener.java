package me.andpay.apos.cdriver.listener;

import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.callback.CardReaderCallback;
import me.andpay.ti.util.SleepUtil;
import me.andpay.timobileframework.util.HexUtils;
import android.util.Log;

import com.itron.protol.android.CommandStateChangedListener;

public class ItronCommandStateChangedListener implements
		CommandStateChangedListener {

	private static final String TAG = ItronCommandStateChangedListener.class
			.getName();

	private long waitTokenTime;

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
			Log.e(TAG, "start sleep");
			SleepUtil.sleep(8 * 1000l);
			Log.e(TAG, "end sleep");
			if (token == waitTokenTime) {

				CardReaderManager.getCurrCallback().onSendDataError();
			}
		}

	}

	public void onSendDataError() {
		CardReaderManager.getCurrCallback().onSendDataError();
	}

	public void onDecodeCompleted(CardInfo cardInfo) {
		CardReaderManager.getCurrCallback().onDecodeCompleted(cardInfo);

	}

	public void OnCheckCRCErr() {
	}

	public void OnConnectErr() {

	}

	public void OnDevicePlug() {

		CardReaderManager.getCurrCallback().onDevicePlugged();
	}

	public void OnDevicePresent() {
		System.out.println("asdd");
	}

	public void OnDeviceUnPlug() {
		CardReaderManager.getCurrCallback().onDeviceUnplugged();

	}

	public void OnDeviceUnPresent() {
		System.out.println("asdd");
	}

	public void OnKeyError() {
		System.out.println("asd");

	}

	public void OnNoAck() {
		System.out.println("asd");
	}

	public void OnPrinting() {
		// TODO Auto-generated method stub

	}

	public void onCancel() {
		CardReaderManager.getCurrCallback().onCancel();
	}

	public void OnTimeout() {
		CardReaderManager.getCurrCallback().onTimeout();

		System.err.println("asd");
	}

	public void OnWaitingOper() {

		waitTokenTime += 1;
		CardReaderManager.getCurrCallback().onWaitingForCardSwipe();
	}

	public void OnWaitingPin() {
		// TODO Auto-generated method stub

	}

	public void OnWaitingcard() {
		System.out.println("asdasd");

	}

	public void onGetCardNo(String arg0) {
		System.out.println("asd");

	}

	public void onGetKsn(String arg0) {
		// TODO Auto-generated method stub

	}

	public void onReceiveDataStart() {
		CardReaderManager.getCurrCallback().onReceiveDataStart();
	}

	public void onDeviceError() {
		CardReaderManager.getCurrCallback().onDeviceError(null, null);
	}

}
