package me.andpay.apos.common.bug;

import java.util.List;

import me.andpay.timobileframework.bugsense.ThrowableRecorder;

import com.google.inject.Inject;

public class AposReportPersistenceThrowService {

	@Inject
	private ThrowableReporter throwableReporter;

	@Inject
	private ThrowableRecorder recorder;

	public void report() {
		new Thread(new Runnable() {
			public void run() {
				List<String> exs = recorder.readThrowables();
				for (String ex : exs)
					throwableReporter.submitError(new RuntimeException(ex));
			}
		}).start();
	}

}
