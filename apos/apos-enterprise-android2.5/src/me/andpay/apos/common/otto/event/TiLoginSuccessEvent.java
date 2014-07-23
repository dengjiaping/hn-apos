package me.andpay.apos.common.otto.event;

import me.andpay.timobileframework.mvc.context.TiContext;

/**
 * 
 * @author tinyliu
 *
 */
public class TiLoginSuccessEvent {
	
	private TiContext ticonfig;
	
	public TiLoginSuccessEvent (TiContext context) {
		this.ticonfig = context;
	}

	public TiContext getTiconfig() {
		return ticonfig;
	}
	
	
	
}
