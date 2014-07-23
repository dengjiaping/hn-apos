package me.andpay.apos.common.activity;

import me.andpay.apos.common.MemoryRecorder;
import me.andpay.timobileframework.mvc.support.TiTabActivity;

public class AposBaseTabActivity extends TiTabActivity {
	
	
	
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if (!MemoryRecorder.isRecordMemory()) {
			MemoryRecorder.reset(this);
		}
	}

	@Override
	protected final void onResume() {
		super.onResume();
		if (!MemoryRecorder.isRecordMemory()) {
			MemoryRecorder.reset(this);
		} else {
			onResumeProcess();
		}
	}
	
	/**
	 * 用于处理
	 */
	protected void onResumeProcess() {
		
	}
}
