package me.andpay.timobileframework.flow.imp;

import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

import me.andpay.timobileframework.flow.TiFlowNode;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowStatusControl;
import android.app.Activity;

public interface TiFlowNodeControl {

	public TiFlowNode startFlow(Activity activity);

	/**
	 * 
	 * 跳转到下个流程
	 * 
	 * 如果跳转到子流程，并且设置finish Flag：子流程不继承当前流程contextData 并且当前流程中所有activity进行销毁
	 * 
	 * @param identity
	 */
	public TiFlowNodeComplete nextSetup(Activity activity, String identity);

	/**
	 * 返回上一步
	 * 
	 * 如果activity实现TiFlowCallback接口，则调用callback方法
	 * 
	 * @param activity
	 */
	public Activity previousSetup();

	/**
	 * 获取上下文
	 * 
	 * @return
	 */
	public Map<String, Serializable> getFlowContext();

	// 设置下个流程
	public void setParentNode(TiFlowNodeControl perviousNode);

	// 获取父流程
	public TiFlowNodeControl getParentNode();
	
	public TiFlowStatusControl getStatusControl();

	// 子流程结束通知
	public TiFlowNodeComplete getLastNodeComplete();

	// 记录activity
	public void recordFlowActivity(Activity activity);

	public Activity getStartActivity(Activity activity);
	
	public Stack<String> getFlowStack();

	/**
	 * 获取当前流程名称
	 * 
	 * @return
	 */
	public String getCurrentFlowName();

	/**
	 * 获取当前流程名称(如果有父流程，则包含父流程名称)
	 * 
	 * @return
	 */
	public String[] getCurrentFlows();

	/**
	 * 获取当前结点名称
	 * 
	 * @return
	 */
	public String getCurrentNodeName();

	// 结束当前流程，并且通知父流程
	public void finishFlow();
	
	/**
	 * 释放所有流程
	 */
	public void releaseActivity();
	 
	/**
	 * 释放所有流程
	 */
	public void popActivity();
	
	public Activity getLastActivity();
	
	
	/**
	 * 是否是最后的节点
	 * @return
	 */
	public boolean isLastNode();
}
