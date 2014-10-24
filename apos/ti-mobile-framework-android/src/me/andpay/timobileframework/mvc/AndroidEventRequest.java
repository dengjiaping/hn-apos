package me.andpay.timobileframework.mvc;

import java.util.HashMap;
import java.util.Map;

import me.andpay.timobileframework.mvc.action.DispatchCenter;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.context.TiContextProvider;
import me.andpay.timobileframework.mvc.context.TiDataContext;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.ReflectUtil;
import android.os.AsyncTask;
import android.os.Build;

/**
 * <p>
 * UI事件请求
 * </p>
 * 
 * <p>
 * 用于处理前段事件请求，组装Event和Form表单数据，转发到Dispach。
 * </p>
 * <p>
 * ProcessFilter不能为空，必须要实现ProcessFilter用于对返回结果进行处理
 * </p>
 * 
 * @author tinyliu
 * 
 */
public class AndroidEventRequest implements EventRequest {

	private String domain = null;

	private String action = null;

	private DispatchCenter dispatcher = null;

	private TiContext context = null;

	private Pattern pattern = null;

	private Map submitData = new HashMap();

	private Object callback = null;
	
	private AfterProcessCallBackHandler afterCallBack = null;

	private TiContextProvider provider = null;

	public AndroidEventRequest(DispatchCenter dispatcher,
			TiContextProvider provider) {
		context = new TiDataContext(null);
		this.dispatcher = dispatcher;
		submitData = new HashMap();
		this.provider = provider;
	}

	public EventRequest open(String domain, String action, Pattern pattern) {
		this.domain = domain;
		this.action = action;
		if (pattern == null) {
			pattern = Pattern.async;
		} else {
			this.pattern = pattern;
		}
		return this;
	}

	public EventRequest open(FormBean formBean, Pattern pattern) {
		this.submitData.put(formBean.getFormName(), formBean.getData());
		return this.open(formBean.getDomain(), formBean.getAction(), pattern);
	}

	public EventRequest callBack(Object callback){
		if (callback.getClass().isAnnotationPresent(CallBackHandler.class)){
			this.callback = CallBackProxyFactory.generateProxy(callback);
			return this;
		}
		if (ReflectUtil.isImplInterface(callback.getClass(),
				AfterProcessCallBackHandler.class.getName())) {
			this.afterCallBack = (AfterProcessCallBackHandler) callback;
			return this;
		}
		throw new RuntimeException(
				"CallBack must be Add CallBackHandler annotation or Implments AfterCallBackHandler");
	}

	public ModelAndView submit() throws RuntimeException {
		return submit(null);
	}

	/**
	 * 提交Action请求
	 * 
	 * @param submitData
	 * @return
	 * @throws RuntimeException
	 */
	public ModelAndView submit(Map submitData) throws RuntimeException {
		if (submitData != null) {
			this.submitData.putAll(submitData);
		}
		if (this.pattern == Pattern.async){
			AsyncActionTask task = new AsyncActionTask(dispatcher,
					this.afterCallBack, this.provider);
			if(Build.VERSION.SDK_INT >10) {
				task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,this);
			}else {
				task.execute(this);
			}
			return null;
		} else {
			return dispatcher.dipatchEvent(this, provider);
		}
	}

	public String getDomain() {
		return domain;
	}

	public String getAction() {
		return action;
	}

	public Map getSubmitData() {
		return submitData;
	}

	public Object getCallback() {
		return callback;
	}

	public TiContext getRequestDataContext() {
		return this.context;
	}

}
