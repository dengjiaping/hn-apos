package me.andpay.apos.cardreader;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.timobileframework.mvc.context.TiContext;
import android.annotation.SuppressLint;

@SuppressLint("UseSparseArrays")
public class CardReaderResourceSelector {

	public final static String CONNECT = "CONN";
	public final static String SWIPER = "SWIPER";
	public final static String INPUTPW = "INPUTPW";
	public final static String SEARCH = "SEARCH";
	public final static String GETTXN = "GETTXN";

	/**
	 * gif提示图片
	 */
	public static Map<String, Integer> gitMap = new HashMap<String, Integer>();

	/**
	 * 设备插入拔出提示图片
	 */
	public static Map<String, Integer> showMap = new HashMap<String, Integer>();

	public static Map<Integer, String> chNames = new HashMap<Integer, String>();

	
	
	public static Map<Integer, Integer> cardreaderOpenMap = new HashMap<Integer, Integer>();


	public static Map<Integer, Integer> cardreaderConnectMap = new HashMap<Integer, Integer>();

	public static Map<Integer, Integer> cardreaderShowMap = new HashMap<Integer, Integer>();




	static {


		cardreaderShowMap.put(Integer.valueOf(CardReaderTypes.ITRON),
				R.drawable.open_device_cardreader_2_img);
		cardreaderShowMap.put(Integer.valueOf(CardReaderTypes.NEW_LAND_AD),
				R.drawable.open_device_cardreader_6_img);
		cardreaderShowMap.put(Integer.valueOf(CardReaderTypes.NEW_LAND_BL),
				R.drawable.open_device_cardreader_5_img);
		cardreaderShowMap.put(Integer.valueOf(CardReaderTypes.NEW_LAND),
				R.drawable.open_device_cardreader_1_img);
		cardreaderShowMap.put(Integer.valueOf(CardReaderTypes.LANDI),
				R.drawable.open_device_cardreader_4_img);
		cardreaderShowMap.put(Integer.valueOf(CardReaderTypes.CLOUD_POS),
				R.drawable.open_device_cardreader_3_img);

		cardreaderConnectMap.put(Integer.valueOf(CardReaderTypes.ITRON),
				R.drawable.open_device_connection_2_img);
		cardreaderConnectMap.put(Integer.valueOf(CardReaderTypes.NEW_LAND_AD),
				R.drawable.open_device_connection_6_img);
		cardreaderConnectMap.put(Integer.valueOf(CardReaderTypes.NEW_LAND_BL),
				R.drawable.open_device_connection_6_img);
		cardreaderConnectMap.put(Integer.valueOf(CardReaderTypes.NEW_LAND),
				R.drawable.open_device_connection_1_img);

		cardreaderOpenMap.put(Integer.valueOf(CardReaderTypes.ITRON),
				R.drawable.open_device_2_img);
		cardreaderOpenMap.put(Integer.valueOf(CardReaderTypes.LANDI),
				R.drawable.open_device_4_img);
		cardreaderOpenMap.put(Integer.valueOf(CardReaderTypes.NEW_LAND_AD),
				R.drawable.open_device_5_img);
		cardreaderOpenMap.put(Integer.valueOf(CardReaderTypes.NEW_LAND_BL),
				R.drawable.open_device_5_img);



		cardreaderOpenMap.put(Integer.valueOf(CardReaderTypes.ITRON), R.drawable.open_device_2_img);
		cardreaderOpenMap.put(Integer.valueOf(CardReaderTypes.LANDI), R.drawable.open_device_4_img);
		cardreaderOpenMap.put(Integer.valueOf(CardReaderTypes.NEW_LAND_AD), R.drawable.open_device_5_img);
		cardreaderOpenMap.put(Integer.valueOf(CardReaderTypes.NEW_LAND_BL), R.drawable.open_device_5_img);

		gitMap.put(CardReaderTypes.ITRON + ":" + CONNECT,
				R.drawable.tam_cardreader_itron_conn_gif);
		gitMap.put(CardReaderTypes.ITRON + ":" + SWIPER,
				R.drawable.tam_cardreader_itron_swiper_gif);
		gitMap.put(CardReaderTypes.ITRON + ":" + INPUTPW,
				R.drawable.tam_cardreader_itron_enter_gif);
		gitMap.put(CardReaderTypes.NEW_LAND + ":" + CONNECT,
				R.drawable.tam_cardreader_newland_conn_gif);
		gitMap.put(CardReaderTypes.NEW_LAND + ":" + SWIPER,
				R.drawable.tam_cardreader_newland_swiper_gif);
		gitMap.put(CardReaderTypes.CLOUD_POS + ":" + SWIPER,
				R.drawable.tam_order_upload_gif);

		gitMap.put(CardReaderTypes.LANDI + ":" + SWIPER,
				R.drawable.tam_cardreader_landi_gif);
		gitMap.put(CardReaderTypes.LANDI + ":" + SEARCH,
				R.drawable.tam_cardreader_landi_conn_gif);
		gitMap.put(CardReaderTypes.LANDI + ":" + INPUTPW,
				R.drawable.tam_cardreader_landi_enter_gif);
		gitMap.put(CardReaderTypes.LANDI + ":" + GETTXN,
				R.drawable.tam_order_upload_landi_gif);

		gitMap.put(CardReaderTypes.NEW_LAND_BL + ":" + CONNECT,
				R.drawable.tam_cardreader_newland_me30_gif);
		gitMap.put(CardReaderTypes.NEW_LAND_BL + ":" + SWIPER,
				R.drawable.tam_cardreader_chipcard_newland_me30_apos5_gif);
		gitMap.put(CardReaderTypes.NEW_LAND_BL + ":" + SEARCH,
				R.drawable.tam_cardreader_newland_me30_conn_gif);
		gitMap.put(CardReaderTypes.NEW_LAND_BL + ":" + INPUTPW,
				R.drawable.tam_cardreader_newland_me30_enter_gif);
		gitMap.put(CardReaderTypes.NEW_LAND_BL + ":" + GETTXN,
				R.drawable.tam_order_upload_newland_me30_gif);

		gitMap.put(CardReaderTypes.NEW_LAND_AD + ":" + CONNECT,
				R.drawable.tam_cardreader_newland_me30_apos6_conn_gif);
		gitMap.put(CardReaderTypes.NEW_LAND_AD + ":" + SWIPER,
				R.drawable.tam_cardreader_newland_me30_apos6_gif);
		gitMap.put(CardReaderTypes.NEW_LAND_AD + ":" + INPUTPW,
				R.drawable.tam_cardreader_newland_me30_apos6_enter_gif);

		showMap.put(CardReaderTypes.ITRON + ":" + CONNECT,
				R.drawable.com_tip_connect_two_img);
		showMap.put(CardReaderTypes.ITRON + ":" + SWIPER,
				R.drawable.com_tip_pull_out_two_img);
		showMap.put(CardReaderTypes.NEW_LAND + ":" + CONNECT,
				R.drawable.com_tip_connect_img);
		showMap.put(CardReaderTypes.NEW_LAND + ":" + SWIPER,
				R.drawable.com_tip_pull_out_img);
		showMap.put(CardReaderTypes.LANDI + ":" + CONNECT,
				R.drawable.com_tip_connect_img);
		showMap.put(CardReaderTypes.LANDI + ":" + SWIPER,
				R.drawable.com_tip_pull_out_img);
		showMap.put(CardReaderTypes.NEW_LAND_AD + ":" + CONNECT,
				R.drawable.com_tip_connect_six_img);
		showMap.put(CardReaderTypes.NEW_LAND_AD + ":" + SWIPER,
				R.drawable.com_tip_pull_out_six_img);
		showMap.put(CardReaderTypes.NEW_LAND_BL + ":" + CONNECT,
				R.drawable.com_tip_connect_six_img);
		showMap.put(CardReaderTypes.NEW_LAND_BL + ":" + SWIPER,
				R.drawable.com_tip_pull_out_six_img);

		chNames.put(CardReaderTypes.NEW_LAND, "APOS1");
		chNames.put(CardReaderTypes.ITRON, "APOS2");
		chNames.put(CardReaderTypes.CLOUD_POS, "APOS3");
		chNames.put(CardReaderTypes.LANDI, "APOS4");
		chNames.put(CardReaderTypes.NEW_LAND_BL, "APOS5");
		chNames.put(CardReaderTypes.NEW_LAND_AD, "APOS6");

	
	}

