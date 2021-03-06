package me.andpay.apos.merchantservice.data;

import me.andpay.apos.base.BaseDataJson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户基本信息
 * 
 * @author Administrator
 *
 */
public class UserBaseInformation implements BaseDataJson {

	private String merchantName = "";// 商户姓名
	private String name = "";// 姓名
	private String idCard = "";// id卡
	private String adress = "";// 地址
	private String phoneNumber = "";// 手机号码
	private String accountBank="";//开口行
	private String accountNo="";//开户账号
	private String accountHolder="";//开户人

	public void parse(JSONObject jo) {
		// TODO Auto-generated method stub

		try {
			merchantName = jo.getString("merchantName");
			name = jo.getString("name");
			idCard = jo.getString("idCard");
			adress = jo.getString("adress");
			phoneNumber = jo.getString("phoneNumber");
			accountBank = jo.getString("accountBank");
			accountNo=jo.getString("accountNo");
			accountHolder=jo.getString("accountHolder");
			
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String page() {
		// TODO Auto-generated method stub
		return null;
	}
	

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	
	

}
