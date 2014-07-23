package me.andpay.timobileframework.flow;

import java.io.Serializable;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;

/**
 * 跳转接口 用于扩展跳转
 * 
 * @author tinyliu
 * 
 */
public interface TiFlowForward {

	public static final String TIFLOW_FORWARD_TYPE_START = "start";

	public static final String TIFLOW_FORWARD_TYPE_STARTFORRESULT = "startForResult";

	/**
	 * 跳转方法
	 * 
	 * @param intent
	 * @param fromActivity
	 * @param node
	 */
	public void forward(Intent intent, Activity fromActivity,
			TiFlowNodeComplete complete, Map<String, Serializable> subFlowContext);

}