	public static int selectGit(int readerType, String gitType) {
		return gitMap.get(readerType + ":" + gitType);
	}

	public static int selectShowImg(int readerType, String imgType) {
 		return showMap.get(readerType + ":" + imgType);
	}

	public static String selectCardreaderCHName(int cardType) {
		return chNames.get(Integer.valueOf(cardType));
	}

	

	public static String getDefaultCardreaderName(TiContext tiContext,
			int cardreadType) {

		String name = null;
		if (cardreadType == CardReaderTypes.LANDI) {
			name = (String) tiContext
					.getAttribute(ConfigAttrNames.LANDI_DEFAULT_BLUETOOTH_NAME);
		} else if (cardreadType == CardReaderTypes.NEW_LAND_BL) {
			name = (String) tiContext
					.getAttribute(ConfigAttrNames.NEWLAND_DEFAULT_BLUETOOTH_NAME);
		}

		return name;
	}
	
	public static String getBluetoothIdKey(int cardreadType) {
		
		if (cardreadType == CardReaderTypes.LANDI) {
			return ConfigAttrNames.LANDI_DEFAULT_BLUETOOTH_IDENTIFIER;
		} else if (cardreadType == CardReaderTypes.NEW_LAND_BL) {
			return ConfigAttrNames.NEWLAND_DEFAULT_BLUETOOTH_IDENTIFIER;
		}
		
		return "";
	}
	
	public static String getBluetoothNameKey(int cardreadType) {
		
		if (cardreadType == CardReaderTypes.LANDI) {
			return ConfigAttrNames.LANDI_DEFAULT_BLUETOOTH_NAME;
		} else if (cardreadType == CardReaderTypes.NEW_LAND_BL) {
			return ConfigAttrNames.NEWLAND_DEFAULT_BLUETOOTH_NAME;
		}
		
		return "";
	}
	
	
	
	public static int selectCardreaderOpen(int cardType) {
		return cardreaderOpenMap.get(cardType);
	}


	public static int selectCardreaderConnect(int cardType) {
		return cardreaderConnectMap.get(cardType);
	}

	public static int selectCardreaderShow(int cardType) {
		return cardreaderShowMap.get(cardType);
	}
}
