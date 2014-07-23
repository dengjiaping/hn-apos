package me.andpay.apos.cdriver.bluetooth;

import me.andpay.apos.cdriver.control.CardReaderInfo;

public interface BluetoothSearchListener {
	
	
	public void onSeachDevice();

	
	public void searchDeviceComplete();
	
	public void searchDevice(CardReaderInfo cardReaderInfo);
}
