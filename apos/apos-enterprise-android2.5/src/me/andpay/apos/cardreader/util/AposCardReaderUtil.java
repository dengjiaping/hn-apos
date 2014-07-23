package me.andpay.apos.cardreader.util;

import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.CardReaderUtil;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.ti.s3.client.BCDASCII;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.context.TiContext;

public class AposCardReaderUtil {

	public static AposSwiperContext convertSwiperRequest(TxnContext txnContext,
			TiContext tiConfig) {

		AposSwiperContext siRequest = new AposSwiperContext();

 		siRequest.setCardNo(txnContext.getParseBinResp().getCardNo());
		siRequest.setExtType(txnContext.getExtType());
		siRequest.setNeedPin(txnContext.isNeedPin());
		siRequest.setPinErrorCount(txnContext.getPinErrorCount());
		siRequest.setSalesAmt(txnContext.getSalesAmt());
		siRequest.setTermTraceNo(TxnUtil.getTermTraceNo(tiConfig));
		siRequest.setIcCardTxn(	txnContext.isIcCardTxn());
	

		siRequest.setCardInfo(txnContext.getCardInfo());

		siRequest.setBase64TermTraceNo(BCDASCII.fromASCIIToBCD(siRequest
				.getTermTraceNo().getBytes(), 0, siRequest.getTermTraceNo()
				.length(), false));
		if (siRequest.getCardNo() != null) {
			String pan = CardReaderUtil.panConvert(siRequest.getCardNo());
			siRequest.setBcdCardNo(BCDASCII.fromASCIIToBCD(pan.getBytes(), 0,
					pan.length(), false));
		}

		siRequest.setBluetoothId(getBluetoothId(tiConfig));

		return siRequest;
	}

	public static AposSwiperContext genAposSwiperContext(TiContext tiConfig) {
		AposSwiperContext siRequest = new AposSwiperContext();
		siRequest.setBluetoothId(getBluetoothId(tiConfig));
		siRequest.setCardreaderType(getCardreaderType(tiConfig));
		return siRequest;
	}

	public static int getCardreaderType(TiContext tiConfig) {
		Object cardReaderTypeStr = tiConfig
				.getAttribute(ConfigAttrNames.CARD_READER_TYPE);
		int cardReaderType = 0;
		if (cardReaderTypeStr != null
				&& StringUtil.isNotBlank(cardReaderTypeStr.toString())) {
			cardReaderType = Integer.valueOf(cardReaderTypeStr.toString());
		}

		return cardReaderType;
	}

	public static String getBluetoothId(TiContext tiConfig) {
		int cardReaderType = getCardreaderType(tiConfig);

		Object bluetoothId;
		if (CardReaderTypes.NEW_LAND_BL == cardReaderType) {
			bluetoothId = tiConfig
					.getAttribute(ConfigAttrNames.NEWLAND_DEFAULT_BLUETOOTH_IDENTIFIER);

		} else {
			bluetoothId = tiConfig
					.getAttribute(ConfigAttrNames.LANDI_DEFAULT_BLUETOOTH_IDENTIFIER);

		}

		if (bluetoothId != null) {
			return bluetoothId.toString();
		}

		return null;
	}
	
	
}
