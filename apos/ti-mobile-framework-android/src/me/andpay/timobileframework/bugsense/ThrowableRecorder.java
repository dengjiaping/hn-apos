package me.andpay.timobileframework.bugsense;

import java.util.List;

public interface ThrowableRecorder {
	
	public void recordThrowable(Throwable ex);
	
	public List<String> readThrowables();
	
	public void deleteAllThrowables();
	
	public static ThrowableRecorder DEFAULT = new TextThrowableRecorder();
}
