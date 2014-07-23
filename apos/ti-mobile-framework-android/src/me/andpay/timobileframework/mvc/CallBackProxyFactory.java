package me.andpay.timobileframework.mvc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.os.Handler;
import android.os.Message;
/**
 * callbackHandler生产工厂。
 * 
 * 由于UI操作都必须是在UI Main Thread中进行。
 * 
 * 所以必须对CallBackHandler进行封装代理，当前端调用CallBack的时候全部都是在发送消息的Main Thread来进行处理。
 * 
 * @author tinyliu
 *
 */
public class CallBackProxyFactory {
	
	public static Object generateProxy(Object handler) {
		InvocationHandler invocationHandler = new CallBackHandlerProxy(handler);
		Class<?> classType = handler.getClass();
		Object proxy = (Object)Proxy.newProxyInstance(classType.getClassLoader(),
				handler.getClass().getInterfaces(), invocationHandler);
		
		return proxy;
	}
	
	static class CallBackHandlerProxy extends Handler implements InvocationHandler {
		/**
		 * 标识消息代码
		 */
		private int CallBackMessage = 101;
		
		private Object callback = null;
		
		public CallBackHandlerProxy(Object callback) {
			this.callback = callback;
		}
		
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			this.sendMessage(this.obtainMessage(CallBackMessage, new InvokeInfo(callback, method, args)));
			return null;
		}
		
		@Override
		public void handleMessage(Message msg) {
			if(msg.what != CallBackMessage) {
				return;
			}
			((InvokeInfo)msg.obj).invoke();
		}
	}
	
	static class InvokeInfo {
		private Object target;
		
		private Method method;
		
		private Object[] args;
		
		InvokeInfo(Object target, Method method, Object [] args) {
			this.target = target;
			this.method = method;
			this.args = args;
		}
		
		public Object invoke() throws RuntimeException {
			try {
				return method.invoke(target, args);
			} catch (Exception e) {
				throw new RuntimeException("invoke callback method happend error", e);
			}
		}
	}
}
