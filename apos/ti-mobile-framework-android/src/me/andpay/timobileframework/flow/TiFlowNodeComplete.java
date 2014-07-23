package me.andpay.timobileframework.flow;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ti.util.StringUtil;

/**
 * 流程节点跳转行为描述对象
 * 
 * @author tinyliu
 * 
 */
public class TiFlowNodeComplete {
	/**
	 * 跳转行为标识
	 */
	private String identity;
	/**
	 * 下一步节点名称
	 */
	private String nextNodeName;
	/**
	 * 关联refClass
	 */
	private String refClass;
	/**
	 * 子流程
	 */
	private String subFlowName;
	/**
	 * 跳转类型：节点,关联Class
	 */
	private int completeType;
	
	/**
	 * 子流程结束后，对应的跳转nodecomplete
	 */
	private String subFinishToComplete;
	
	/**
	 * 跳转完成后移除当前结点
	 */
	private boolean removeNode;
	/**
	 * 是否清空Context
	 */
	private boolean isFlushContext = false;
	/**
	 * 是否清空Context
	 */
	private boolean isFinishFlow = false;
	
	private boolean isHoldAfterSubFlowFinished = false;
	/**
	 * 是否清空Context
	 */
	private boolean isForwardNextFlow = false;
	/**
	 * 关联Node
	 */
	private TiFlowNode refrenceFlowNode;
	/**
	 * request Code
	 */
	private int requestCode;
	/**
	 * 数据转换类型
	 */
	private String tranferType;
	/**
	 * 跳转类型
	 */
	private String forwardType;
	/**
	 * 关联数据转换器
	 */
	private TiFlowNodeDataTransfer transfer;

	private Map<String, String> unTransferData = new HashMap<String, String>();

	private TiFlowForward forward;

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getNextNodeName() {
		return nextNodeName;
	}

	public void setNextNodeName(String nextNodeName) {
		this.nextNodeName = nextNodeName;
	}

	public String getRefClass() {
		return refClass;
	}

	public void setRefClass(String refClass) {
		this.refClass = refClass;
	}

	public String getSubFlowName() {
		return subFlowName;
	}

	public void setSubFlowName(String subFlowName) {
		this.subFlowName = subFlowName;
	}

	public int getCompleteType() {
		return completeType;
	}

	public void setCompleteType(int completeType) {
		this.completeType = completeType;
	}

	public boolean isFlushContext() {
		return isFlushContext;
	}

	public void setFlushContext(boolean isFlushContext) {
		this.isFlushContext = isFlushContext;
	}

	public TiFlowNode getRefrenceFlowNode() {
		return refrenceFlowNode;
	}

	public void setRefrenceFlowNode(TiFlowNode refrenceFlowNode) {
		this.refrenceFlowNode = refrenceFlowNode;
	}

	public TiFlowNodeDataTransfer getTransfer() {
		return transfer;
	}

	public void setTransfer(TiFlowNodeDataTransfer transfer) {
		this.transfer = transfer;
	}

	public Map<String, String> getUnTransferData() {
		return unTransferData;
	}

	public void setUnTransferData(Map<String, String> unTransferData) {
		this.unTransferData = unTransferData;
	}

	public TiFlowForward getForward() {
		return forward;
	}

	public void setForward(TiFlowForward forward) {
		this.forward = forward;
	}

	public boolean isForwardNextFlow() {
		return isForwardNextFlow;
	}

	public void setForwardNextFlow(boolean isForwardNextFlow) {
		this.isForwardNextFlow = isForwardNextFlow;
	}

	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}

	public String getTranferType() {
		return tranferType;
	}

	public void setTranferType(String tranferType) {
		this.tranferType = tranferType;
	}

	public String getForwardType() {
		return forwardType;
	}

	public void setForwardType(String forwardType) {
		this.forwardType = forwardType;
	}
	
	public boolean isFinishFlow() {
		return isFinishFlow;
	}

	public void setFinishFlow(boolean isFinishFlow) {
		this.isFinishFlow = isFinishFlow;
	}

	public String getSubFinishToComplete() {
		return subFinishToComplete;
	}

	public void setSubFinishToComplete(String subFinishToComplete) {
		this.subFinishToComplete = subFinishToComplete;
	}

	/*public Map<String, String> getTransferData(TiFlowActivity activity) {
		if(this.getTransfer() != null) {
			return this.getTransfer().transfterData(activity, this.getUnTransferData(), this);
		}
		return getUnTransferData();
	}*/

	public String getRefForwardClass() {
		if (!StringUtil.isEmpty(getNextNodeName())) {
			TiFlowNode nextNode = this.getRefrenceFlowNode().getRefrenceDiagram()
					.getNodeByName(getNextNodeName());

			return nextNode.getRefClass();
		} else if (!StringUtil.isEmpty(getRefClass())){
			return getRefClass();
		}
		return null;
	}
	
	
	
	public boolean isRemoveNode() {
		return removeNode;
	}

	public void setRemoveNode(boolean removeNode) {
		this.removeNode = removeNode;
	}

	public String toString() {
		return String.format("diagramName=%s ,nodeName=%s, complete=%s", this.refrenceFlowNode
				.getRefrenceDiagram().getDiagramName(), this.getRefrenceFlowNode().getNodeName(),
				this.getIdentity());
	}

	public boolean isHoldAfterSubFlowFinished() {
		return isHoldAfterSubFlowFinished;
	}

	public void setHoldAfterSubFlowFinished(boolean isHoldAfterSubFlowFinished) {
		this.isHoldAfterSubFlowFinished = isHoldAfterSubFlowFinished;
	}
}
