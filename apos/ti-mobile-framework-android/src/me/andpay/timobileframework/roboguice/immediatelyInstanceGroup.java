package me.andpay.timobileframework.roboguice;

import java.util.List;

import com.google.inject.Injector;

public class immediatelyInstanceGroup {
	private static List<Class> instanceClasses = null;

	private boolean isInit;

	public void register(Class instanceClass) {
		instanceClasses.add(instanceClass);
	}

	public void instance(Injector injector) {
		if (!isInit)
			return;
		for (Class instanceClass : instanceClasses) {
			injector.getInstance(instanceClass);
		}
		isInit = true;
	}

}
