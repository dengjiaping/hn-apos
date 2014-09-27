package me.andpay.apos.tam.form;

import me.andpay.apos.R;
import me.andpay.apos.tam.action.PostVoucherAction;
import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.annotation.IsConst;
import me.andpay.timobileframework.mvc.form.annotation.ParamId;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate.TRANSTYPE;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

@FormInfo(domain = PostVoucherAction.DOMAIN_NAME, action = PostVoucherAction.POSTVC_ACTION, formName = "postVoucherForm")
@FieldErrorMsgTranslate(transType = TRANSTYPE.RESOURCE, resouceInfo = "properties/postVoucherErrorMsg.properties")
public class PostVoucherForm {

	/**
	 * 电话
	 */
	@ParamId(R.id.tam_post_phone_edit)
	// @FieldValidate.PHONENUMBER
	private String phone;

	/**
	 * 邮箱
	 */

	@ParamId(R.id.tam_post_email_edit)
	@FieldValidate.EMAIL
	private String email;

	/**
	 * txnId
	 */
	@IsConst
	private String txnId;

	// /**
	// * 微博
	// */
	// @ParamId(R.id.tam_post_weibo_edit)
	// @ErrorCode(AposValidateErrorCodes.MICROBLOG_ERROR)
	// @FieldValidate.EMAIL
	// private String microblog;

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

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

}
