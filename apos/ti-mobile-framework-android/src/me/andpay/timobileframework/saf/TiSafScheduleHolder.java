package me.andpay.timobileframework.saf;

import java.util.Date;

/**
 * schedule持有者
 * 
 * @author tinyliu
 *
 */
public interface TiSafScheduleHolder {
	/**
	 * 获取Schedule分配编号
	 * @return
	 */
	public String getScheduleId();
	/**
	 * schedule是否运行
	 * @return
	 */
	public boolean isWork();
	/**
	 * 获取最后执行时间
	 * @return
	 */
	public Date getLastExcuteTime();
	/**
	 * 获取执行次数
	 * @return
	 */
	public int getExcuteCount();
	/**
	 * 获取轮询配置
	 * @return
	 */
	public TiSafScheduleConfig getConfig();
	/**
	 * 获取间隔时间
	 * @return
	 */
	public int getPeriodMills();
	/**
	 * 获取延迟执行时间
	 * @return
	 */
	public int getDelayMills();
}
