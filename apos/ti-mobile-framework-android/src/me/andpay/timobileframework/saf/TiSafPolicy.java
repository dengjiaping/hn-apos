package me.andpay.timobileframework.saf;

import java.util.Date;

import me.andpay.timobileframework.saf.enumeration.TiSafEventPollPolicy;

/**
 * SAF执行策略
 * @author tinyliu
 *
 */
public class TiSafPolicy {
	
	/**
	 * 轮询策略
	 */
	private TiSafEventPollPolicy policy;
	/**
	 * 时间间隔
	 */
	private int timeMills;
	/**
	 * 最大轮询次数
	 */
	private int pollMaxCount;
	
	public Date getNextForwardTime() {
		return null;
	}

	public TiSafEventPollPolicy getPolicy() {
		return policy;
	}

	public void setPolicy(TiSafEventPollPolicy policy) {
		this.policy = policy;
	}

	public int getTimeMills() {
		return timeMills;
	}

	public void setTimeMills(int timeMills) {
		this.timeMills = timeMills;
	}

	public int getPollMaxCount() {
		return pollMaxCount;
	}

	public void setPollMaxCount(int pollMaxCount) {
		this.pollMaxCount = pollMaxCount;
	}
}
