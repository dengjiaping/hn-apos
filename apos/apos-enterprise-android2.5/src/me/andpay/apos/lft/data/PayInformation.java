package me.andpay.apos.lft.data;

import java.util.Map;

import me.andpay.ac.term.api.vas.txn.VasTxnPropNames;
import me.andpay.apos.base.BaseData;

public class PayInformation implements BaseData {
	/* 应缴电水费总额 */
	private String totalBills="";
	/* 单位编码 */
	private String unitCode="";
	/* 客户姓名 */
	private String userName="";
	/* 总应缴水电费 */
	private String totalPayBills="";
	/* 总违约金 */
	private String totalPenaltyContract="";
	/* 欠费月数 */
	private String oweMonths="";

	public String getTotalBills() {
		return totalBills;
	}

	public void setTotalBills(String totalBills) {
		this.totalBills = totalBills;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTotalPayBills() {
		return totalPayBills;
	}

	public void setTotalPayBills(String totalPayBills) {
		this.totalPayBills = totalPayBills;
	}

	public String getTotalPenaltyContract() {
		return totalPenaltyContract;
	}

	public void setTotalPenaltyContract(String totalPenaltyContract) {
		this.totalPenaltyContract = totalPenaltyContract;
	}

	public String getOweMonths() {
		return oweMonths;
	}

	public void setOweMonths(String oweMonths) {
		this.oweMonths = oweMonths;
	}

	public void parse(Map map) {
		// TODO Auto-generated method stub
		totalBills = (String) map.get(VasTxnPropNames.TOTAL_BILLS);

		unitCode = (String) map.get(VasTxnPropNames.UNIT_CODE);
		userName = (String) map.get(VasTxnPropNames.USER_NAME);
		totalPayBills = (String) map.get(VasTxnPropNames.TOTAL_PAY_BILLS);
		totalPenaltyContract = (String) map
				.get(VasTxnPropNames.TOTAL_PENALTY_CONTRACT);
		oweMonths = (String) map.get(VasTxnPropNames.OWE_MONTHS);
	}

	public Map page() {
		// TODO Auto-generated method stub
		return null;
	}

}
