package me.andpay.apos.lam.form;

import java.util.Map;

import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate.TRANSTYPE;

@FormInfo(domain = "/lam/activte.action", action = "sendActivite", formName = "helloForm")
@FieldErrorMsgTranslate(transType = TRANSTYPE.RESOURCE)
public class ActivateForm {

	/**
	 * 激活码
	 */
	private String activateCode;
	/**
	 * 设备信息
	 */
	private Map<String, String> deviceEnv;

	public String getActivateCode() {
		return activateCode;
	}

	public void setActivateCode(String activateCode) {
		this.activateCode = activateCode;
	}

	public Map<String, String> getDeviceEnv() {
		return deviceEnv;
	}

	public void setDeviceEnv(Map<String, String> deviceEnv) {
		this.deviceEnv = deviceEnv;
	}

}
