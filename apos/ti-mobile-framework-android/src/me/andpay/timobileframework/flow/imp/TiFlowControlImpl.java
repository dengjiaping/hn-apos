package me.andpay.timobileframework.flow.imp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.TiFlowAdmin;
import me.andpay.timobileframework.flow.TiFlowCallback;
import me.andpay.timobileframework.flow.TiFlowControl;
import me.andpay.timobileframework.flow.TiFlowErrorCode;
import me.andpay.timobileframework.flow.TiFlowException;
import me.andpay.timobileframework.flow.TiFlowForward;
import me.andpay.timobileframework.flow.TiFlowNode;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowStatusControl;
import me.andpay.timobileframework.flow.TiFlowSubFinishAware;
import me.andpay.timobileframework.util.ReflectUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class TiFlowControlImpl implements TiFlowControl {

	private static TiFlowControlImpl inControlImpl;

	// private static Map<String,TiFlowControlImpl> tiFlowControls = new
	// HashMap<String, TiFlowControlImpl>();

	private TiFlowNodeControl controlNode;

	// private Activity rootActivity;

	private TiFlowControlImpl() {
	}

	public static TiFlowControlImpl instanceControl() {
		if (inControlImpl == null) {
			inControlImpl = new TiFlowControlImpl();
		}
		return inControlImpl;
	}

	/**
	 * 启动程序流程
	 */
	public void startFlow(Activity activity, String flowName) {
		this.startFlow(activity, flowName, null);
	}

	/**
	 * 启动流程图
	 */
	public void startFlow(Activity activity, String flowName,
			Map<String, String> params) {
		if (controlNode != null) {
			releaseNode(controlNode);
		}
		TiFlowAdmin admin = TiFlowAdmin.singletonInstance();// 完成流程图的初始，注入检测
		TiFlowStatusControl control = admin.getFlowStatusControl(flowName);// 获得流程图控制器
		if (control == null) {
			throw new TiFlowException(TiFlowException.TIFLOWEX_GROUP_PROCESS,
					TiFlowErrorCode.PROCESS_FLOW_STATUS_ERROR,
					"flow control is not found, the flow name is" + flowName);
		}
		controlNode = new TiFlowControlNode(control);
		startFlowByBeginNode(activity, params);

		// rootActivity = activity;
	}

	/**
	 * 
	 * 跳转到下个流程
	 * 
	 * 如果跳转到子流程，并且设置finish Flag：子流程不继承当前流程contextData 并且当前流程中所有activity进行销毁
	 * 
	 * @param identity
	 */
	public void nextSetup(Activity activity, String identity) {
		this.nextSetup(activity, identity, null);
	}

	/**
	 * 
	 * 跳转到下个流程
	 * 
	 * 如果跳转到子流程，并且设置finish Flag：子流程不继承当前流程contextData 并且当前流程中所有activity进行销毁
	 * 
	 * @param identity
	 */
	public void nextSetup(Activity activity, String identity,
			Map<String, String> sendData) {
		nextSetup(activity, identity, sendData, null);
	}

	private void nextSetup(Activity activity, String identity,
			Map<String, String> sendData, Map<String, Serializable> subContext){
		TiFlowAdmin admin = TiFlowAdmin.singletonInstance();
		TiFlowNodeComplete complete = getNodeContrl().nextSetup(activity,
				identity);
		// 判断当前节点是否要清空context data
		if (complete.isFlushContext()) {
			getNodeContrl().getFlowContext().clear();
		}
		// 如果只设置了finish标签,则结束流程，并且结束所有activity. 自己是子流程
		if (complete.isFinishFlow()) {
			if (processFlowFinished(activity, controlNode, sendData)) {
				return;
			}
			if (complete.getRefForwardClass() == null
					&& StringUtil.isEmpty(complete.getSubFlowName())) {
				return;
			}
		}

		// 转换传递数据，执行activity跳转
		Map<String, String> transferData = transferDataByComplete(complete,
				subContext, activity, sendData);
		if (sendData != null && !sendData.isEmpty() && transferData != null) {
			transferData.putAll(sendData);
		}
		// 跳转子流程
		if (complete.isForwardNextFlow()) { // 自己是父流程
			startSubFlow(complete, admin, activity, transferData);
			return;
		}

		// 下一个节点 或 属性activity的跳转
		Intent forwardIntent = new Intent(activity.getApplicationContext(),
				getClassByStr(complete.getRefForwardClass()));
		if (!StringUtil.isEmpty(complete.getRefClass())){
			forwardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		}

		if (transferData != null && !transferData.isEmpty()){
			for (String key : transferData.keySet()) {
				forwardIntent.putExtra(key, transferData.get(key));
			}
		}
		TiFlowForward forward = complete.getForward();
		forward.forward(forwardIntent, activity, complete, subContext);
		if (complete.isRemoveNode()) {// 如果有移除标志,跳转后移除当前节点，包括所在activity
			activity.finish();
		}
		
		if (!StringUtil.isEmpty(complete.getRefClass())){ //???????????
			releaseNode(controlNode);
			this.controlNode = null;
		}
	}

	/**
	 * 处理子流程完成通知
	 * 
	 * @param node
	 * @param parentNode
	 */
	private boolean processFlowFinished(Activity activity,
			TiFlowNodeControl node, Map<String, String> sendData) {
		if (node.getParentNode() == null) {// 如果没有父流程，直接结束本流程
			node.finishFlow();
			node.releaseActivity();
			controlNode = null;
			return false;
		}
		// 结束子流程
		TiFlowNodeComplete complete = node.getParentNode()
				.getLastNodeComplete();// 父流程最后一个节点行为
		TiFlowNodeComplete subComplete = node.getLastNodeComplete();
		Map<String, Serializable> subContext = new HashMap<String, Serializable>();
		subContext.putAll(node.getFlowContext());
		node.releaseActivity();
		node.finishFlow();
		this.controlNode = node.getParentNode();
		Activity lastActivity = node.getParentNode().getLastActivity();
		// 通知子流程结束事件
		if (lastActivity != null
				&& ReflectUtil.isImplInterface(lastActivity.getClass(),
						TiFlowSubFinishAware.class.getName())) {
			((TiFlowSubFinishAware) lastActivity).subFlowFinished(subContext);
			return true;
		}

		// 如果子流程结束后，发现子流程结束节点或者父流程的最后跳转节点设置了"hold-sub-finish"属性，则只是结束子流程，重新恢复到父流程跳转子流程节点
		if ((complete != null && complete.isHoldAfterSubFlowFinished())
				|| subComplete == null
				|| subComplete.isHoldAfterSubFlowFinished()) {
			// node.popActivity();
			if (controlNode.getParentNode() != null) {
				return processFlowFinished(activity, node.getParentNode(),
						sendData);
			}
			return true;
		}

		if (complete == null
				|| StringUtil.isEmpty(complete.getSubFinishToComplete())) {
			return processFlowFinished(activity, node.getParentNode(), sendData);
		}
		this.nextSetup(lastActivity,complete.getSubFinishToComplete(),
				sendData, subContext);
		return true;
	}

	// 上一步回到前一个activity
	public void previousSetup(Activity activity) {

		String current = this.getCurrentNodeName();
		Activity previousActivity = this.getNodeContrl().previousSetup();
		activity.finish();
		if (previousActivity != null
				&& ReflectUtil.isImplInterface(previousActivity.getClass(),
						TiFlowCallback.class.getName())) {
			((TiFlowCallback) previousActivity).callback(current);
		}

		if (previousActivity == null) {
			this.controlNode.releaseActivity();
			this.controlNode.finishFlow();
			this.controlNode = this.controlNode.getParentNode() == null ? null
					: this.controlNode.getParentNode();
			if (this.controlNode != null)
				this.controlNode.popActivity();
		}

	}

	public Stack<String> getFlowStack() {
		return this.getNodeContrl().getFlowStack();
	}

	/**
	 * 获取当前上下文
	 * 
	 * @return
	 */
	public Map<String, Serializable> getFlowContext() {
		if (controlNode == null) {
			return null;
		}
		return controlNode.getFlowContext();
	}

	public void removeContextDate(Class<?> clazz) {
		Map<String, Serializable> flowContext = getFlowContext();
		if (flowContext == null) {
			return;
		}
		flowContext.remove(clazz.getClass().getName());
	}

	public <T> T getFlowContextData(Class<? extends T> clazz) {
		Map<String, Serializable> flowContext = getFlowContext();
		if (flowContext == null) {
			return null;
		}
		Object obj = flowContext.get(clazz.getName());
		if (obj == null) {
			return null;
		}
		return clazz.cast(obj);
	}

	public void setFlowContextData(Serializable date) {
		Map<String, Serializable> flowContext = getFlowContext();
		flowContext.put(date.getClass().getName(), date);
	}

	public String getCurrentFlowName() {
		return getNodeContrl().getCurrentFlowName();
	}

	public String[] getCurrentFlows() {
		return getNodeContrl().getCurrentFlows();
	}

	public String getCurrentNodeName() {
		return getNodeContrl().getCurrentNodeName();
	}

	/**
	 * 存储当前状态
	 */
	public void persistenceFlow(Context context) {
		TiFlowAdmin admin = TiFlowAdmin.singletonInstance();
		admin.persistence(context);
	}

	/**
	 * 恢复当前状态
	 */
	public void restoreFlow(Context context) {
		TiFlowAdmin admin = TiFlowAdmin.singletonInstance();
		admin.restoreFlowControl(context);
	}

	public boolean isInFlow() {
		return !(controlNode == null);
	}

	/**
	 * 获取当前TiFlowControl
	 * 
	 * @return
	 */
	protected TiFlowNodeControl getNodeContrl() {
		if (controlNode == null) {
			throw new TiFlowException(TiFlowException.TIFLOWEX_GROUP_PROCESS,
					TiFlowErrorCode.PROCESS_FLOW_STATUS_ERROR,
					"flow control is not found");
		}
		return controlNode;
	}

	/**
	 * 启动流程
	 * 
	 * @param activity
	 * @param params
	 */
	private void startFlowByBeginNode(Activity activity,
			Map<String, String> params) {
		TiFlowNode node = controlNode.startFlow(activity);

		if (node.isStartByComplete()) {
			startFlowByNodeComplete(activity, params, node);
		}
		if (node.getRefClass() != null) {
			startFlowByRefClass(activity, params, node);
		}
		return;
	}

	private void startFlowByRefClass(Activity activity,
			Map<String, String> params, TiFlowNode node) {
		Intent intent = new Intent(activity, getClassByStr(node.getRefClass()));
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				intent.putExtra(key, params.get(key));
			}
		}
		activity.startActivity(intent);
	}

	private void startFlowByNodeComplete(Activity activity,
			Map<String, String> params, TiFlowNode node) {
		TiFlowNodeComplete complete = node.getStartComplete();
		Map<String, String> transferData = transferDataByComplete(complete,
				null, activity, params);// 获得传递过来的上下文数据
		if (transferData != null && !transferData.isEmpty() && params != null) {
			params.putAll(transferData);
		}
		if (complete.isForwardNextFlow()) {// 如果有子流程，则开始子流程
			startSubFlow(complete, TiFlowAdmin.singletonInstance(), activity,
					params);
			return;
		}
		// 跳转activity，并传递上下文数据
		Intent intent = new Intent(activity, getClassByStr(node.getRefClass()));
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				intent.putExtra(key, params.get(key));
			}
		}
		activity.startActivity(intent);
	}

	private void startSubFlow(TiFlowNodeComplete complete, TiFlowAdmin admin,
			Activity activity, Map<String, String> transferData) {
		TiFlowControlNode subNode = new TiFlowControlNode(
				admin.getFlowStatusControl(complete.getSubFlowName()));
		subNode.setParentNode(this.controlNode);
		controlNode = subNode;
		this.startFlowByBeginNode(activity, transferData);
		if (complete.isRemoveNode()) {
			activity.finish();
		}
	}

	/**
	 * 转换数据
	 * 
	 * @param complete
	 * @param subFlowContext
	 * @param activity
	 * @return
	 */
	private Map<String, String> transferDataByComplete(
			TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext, Activity activity,
			Map<String, String> sendData) {
		Map<String, String> transferData = complete.getUnTransferData();
		if (sendData != null && !sendData.isEmpty())
			transferData.putAll(sendData);
		if (complete.getTransfer() != null) {
			transferData = complete.getTransfer().transfterData(activity,
					complete.getUnTransferData(), complete, subFlowContext);
		}
		return transferData;
	}

	/**
	 * 释放所有流程
	 * 
	 * @param node
	 */
	private void releaseNode(TiFlowNodeControl node) {

		if (node == null) {
			return;
		}
		node.finishFlow();
		node.releaseActivity();
		if (node.getParentNode() != null) {
			releaseNode(node.getParentNode());
		}
	}

	@SuppressWarnings("rawtypes")
	private Class getClassByStr(String classStr) {
		try {
			return Class.forName(classStr);
		} catch (ClassNotFoundException e) {
			throw new TiFlowException(TiFlowException.TIFLOWEX_GROUP_PROCESS,
					TiFlowErrorCode.PROCESS_SUBFLOW_ISNULL,
					"flow complete error, the load Class happend error, class type is "
							+ classStr, e);
		}
	}

}
