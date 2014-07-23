package me.andpay.timobileframework.saf.enumeration;

/**
 * saf event轮询策略
 * 
 * REPEART 按照指定时间周期进行轮询 NORMAL 按照saf主控制器轮询时间执行 INCREASE 递增方式轮询
 * 
 * @author tinyliu
 */
public enum TiSafEventPollPolicy {

	REPEART(0), INCREASE(1), NORMAL(2);

	private int value;

	public int getValue() {
		return this.value;
	}

	private TiSafEventPollPolicy(int value) {
		this.value = value;
	}

}
