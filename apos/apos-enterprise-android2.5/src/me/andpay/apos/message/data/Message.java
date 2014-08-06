package me.andpay.apos.message.data;

import me.andpay.apos.lft.data.BaseData;

/**
 * 消息对象
 * 
 * @author Administrator
 * 
 */
public class Message implements BaseData {
	private String time;// 消息时间
	private String id;// 消息id
	private String title;// 消息标题
	private boolean isReader;// 消息是否已读
	private String content="今天所有员工到一楼办公室开会，商讨年终奖的发放问题";// 消息内容

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isReader() {
		return isReader;
	}

	public void setReader(boolean isReader) {
		this.isReader = isReader;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
