package me.andpay.apos.message.data;

import java.util.ArrayList;

import me.andpay.apos.base.BaseDataJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 消息对象
 * 
 * @author Administrator
 * 
 */
public class Message implements BaseDataJson {
	private String id = "";

	private String subject = "";
	private String content = "";
	private String annoType = "";

	private String startTime = "";
	private String endTime = "";
	private String action = "";

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnnoType() {
		return annoType;
	}

	public void setAnnoType(String annoType) {
		this.annoType = annoType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void parse(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			id = jo.getString("id");

			subject = jo.getString("subject");
			content = jo.getString("content");
			annoType = jo.getString("annoType");

			startTime = jo.getString("startTime");
			endTime = jo.getString("endTime");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String page() {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Message> getArrays(String jsonStr) {
		ArrayList<Message> list = new ArrayList<Message>();
		try {
			JSONArray ja = new JSONArray(jsonStr);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				Message message = new Message();
				message.parse(jo);
				list.add(message);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}
}
