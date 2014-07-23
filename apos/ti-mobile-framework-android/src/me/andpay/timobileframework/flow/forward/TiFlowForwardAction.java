package me.andpay.timobileframework.flow.forward;

import me.andpay.timobileframework.flow.TiFlowForward;
import me.andpay.timobileframework.flow.TiFlowForwarder;
import android.app.Activity;

public class TiFlowForwardAction {
	
	private TiFlowForwarder forwarder;
	
	private TiFlowForward forward;
	
	public TiFlowForwardAction(TiFlowForwarder forwarder, TiFlowForward forward) {
		this.forwarder = forwarder;
		this.forward = forward;
	}
	
	public void forward(Activity activity) {
		String classStr = forwarder.getForwardActivity();
		
	}
	
}
