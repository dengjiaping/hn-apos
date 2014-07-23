package me.andpay.apos.cdriver.callback;

import java.util.List;

import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;

public class DefaultCardReaderCallBack implements CardReaderCallback {

	public void onDevicePlugged() {
		// TODO Auto-generated method stub

	}

	public void onDeviceUnplugged() {
		// TODO Auto-generated method stub

	}

	public void onTimeout() {
		// TODO Auto-generated method stub

	}

	public void onWaitingForCardSwipe() {
		// TODO Auto-generated method stub

	}

	public void onDecodeCompleted(CardInfo cardInfo) {
		// TODO Auto-generated method stub

	}

	public void onDecodeError(String msg) {
		// TODO Auto-generated method stub

	}

	public void onDeviceError(String Msg, String errorCode) {
		// TODO Auto-generated method stub

	}

	public void onCancel() {
		// TODO Auto-generated method stub

	}

	public void onDecodingStart() {
		// TODO Auto-generated method stub

	}

	public void onReceiveDataStart() {
		// TODO Auto-generated method stub

	}

	public void OnWaitingPin() {
		// TODO Auto-generated method stub

	}

	public void seachDeviceComplete(List<CardReaderInfo> cardReaderInfos) {
		// TODO Auto-generated method stub

	}

	public void seachFoundDevice(CardReaderInfo cardReaderInfo,
			List<CardReaderInfo> cardReaderInfos) {
		// TODO Auto-generated method stub

	}

	public void onSeachDevice() {
		// TODO Auto-generated method stub

	}

	public void onShowSwiper() {
		// TODO Auto-generated method stub

	}

	public void getTxnError() {
		// TODO Auto-generated method stub

	}

	public void onSendDataError() {
		// TODO Auto-generated method stub

	}

	public void matchBluetoothHelp() {
		// TODO Auto-generated method stub

	}

	public void onConnectError() {
		// TODO Auto-generated method stub

	}

	public void initKeyError(String errorMsg) {
		// TODO Auto-generated method stub

	}

	public void onICFinished(boolean finish, AposICCardDataInfo icCardDataInfo) {
		// TODO Auto-generated method stub

	}

	public void onICSwipeRefuse() {
		// TODO Auto-generated method stub

	}

	public void onICRequestOnline(AposICCardDataInfo icCardDataInfo) {
		// TODO Auto-generated method stub

	}

	public void onSecondIssuanceError() {
		// TODO Auto-generated method stub
		
	}

}
