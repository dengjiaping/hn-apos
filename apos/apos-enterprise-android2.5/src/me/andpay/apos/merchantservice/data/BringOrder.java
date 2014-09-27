package me.andpay.apos.merchantservice.data;

import me.andpay.apos.lft.data.BaseData;

/*调单*/
public class BringOrder implements BaseData {
	/* 标题 */
	private String title = "";
	/* 时间 */
	private String time = "";
	/* 是否处理 */
	private String dispose = "";
	/* 描述 */
	private String describle = "";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}

	public String getDescrible() {
		return describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}
}
