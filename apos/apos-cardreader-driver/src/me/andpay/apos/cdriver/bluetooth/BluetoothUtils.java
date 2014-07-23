package me.andpay.apos.cdriver.bluetooth;

import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.ti.util.SleepUtil;
import android.bluetooth.BluetoothAdapter;

public class BluetoothUtils {
	
	public static final String NEWLAND_PREFIX = "ME30-";
	public static final String LANDI_PREFIX = "M35";
	
	
	public static void startBluetooth(BluetoothAdapter adapter) {
		
		if(!adapter.isEnabled()) {
			int times = 0;
			adapter.enable();
			
			while(times <6 ||adapter.getState() != BluetoothAdapter.STATE_ON) {
				SleepUtil.sleep(500);
				times ++;
			}
		}
	}
	
	
	public static String selectBluetoothPrefixCHName(int cardreaderType) {
		if(cardreaderType == CardReaderTypes.LANDI) {
			return LANDI_PREFIX;
		}else if(cardreaderType == CardReaderTypes.NEW_LAND_BL) {
			return NEWLAND_PREFIX;
		}
		
		return NEWLAND_PREFIX;
	}
}
