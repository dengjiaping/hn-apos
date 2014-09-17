package me.andpay.apos.ssm.form;

import me.andpay.apos.R;
import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.annotation.IsConst;
import me.andpay.timobileframework.mvc.form.annotation.ParamId;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate.TRANSTYPE;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

@FormInfo(action = "sendSettledInfo", domain = "/ssm/send.action", formName = "sendForm")
@FieldErrorMsgTranslate(transType = TRANSTYPE.RESOURCE, resouceInfo = "properties/sendFormErrorMsg.properties")
public class SendForm {

	@FieldValidate.EMAIL
	@ParamId(R.id.ssm_batch_send_mail_tv)
	private String email;

	@FieldValidate.PHONENUMBER
	@ParamId(R.id.ssm_batch_send_phone_tv)
	private String phone;

	@IsConst
	private Long batchId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
		
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

}
