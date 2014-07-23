package me.andpay.timobileframework.lnk;

import java.lang.reflect.Field;

import me.andpay.ti.lnk.annotaion.LnkService;
import me.andpay.ti.lnk.transport.wsock.client.NetworkStatusChecker;
import me.andpay.timobileframework.util.ReflectUtil;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class TiLnkServiceTypeListener implements TypeListener {

	@Inject
	private TiRpcClient client;
	@Inject
	private TiLnkServiceInvocation lnkServiceInvocation;
	
	@Inject
	private NetworkStatusChecker networkStatusChecker;

	public TiLnkServiceTypeListener() {
	}

	public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
	
		for (Class<?> c = type.getRawType(); c != Object.class; c = c
				.getSuperclass()) {
			if (ReflectUtil
					.isImplInterface(c, TiRpcClientAware.class.getName())){
				encounter.register(new InjectionListener() {

					public void afterInjection(Object injectee){
						((TiRpcClientAware) injectee).setLnkRpcClient(client);

					}
				});
			}
			for (Field field : c.getDeclaredFields()){
				if (field.getType().isAnnotationPresent(LnkService.class)) {
					encounter.register(new TiLnkServiceInjector(field, this));
				}
			}

		}
		
		
	}
	
	public TiRpcClient getClient() {
		return client;
	}

	public TiLnkServiceInvocation getLnkServiceInvocation() {
		return lnkServiceInvocation;
	}

	public NetworkStatusChecker getNetworkStatusChecker() {
		return networkStatusChecker;
	}
	
	
}
