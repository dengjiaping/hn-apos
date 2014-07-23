package me.andpay.timobileframework.flow.forward;

import java.io.Serializable;
import java.util.Map;

import me.andpay.timobileframework.flow.TiFlowForward;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import android.app.Activity;
import android.content.Intent;

public class TiFlowDefaultForward implements TiFlowForward {

	public void forward(Intent intent, Activity fromActivity,
			TiFlowNodeComplete complete, Map<String, Serializable> subFlowContext) {
		fromActivity.startActivity(intent);
	}

}
