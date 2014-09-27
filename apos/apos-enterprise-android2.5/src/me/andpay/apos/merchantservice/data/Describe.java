package me.andpay.apos.merchantservice.data;

import me.andpay.apos.lft.data.BaseData;

/*描述*/
public class Describe implements BaseData {
	/* 标题 */
	private String title;
	/* 描述 */
	private String describe;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
