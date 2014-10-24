package me.andpay.apos.base.requestmanage;

import java.util.ArrayList;

import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.operation.VasOptService;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnRequest;
import me.andpay.ac.term.api.vas.txn.VasTxnService;

import com.google.inject.Inject;

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

	/* 请求数据 */
	private CommonTermTxnRequest txnRequest;

	private CommonTermOptRequest optRequest;

	protected VasTxnService txnService;

	protected VasOptService optService;

	public CommonTermTxnRequest getTxnRequest() {
		return txnRequest;
	}

	public void setTxnRequest(CommonTermTxnRequest txnRequest) {
		this.txnRequest = txnRequest;
		this.optRequest = null;
	}

	public CommonTermOptRequest getOptRequest() {
		return optRequest;
	}

	public void setOptRequest(CommonTermOptRequest optRequest) {
		this.optRequest = optRequest;
		this.txnRequest = null;
	}

	/* 相应接口 */
	private ArrayList<FinishRequestInterface> list;

	public void addFinishRequestResponse(FinishRequestInterface response) {
		if (list == null) {
			list = new ArrayList<FinishRequestInterface>();
		}
		for(int i=0;i<list.size();i++){
			FinishRequestInterface fi = list.get(i);
			if(fi==response){
				return;
			}
		}
		list.add(response);
	}

	/**
	 * 开启服务调用
	 */
	public void startService() {
		AsyncRequesTask serviceAsyTask = new AsyncRequesTask();
		if (txnRequest != null) {
			serviceAsyTask.setTxnRequest(txnRequest);
			serviceAsyTask.setTxnService(txnService);
		} else {
			serviceAsyTask.setOptRequest(optRequest);
			serviceAsyTask.setOptService(optService);
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
		for (int i = 0; i < list.size(); i++) {
			FinishRequestInterface fi = list.get(i);
			fi.callBack(response);
		}

	}

}
