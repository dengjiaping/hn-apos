package me.andpay.apos.common.bug;

import java.util.Map;

public interface ThrowableReporter {
	
	public void submitError(Throwable ex);
	
	public void submitError(String errorStr,Map<String, String> devEnv);
	
	public void submitError(Throwable ex, Object data);

}
