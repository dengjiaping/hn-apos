package me.andpay.apos.base.requestmanage;

import java.util.ArrayList;

import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnRequest;

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
	@Inject
	AsyncRequesTask serviceAsyTask;

	/* 请求数据 */
	private CommonTermTxnRequest txnRequest;

	private CommonTermOptRequest optRequest;

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
		list.add(response);
	}

	/**
	 * 开启服务调用
	 */
	public void startService() {
		if (txnRequest != null) {
			serviceAsyTask.setTxnRequest(txnRequest);
		} else {
			serviceAsyTask.setOptRequest(optRequest);
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
