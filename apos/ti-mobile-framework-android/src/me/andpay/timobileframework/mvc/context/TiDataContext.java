package me.andpay.timobileframework.mvc.context;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.util.Log;

public class TiDataContext implements TiContext {

	private Map attributes;

	private Date createTime;

	private Date lastAccessTime;

	private Application application;

	public TiDataContext(Application application) {
		attributes = new HashMap();
		createTime = new Date();
		this.application = application;
	}

	public Object getAttribute(String name) {
		setLastAccessTime();
		Log.d(this.getClass().getName(),
				"TiDataContext get Attribute , key is " + name);
		Object value = attributes.get(name);
//		if(value == null && application != null) {
//			
//			TiAppContextStore tiStore = RoboGuice.getInjector(application.getApplicationContext()).getInstance(TiAppContextStore.class);
//			Object data = tiStore.getAttribute(name);
//			attributes.put(name, data);
//			return data;
//		}
		return value;

	}
	
	
	/**
	 * 泛型获取
	 */
	public Object getAttribute(Type type, String name) {
		setLastAccessTime();
		Object value = attributes.get(name);
//		if(value == null && application != null) {
//			TiAppContextStore tiStore = RoboGuice.getInjector(application.getApplicationContext()).getInstance(TiAppContextStore.class);
//			Object data = tiStore.getAttribute(type,name);
//			attributes.put(name, data);
//			return data;
//		}
		return value;
	}
	public void setAttribute(String name, Object value) {
		setLastAccessTime();

//		// 上下文数据保存
//		if (application != null) {
//			TiAppContextStore tiStore = RoboGuice.getInjector(application)
//					.getInstance(TiAppContextStore.class);
//			tiStore.setAttribute(name, value);
//		}
		attributes.put(name, value);
	}

	public void setAttribute(Map attribs) {
		setLastAccessTime();
		for (Object name : attribs.keySet()) {
			setAttribute(name.toString(), attribs.get(name));
		}
	}

	public void removeAttribute(String name) {
		setLastAccessTime();
//		if (application != null) {
//			TiAppContextStore tiStore = RoboGuice.getInjector(application)
//					.getInstance(TiAppContextStore.class);
//			tiStore.removeAttribute(name);
//		}
		attributes.remove(name);
	}

	public Date getCreationTime() {
		return createTime;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public int getScope() {
		return TiContext.CONTEXT_SCOPE_EVENTPROCESS;
	}

	private void setLastAccessTime() {
		lastAccessTime = new Date();
	}

	public void inValidate() {
//		if (application != null) {
//			TiAppContextStore tiStore = RoboGuice.getInjector(application)
//					.getInstance(TiAppContextStore.class);
//			tiStore.clearAll();
//		}
		attributes.clear();
	}

	public boolean isEmpty() {
		return attributes.isEmpty();
	}

}
