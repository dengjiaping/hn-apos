package me.andpay.apos.merchantservice.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 清算列表by交易类型
 * 
 * @author Administrator
 *
 */
public class SelementOrderByTxntype extends SelementOrder {

	private String txnType = "";// 交易类型

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public void parse(JSONObject jo) {
		// TODO Auto-generated method stub

		try {
			super.parse(jo);
			txnType = jo.getString("txnType");
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
	 * 获得对象数组
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static ArrayList<SelementOrderByTxntype> getArrays(String jsonStr) {
		ArrayList<SelementOrderByTxntype> list = new ArrayList<SelementOrderByTxntype>();
		JSONArray ja;
		try {
			ja = new JSONArray(jsonStr);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				SelementOrderByTxntype selement = new SelementOrderByTxntype();
				selement.parse(jo);
				list.add(selement);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

}
