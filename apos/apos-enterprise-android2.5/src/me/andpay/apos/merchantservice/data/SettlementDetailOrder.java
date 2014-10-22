package me.andpay.apos.merchantservice.data;

import java.util.ArrayList;

import me.andpay.apos.base.BaseDataJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 结算详细订单
 * 
 * @author Administrator
 *
 */
public class SettlementDetailOrder implements BaseDataJson {

	private String recvOrg = "";
	private String mchtNo = "";
	private String ssn = "";// 系统跟踪号
	private String txnTime = "";// 交易时间
	private String termNo = "";
	private String acctNumer = "";
	private String bank = "";
	private String txnAmt = "";// 交易金额
	private String mchtFee = "";// 商户花费
	private String outAmt = "";// 结算金额
	private String sysNo = "";
	private String txnChnl = "";
	private String txnType = "";
	private String stlBatch = "";
	private String beginDate = "";// 开始时间
	private String endDate = "";// 结算时间
	private String mchtName = "";// 商户名称
	private String createDate = "";// 创建时间

	public String getRecvOrg() {
		return recvOrg;
	}

	public void setRecvOrg(String recvOrg) {
		this.recvOrg = recvOrg;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getAcctNumer() {
		return acctNumer;
	}

	public void setAcctNumer(String acctNumer) {
		this.acctNumer = acctNumer;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getMchtFee() {
		return mchtFee;
	}

	public void setMchtFee(String mchtFee) {
		this.mchtFee = mchtFee;
	}

	public String getOutAmt() {
		return outAmt;
	}

	public void setOutAmt(String outAmt) {
		this.outAmt = outAmt;
	}

	public String getSysNo() {
		return sysNo;
	}

	public void setSysNo(String sysNo) {
		this.sysNo = sysNo;
	}

	public String getTxnChnl() {
		return txnChnl;
	}

	public void setTxnChnl(String txnChnl) {
		this.txnChnl = txnChnl;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getStlBatch() {
		return stlBatch;
	}

	public void setStlBatch(String stlBatch) {
		this.stlBatch = stlBatch;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMchtName() {
		return mchtName;
	}

	public void setMchtName(String mchtName) {
		this.mchtName = mchtName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public void parse(JSONObject jo) {
		// TODO Auto-generated method stub

		try {
			recvOrg = jo.getString("recvOrg");
			mchtNo = jo.getString("mchtNo");
			ssn = jo.getString("ssn");
			txnTime = jo.getString("txnTime");
			termNo = jo.getString("termNo");
			acctNumer = jo.getString("acctNumer");
			bank = jo.getString("bank");
			txnAmt = jo.getString("txnAmt");
			mchtFee = jo.getString("mchtFee");
			outAmt = jo.getString("outAmt");
			sysNo = jo.getString("sysNo");
			txnChnl = jo.getString("txnChnl");
			txnType = jo.getString("txnType");
			stlBatch = jo.getString("stlBatch");
			beginDate = jo.getString("beginDate");
			endDate = jo.getString("endDate");
			mchtName = jo.getString("mchtName");
			createDate = jo.getString("createDate");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String page() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获得结算详情
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static ArrayList<SettlementDetailOrder> getArrays(String jsonStr) {
		ArrayList<SettlementDetailOrder> list = new ArrayList<SettlementDetailOrder>();
		JSONArray ja;
		try {
			ja = new JSONArray(jsonStr);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				SettlementDetailOrder order = new SettlementDetailOrder();
				order.parse(jo);
				list.add(order);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
}
