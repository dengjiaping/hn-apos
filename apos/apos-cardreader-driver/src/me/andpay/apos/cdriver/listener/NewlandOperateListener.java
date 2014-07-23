package me.andpay.apos.cdriver.listener;

import java.util.Map;

import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.cdriver.control.NewlandAudioCardReaderControl;
import me.andpay.apos.cdriver.control.NewlandBluetoothCardReaderControl;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.cdriver.model.ModelConverter;
import me.andpay.timobileframework.util.HexUtils;
import android.util.Log;

import com.NLmpos.API.Hefu.ICCardDataInfo;
import com.NLmpos.API.Hefu.OperateRequest;
import com.NLmpos.API.Hefu.OperateResponse;
import com.NLmpos.API.Hefu.UserOperateListener;
import com.NLmpos.Controller.ICCardTxnController;
import com.NLmpos.Data.ErrorCode;

public class NewlandOperateListener implements UserOperateListener {

	private static final String TAG = NewlandAudioCardReaderControl.class
			.getName();

	private NewlandBluetoothCardReaderControl newlandBluetoothCardReaderControl;

	public void DataReceive(OperateResponse reOperateResponse) {

		AposSwiperContext aposSwiperContext = CardReaderManager
				.getCurrSwiperContext();

		CardInfo cardInfo = null;
		
		if (reOperateResponse.getmode() == OperateRequest.GET_PIN) {
			cardInfo = aposSwiperContext.getCardInfo();

		} else if (reOperateResponse.getmode() == OperateRequest.GET_GROUP) {
			cardInfo = new CardInfo();
			cardInfo.setEncTracks(reOperateResponse.getallTrackData());
			cardInfo.setRandomNumber(aposSwiperContext.getTermTraceNo());

		}
		// 如果密码为空，设备端返回24个F
		if (reOperateResponse.getpinData() != null
				&& !"FFFFFFFFFFFFFFFFFFFFFFFF".equals(reOperateResponse
						.getpinData())) {
			cardInfo.setPin(HexUtils.hexString2Bytes(reOperateResponse
					.getpinData()));
		}
		cardInfo.setKsn(reOperateResponse.getksn());
		cardInfo.setPinRandNumber(aposSwiperContext.getTermTraceNo());
		
		CardReaderManager.getCurrCallback().onDecodeCompleted(cardInfo);
		
	}

	public void onDataReceiveing(int arg0) {

	}

	public void onDeviceConnect() {
		if (CardReaderManager.getDeviceCommType() == DeviceCommunicationTypes.COMM_AUDIO) {
			CardReaderManager.getCurrCallback().onDevicePlugged();
		}
	}

	public void onDeviceDisconnect() {

		CardReaderManager.getCurrCallback().onDeviceUnplugged();
	}

	public void onError(ErrorCode errorCode) {

		if (errorCode.getErrorCode() == ErrorCode.CMD_CANCEL.getErrorCode()) {
			CardReaderManager.getCurrCallback().onCancel();
			return;
		}

		if (errorCode.getErrorCode() == ErrorCode.CMD_SEND_DATA_ERR
				.getErrorCode()) {
			CardReaderManager.getCurrCallback().onSendDataError();
			return;
		}
		
		if (errorCode.getErrorCode() == ErrorCode.CMD_KEY_NOLOAD.getErrorCode()) {
			CardReaderManager.getCurrCallback().onDeviceError(
					"设备通讯不稳定，数据接收失败，请重试！", errorCode.toString());
			return;
		}

		if (errorCode.getErrorCode() == ErrorCode.CMD_PBOC_FAILED
				.getErrorCode()
				|| errorCode.getErrorCode() == ErrorCode.CMD_FALLBACK_FORBIDDEN
						.getErrorCode()) {
			CardReaderManager.getCurrCallback().onDeviceError(
					"IC卡交易出现错误,请检查IC卡正确插入后重试。", errorCode.toString());
			return;
		}

		if (errorCode.getErrorCode() == ErrorCode.CMD_SENCONDISSUANCE_FAILED
				.getErrorCode()) {
 			CardReaderManager.clearScreen();
			CardReaderManager.showLCD("\n       处理失败", 2);
			
			CardReaderManager.getCurrCallback().onSecondIssuanceError();

			return;
		}

		CardReaderManager.getCurrCallback().onDeviceError(null,
				errorCode.toString());

	}

	public void initKeyError(String errorMsg) {

		CardReaderManager.getCurrCallback().initKeyError(errorMsg);
	}

	public void onSendDataSuccess(int arg0) {

		Log.e(TAG, "send data success");
		CardReaderManager.getCurrCallback().onShowSwiper();
	}

	public void onTimeout(int arg0) {
		CardReaderManager.getCurrCallback().onTimeout();
	}

	public void onConnectError() {
		CardReaderManager.getCurrCallback().onConnectError();
	}

	public void onProgress(Map arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	public void onICFinished(boolean finish, ICCardDataInfo icCardDataInfo) {


 		if (finish) {
			CardReaderManager.getCurrCallback().onICFinished(finish,
					ModelConverter.covertAposIcCardDataInfo(icCardDataInfo));
		} else {
			CardReaderManager.getCurrCallback().onSecondIssuanceError();

		}

	}

	public void onICRequestOnline(ICCardTxnController icCardTxnController,
			ICCardDataInfo icCardDataInfo) {
		Log.e(this.getClass().getName(), "end swiper");
		
		

	
		AposSwiperContext aposSwiperContext = CardReaderManager
				.getCurrSwiperContext();
		AposICCardDataInfo aposICCardDataInfo = ModelConverter
				.covertAposIcCardDataInfo(icCardDataInfo);
		// 如果密码为空，设备端返回24个F
		if ("FFFFFFFFFFFFFFFFFFFFFFFF".equals(icCardDataInfo.getPinData())) {
			aposICCardDataInfo.setPinData(null);

		}
		aposICCardDataInfo.setTerminalTraceNo(aposSwiperContext
				.getTermTraceNo());
		String trackAll = CardReaderManager.fetchEncryptSecTrackInfo(aposSwiperContext
				.getTermTraceNo());
		if(trackAll == null) {
			CardReaderManager.getCurrCallback().onDeviceError("无法获取IC卡信息。","201");
			return;
		}
		aposICCardDataInfo.setTrackAll(trackAll);

		newlandBluetoothCardReaderControl
				.setIcCardTxnController(icCardTxnController);
		CardReaderManager.getCurrCallback().onICRequestOnline(
				aposICCardDataInfo);

		CardReaderManager.clearScreen();
		CardReaderManager.showLCD("      交易处理中\n    请不要拔出IC卡", 120); 

	}

	public void onICSwipeRefuse() {
		CardReaderManager.getCurrCallback().onICSwipeRefuse();
	}

	public void onRequestSelectApplication(ICCardTxnController arg0,
			ICCardDataInfo arg1) {
	}

	public void setNewlandBluetoothCardReaderControl(
			NewlandBluetoothCardReaderControl newlandBluetoothCardReaderControl) {
		this.newlandBluetoothCardReaderControl = newlandBluetoothCardReaderControl;
	}

}
