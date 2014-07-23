package me.andpay.timobileframework.saf.impl;

import java.util.List;
import java.util.TimerTask;

import me.andpay.timobileframework.saf.event.TiSafActionReporter;
import me.andpay.timobileframework.saf.event.TiSafEvent;
import me.andpay.timobileframework.saf.event.TiSafEventDispather;
import me.andpay.timobileframework.saf.event.TiSafEventExcuInfo;
import me.andpay.timobileframework.saf.event.TiSafEventPersistencer;

/*public class TiSafTask extends TimerTask implements TiSafActionReporter{

	private TiSafEventPersistencer persistencer;



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
		// TODO Auto-generated method stub
		
	}
}*/
