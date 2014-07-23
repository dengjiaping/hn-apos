package me.andpay.timobileframework.flow.persistence;

import java.io.Serializable;
import java.util.Map;

/**
 * 持久化接口
 * @author tinyliu
 *
 */
public interface TiFlowControlPersistencer {
	
	/**
	 * 持久化数据接口
	 * @param storeName
	 * @param data
	 */
	public void persistenceData(String path, String storeName, Map<String, Serializable> data);
	
	/**
	 * 恢复存储数据
	 * @param storeName
	 * @param isNeedToDel 是否恢复后需要删除存储信息
	 * @return
	 */
	public Map<String, Serializable> restoreData (String path, String storeName, boolean isNeedToDel);
	
}
