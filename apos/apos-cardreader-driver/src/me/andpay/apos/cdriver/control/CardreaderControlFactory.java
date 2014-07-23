package me.andpay.apos.cdriver.control;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.cdriver.CardReaderTypes;

public class CardreaderControlFactory {

	private static Map<Integer, CardReaderControl> cardReaderControls = new HashMap<Integer, CardReaderControl>();

	public static CardReaderControl getCardreaderControl(int cardReaderType) {

		CardReaderControl cardReaderControl = cardReaderControls.get(Integer
				.valueOf(cardReaderType));
		if (cardReaderControl != null) {
			return cardReaderControl;
		}
		if (cardReaderType == CardReaderTypes.NEW_LAND) {
			cardReaderControl = new NewlandCardReaderControl();
		} else if (cardReaderType == CardReaderTypes.ITRON) {
			cardReaderControl = new ItronCardReaderControl();

		} else if (cardReaderType == CardReaderTypes.CLOUD_POS) {
			cardReaderControl = new CloudPosReaderControl();

		} else if (cardReaderType == CardReaderTypes.LANDI) {
			cardReaderControl = new LandiCardReaderControl();

		} else if (cardReaderType == CardReaderTypes.NEW_LAND_AD) {
			cardReaderControl = new NewlandAudioCardReaderControl();

		} else if (cardReaderType == CardReaderTypes.NEW_LAND_BL) {
			cardReaderControl = new NewlandBluetoothCardReaderControl();

		}
		cardReaderControls.put(Integer.valueOf(cardReaderType),
				cardReaderControl);

		if (cardReaderControl == null) {
			throw new RuntimeException("can not find cardReader!");
		}

		return cardReaderControl;
	}

}
