package me.andpay.timobileframework.flow.imp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.TIFlowDiagram;
import me.andpay.timobileframework.flow.TiFlowErrorCode;
import me.andpay.timobileframework.flow.TiFlowException;
import me.andpay.timobileframework.flow.TiFlowNode;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowStatusControl;
import me.andpay.timobileframework.flow.persistence.TiFlowControlPersistDataOperator;

/**
 * 流程控制器管理接口
 * 
 * @author tinyliu
 * 
 */
public class TiFlowStatusControlImpl implements TiFlowStatusControl,
		TiFlowControlPersistDataOperator {

	/**
	 * 流程控制图
	 */
	private TIFlowDiagram diagram;

	private Stack<String> controlStack = new Stack<String>();

	private Stack<TiFlowNodeComplete> completeStack = new Stack<TiFlowNodeComplete>();
	/**
	 * 流程上下文
	 */
	private Map<String, Serializable> flowContext = new HashMap<String, Serializable>();

	public TiFlowStatusControlImpl(TIFlowDiagram diagram) {
		this.flowContext = new HashMap<String, Serializable>();
		this.diagram = diagram;
	}

	public void start() {
		start(null);
	}

	/**
	 * 开始流程
	 * 
	 * @param activity
	 * @param params
	 *            出入参数会设置到Context上下文中
	 * @return
	 */
	public TiFlowNode start(Map<String, Serializable> values) {
		reset();
		controlStack.push(this.diagram.getBeginNodeName());
		if (values != null) {
			this.flowContext.putAll(values);
		}
		TiFlowNode node = this.diagram.getBeginNode();
		if (node.isStartByComplete()){
			completeStack.push(node.getStartComplete());
		}
		return node;
	}

	public boolean isFlowFinished() {
		return controlStack.isEmpty();
	}

	public TiFlowNodeComplete nextStep() {
		return this.nextStepWithIdentity(null);
	}

	public TiFlowNodeComplete nextStepWithIdentity(String identity) {
		TiFlowNodeComplete complete = this.diagram.getNodeByName(controlStack.peek())
				.getComplete(identity);
		if (complete == null) {
			throw new TiFlowException(TiFlowException.TIFLOWEX_GROUP_PROCESS,
					TiFlowErrorCode.PROCESS_IDENTITY_NOTFOUND_ERROR,
					"complete is not found, the identity is " + identity);
		}
		if (complete.isRemoveNode()){
			if (!controlStack.isEmpty())
				controlStack.pop();
		} else {
			completeStack.push(complete);
		}
		if (!StringUtil.isEmpty(complete.getNextNodeName())){
			controlStack.push(complete.getNextNodeName());
		}

		return complete;
	}

	public String previousStep(){
		if (controlStack.isEmpty()){
			return null;
		}
		controlStack.pop();
		if(!completeStack.isEmpty()){
			completeStack.pop();
		}
		return controlStack.isEmpty() ? null : controlStack.peek();
	}

	/**
	 * 获取流程运行堆栈
	 */
	public Stack<String> getFlowStack() {
		Stack<String> stack = new Stack<String>();
		stack.addAll(this.controlStack);
		return stack;
	}

	public TIFlowDiagram getDiagram() {
		return this.diagram;
	}

	public String getCurrentNodeName() {
		if(controlStack.isEmpty()) {
			return null;
		}
		return controlStack.peek();
	}

	public String getPreviousNodeName() {
		if (controlStack != null && !controlStack.isEmpty()){
			String curNode = controlStack.pop();
			String previosNode = controlStack.isEmpty() ? null : controlStack.peek();
			controlStack.push(curNode);
			return previosNode;
		}
		return null;
	}

	public void finish() {
		reset();
	}

	private void reset() {
		controlStack.removeAllElements();
		flowContext.clear();
		completeStack.removeAllElements();
	}

	public Map<String, Serializable> getFlowContext() {
		return flowContext;
	}

	public Map<String, Serializable> needToPersistenceData() {
		Map<String, Serializable> storeData = new HashMap<String, Serializable>();
		if (!this.flowContext.isEmpty()) {
			storeData.put("flowContext", (HashMap<String, Serializable>) flowContext);
		}
		if (!this.controlStack.isEmpty()) {
			storeData.put("controlStack", controlStack);
		}
		return this.flowContext;
	}

	@SuppressWarnings("unchecked")
	public void restoreControlStatus(Map<String, Serializable> persistenceData) {
		if (persistenceData == null || persistenceData.isEmpty()) {

			return;
		}
		ArrayList<String> stack = (ArrayList<String>) persistenceData.get("controlStack");
		if (stack != null && !stack.isEmpty()) {
			this.controlStack = new Stack<String>();
			this.controlStack.addAll(stack);
		}
		Map<String, Serializable> context = (Map<String, Serializable>) persistenceData
				.get("flowContext");
		if (context != null && !context.isEmpty()) {
			this.flowContext = context;
		}
	}

	public boolean isNeedToPersistence() {
		if (flowContext.isEmpty() && controlStack.isEmpty()) {
			return false;
		}
		return true;
	}

	public TiFlowNodeComplete getLastNodeComplete() {
		if (completeStack.isEmpty()) {
			return null;
		}
		return completeStack.pop();
	}

}
