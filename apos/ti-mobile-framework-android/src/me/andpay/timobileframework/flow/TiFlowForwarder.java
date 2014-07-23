package me.andpay.timobileframework.flow;

import me.andpay.timobileframework.flow.forward.TiFlowForwardAction;

public interface TiFlowForwarder {

	public String getForwardActivity();
	
	public String setForwardAction(TiFlowForwardAction action);
}
