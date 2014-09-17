package me.andpay.apos.common.activity;

import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.MemoryRecorder;
import me.andpay.timobileframework.flow.activity.TiFlowActivity;
import android.view.KeyEvent;

public class AposBaseActivity extends TiFlowActivity {

	@Override
	protected void onRestart() {
		super.onRestart();
		if (!MemoryRecorder.isRecordMemory()) {
			MemoryRecorder.reset(this);
		}
	}

	//在onRestart和OnResume中判断jvm是否被重置，如果重置则跳转到首页进行初始化恢复
	//所有想在Onresume中实现的逻辑都必须在onResumeProcess中实现，否则会出现
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
	/**
	 * 监听返回键按钮点击事件，如果当前存在流程，则用流程控制器进行回退
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (getControl().isInFlow()) {
				getControl().previousSetup(this);
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);

	}
	
	public void alertErrorMsg(String msg) {
		PromptDialog dialog = new PromptDialog(this, "错误提示", msg);
		dialog.show();
	}
	

}
