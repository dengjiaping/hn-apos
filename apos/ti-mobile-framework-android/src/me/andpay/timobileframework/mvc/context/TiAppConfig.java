package me.andpay.timobileframework.mvc.context;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 应用配置上下文
 * 
 * Scope：永久
 * 
 * @author tinyliu
 * 
 */

public class TiAppConfig implements TiContext {

	private SharedPreferences sharedPreferences;

	private String appName;

	public TiAppConfig(SharedPreferences sharedPreferences, String appName) {
		this.sharedPreferences = sharedPreferences;
		this.appName = appName;
	}

	@Deprecated
	public Date getCreationTime() {
		return null;
	}

	@Deprecated
	public Date getLastAccessTime() {
		return null;
	}

	public int getScope() {
		return TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG;
	}

	public String getAttribute(String name) {
		return sharedPreferences.getString(name, "");
	}

	public void setAttribute(String name, Object value) {
		Editor edit = sharedPreferences.edit();
		edit.putString(name, value.toString());
		edit.commit();
	}

	public void removeAttribute(String name) {
		Editor edit = sharedPreferences.edit();
		edit.remove(name);
		edit.commit();
	}

	public void setAttribute(Map<String, Object> attribs) {
		Editor edit = sharedPreferences.edit();
		for (String key : attribs.keySet()) {
			edit.putString(key, attribs.get(key).toString());
		}
		edit.commit();

	}

	public void inValidate() {
		Editor edit = sharedPreferences.edit();
		edit.clear();
		edit.commit();
		return;
	}

	public boolean isEmpty() {
		return false;
	}
	
	@Deprecated
	public Object getAttribute(Type type, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
