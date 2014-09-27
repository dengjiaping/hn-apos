package me.andpay.apos.common.contextdata;

import java.util.Map;

public class LoginUserInfo {

	/**
	 * 用户编号
	 */
	private String userName;

	/**
	 * 用户姓名
	 */
	private String personName;

	/**
	 * 最近登录时间
	 */
	private java.util.Date lastLoginTime;

	/**
	 * 最近失败登录时间
	 */
	private java.util.Date lastFailLoginTime;

	/**
	 * 最近修改密码时间
	 */
	private java.util.Date lastChangePwdTime;

	/**
	 * 最近交易时间
	 */
	private java.util.Date lastTransactionTime;

	/**
	 * 权限集
	 */
	private Map<String, String> rights;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public java.util.Date getLastFailLoginTime() {
		return lastFailLoginTime;
	}

	public void setLastFailLoginTime(java.util.Date lastFailLoginTime) {
		this.lastFailLoginTime = lastFailLoginTime;
	}

	public java.util.Date getLastChangePwdTime() {
		return lastChangePwdTime;
	}

	public void setLastChangePwdTime(java.util.Date lastChangePwdTime) {
		this.lastChangePwdTime = lastChangePwdTime;
	}

	public java.util.Date getLastTransactionTime() {
		return lastTransactionTime;
	}

	public void setLastTransactionTime(java.util.Date lastTransactionTime) {
		this.lastTransactionTime = lastTransactionTime;
	}

	public Map<String, String> getRights() {
		return rights;
	}

	public void setRights(Map<String, String> rights) {
		this.rights = rights;
	}
}
