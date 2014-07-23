package me.andpay.apos.common.service;

import me.andpay.ac.consts.GeoCooTypes;
import me.andpay.ac.term.api.lbs.LocateRequest;
import me.andpay.ac.term.api.lbs.LocateResult;
import me.andpay.ac.term.api.lbs.LocationCoordinate;
import me.andpay.apos.common.bug.ThrowableReporter;
import me.andpay.apos.common.service.model.TiLocation;
import me.andpay.ti.util.StringUtil;
import android.app.Application;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.inject.Inject;

/**
 * 位置服务
 * 
 * @author cpz
 * 
 */
public class LocationService {

	@Inject
	private Application application;

	private me.andpay.ac.term.api.lbs.LocationService locationService;

	private LocationClient mLocationClient;

	private LocationManager locationManager;

	// 坐标
	private TiLocation tiLocation;
	// 历史坐标缓存
	private TiLocation historyTiLocation;
	@Inject
	private ThrowableReporter throwableReporter;

	public void init() {

		if (mLocationClient == null) {
			LocationListenner myListener = new LocationListenner();
			mLocationClient = new LocationClient(
					application.getApplicationContext());
			mLocationClient.registerLocationListener(myListener);
		}
		setLocationOption();
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
		}

		if (locationManager == null) {
			locationManager = (LocationManager) application
					.getSystemService(Context.LOCATION_SERVICE);
		}
		// 重置位置数据
		historyTiLocation = tiLocation;
		tiLocation = null;

	}

	public void requestLocation() {

		init();

		mLocationClient.requestLocation();

	}

	public void unRegisterLocation() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
		if (locationManager != null) {
			locationManager.removeUpdates(lbslistener);
		}
	}

	public class LocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation bdLocation) {
			if (tiLocation == null) {
				tiLocation = new TiLocation();

				if ((bdLocation.getLatitude() < 0.00000000001 && bdLocation
						.getLatitude() > 0)
						|| (bdLocation.getLatitude() < -0.00000000001 && bdLocation
								.getLatitude() < 0)
						|| (bdLocation.getLongitude() < 0.00000000001 && bdLocation
								.getLongitude() > 0)
						|| (bdLocation.getLongitude() > -0.00000000001 && bdLocation
								.getLongitude() < 0)) {
					requestAndroidLocation();
					return;
				}
				// 原始坐标未获取到
				if (tiLocation.getLatitude() == 0) {
					if (StringUtil.isNotBlank(bdLocation.getAddrStr())) {
						tiLocation.setAddress(bdLocation.getAddrStr());
					}
					tiLocation.setSpecLatitude(bdLocation.getLatitude());
					tiLocation.setSpecLongitude(bdLocation.getLongitude());
					requestAndroidLocation();
				}
			}
		}

		public void onReceivePoi(BDLocation location) {
		}

	}

	public void requestAndroidLocation() {

		String provider = locationManager.getBestProvider(new Criteria(), true);
		if (StringUtil.isNotBlank(provider)) {
			locationManager.requestLocationUpdates(provider, 1000, 0,
					lbslistener);
		}
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setAddrType("all"); // 设置地址信息，仅设置为“all”时有地址信息，默认无地址信息
		mLocationClient.setLocOption(option);
	}

	/**
	 * 是否已获取过地址
	 * 
	 * @return
	 */
	public boolean hasLocation() {

		if (tiLocation == null && historyTiLocation == null) {
			return false;
		}

		return true;
	}

	public TiLocation getLocation() {

		if (tiLocation != null) {
			return tiLocation;
		}
		return historyTiLocation;
	}

	private LocationListener lbslistener = new LocationListener() {
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
			System.out.println("");
		}

		public void onProviderDisabled(String provider) {
			System.out.println("");
		}

		public void onLocationChanged(Location location) {
			if (location != null) {
				unRegisterLocation();
				queryAddress(location);
			}
		}

	};

	public void queryAddress(Location location) {

		final Location locationIn = location;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					if (tiLocation == null) {
						tiLocation = new TiLocation();
					}
					tiLocation.setLatitude(locationIn.getLatitude());
					tiLocation.setLongitude(locationIn.getLongitude());

					if (StringUtil.isBlank(tiLocation.getAddress())) {
						LocateRequest lbsRequest = new LocateRequest();
						lbsRequest.setLatitude(locationIn.getLatitude());
						lbsRequest.setLongitude(locationIn.getLongitude());
						LocateResult result = locationService
								.locate(lbsRequest);
						if (result.isSuccess()) {
							if (!StringUtil.isBlank(result.getAddress())) {
								tiLocation.setAddress(result.getAddress());
							}
							// else {
							// Exception exception = new Exception(
							// "server baidu can not find address!");
							// throwableReporter.submitError(exception);
							// }

						}
					}

					if (tiLocation.getLongitude() == 0) {
						LocationCoordinate srcCoord = new LocationCoordinate();
						srcCoord.setLatitude(locationIn.getLatitude());
						srcCoord.setLongitude(locationIn.getLongitude());
						srcCoord.setCoordType(GeoCooTypes.WGS_84);
						LocationCoordinate resultL = locationService
								.coordinateConvert(srcCoord, GeoCooTypes.BD_09);
						tiLocation.setSpecLatitude(resultL.getLatitude());
						tiLocation.setSpecLongitude(resultL.getLongitude());
						
					}

				} catch (Throwable e) {
					// ignore ex
					Log.e(this.getClass().getName(), "system error", e);
				}
			}
		});

		thread.start();
	}

}
