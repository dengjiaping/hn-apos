package me.andpay.apos.stl.form;

import java.util.Date;

public class QuerySettleCondForm {

	/**
	 * 结算指令编号
	 */
	private Long settleOrderId;

	/**
	 * 最大结算指令编号 <
	 */
	private Long maxSettleOrderId;

	/**
	 * 最小结算指令编号 >
	 */
	private Long minSettleOrderId;

	/**
	 * 起始结算时间 >=
	 */
	private Date startSettleDate;

	/**
	 * 结束结算时间 <
	 */
	private Date endSettleDate;

	/**
	 * 最大结算时间 <
	 */
	private Date maxSettleTime;

	/**
	 * 最小结算时间 >
	 */
	private Date minSettleTime;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 排序
	 */
	private String orders;

	public Long getSettleOrderId() {
		return settleOrderId;
	}

	public void setSettleOrderId(Long settleOrderId) {
		this.settleOrderId = settleOrderId;
	}

	public Long getMaxSettleOrderId() {
		return maxSettleOrderId;
	}

	public void setMaxSettleOrderId(Long maxSettleOrderId) {
		this.maxSettleOrderId = maxSettleOrderId;
	}

	public Long getMinSettleOrderId() {
		return minSettleOrderId;
	}

	public void setMinSettleOrderId(Long minSettleOrderId) {
		this.minSettleOrderId = minSettleOrderId;
	}

	public Date getStartSettleDate() {
		return startSettleDate;
	}

	public void setStartSettleDate(Date startSettleDate) {
		this.startSettleDate = startSettleDate;
	}

	public Date getEndSettleDate() {
		return endSettleDate;
	}

	public void setEndSettleDate(Date endSettleDate) {
		this.endSettleDate = endSettleDate;
	}

	public Date getMaxSettleTime() {
		return maxSettleTime;
	}

	public void setMaxSettleTime(Date maxSettleTime) {
		this.maxSettleTime = maxSettleTime;
	}

	public Date getMinSettleTime() {
		return minSettleTime;
	}

	public void setMinSettleTime(Date minSettleTime) {
		this.minSettleTime = minSettleTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

}
