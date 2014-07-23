package me.andpay.timobileframework.flow;

import java.util.HashMap;
import java.util.Map;

public class TiFlowNode {

	private String nodeName;

	private String refClass;

	private TIFlowDiagram refrenceDiagram;
	/**
	 * 是否根据指定的complete启动流程
	 */
	private boolean isStartByComplete = false;

	private String startCompleteIdentity;

	private Map<String, TiFlowNodeComplete> completes = new HashMap<String, TiFlowNodeComplete>();

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getRefClass() {
		return refClass;
	}

	public void setRefClass(String refClass) {
		this.refClass = refClass;
	}

	public Map<String, TiFlowNodeComplete> getCompletes() {
		return completes;
	}

	public void setCompletes(Map<String, TiFlowNodeComplete> completes) {
		this.completes = completes;
	}

	public TIFlowDiagram getRefrenceDiagram() {
		return refrenceDiagram;
	}

	public void setRefrenceDiagram(TIFlowDiagram refrenceDiagram) {
		this.refrenceDiagram = refrenceDiagram;
	}

	/**
	 * 根据identity获取complete对象
	 * 
	 * @param completeIdentity
	 * @return
	 */
	public TiFlowNodeComplete getComplete(String completeIdentity) {
		return completes.get(completeIdentity);
	}

	public boolean isStartByComplete() {
		return isStartByComplete;
	}

	public void setStartByComplete(boolean isStartByComplete) {
		this.isStartByComplete = isStartByComplete;
	}

	public TiFlowNodeComplete getStartComplete() {
		return completes.get(startCompleteIdentity);
	}

	public void setStartCompleteIdentity(String startCompleteIdentity) {
		this.startCompleteIdentity = startCompleteIdentity;
	}

}
