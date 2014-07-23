package me.andpay.apos.common.service.model;

public class TiLocation {

	// 地址
	public String address;
	// 经度
	public double longitude;
	// 纬度
	public double latitude;
	// 百度经度
	public double specLongitude;
	// 百度纬度
	public double specLatitude;
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getSpecLongitude() {
		return specLongitude;
	}
	public void setSpecLongitude(double specLongitude) {
		this.specLongitude = specLongitude;
	}
	public double getSpecLatitude() {
		return specLatitude;
	}
	public void setSpecLatitude(double specLatitude) {
		this.specLatitude = specLatitude;
	}
	
	
	
	
}
