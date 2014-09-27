package me.andpay.apos.cardreader.callback;

import java.util.List;

import me.andpay.apos.cdriver.control.CardReaderInfo;
import me.andpay.apos.common.log.OperationDataKeys;
import me.andpay.apos.scm.activity.BluetoothListActivity;

public class CardreaderSetSearchCallback extends DefaultCardReaderCallBack {

	private BluetoothListActivity bluetoothListActivity;

	@Override
	public void onSeachDevice() {
		super.onSeachDevice();

	}

	@Override
	public void seachDeviceComplete(final List<CardReaderInfo> cardReaderInfos) {
		bluetoothListActivity.stopSearch();
		if (cardReaderInfos.size() == 0) {
			bluetoothListActivity.noDevice();
			return;
		}
		// 记录蓝牙搜索结果
		StringBuffer cardReaderStr = new StringBuffer();
		for (CardReaderInfo cardReaderInfo : cardReaderInfos) {
			cardReaderStr.append(cardReaderInfo.getmName() + ",");
		}
		bluetoothListActivity
				.getCardReaderSetContext()
				.getOpLogData()
				.put(OperationDataKeys.OPKEYS_BLUE_TOOTHT_LIST,
						cardReaderStr.toString());

	}

	@Override
	public void seachFoundDevice(CardReaderInfo cardReaderInfo,
			List<CardReaderInfo> cardReaderInfos) {

		bluetoothListActivity.progressLay.setVisibility(android.view.View.GONE);

		bluetoothListActivity.getBluetoothCardReaderListAdapter().updateData(
				cardReaderInfo);
		bluetoothListActivity.getBluetoothCardReaderListAdapter()
				.notifyDataSetChanged();

	}

	public void setBluetoothListActivity(
			BluetoothListActivity bluetoothListActivity) {
		this.bluetoothListActivity = bluetoothListActivity;
	}

}
