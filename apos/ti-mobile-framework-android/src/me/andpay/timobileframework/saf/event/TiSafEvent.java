package me.andpay.timobileframework.saf.event;

import java.io.Serializable;
import java.util.Date;

import me.andpay.timobileframework.saf.enumeration.TiSafEventPriority;

public class TiSafEvent {
	
	private String eventType;
	
	private String eventIdentityNo;
	
	private Serializable refObj;
	
	private TiSafEventPriority priority;
	
	private Date createTime;
	
	private Date lastEventSendTime;
	
	private int sendCount;
	
	private int maxSendCount;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventIdentityNo() {
		return eventIdentityNo;
	}

	public void setEventIdentityNo(String eventIdentityNo) {
		this.eventIdentityNo = eventIdentityNo;
	}

	public Serializable getRefObj() {
		return refObj;
	}

	public void setRefObj(Serializable refObj) {
		this.refObj = refObj;
	}

	public TiSafEventPriority getPriority() {
		return priority;
	}

	public void setPriority(TiSafEventPriority priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEventSendTime() {
		return lastEventSendTime;
	}

	public void setLastEventSendTime(Date lastEventSendTime) {
		this.lastEventSendTime = lastEventSendTime;
	}

	public int getSendCount() {
		return sendCount;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}
}
