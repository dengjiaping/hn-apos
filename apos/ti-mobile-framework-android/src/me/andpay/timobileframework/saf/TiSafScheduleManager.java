package me.andpay.timobileframework.saf;

import java.util.List;

public interface TiSafScheduleManager {

	public TiSafScheduleHolder startSchedule(TiSafScheduleConfig config);
	
	public List<TiSafScheduleHolder> getAllSchedule();
	
	public void stopSchedule(TiSafScheduleHolder holder);
	
	public void stopAll();
}
