package me.andpay.apos.cdriver.bluetooth;

import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.OpenDeivceResult;
import me.andpay.ti.util.SleepUtil;

import com.google.inject.Inject;

public class BluetoothKeepConnect {

	private long DELAY_TIME = 5 * 60 * 1000l - 20000;

	private long currentTime = 0;

	private Thread thread;

	private boolean connect;
	@Inject
	private CardReaderManager cardReaderManager;

	public synchronized OpenDeivceResult openDevice(String defaultIdentifier) {
		return openDeivce(defaultIdentifier);
	}

	public void registCloseDeviceTimer() {
		currentTime = System.currentTimeMillis();
		if (thread == null) {
			thread = new Thread(new Runnable() {
				public void run() {
					checkTime(DELAY_TIME);
				}
			});
			thread.start();
		}

	}

	private void checkTime(long delayTime) {
		SleepUtil.sleep(delayTime);
		long lastTime = (System.currentTimeMillis() - currentTime);
		if (lastTime >= DELAY_TIME) {
			closeDevice();
			thread = null;
		} else {
			this.checkTime(DELAY_TIME - lastTime);
		}
	}

	private OpenDeivceResult openDeivce(String defaultIdentifier) {
		OpenDeivceResult openDeivceResult = CardReaderManager
				.openDevice(defaultIdentifier);
		if (openDeivceResult.isSuccess()) {
			connect = openDeivceResult.isSuccess();
			if(CardReaderManager.getCardReaderType() != CardReaderTypes.NEW_LAND_BL) {
				registCloseDeviceTimer();
			}
		}
		return openDeivceResult;

	}

	public synchronized void closeDevice() {
		closeDeviceImpl();
	}

	private void closeDeviceImpl() {
		if (connect) {
			CardReaderManager.stopCardReader(true);
		}
		connect = false;
	}

	public boolean isConnect() {
		return connect;
	}

}
