package me.andpay.timobileframework.bugsense;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;

/**
 * 异常感知类
 * 
 * @author tinyliu
 * 
 */
public class ThrowableSense {

	private static List<ThrowableHandler> handlers = new ArrayList<ThrowableHandler>();

	/**
	 * 初始化异常感知
	 */
	public static ThrowableCaughter caughtThrowable(Thread currentThread) {
		UncaughtExceptionHandler currentHandler = currentThread
				.getDefaultUncaughtExceptionHandler();
		if (!(currentHandler instanceof ThrowableCaughter)) {
			ThrowableCaughter caughter = new ThrowableCaughter(currentHandler,
					handlers,
					TiAndroidRuntimeInfo.isUIMainThread(currentThread));
			currentThread.setUncaughtExceptionHandler(caughter);
		}
		return (ThrowableCaughter) currentThread.getUncaughtExceptionHandler();
	}

	public static void caughtThrowable(Throwable throwable) {

		ThrowableCaughter caughter = new ThrowableCaughter(null, handlers,
				false);
		caughter.uncaughtException(Thread.currentThread(), throwable);
	}

	public static void registerHandler(ThrowableHandler handler) {
		handlers.add(handler);
	}

	public static boolean isAssignThrowable(Throwable throwable,
			Class<? extends Throwable> exClass) {
		if (throwable == null) {
			return false;
		}
		if (throwable.getClass().getName().equals(exClass.getName())) {
			return true;
		} else {
			return isAssignThrowable(throwable.getCause(), exClass);
		}

	}

	public static <T extends Throwable> T getAssignThrowable(
			Throwable throwable, Class<T> classes) {
		if (throwable == null) {
			return null;
		}
		if (throwable.getClass().getName().equals(classes.getName())) {
			return classes.cast(throwable);
		} else {
			return getAssignThrowable(throwable.getCause(), classes);
		}

	}

	public static void removeHandler() {

	}

}
