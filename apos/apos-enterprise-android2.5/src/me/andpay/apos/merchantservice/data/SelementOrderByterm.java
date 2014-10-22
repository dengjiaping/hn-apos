package me.andpay.apos.merchantservice.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*结算明细by终端*/
public class SelementOrderByterm extends SelementOrder {

	private String termNo = "";// 终端号

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public void parse(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			super.parse(jo);
			termNo = jo.getString("termNo");// 终端号

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//

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
	public static ArrayList<SelementOrderByterm> getArrays(String jsonStr) {
		ArrayList<SelementOrderByterm> list = new ArrayList<SelementOrderByterm>();
		JSONArray ja;
		try {
			ja = new JSONArray(jsonStr);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				SelementOrderByterm selement = new SelementOrderByterm();
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
