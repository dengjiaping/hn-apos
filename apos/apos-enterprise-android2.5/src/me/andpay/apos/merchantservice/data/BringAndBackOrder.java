package me.andpay.apos.merchantservice.data;

import java.util.ArrayList;

import me.andpay.apos.base.BaseDataJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*调单*/
public class BringAndBackOrder implements BaseDataJson {
	private String id = "";// id
	private String subject = "";// 标题
	private String createTime = "";// 创建时间
	private String description = "";// 描述
	private String dispose = "";// 是否处理
	private String imagePaths = "";// 图片路径

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}

	public String getImagePaths() {
		return imagePaths;
	}

	public void setImagePaths(String imagePaths) {
		this.imagePaths = imagePaths;
	}

	public void parse(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			id = jo.getString("id");
			subject = jo.getString("subject");
			createTime = jo.getString("createTime");
			description = jo.getString("description");
			dispose = jo.getString("dispose");
			imagePaths = jo.getString("imagePaths");
			if(imagePaths.startsWith("[")){
				imagePaths=imagePaths.substring(1);
			}
			if(imagePaths.endsWith("]")){
				imagePaths=imagePaths.substring(0,imagePaths.length()-1);
			}
			
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
	 * 获得数组
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static ArrayList<BringAndBackOrder> getArrays(String jsonStr) {
		ArrayList<BringAndBackOrder> list = new ArrayList<BringAndBackOrder>();
		JSONArray ja;
		try {
			ja = new JSONArray(jsonStr);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				BringAndBackOrder order = new BringAndBackOrder();
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
