package me.andpay.timobileframework.saf.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import me.andpay.timobileframework.saf.TiSafScheduleHolder;
import me.andpay.timobileframework.saf.TiSafScheduleManager;
import me.andpay.timobileframework.saf.TiSafService;
import me.andpay.timobileframework.saf.TiSafStartConfig;
import me.andpay.timobileframework.saf.enumeration.TiSafEventPriority;
import me.andpay.timobileframework.saf.event.TiSafActionReporter;
import me.andpay.timobileframework.saf.event.TiSafEvent;
import me.andpay.timobileframework.saf.event.TiSafEventDispather;
import me.andpay.timobileframework.saf.event.TiSafEventExcuInfo;
import me.andpay.timobileframework.saf.event.TiSafEventPersistencer;

public class TiSafServiceImpl implements TiSafService {
	/**
	 * 默认延迟执行时间
	 */
	private static int SAF_DEFAULT_POLL_DELAY = 10 * 1000;
	/**
	 * 默认重复执行时间
	 */
	private static int SAF_DEFAULT_POLL_PERIOD = 30 * 1000;

	private TiSafStartConfig config;

	private TiSafScheduleManager scheduleManager;

	private TiSafScheduleHolder mainScheduleHolder;
	
	private TiSafEventPersistencer persistencer;
	
	private TiSafEventDispather dispather;
	
	private Map<String, TiSafScheduleHolder> customScheduleHolders;

	public TiSafServiceImpl() {
		config = new TiSafStartConfig();
		config.setDelayMills(SAF_DEFAULT_POLL_DELAY);
		config.setPeriod(SAF_DEFAULT_POLL_PERIOD);
		customScheduleHolders = new HashMap<String, TiSafScheduleHolder>();
	}

	public void start() {
		start(null);
	}

	public void start(TiSafStartConfig config) {
		if (config != null)
			this.config = new TiSafStartConfig();
		synchronized (config) {
			if (mainScheduleHolder != null) {
				throw new RuntimeException("the TiSafService is started");
			}
			//scheduleManager.startSchedule(config);
		}
	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void resume() {
		// TODO Auto-generated method stub

	}

	public void registeEvent(Serializable event) {
		// TODO Auto-generated method stub

	}

	public void registeEvent(Serializable event, TiSafEventPriority priority) {
		// TODO Auto-generated method stub

	}

	public void removeEvent(Serializable event) {
		// TODO Auto-generated method stub

	}

	public void registeEventHandler(Object safEventHandler) {
		// TODO Auto-generated method stub

	}
	
	class TiSafTask extends TimerTask implements TiSafActionReporter{

		@Override
		public void run() {
			List<TiSafEvent> events = persistencer.queryPollSafEvent();
			if (events == null || events.size() == 0) {
				return;
			}
			for (TiSafEvent event : events) {
				dispather.diapatheSafEvent(event, this);
			}
		}

		public void reporterExcuteResult(TiSafEventExcuInfo info) {
			if(info.isNeedRemove()) {
				persistencer.removeSafEvent(info.getEvent());
				return;
			}
			TiSafEvent event = info.getEvent();
			//event.setLastEventSendTime(lastEventSendTime)
		}

	}
}
