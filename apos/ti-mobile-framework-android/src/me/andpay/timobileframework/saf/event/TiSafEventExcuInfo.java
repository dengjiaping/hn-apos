package me.andpay.timobileframework.saf.event;

import java.util.Date;

/**
 * Event 执行信息
 * 
 * @author tinyliu
 * 
 */
public class TiSafEventExcuInfo {
	/**
	 * 执行事件
	 */
	private TiSafEvent event;
	/**
	 * SafEvent 删除标识
	 */
	private boolean isNeedRemove;
	/**
	 * 执行过程中是否出现异常
	 */
	private boolean isHappedEx;
	/**
	 * 异常信息
	 */
	private Throwable throwable;
	/**
	 * event执行起始时间
	 */
	private Date startDate;
	/**
	 * event执行结束时间
	 */
	private Date finishDate;

	public TiSafEvent getEvent() {
		return event;
	}

	public void setEvent(TiSafEvent event) {
		this.event = event;
	}

	public boolean isNeedRemove() {
		return isNeedRemove;
	}

	public void setNeedRemove(boolean isNeedRemove) {
		this.isNeedRemove = isNeedRemove;
	}

	public boolean isHappedEx() {
		return isHappedEx;
	}

	public void setHappedEx(boolean isHappedEx) {
		this.isHappedEx = isHappedEx;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

}
