package me.andpay.timobileframework.flow.activity;

import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

import me.andpay.timobileframework.flow.TiFlowControl;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.support.TiActivity;

public class TiFlowActivity extends TiActivity {

	public TiFlowControl getTiFlowControl() {
		return TiFlowControlImpl.instanceControl();
	}

	public void startFlow(String flowName) {
		getTiFlowControl().startFlow(this, flowName);
	}

	public void startFlow(String flowName, Map<String, String> params) {
		getTiFlowControl().startFlow(this, flowName, params);
	}

	/**
	 * 
	 * 跳转到下个流程
	 * 
	 * 如果跳转到子流程，并且设置finish Flag：子流程不继承当前流程contextData 并且当前流程中所有activity进行销毁
	 * 
	 * @param identity
	 */
	public void nextSetup(String identity) {
		nextSetup(identity, null);
	}

	/**
	 * 
	 * 跳转到下个流程
	 * 
	 * 如果跳转到子流程，并且设置finish Flag：子流程不继承当前流程contextData 并且当前流程中所有activity进行销毁
	 * 
	 * @param identity
	 */
	public void nextSetup(String identity, Map<String, String> sendData) {
		getTiFlowControl().nextSetup(this, identity, sendData);
	}

	public void previousSetup() {
		getTiFlowControl().previousSetup(this);
	}

	public Stack<String> getFlowStack() {
		return getTiFlowControl().getFlowStack();
	}

	/**
	 * 获取当前上下文
	 * 
	 * @return
	 */
	public Map<String, Serializable> getFlowContext() {
		return getTiFlowControl().getFlowContext();
	}

	public <T> T getFlowContextData(Class<? extends T> clazz) {
		return getTiFlowControl().getFlowContextData(clazz);
	}

	public void setFlowContextData(Serializable data) {
		getTiFlowControl().setFlowContextData(data);
	}

	public String getCurrentFlowName() {
		return getTiFlowControl().getCurrentFlowName();
	}

	public String[] getCurrentFlowsName() {
		return getTiFlowControl().getCurrentFlows();
	}

	public String getCurrentNodeName() {
		return getTiFlowControl().getCurrentNodeName();
	}
}
