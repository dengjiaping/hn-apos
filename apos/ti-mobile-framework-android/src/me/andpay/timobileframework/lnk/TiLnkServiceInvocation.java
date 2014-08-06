package me.andpay.timobileframework.lnk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import me.andpay.ti.lnk.rpc.NextStep;
import me.andpay.ti.lnk.transport.ChannelReadTimeoutException;
import me.andpay.ti.lnk.transport.websock.common.NetworkErrorException;
import me.andpay.ti.lnk.transport.websock.common.NetworkOpPhase;
import me.andpay.ti.lnk.transport.wsock.client.NetworkStatusChecker;

public class TiLnkServiceInvocation {

	public Object proxy(Class<?> lnkClass, TiRpcClient tiRpcClient,NetworkStatusChecker networkStatusChecker) {

		TiLnkServiceProxy eventInvocation = new TiLnkServiceProxy();
		eventInvocation.setTiRpcClient(tiRpcClient);
		eventInvocation.setLnkClass(lnkClass);
		eventInvocation.setNetworkStatusChecker(networkStatusChecker);
		Object proxy = Proxy.newProxyInstance(lnkClass.getClassLoader(),
				new Class[] { lnkClass }, eventInvocation);

		return proxy;
	}

	
	public class TiLnkServiceProxy implements InvocationHandler {

		private TiRpcClient tiRpcClient;

		private Class<?> lnkClass;
		
		private NetworkStatusChecker networkStatusChecker;

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			
			if(!networkStatusChecker.available()) {
				throw new NetworkErrorException(NetworkOpPhase.CONNECT, "network error", 0, "network error", 0);
			}
			
			tiRpcClient.start();

			Object linkServcie = tiRpcClient.getLnkService(lnkClass);
			try {
				return method.invoke(linkServcie,args);
			} catch (InvocationTargetException invocationTargetException) {
				
				if(invocationTargetException.getCause() instanceof ChannelReadTimeoutException) {
					throw new NetworkErrorException(NetworkOpPhase.READ_WRITE, 0, "channel read timeout", 0);
				}
				
				throw invocationTargetException.getCause();
			}

		}

		public void setTiRpcClient(TiRpcClient tiRpcClient) {
			this.tiRpcClient = tiRpcClient;
		}

		public void setLnkClass(Class<?> lnkClass) {
			this.lnkClass = lnkClass;
		}

		public void setNetworkStatusChecker(NetworkStatusChecker networkStatusChecker) {
			this.networkStatusChecker = networkStatusChecker;
		}
		
		

	}

}
