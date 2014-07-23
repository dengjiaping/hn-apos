package me.andpay.timobileframework.flow;

import java.util.Stack;

/**
 * 流程控制状态记录器
 * 在android中永远只会有单个流程存在
 * @author tinyliu
 *
 */
public interface TiFlowControlStatusRecord {
	
	public void startFlow(String flowName);
		
	public void removeFlow(String flowName);
	
	public void pushFlowNode(String nodeName);
	
	public void popFlowNode();
	
	public String getCurrentFlow();
	
	public String[] getFlows();
	
	public String getCurrentFlowNode();
	
	public Stack<String> getCurrentStack();
	
	public void reset();
}
