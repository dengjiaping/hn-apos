package me.andpay.apos.tcm.flow;

import java.io.Serializable;

public class ChallengeContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 647026861989414016L;

	/**
	 * 手机号
	 */
	private String phoneNumber;

	/**
	 * 卡主姓名
	 */
	private String name;

	/**
	 * 卡号
	 */
	private String identityId;

	/**
	 * cvv2码
	 */
	private String cvv2Id;

	/**
	 * 手机验证码
	 */
	private String verificationCode;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public String getCvv2Id() {
		return cvv2Id;
	}

	public void setCvv2Id(String cvv2Id) {
		this.cvv2Id = cvv2Id;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
}
