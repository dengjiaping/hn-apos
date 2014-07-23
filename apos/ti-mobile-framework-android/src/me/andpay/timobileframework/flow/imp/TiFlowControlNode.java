package me.andpay.timobileframework.flow.imp;

import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

import me.andpay.timobileframework.flow.TIFLowSignTask;
import me.andpay.timobileframework.flow.TiFlowNode;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowStatusControl;
import android.app.Activity;

/**
 * 结点控制用于管理父子流程关系
 * 
 * @author tinyliu
 * 
 */
public class TiFlowControlNode implements TiFlowNodeControl {

	private TiFlowNodeControl parentNode;

	private TiFlowStatusControl control;

	private Activity startActivity = null;

	private Stack<Activity> activityStack = new Stack<Activity>();

	public TiFlowControlNode(TiFlowStatusControl control) {
		this.control = control;
	}

	public void setParentNode(TiFlowNodeControl parentNode) {
		this.parentNode = parentNode;
	}

	public void recordFlowActivity(Activity activity) {
		activityStack.push(activity);
	}

	/**
	 * 结束流程
	 */
	public void finishFlow() {
		control.finish();
		startActivity = null;
	}

	/**
	 * 释放所有流程
	 */
	public void releaseActivity() {
		if (activityStack.isEmpty()) {
			return;
		}
		Activity activity = null;
		do {
			activity = activityStack.pop();
			if(startActivity!=null && activity.hashCode() == startActivity.hashCode()) {
				continue;
			}

			TIFLowSignTask signTask = activity.getClass().getAnnotation(
					TIFLowSignTask.class);
			if (signTask == null) {
				activity.finish();
			}

		} while (!activityStack.isEmpty());
		activityStack.clear();
	}

	/**
	 * 开启流程
	 */
	public TiFlowNode startFlow(Activity activity){
		startActivity = activity;
		return control.start(parentNode == null ? null : parentNode.getFlowContext());
	}

	public TiFlowNodeComplete nextSetup(Activity activity, String identity) {
		TiFlowNodeComplete nodeComplete = control.nextStepWithIdentity(identity);
		if (!activityStack.isEmpty() && activityStack.peek() == activity){
			return nodeComplete;
		}
		if (!nodeComplete.isRemoveNode()){
			activityStack.push(activity);
		}
		return nodeComplete;
	}

	public Activity previousSetup() {
		control.previousStep();
		if (!activityStack.isEmpty()){
			return activityStack.pop();
		}
		return null;
	}

	public Map<String, Serializable> getFlowContext() {
		return this.control.getFlowContext();
	}

	public String getCurrentFlowName() {
		return this.control.getDiagram().getDiagramName();
	}

	public String[] getCurrentFlows() {
		Stack<String> flows = new Stack<String>();
		getParentFlow(flows, this);
		return flows.toArray(new String[] {});
	}

	public String getCurrentNodeName() {
		return control.getCurrentNodeName();
	}

	public TiFlowNodeControl getParentNode() {
		return parentNode;
	}

	public TiFlowNodeComplete getLastNodeComplete() {
		return control.getLastNodeComplete();
	}

	public Activity getStartActivity(Activity activity) {
		return startActivity;
	}

	public Stack<String> getFlowStack() {
		Stack<String> flowStacks = new Stack<String>();
		getParentFlowStack(flowStacks, this);
		return flowStacks;
	}

	public TiFlowStatusControl getStatusControl() {
		return control;
	}

	private void getParentFlow(Stack<String> flows, TiFlowNodeControl node) {
		if (node.getParentNode() != null) {
			getParentFlow(flows, node.getParentNode());
		}
		flows.add(node.getCurrentFlowName());
	}

	private void getParentFlowStack(Stack<String> flowStacks, TiFlowNodeControl node) {
		if (node.getParentNode() != null) {
			getParentFlowStack(flowStacks, node.getParentNode());
		}
		Stack<String> temp = node.getStatusControl().getFlowStack();
		for (int i = 0; i < temp.size(); i++) {
			flowStacks.push(temp.get(i));
		}
	}

	public void popActivity() {
		if (!this.activityStack.isEmpty()) {
			 activityStack.pop();
		}
	}
	
	public Activity getLastActivity() {
		if (!this.activityStack.isEmpty()) {
			return activityStack.peek();
		}
		return startActivity;
	}
	
	/**
	 * 是否是最后的节点
	 * @return
	 */
	public boolean isLastNode(){
		if(activityStack.isEmpty()) {
			return true;
		}
		return false;
	}

}
