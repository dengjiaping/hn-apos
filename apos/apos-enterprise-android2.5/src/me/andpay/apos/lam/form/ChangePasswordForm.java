package me.andpay.apos.lam.form;

import me.andpay.apos.R;
import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.annotation.ParamId;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate.TRANSTYPE;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

@FormInfo(domain = "/lam/changepwd.action", action = "changePassword", formName = "changePasswordForm")
@FieldErrorMsgTranslate(transType = TRANSTYPE.RESOURCE, resouceInfo = "properties/changePwdErrorMsg.properties")
public class ChangePasswordForm {

	/**
	 * 新密码
	 */
	@ParamId(R.id.lam_new_password_edit)
	@FieldValidate.REQUIRED
	@FieldValidate.STRRANGE(max = 20, min = 8)
	@FieldValidate.MASK(pattern = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[\\w]")
	private String newPassword;
	/**
	 * 旧密码
	 */
	@ParamId(R.id.lam_old_password_edit)
	@FieldValidate.REQUIRED
	private String oldPasswod;

	/**
	 * 重复密码
	 */
	@ParamId(R.id.lam_repeat_password_edit)
	@FieldValidate.REQUIRED
	@FieldValidate.EQUALSFIELD(paramName = "newPassword")
	private String reNewPasswor;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPasswod() {
		return oldPasswod;
	}

	public void setOldPasswod(String oldPasswod) {
		this.oldPasswod = oldPasswod;
	}

	public String getReNewPasswor() {
		return reNewPasswor;
	}

	public void setReNewPasswor(String reNewPasswor) {
		this.reNewPasswor = reNewPasswor;
	}
}
