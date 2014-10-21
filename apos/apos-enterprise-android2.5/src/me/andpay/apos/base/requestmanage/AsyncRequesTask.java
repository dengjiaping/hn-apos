package me.andpay.apos.base.requestmanage;

import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.operation.VasOptService;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnRequest;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.ac.term.api.vas.txn.VasTxnService;
import android.os.AsyncTask;

public class AsyncRequesTask extends AsyncTask<Void, Void, Object> {
	protected VasTxnService txnService;

	protected VasOptService optService;

	private CommonTermTxnRequest txnRequest;

	private CommonTermOptRequest txnRequest1;

	public CommonTermOptRequest getTxnRequest1() {
		return txnRequest1;
	}

	public void setTxnRequest1(CommonTermOptRequest txnRequest1) {
		this.txnRequest1 = txnRequest1;
	}

	public CommonTermTxnRequest getTxnRequest() {
		return txnRequest;
	}

	public void setTxnRequest(CommonTermTxnRequest txnRequest) {
		this.txnRequest = txnRequest;
	}

	private RequestManager manager;

	public RequestManager getManager() {
		return manager;
	}

	public void setManager(RequestManager manager) {
		this.manager = manager;
	}

	@Override
	protected Object doInBackground(Void... params) {
		// TODO Auto-generated method stub
		if (txnRequest != null) {
			return txnService.processCommonTxn(txnRequest);
		} else {
			return optService.processCommonOpt(txnRequest1);
		}
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		manager.callBack(result);
	}

}
