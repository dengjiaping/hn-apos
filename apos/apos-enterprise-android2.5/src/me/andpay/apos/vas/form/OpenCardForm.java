package me.andpay.apos.vas.form;

import me.andpay.apos.R;
import me.andpay.apos.vas.action.OpenCardAction;
import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.annotation.ParamId;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate.TRANSTYPE;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

@FormInfo(action = OpenCardAction.OPEN_CARD, domain = OpenCardAction.DOMAIN_NAME, formName = "OpenCardForm")
@FieldErrorMsgTranslate(transType = TRANSTYPE.RESOURCE, resouceInfo = "properties/opencardErrorMsg.properties")
public class OpenCardForm {

	/**
	 * 手机号码
	 */
	@ParamId(R.id.vas_telno_text)
	@FieldValidate.PHONENUMBER
	public String phoneNo;

	/**
	 * 重输手机号码
	 */
	@ParamId(R.id.vas_repeat_telno_text)
	@FieldValidate.PHONENUMBER
	@FieldValidate.EQUALSFIELD(paramName = "phoneNo")
	public String rePhoneNo;
	/**
	 * 起始空白卡部分卡号
	 */
	/**
	 * 六位尾号
	 */

	private String startBlankPartCardNo;

	/**
	 * 截至空白卡部分卡号
	 */
	private String endBlankPartCardNo;

	/**
	 * 用户姓名
	 */
	@ParamId(R.id.vas_user_text)
	@FieldValidate.STRRANGE(max = 64, min = 0)
	public String holderName;

	/**
	 * 证件类型
	 */
	@ParamId(R.id.vas_id_type_spinner)
	public String idType;

	/**
	 * 证件号码
	 */
	@ParamId(R.id.vas_id_NO_text)
	public String idNo;

	/**
	 * 订单编号
	 */
	public Long orderId;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getRePhoneNo() {
		return rePhoneNo;
	}

	public void setRePhoneNo(String rePhoneNo) {
		this.rePhoneNo = rePhoneNo;
	}

	public String getStartBlankPartCardNo() {
		return startBlankPartCardNo;
	}

	public void setStartBlankPartCardNo(String startBlankPartCardNo) {
		this.startBlankPartCardNo = startBlankPartCardNo;
	}

	public String getEndBlankPartCardNo() {
		return endBlankPartCardNo;
	}

	public void setEndBlankPartCardNo(String endBlankPartCardNo) {
		this.endBlankPartCardNo = endBlankPartCardNo;
	}

	public String getIdType() {
		return idType;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
