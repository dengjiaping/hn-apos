package me.andpay.timobileframework.bugsense;

import android.os.Looper;

public class ThrowableInfo {

	private Throwable ex;

	private Thread thread;

	private boolean isError;

	private boolean isAlwaysThrows = true;

	private boolean isUIThread = false;

	public ThrowableInfo(Throwable ex, Thread thread, boolean isAlwaysThrows) {
		this.ex = ex;
		this.thread = thread;
		this.isUIThread = (Looper.getMainLooper().getThread() == thread);
		this.isError = (ex instanceof Error);
		this.isAlwaysThrows = isAlwaysThrows;
	}

	public Throwable getEx() {
		return ex;
	}

	public void setEx(Throwable ex) {
		this.ex = ex;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public boolean isAlwaysThrows() {
		return isAlwaysThrows;
	}

	public void setAlwaysThrows(boolean isAlwaysThrows) {
		this.isAlwaysThrows = isAlwaysThrows;
	}

	public boolean isUIThread() {
		return isUIThread;
	}

	public void setUIThread(boolean isUIThread) {
		this.isUIThread = isUIThread;
	}
	
	

}
