package me.andpay.apos.tqm.activity;

import me.andpay.apos.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

public class TxnMapActivity extends Activity {

	private MapView mMapView;
	private BaiduMap mBaiduMap;

	private static final int initSize = 24;
	private static final int readHeightPixels = 533;

	// String BAIMAP_KEY = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/**
		 * 初始化地图信息－－－－－－begin
		 */
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(this.getApplication());
		//
		// this.setContentView(R.layout.tqm_txn_detail_map_layout);

		Double latitude = this.getIntent().getDoubleExtra("longitude", 0);
		Double longitude = this.getIntent().getDoubleExtra("latitude", 0);

		String locationInfo = this.getIntent().getStringExtra("locationInfo");

		/**
		 * 设置地图中心点，即为用户交易坐标地点
		 */
		LatLng p = new LatLng(longitude, latitude);
		BaiduMapOptions options = new BaiduMapOptions();
		options.mapStatus(new MapStatus.Builder().target(p).zoom(16).build());
		mMapView = new MapView(this, options);
		setContentView(mMapView);
		mBaiduMap = mMapView.getMap();
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.com_icon_pin_point_img);
		OverlayOptions option = new MarkerOptions().position(p).icon(bitmap);
		mBaiduMap.addOverlay(option);

		DisplayMetrics metric = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int fontSize = (int) (initSize * (metric.heightPixels / metric.density / readHeightPixels));
		OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00)
				.fontSize(fontSize).fontColor(0xFFFF00FF).text(locationInfo)
				.position(p);
		mBaiduMap.addOverlay(textOption);
	}

	@Override
	protected void onDestroy() {
		// if (mBMapMan != null) {
		// mBMapMan.destroy();
		// mBMapMan = null;
		// }
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onPause() {
		// if (mBMapMan != null) {
		// mBMapMan.stop();
		// mBMapMan.destroy();
		// mBMapMan = null;
		// }
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		// if (mBMapMan != null) {
		// mBMapMan.start();
		// }
		super.onResume();
		mMapView.onResume();
	}

}
