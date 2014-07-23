package me.andpay.timobileframework.flow;

import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

/**
 * 流程状态控制接口
 * @author tinyliu
 *
 */
public interface TiFlowStatusControl {
	
	/**
	 * 开始流程
	 * @param activity
	 * @param params 出入参数会设置到Context上下文中
	 * @return
	 */
	public TiFlowNode start(Map<String, Serializable> values);
	
	/**
	 * 根据标示进行流转下一步
	 * @param identity
	 */
	public TiFlowNodeComplete nextStepWithIdentity(String identity);
	
	/**
	 * 返回上一步
	 * @param activity
	 */
	public String previousStep();
	
	/**
	 * 获取当前节点名称
	 * @return
	 */
	public String getCurrentNodeName();
	/**
	 * 获取上一处理节点名称
	 * @return
	 */
	public String getPreviousNodeName();
	/**
	 * 获取流程处理堆栈
	 * @return
	 */
	public Stack<String> getFlowStack();
	
	/**
	 * 判断当前流程是否结束
	 * @return
	 */
	public boolean isFlowFinished();
	
	/**
	 * 获取当前流程图 
	 * @return
	 */
	public TIFlowDiagram getDiagram();
	
	/**
	 * 完成流程图步骤 
	 * 注意：如果当前流程控制器没有finish，无法进行重新start流程
	 */
	public void finish();
	/**
	 * 获取流程上下文
	 * @return
	 */
	public Map<String, Serializable> getFlowContext();
	
	/**
	 * 获取最后一次跳转结点
	 * @return
	 */
	public TiFlowNodeComplete getLastNodeComplete();
}
