package me.andpay.apos.tam.activity;

import java.util.ArrayList;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@ContentView(R.layout.tam_select_device_layout)
public class SelectDeviceActivity extends AposBaseActivity {

	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	private ArrayList<String> ddArrayList = new ArrayList<String>();

	@InjectView(R.id.tam_device_list)
	ListView newDevicesListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Log.e("device", intent.getStringArrayListExtra("device").get(0));
		ddArrayList = intent.getStringArrayListExtra("device");
		// mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
		// R.layout.device_name);
		for (int i = 0; i < ddArrayList.size(); i++) {
			mNewDevicesArrayAdapter.add(ddArrayList.get(i));
		}
		// mNewDevicesArrayAdapter.add(intent.getStringExtra("device"));
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(ddClickListener);
	}

	// @Override
	// public void onBackPressed() {
	// // TODO Auto-generated method stub
	// super.onBackPressed();
	// Log.e("DeviceActivity", "Back Press");
	// //Intent aintent = new Intent(DeviceActivity.this, MainActivity.class);
	// setResult(-1);
	// finish();
	// }
	//
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		Log.e("devicename", intent.getStringExtra("device"));
		// ddArrayList = intent1.getStringArrayListExtra("device");
		mNewDevicesArrayAdapter.add(intent.getStringExtra("device"));
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
	}

	private OnItemClickListener ddClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			Intent aintent = new Intent(SelectDeviceActivity.this,
					TxnAcitivty.class);
			setResult(arg2 + 1, aintent);
			finish();
		}

	};

}
