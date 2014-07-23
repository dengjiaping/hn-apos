package me.andpay.apos.cdriver.listener;

import java.util.ArrayList;
import java.util.List;

import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.bluetooth.BluetoothSearchListener;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import me.andpay.ti.util.SleepUtil;

public class AposBluetoothSearchListener implements BluetoothSearchListener {

	private List<CardReaderInfo> cardReaderInfos =  new ArrayList<CardReaderInfo>();;
	
	public void onSeachDevice() {
		SleepUtil.sleep(1000l);
		CardReaderManager.getCurrCallback().onSeachDevice();
	}

	public void searchDeviceComplete() {
		List<CardReaderInfo> cardReaderInfosTemp= new ArrayList<CardReaderInfo>();
		cardReaderInfosTemp.addAll(cardReaderInfos);
		cardReaderInfos.clear();
		CardReaderManager.getCurrCallback().seachDeviceComplete(cardReaderInfosTemp);

	}

	public void searchDevice(CardReaderInfo cardReaderInfo) {

		cardReaderInfos.add(cardReaderInfo);
		CardReaderManager.getCurrCallback().seachFoundDevice(cardReaderInfo,cardReaderInfos);

	}

	public void matchBluetoothHelp() {
		CardReaderManager.getCurrCallback().matchBluetoothHelp();
	}

}
