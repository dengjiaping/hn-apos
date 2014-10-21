package me.andpay.apos.merchantservice.data;

import java.util.Map;

import me.andpay.apos.base.BaseData;

/*退单*/
public class BackOrder implements BaseData {
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

	public void parse(Map map) {
		// TODO Auto-generated method stub
		
	}

	public Map page() {
		// TODO Auto-generated method stub
		return null;
	}

}
