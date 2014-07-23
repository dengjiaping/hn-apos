package me.andpay.timobileframework.flow.persistence;

import java.io.Serializable;
import java.util.Map;

/**
 * 流程状态持久化
 * @author tinyliu
 *
 */
public interface TiFlowControlPersistDataOperator {
	
	public Map<String, Serializable> needToPersistenceData();
	
	public void restoreControlStatus(Map<String, Serializable> persistenceData);
	
	public boolean isNeedToPersistence() ;
}
