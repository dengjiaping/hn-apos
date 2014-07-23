package me.andpay.apos.mopm.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.MemoryRecorder;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.common.util.CrashLyticsUtil;
import me.andpay.apos.mopm.order.OrderPayContext;
import me.andpay.orderpay.util.OrderObjectConverter;
import me.andpay.timobileframework.common.AsyncTask;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MerchantOrderPayActivity extends Activity {
	private final static String STARTED_APOS = "startedApos";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lam_firstpage_layout);
		Intent intent = getIntent();
		boolean isStartedApos = intent.getBooleanExtra(STARTED_APOS, false);
		if (isStartedApos) {
			runWithStartedApos(intent);
		} else {
			runWithUnstartedApos(intent);
		}

	}

	private void runWithStartedApos(Intent intent) {
		MemoryRecorder.startRecordMemory();
		CrashLyticsUtil.initCrashLytics(MerchantOrderPayActivity.this);
		OrderPayContext orderPayContext = new OrderPayContext();
		orderPayContext.setOrderPayRequest(OrderObjectConverter
				.intentToRequest(intent));
		orderPayContext.setNeedAutoLogin(true);
		TiFlowControlImpl.instanceControl().startFlow(
				MerchantOrderPayActivity.this,
				FlowNames.MOPM_MERCHANT_ORDER_PAY_FLOW);
		TiFlowControlImpl.instanceControl().setFlowContextData(orderPayContext);
		finish();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void runWithUnstartedApos(final Intent intent) {
		new AsyncTask() {
			@Override
			protected Object doInBackground(Object... params) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				MemoryRecorder.startRecordMemory();
				CrashLyticsUtil.initCrashLytics(MerchantOrderPayActivity.this);
				OrderPayContext orderPayContext = new OrderPayContext();
				orderPayContext.setOrderPayRequest(OrderObjectConverter
						.intentToRequest(intent));
				orderPayContext.setNeedAutoLogin(true);
				TiFlowControlImpl.instanceControl().startFlow(
						MerchantOrderPayActivity.this,
						FlowNames.MOPM_MERCHANT_ORDER_PAY_FLOW);
				TiFlowControlImpl.instanceControl().setFlowContextData(
						orderPayContext);
				finish();
			}
		}.execute(new Object[] {});
	}
}
