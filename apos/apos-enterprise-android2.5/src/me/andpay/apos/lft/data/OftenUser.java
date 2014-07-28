package me.andpay.apos.lft.data;
/**
 * 常用联系人
 * @author Administrator
 *
 */
public class OftenUser implements BaseData{
	private String userName;//用户名
	private String bankCardNumber;//银行号码
	private String bankName;//银行名称
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBankCardNumber() {
		return bankCardNumber;
	}
	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	

}
