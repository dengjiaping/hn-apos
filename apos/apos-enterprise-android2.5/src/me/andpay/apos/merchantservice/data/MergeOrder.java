package me.andpay.apos.merchantservice.data;

import me.andpay.apos.lft.data.BaseData;

/*并账订单*/
public class MergeOrder implements BaseData {
	/* 标题 */
	private String title;
	/* 描述 */
	private String describe;
	/* 时间 */
	private String time;

	/* 借款 */
	private String borrowing;

	/* 贷款 */
	private String loan;

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBorrowing() {
		return borrowing;
	}

	public void setBorrowing(String borrowing) {
		this.borrowing = borrowing;
	}

	public String getLoan() {
		return loan;
	}

	public void setLoan(String loan) {
		this.loan = loan;
	}

}
