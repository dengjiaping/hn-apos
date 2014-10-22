package me.andpay.apos.merchantservice.data;

import org.json.JSONException;
import org.json.JSONObject;

import me.andpay.apos.base.BaseDataJson;

/**
 * 清算列表数据
 * 
 * @author Administrator
 *
 */
public class SelementOrder implements BaseDataJson {

	private String recvOrg = "";//
	private String mchtNo = "";// 商户号
	// private String termNo = "";// 终端号
	private String beginDate = "";// 开始时间
	private String endDate = "";// 结束时间
	private String mchtName = "";// 商户名称
	private String createDate = "";// 创建时间
	private String txnCnt = "";// 交易次数
	private String txnAmt = "";// 交易金额
	private String mchtFee = "";// 商户费用
	private String outAmt = "";// 结算金额

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

	// public String getTermNo() {
	// return termNo;
	// }
	//
	// public void setTermNo(String termNo) {
	// this.termNo = termNo;
	// }

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

	public String getTxnCnt() {
		return txnCnt;
	}

	public void setTxnCnt(String txnCnt) {
		this.txnCnt = txnCnt;
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

	public void parse(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			recvOrg = jo.getString("recvOrg");
			mchtNo = jo.getString("mchtNo");// 商户号
			// termNo = jo.getString("termNo");// 终端号
			beginDate = jo.getString("beginDate");// 开始时间
			endDate = jo.getString("endDate");// 结束时间
			mchtName = jo.getString("mchtName");// 商户名称
			createDate = jo.getString("createDate");// 创建时间
			txnCnt = jo.getString("txnCnt");// 交易次数
			txnAmt = jo.getString("txnAmt");// 交易金额
			mchtFee = jo.getString("mchtFee");// 手续费
			outAmt = jo.getString("outAmt");// 超支额度
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//

	}

	public String page() {
		// TODO Auto-generated method stub
		return null;
	}

}
