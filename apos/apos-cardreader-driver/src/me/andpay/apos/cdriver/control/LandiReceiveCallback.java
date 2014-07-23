package me.andpay.apos.cdriver.control;


import android.os.ConditionVariable;


public class LandiReceiveCallback {

	private String data;
	
	private long timeout;
	
	
	private ConditionVariable receiveCondition = null;

	
	
	public  LandiReceiveCallback(long timeout) {
		this.timeout = timeout;
		receiveCondition = new ConditionVariable();
	}
	
	public void  onReceiveData(String data) {
		this.data = data;
		receiveCondition.open();
	}

	public String getData() {
		
		receiveCondition.block(timeout);
		
		return data;
	}
	
	
	
}
