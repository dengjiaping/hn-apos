package me.andpay.timobileframework.saf.enumeration;

/**
 * SAF EVENT 优先级别
 * 
 * @author tinyliu
 * 
 */
public enum TiSafEventPriority {
	
	HIGH(0), NORMAL(1), LOW(2);

	private int value;

	public int getValue() {
		return this.value;
	}

	private TiSafEventPriority(int value) {
		this.value = value;
	}
}
