package me.andpay.timobileframework.bugsense;

public interface ThrowableHandler {
	
	public void processThrowable(ThrowableInfo info);
	
	public boolean isSupport(Thread thread, Throwable ex);

}
