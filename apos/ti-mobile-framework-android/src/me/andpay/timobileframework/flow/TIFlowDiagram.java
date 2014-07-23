package me.andpay.timobileframework.flow;

import java.util.HashMap;
import java.util.Map;

public class TIFlowDiagram {

	/**
	 * 流程图名称
	 */
	private String diagramName;
	
	/**
	 * 流程图版本
	 */
	private String diagramVersion;
	
	/**
	 * 流程图起始结点
	 */
	private String beginNodeName;
	/**
	 * 流程图起始结点
	 */
	private String forward;
	
	private TiFlowForward flowForward;
	
	/**
	 * 流程结点集合
	 */
	private Map<String, TiFlowNode> nodes = new HashMap<String, TiFlowNode>();

	public String getDiagramName() {
		return diagramName;
	}

	public void setDiagramName(String diagramName) {
		this.diagramName = diagramName;
	}

	public String getDiagramVersion() {
		return diagramVersion;
	}

	public void setDiagramVersion(String diagramVersion) {
		this.diagramVersion = diagramVersion;
	}

	public String getBeginNodeName() {
		return beginNodeName;
	}

	public void setBeginNodeName(String beginNodeName) {
		this.beginNodeName = beginNodeName;
	}

	public Map<String, TiFlowNode> getNodes() {
		return nodes;
	}

	public void setNodes(Map<String, TiFlowNode> nodes) {
		this.nodes = nodes;
	}
	
	public void addFlowNode(TiFlowNode node) {
		this.nodes.put(node.getNodeName(), node);
	}
	
	public TiFlowNode getBeginNode() {
		return nodes.get(this.beginNodeName);
	}
	
	public TiFlowNode getNodeByName(String nodeName) {
		return nodes.get(nodeName);
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public TiFlowForward getFlowForward() {
		return flowForward;
	}

	public void setFlowForward(TiFlowForward flowForward) {
		this.flowForward = flowForward;
	}
	
	
}
