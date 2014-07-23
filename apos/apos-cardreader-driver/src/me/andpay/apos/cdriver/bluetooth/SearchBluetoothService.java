package me.andpay.apos.cdriver.bluetooth;

import java.util.HashSet;
import java.util.Set;

import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import me.andpay.ti.util.SleepUtil;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class SearchBluetoothService {

	private BluetoothAdapter bluetoothAdapter;
	

	private BluetoothSearchListener bluetoothSearchListener;

	private Set<String> deviceIds = new HashSet<String>();

	private AposBroadcastReceiver currentReceiver;

	private Context context;
	
	public SearchBluetoothService(Context context) {
		this.context = context;
		this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	public class AposBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {

				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				if (deviceIds.contains(device.getAddress())
						|| !isCardreader(device.getName())) {
					return;
				}
				deviceIds.add(device.getAddress());
				CardReaderInfo cardReaderInfo = new CardReaderInfo();
				cardReaderInfo.setmIdentifier(device.getAddress());
				cardReaderInfo.setmName(device.getName());
				cardReaderInfo.setBoundState(device.getBondState());
				bluetoothSearchListener.searchDevice(cardReaderInfo);

			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				context.unregisterReceiver(this);
				if (this.equals(currentReceiver)) {
					bluetoothSearchListener.searchDeviceComplete();
				}
			}

		}

	}

	public void searchBluetooth() {
		CardReaderManager.stopCardReader(false);
		deviceIds.clear();
		currentReceiver = new AposBroadcastReceiver();
		stopSearch();
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		BluetoothUtils.startBluetooth(adapter);
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		context.registerReceiver(currentReceiver, filter);
		adapter.startDiscovery();
	}

	public void stopSearch() {
		if (bluetoothAdapter.isDiscovering()) {
			deviceIds.clear();
			bluetoothAdapter.cancelDiscovery();
			SleepUtil.sleep(1000);
		}
	}

	private boolean isCardreader(String deviceName) {

		if (me.andpay.ti.util.StringUtil.isNotBlank(deviceName)
				&& deviceName.indexOf(BluetoothUtils
						.selectBluetoothPrefixCHName(CardReaderManager
								.getCardReaderType())) >= 0) {

			return true;

		} else {
			return false;
		}
	}

	public int getDeviceCount() {
		return deviceIds.size();
	}


	public void setBluetoothSearchListener(
			BluetoothSearchListener bluetoothSearchListener) {
		this.bluetoothSearchListener = bluetoothSearchListener;
	}

	public BluetoothSearchListener getBluetoothSearchListener() {
		return bluetoothSearchListener;
	}
	
	
}
