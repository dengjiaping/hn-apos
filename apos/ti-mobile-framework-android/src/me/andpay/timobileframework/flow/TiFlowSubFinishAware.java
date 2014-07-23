package me.andpay.timobileframework.flow;

import java.io.Serializable;
import java.util.Map;

/**
 * 子流程结束感知接口
 * 当子流程结束后，如果父流程
 * @author tinyliu
 *
 */
public interface TiFlowSubFinishAware {
	public void subFlowFinished(Map<String, Serializable> subFlowContext);
}
