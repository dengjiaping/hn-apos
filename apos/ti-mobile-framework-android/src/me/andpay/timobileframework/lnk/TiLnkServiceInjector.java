package me.andpay.timobileframework.lnk;

import java.lang.reflect.Field;

import com.google.inject.MembersInjector;

/**
 * 注入TiLnkSrv
 * 
 * @author tinyliu
 * 
 */
public class TiLnkServiceInjector implements MembersInjector {

	private Field field = null;

	private TiLnkServiceTypeListener client;

	public TiLnkServiceInjector(Field field, TiLnkServiceTypeListener client) {
		this.field = field;
		this.client = client;
	}

	public void injectMembers(Object instance){
		Class<?> tiLnkSrvClass = field.getType();

		Object tiLnkSrv = client.getLnkServiceInvocation().proxy(tiLnkSrvClass,
				client.getClient(),client.getNetworkStatusChecker());
		field.setAccessible(true);
		try {
			field.set(instance, tiLnkSrv);
		} catch (Exception ex) {
			throw new RuntimeException("inject TiLnkSrv happend error", ex);
		}
	}

}
