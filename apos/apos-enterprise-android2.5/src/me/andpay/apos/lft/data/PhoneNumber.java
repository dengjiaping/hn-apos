package me.andpay.apos.lft.data;

public class PhoneNumber implements BaseData {
	private String displayName = "";// 显示姓名
	private String displayNumber = "";// 显示号码

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayNumber() {
		return displayNumber;
	}

	public void setDisplayNumber(String displayNumber) {
		this.displayNumber = displayNumber;
	}

}
