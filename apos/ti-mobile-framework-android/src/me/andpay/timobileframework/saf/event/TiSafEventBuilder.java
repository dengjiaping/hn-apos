package me.andpay.timobileframework.saf.event;

/**
 * Saf组装器
 * @author tinyliu
 *
 */
public interface TiSafEventBuilder {

	public TiSafEvent buildSafEvent();
	
	public TiSafHandlerClazzInfo buildSafHandler(); 
	
}
