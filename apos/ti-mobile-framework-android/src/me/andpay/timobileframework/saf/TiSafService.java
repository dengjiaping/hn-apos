package me.andpay.timobileframework.saf;

import java.io.Serializable;

import me.andpay.timobileframework.saf.enumeration.TiSafEventPriority;

/**
 * saf服务类
 * 
 * 
 * @author tinyliu
 *
 */
public interface TiSafService {

	/**
	 * 开启saf服务
	 */
	public void start();
	/**
	 * 开启saf服务
	 * @param config
	 */
	public void start(TiSafStartConfig config);
	
	/**
	 * 停止saf服务
	 */
	public void stop();
	/**
	 * 暂停saf服务
	 */
	public void pause();
	/**
	 * 恢复saf服务
	 */
	public void resume();
	/**
	 * 注册Saf event
	 * @return
	 */
	public void registeEvent(Serializable event);
	/**
	 * 注册Saf event
	 * @return
	 */
	public void registeEvent(Serializable event, TiSafEventPriority priority);
	/**
	 * 注销Saf对象
	 * @param event
	 */
	public void removeEvent(Serializable event);
	/**
	 * 注册saf event处理器
	 * @param safEventHandler
	 */
	public void registeEventHandler(Object safEventHandler);
}
