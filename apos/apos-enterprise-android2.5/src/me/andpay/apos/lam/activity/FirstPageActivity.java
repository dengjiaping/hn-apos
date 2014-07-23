package me.andpay.apos.lam.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.MemoryRecorder;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.common.util.CrashLyticsUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

/**
 * 
 * 
 * @author cpz
 * 
 */
public class FirstPageActivity extends Activity {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		try {
			CrashLyticsUtil.initCrashLytics(this);

			StatConfig.setAutoExceptionCaught(false);
			StatService.trackCustomEvent(this, "onCreate", "");

		} catch (Throwable ex) {
			Log.e(this.getClass().getName(), "init crash or mta  error", ex);
		}

		setContentView(R.layout.lam_firstpage_layout);

		new AsyncTask() {
			@Override
			protected Object doInBackground(Object... params) {

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// ignore
				}
				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				// final TiInitActivity tiInit = new TiInitActivity(
				// getApplicationContext());
				// tiInit.onCreate(bundle);
				TiFlowControlImpl.instanceControl().startFlow(
						FirstPageActivity.this, FlowNames.COM_START_FLOW);
				MemoryRecorder.startRecordMemory();
				finish();
			}
		}.execute(new Object[] {});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
