package me.andpay.timobileframework.flow;

import java.io.Serializable;
import java.util.Map;

import android.app.Activity;

import me.andpay.timobileframework.flow.activity.TiFlowActivity;

/**
 * Complete 数据转换器
 * 
 * @author tinyliu
 * 
 */
public interface TiFlowNodeDataTransfer {
	/**
	 * 用于跳转时数据转换
	 * 
	 * @param activity
	 * @param data
	 * @param complete
	 * @return
	 */
	public Map<String, String> transfterData(Activity activity, Map<String, String> data,
			TiFlowNodeComplete complete, Map<String, Serializable> subFlowContext);

}
