package me.andpay.timobileframework.flow;

import java.util.Map;

public interface TiFlowControlConfigLoader {
	
	public Map <String, TIFlowDiagram> loadFlow() throws TiFlowConfigException;
	
	public Map <String, TiFlowNodeDataTransfer> loadTransfer() throws TiFlowConfigException;
	
	public Map <String, TiFlowForward> loadForward() throws TiFlowConfigException;
}
