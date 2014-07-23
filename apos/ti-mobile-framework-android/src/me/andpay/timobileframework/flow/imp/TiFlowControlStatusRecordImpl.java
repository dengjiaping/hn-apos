package me.andpay.timobileframework.flow.imp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.TiFlowControlStatusRecord;
import me.andpay.timobileframework.flow.TiFlowErrorCode;
import me.andpay.timobileframework.flow.TiFlowException;
import me.andpay.timobileframework.flow.persistence.TiFlowControlPersistDataOperator;

public class TiFlowControlStatusRecordImpl implements TiFlowControlStatusRecord,
		TiFlowControlPersistDataOperator {
	/**
	 * flowcontrol分隔符号
	 */
	public static final String FLOWCONTROL_FLAG_SPEARTOR = "-";

	static final String CURRENT_FLOW_KEY = "flow";

	static final String CURRENT_FLOW_NODE_KEY = "flowNode";

	static final String CURRENT_FLOW_STACK_KEY = "flowStack";

	private Stack<String> stacks = new Stack<String>();

	private Stack<String> flow  = new Stack<String>();

	public TiFlowControlStatusRecordImpl() {
	}

	public void startFlow(String flowName) {
		this.flow.push(flowName);
	}
	
	public void pushFlow(String flowName) {
		this.flow.push(flowName);
	}

	public void removeFlow(String flowName) {
		String lastFlow = flow.pop();
		if(!lastFlow.equalsIgnoreCase(flowName)) {
			reset();
			throw new TiFlowException(TiFlowException.TIFLOWEX_GROUP_PROCESS,
					TiFlowErrorCode.PROCESS_FLOW_STATUS_ERROR,
					"last flow is not mapping the remove flow, the flow name is" + flowName + ", the last flow name is " + lastFlow);
		}
		if (flow.isEmpty()) {
			reset();
			return;
		}
	}

	public String getCurrentFlow() {
		if (flow.isEmpty()) {
			return null;
		}
		return flow.peek();
	}

	public String getCurrentFlowNode() {
		return stacks.peek();
	}

	public Stack<String> getCurrentStack() {
		return stacks;
	}

	public void reset() {
		this.flow.removeAllElements();
		this.stacks.removeAllElements();
	}

	public void pushFlowNode(String nodeName) {
		stacks.push(nodeName);
	}

	public void popFlowNode() {
		if (!stacks.isEmpty()) {
			stacks.pop();
		}
	}

	public Map<String, Serializable> needToPersistenceData() {
		Map<String, Serializable> data = new HashMap<String, Serializable>();
		if (!flow.isEmpty()) {
			data.put(CURRENT_FLOW_KEY, PersistenceStackUtil.toPersistenceString(flow));
		}
		if (!stacks.isEmpty()) {
			data.put(CURRENT_FLOW_STACK_KEY, PersistenceStackUtil.toPersistenceString(stacks));
		}
		return data;
	}

	public void restoreControlStatus(Map<String, Serializable> persistenceData){
		if (persistenceData == null || persistenceData.isEmpty()) {
			return;
		}
		String stackStr = (String) persistenceData.get(CURRENT_FLOW_STACK_KEY);
		if(StringUtil.isEmpty(stackStr)) {
			this.stacks = PersistenceStackUtil.persistenceToStack(stackStr);
		}
		String flowStr = (String) persistenceData.get(CURRENT_FLOW_KEY);
		if(StringUtil.isEmpty(flowStr)) {
			this.flow = PersistenceStackUtil.persistenceToStack(flowStr);
		}
	}

	public boolean isNeedToPersistence() {
		if (flow.isEmpty() && stacks.isEmpty()) {
			return false;
		}
		return true;
	}

	public String[] getFlows() {
		if(flow.isEmpty()) {
			return null;
		}
		return flow.toArray(new String[]{});
	}
}
