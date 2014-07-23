package me.andpay.apos.lam.form;


import me.andpay.apos.R;
import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.annotation.ParamId;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate.TRANSTYPE;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;




@FormInfo(domain = "/lam/login.action", action = "login", formName = LoginUserForm.FORM_NAME)
@FieldErrorMsgTranslate(transType = TRANSTYPE.RESOURCE, resouceInfo = "properties/loginErrorMsg.properties")
public class LoginUserForm {
	
	public static final String FORM_NAME = "loginUserForm";
	
	/**
	 * 用户名
	 */
	@ParamId(R.id.lam_username_autotext)
	@FieldValidate.REQUIRED
	@FieldValidate.PHONENUMBER
	private String userName;
	
	/**
	 * 密码
	 */
	@ParamId(R.id.lam_passwd_edittext)
	@FieldValidate.REQUIRED
	@FieldValidate.STRRANGE(max = 200, min = 6)
//	@FieldValidate.MASK(pattern = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[\\w]")
	private  String password;
	
	/**
	 * 自动登录标志
	 */
	private boolean autoFlag;

	/**
	 * 是否加密密码
	 */
	private boolean encryptedPwd;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(boolean autoFlag) {
		this.autoFlag = autoFlag;
	}

	public boolean isEncryptedPwd() {
		return encryptedPwd;
	}

	public void setEncryptedPwd(boolean encryptedPwd) {
		this.encryptedPwd = encryptedPwd;
	}
	
	
	
}
