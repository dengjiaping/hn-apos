package me.andpay.apos.merchantservice.data;

import java.util.ArrayList;

import me.andpay.apos.base.BaseDataJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*并账订单*/
public class MergeOrder implements BaseDataJson {
	private String mchtNo = "";
	private String acctDate = "";// 并账日期
	private String mchtName = "";
	private String beginSdate = "";
	private String endSdate = "";
	private String createDate = "";
	private String jAmt = "";// 借方金额
	private String dAmt = "";// 贷方金额
	private String memo = "";// 并账说明

	public void parse(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			mchtNo = jo.getString(mchtNo);
			acctDate = jo.getString(acctDate);
			mchtName = jo.getString(mchtName);
			beginSdate = jo.getString(beginSdate);
			endSdate = jo.getString(endSdate);
			createDate = jo.getString(createDate);
			jAmt = jo.getString(jAmt);
			dAmt = jo.getString(dAmt);
			memo = jo.getString(memo);
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
	 * 获取并账数组和明细
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static ArrayList<MergeOrder> getArrays(String jsonStr) {
		ArrayList<MergeOrder> list = new ArrayList<MergeOrder>();
		JSONArray ja;
		try {
			ja = new JSONArray(jsonStr);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				MergeOrder mergeOrder = new MergeOrder();
				mergeOrder.parse(jo);
				list.add(mergeOrder);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getAcctDate() {
		return acctDate;
	}

	public void setAcctDate(String acctDate) {
		this.acctDate = acctDate;
	}

	public String getMchtName() {
		return mchtName;
	}

	public void setMchtName(String mchtName) {
		this.mchtName = mchtName;
	}

	public String getBeginSdate() {
		return beginSdate;
	}

	public void setBeginSdate(String beginSdate) {
		this.beginSdate = beginSdate;
	}

	public String getEndSdate() {
		return endSdate;
	}

	public void setEndSdate(String endSdate) {
		this.endSdate = endSdate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

}
