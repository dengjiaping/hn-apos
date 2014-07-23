package me.andpay.timobileframework.flow;

import me.andpay.timobileframework.flow.imp.TiFlowStatusControlImpl;

public class TiFlowControlFactory {
	
	public TiFlowStatusControl newControl(TiFlowAdmin admin, TIFlowDiagram diagram, TiFlowControlStatusRecord record) {
		return new TiFlowStatusControlImpl(diagram);
	}
	
}
