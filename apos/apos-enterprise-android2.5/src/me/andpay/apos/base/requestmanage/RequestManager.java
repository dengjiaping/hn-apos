package me.andpay.apos.base.requestmanage;

import java.util.ArrayList;

import com.google.inject.Inject;

import roboguice.inject.InjectView;
import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnRequest;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.ac.term.api.vas.txn.VasTxnService;

/**
 * 请求管理
 * 
 * @author Administrator
 *
 */
public class RequestManager {
	
	
	/**
	 * 异步服务
	 */
	@Inject AsyncRequesTask serviceAsyTask;

	/* 请求数据 */
	private CommonTermTxnRequest request;
	
	private CommonTermOptRequest request1;
	public CommonTermOptRequest getRequest1() {
		return request1;
	}

	public void setRequest1(CommonTermOptRequest request1) {
		this.request1 = request1;
	}
	/* 相应接口 */
	private ArrayList<FinishRequestInterface> list;

	public void addFinishRequestResponse(FinishRequestInterface response) {
		if (list == null) {
			list = new ArrayList<FinishRequestInterface>();
		}
		list.add(response);
	}

	public CommonTermTxnRequest getRequest() {
		return request;
	}

	public void setRequest(CommonTermTxnRequest request) {
		this.request = request;
	}

	
	/**
	 * 开启服务调用
	 */
	public void startService(){
		if(request!=null){
			serviceAsyTask.setTxnRequest(request);
		}else{
			serviceAsyTask.setTxnRequest1(request1);
		}
		
		serviceAsyTask.setManager(this);
		serviceAsyTask.execute();
	}
	/**
	 * 异步执行后的响应
	 * 
	 * @param response
	 */
	public void callBack(Object response) {
		for(int i=0;i<list.size();i++){
			FinishRequestInterface fi = list.get(i);
			fi.callBack(response);
		}

	}

}
