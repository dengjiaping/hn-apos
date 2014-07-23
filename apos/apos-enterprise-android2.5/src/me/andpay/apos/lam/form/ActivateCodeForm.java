package me.andpay.apos.lam.form;

import me.andpay.apos.R;
import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.annotation.ParamId;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;



@FormInfo(domain = "/lam/activte.action", action = "signCertWithActivateCode", formName = "activateCodeForm")
public class ActivateCodeForm {
	
	@ParamId(R.id.lam_activate_code_edit)
	@FieldValidate.REQUIRED
	@FieldValidate.STRRANGE(max = 6, min = 6)
	private String activateCode;

	public String getActivateCode() {
		return activateCode;
	}

	public void setActivateCode(String activateCode) {
		this.activateCode = activateCode;
	}
	
	
	
}
