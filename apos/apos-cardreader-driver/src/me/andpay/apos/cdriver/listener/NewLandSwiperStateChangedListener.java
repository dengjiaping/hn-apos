package me.andpay.apos.cdriver.listener;

import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.SwiperStatus;
import me.andpay.apos.cdriver.control.NewlandCardReaderControl;

import com.lakala.cswiper3.CSwiperController.CSwiperStateChangedListener;
import com.lakala.cswiper3.CSwiperController.DecodeResult;

public class NewLandSwiperStateChangedListener implements
		CSwiperStateChangedListener {

	private NewlandCardReaderControl cardReaderControl;

	public NewLandSwiperStateChangedListener(
			NewlandCardReaderControl newlandCardReaderControl) {
		this.cardReaderControl = newlandCardReaderControl;
	}

	public void onCardSwipeDetected() {
	}

	public void onDecodeCompleted(String formatID, String ksn,
			String encTracks, int track1Length, int track2Length,
			int track3Length, String randomNumber, String maskedPAN,
			String expiryDate, String cardHolderName) {

		CardInfo cardInfo = new CardInfo();

		cardInfo.setFormatID(formatID);
		cardInfo.setKsn(ksn);
		cardInfo.setEncTracks(encTracks);
		cardInfo.setTrack1Length(track1Length);
		cardInfo.setTrack2Length(track2Length);
		cardInfo.setTrack3Length(track3Length);
		cardInfo.setRandomNumber(randomNumber);
		cardInfo.setMaskedPAN(maskedPAN);
		cardInfo.setExpiryDate(expiryDate);
		cardInfo.setCardHolderName(cardHolderName);

		if (cardReaderControl.getSwiperState() != SwiperStatus.STATE_IDLE) {
			cardReaderControl.stopSwiper();
		}

		CardReaderManager.getCurrCallback().onDecodeCompleted(cardInfo);

	}

	public void onDecodeError(DecodeResult decodeResult) {

		cardReaderControl.stopSwiper();
		cardReaderControl.startSwiper(CardReaderManager.getCurrSwiperContext());

		CardReaderManager.getCurrCallback().onDecodeError("请平稳稍快速度刷卡...");
	}

	public void onDecodingStart() {
		CardReaderManager.getCurrCallback().onDecodingStart();
	}

	public void onDevicePlugged() {
		CardReaderManager.getCurrCallback().onDevicePlugged();

	}

	public void onDeviceUnplugged() {
		cardReaderControl.setInitConnect(false);
		CardReaderManager.getCurrCallback().onDeviceUnplugged();
	}

	public void onError(int errorCode, String errorMsg) {
		CardReaderManager.getCurrCallback().onDeviceError(null,
				String.valueOf(errorCode));

	}

	public void onInterrupted() {

	}

	public void onNoDeviceDetected() {

	}

	public void onTimeout() {
		CardReaderManager.getCurrCallback().onTimeout();
	}

	public void onWaitingForCardSwipe() {
		CardReaderManager.getCurrCallback().onWaitingForCardSwipe();
	}

	public void onWaitingForDevice() {

	}

	public void setCardReaderControl(NewlandCardReaderControl cardReaderControl) {
		this.cardReaderControl = cardReaderControl;
	}

}
