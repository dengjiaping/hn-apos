package me.andpay.timobileframework.saf.event;

/**
 * saf 事件分发器
 * @author tinyliu
 *
 */
public interface TiSafEventDispather {
	
	public void diapatheSafEvent(TiSafEvent event);
	
	public void diapatheSafEvent(TiSafEvent event, TiSafActionReporter reporter);
}
