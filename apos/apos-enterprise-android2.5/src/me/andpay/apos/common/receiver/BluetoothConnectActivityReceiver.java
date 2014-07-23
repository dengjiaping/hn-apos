package me.andpay.apos.common.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothConnectActivityReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
			   BluetoothDevice btDevice = intent
					                .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			   
			   
			   

		}

	}

}
