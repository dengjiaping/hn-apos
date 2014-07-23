package me.andpay.timobileframework.flow;

import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

import android.app.Activity;
import android.content.Context;

public interface TiFlowControl {
	
	/**
	 * 通过传入流程名称开启流程
	 * @param activity
	 * @param flowName 流程名称
	 */
	public void startFlow(Activity activity, String flowName);
	/**
	 * 通过传入流程名称开启流程
	 * @param activity
	 * @param flowName 流程名称
	 * @param params 需要传递的参数(注：参数设置在intent中)
	 */
	public void startFlow(Activity activity, String flowName, Map<String, String> params);
	
	/**
	 * 
	 * 跳转到下个流程
	 * 
	 * 如果跳转到子流程，并且设置finish Flag：子流程不继承当前流程contextData 并且当前流程中所有activity进行销毁
	 * 
	 * @param identity
	 */
	public void nextSetup(Activity activity, String identity);

	/**
	 * 
	 * 跳转到下个流程
	 * 
	 * 如果跳转到子流程，并且设置finish Flag：子流程不继承当前流程contextData 并且当前流程中所有activity进行销毁
	 * 
	 * @param activity
	 * @param identity node－complete对应名称
	 * @param sendData 需要传递的参数(注：参数设置在intent中)
	 */
	public void nextSetup(Activity activity, String identity, Map<String, String> sendData) ;
	/**
	 * 返回上一步
	 * 
	 * 如果activity实现TiFlowCallback接口，则调用callback方法
	 * 
	 * @param activity
	 */
	public void previousSetup(Activity activity) ;

	/**
	 * 获取当前流程堆栈
	 * @return
	 */
	public Stack<String> getFlowStack();

	/**
	 * 获取当前上下文
	 * 
	 * @return
	 */
	public Map<String, Serializable> getFlowContext();
	/**
	 * 根据数据类型获取上下文数据
	 * @param clazz
	 * @return
	 */
	public <T> T getFlowContextData(Class<? extends T> clazz);

	/**
	 * 设置当前上下文数据
	 * key＝ClassName
	 * @param date
	 */
	public void setFlowContextData(Serializable date);

	/**
	 * 获取当前流程名称
	 * @return
	 */
	public String getCurrentFlowName();
	/**
	 * 获取当前流程名称(如果有父流程，则包含父流程名称)
	 * @return
	 */
	public String[] getCurrentFlows();

	/**
	 * 获取当前结点名称
	 * @return
	 */
	public String getCurrentNodeName();

	/**
	 * 存储当前状态
	 */
	public void persistenceFlow(Context context);

	/**
	 * 恢复当前状态
	 */
	public void restoreFlow(Context context) ;
	/**
	 * 判断当前是否在流程中
	 * @return
	 */
	public boolean isInFlow();
	
}
