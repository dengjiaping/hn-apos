package me.andpay.apos.merchantservice.data;

import java.util.Map;

import me.andpay.apos.base.BaseData;

/*并账订单*/
public class MergeOrder implements BaseData {
	private String acctDate="";// 并账日期
	private String jAmt="";// 借方金额
	private String dAmt="";// 贷方金额
	private String memo="";// 并账说明

	public String getAcctDate() {
		return acctDate;
	}

	public void setAcctDate(String acctDate) {
		this.acctDate = acctDate;
	}

	public String getjAmt() {
		return jAmt;
	}

	public void setjAmt(String jAmt) {
		this.jAmt = jAmt;
	}

	public String getdAmt() {
		return dAmt;
	}

	public void setdAmt(String dAmt) {
		this.dAmt = dAmt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void parse(Map map) {
		// TODO Auto-generated method stub
		acctDate = (String) map.get("acctDate");// 并账日期
		jAmt = (String) map.get("jAmt");// 借方金额
		dAmt = (String) map.get("dAmt");// 贷方金额
		memo = (String) map.get("memo");// 并账说明

	}

	public Map page() {
		// TODO Auto-generated method stub
		return null;
	}

}
