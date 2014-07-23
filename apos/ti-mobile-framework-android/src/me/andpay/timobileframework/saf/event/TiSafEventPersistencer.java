package me.andpay.timobileframework.saf.event;

import java.util.List;

/**
 * Saf Task 持久化服务
 * @author tinyliu
 *
 */
public interface TiSafEventPersistencer {
	/**
	 * 持久化Saf Task
	 */
	public void persistenceSafEvent(TiSafEvent safEvent);
	/**
	 * 移除持久化Saf Task
	 */
	public void removeSafEvent(TiSafEvent safEvent);
	/**
	 * 查询所有SafEvent
	 */
	public void queryAllSafEvent();
	
	public List<TiSafEvent> queryPollSafEvent();
	
	public void updateSafEvent(TiSafEvent safEvent);
}
