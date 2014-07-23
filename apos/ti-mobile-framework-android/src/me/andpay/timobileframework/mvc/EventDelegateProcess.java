package me.andpay.timobileframework.mvc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.FormProcesser;
import me.andpay.timobileframework.mvc.form.FormProcesser.FormProcessPattern;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.roboguice.ActivityVIewInjectorUtil;
import me.andpay.timobileframework.util.FastDoubleClickUtil;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Injector;

/*
 * 控件事件委托器
 */
public class EventDelegateProcess {

	@Inject
	private FormProcesser formProcesser;

	private String method_prefix = "set";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void delegate(Activity activity, View view, EventDelegate delegate,
			Injector injector) {
		DelegateType type = delegate.type();
		Class delegateListenClass = delegate.delegateClass();
		String delegateListen = "".equals(delegate.delegate()) ? method_prefix
				+ delegateListenClass.getSimpleName() : delegate.delegate();
		String method = delegate.toMethod();
		Class eventController = delegate.toEventController();
		boolean isNeedFormBean = delegate.isNeedFormBean();
		// 设置Invocation所需参数
		EventInvocation eventInvocation = new EventInvocation();
		eventInvocation.setActivity(activity);
		eventInvocation.setType(type);
		eventInvocation.setMethodName(method);
		eventInvocation.setNeedFormBean(isNeedFormBean);
		if(view == null) {
			throw new RuntimeException(
					"delegate happend error, view inject error the eventController is "
							+ eventController.getName());
		}
		eventInvocation.setViewId(view.getId()+Long.valueOf(System.currentTimeMillis()).intValue() );
		eventInvocation.setPassFastClick(delegate.isPassFastClick());
		// 注入实例化EventController并且将依赖关系注入
		if (type == DelegateType.eventController) {
			AbstractEventController controller = injector
					.getInstance(eventController);
			ActivityVIewInjectorUtil.injectorView(controller, activity);
			eventInvocation.setEventController(controller);
		}
		try {
			// 创建事件代理对象
			Object proxy = Proxy.newProxyInstance(
					delegateListenClass.getClassLoader(),
					new Class[] { delegateListenClass }, eventInvocation);
			view.getClass().getMethod(delegateListen, delegateListenClass)
					.invoke(view, proxy);
		} catch (Exception ex) {
			throw new RuntimeException(
					"delegate happend error, the delegateClass is "
							+ delegateListenClass.getName()
							+ ", the eventController is "
							+ eventController.getName(), ex);
		}

	}

	class EventInvocation implements InvocationHandler {

		private Activity activity;

		private EventController eventController;

		private String methodName;

		private DelegateType type;

		private boolean isNeedFormInfo;
		
		private int viewId;
		
		private boolean passFastClick;


		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			if(!passFastClick && method.getName().equals("onClick")&& FastDoubleClickUtil.isFastDoubleClick(viewId)) {
				Log.e(this.getClass().getName(), "click fast");
		
				return null;
			}
			
			if (type == DelegateType.method) {
				method = activity.getClass().getDeclaredMethod(methodName,
						method.getParameterTypes());
				return method.invoke(activity, args);
			} else {
				if (!eventController.preProcess(activity)) {
					Log.e(this.getClass().getName(), "click preProcess");
					return null;
				}
				FormBean formBean = null;
//				Log.e(this.getClass().getName(), eventController.getClass().getName());
				if (isNeedFormInfo) {
					formBean = formProcesser.loadFormBeanAndValidate(
							(ValueContainer) activity,
							FormProcessPattern.ANDROID);
				}
				Method proxyMethod = eventController.getClass().getMethod(
						method.getName(),
						generateMethodClassTypes(method.getParameterTypes()));
				return proxyMethod.invoke(eventController,
						getMethodParam(formBean, args));
			}
		}

		/**
		 * 获取对应方法签名的参数列表
		 * 
		 * @param args
		 * @return
		 */
		private Class[] generateMethodClassTypes(Class[] args) {
			List<Class> paramClasses = new ArrayList<Class>();
			paramClasses.add(Activity.class);
			paramClasses.add(FormBean.class);
			if (args != null) {
				for (Class arg : args) {
					paramClasses.add(arg);
				}
			}
			return paramClasses.toArray(new Class[paramClasses.size()]);
		}

		/**
		 * 获取对应的方法签名
		 * 
		 * @param activity
		 * @param formBean
		 * @param args
		 * @return
		 */
		private Object[] getMethodParam(FormBean formBean, Object[] args) {
			int argNum = args == null ? 0 : args.length;
			Object[] params = new Object[argNum + 2];
			params[0] = activity;
			params[1] = formBean;
			if (args == null) {
				return params;
			}
			for (int i = 0; i < args.length; i++) {
				params[2 + i] = args[i];
			}
			return params;
		}

		public void setPassFastClick(boolean passFastClick) {
			this.passFastClick = passFastClick;
		}

		public void setNeedFormBean(boolean isNeedFormBean) {
			this.isNeedFormInfo = isNeedFormBean;
		}

		public void setActivity(Activity activity) {
			this.activity = activity;
		}

		public void setEventController(EventController eventController) {
			this.eventController = eventController;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		public void setType(DelegateType type) {
			this.type = type;
		}

		public void setViewId(int viewId) {
			this.viewId = viewId;
		}
		
		
	}

}
