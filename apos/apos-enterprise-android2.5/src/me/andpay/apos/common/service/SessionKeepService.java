package me.andpay.apos.common.service;

import me.andpay.ac.term.api.info.TimeService;
import me.andpay.ti.util.SleepUtil;
import me.andpay.timobileframework.util.NetWorkUtil;
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

/**
 * session维持器
 * 
 * @author cpz
 * 
 */
public class SessionKeepService {

	private TimeService timeService;

	@Inject
	private Application application;

	private Thread thread = null;

	public void keepSession() {
		if (!NetWorkUtil.isNetworkConnected(application.getApplicationContext())) {
			return;
		}
		if (thread != null) {
			thread.interrupt();
		}
		thread = new Thread(new KeepRunner());
		thread.start();
	}

	public class KeepRunner implements Runnable {
		public void run() {

			try {
				getTime();
				Log.i(this.getClass().getName(), "session keep service runner");
				SleepUtil.sleep(3 * 1000l);
				keepSession();
			} catch (Exception e) {
				// ignore;
			}
		}
	}

	private void getTime() {
		try {
			timeService.getTime();
		} catch (Exception e) {
			SleepUtil.sleep(30000l);
			getTime();
		}
	}

}
