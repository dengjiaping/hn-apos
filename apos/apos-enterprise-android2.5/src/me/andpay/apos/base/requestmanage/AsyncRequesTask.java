package me.andpay.apos.base.requestmanage;

import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.operation.VasOptService;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnRequest;
import me.andpay.ac.term.api.vas.txn.VasTxnService;
import me.andpay.ti.lnk.transport.websock.common.NetworkErrorException;
import android.os.AsyncTask;

public class AsyncRequesTask extends AsyncTask<Void, Void, Object> {
	protected VasTxnService txnService;

	protected VasOptService optService;
	
	
	

	public VasTxnService getTxnService() {
		return txnService;
	}

	public void setTxnService(VasTxnService txnService) {
		this.txnService = txnService;
	}

	public VasOptService getOptService() {
		return optService;
	}

	public void setOptService(VasOptService optService) {
		this.optService = optService;
	}

	private CommonTermTxnRequest txnRequest;

	private CommonTermOptRequest optRequest;

	public CommonTermTxnRequest getTxnRequest() {
		return txnRequest;
	}

	public void setTxnRequest(CommonTermTxnRequest txnRequest) {
		this.txnRequest = txnRequest;
	}

	public CommonTermOptRequest getOptRequest() {
		return optRequest;
	}

	public void setOptRequest(CommonTermOptRequest optRequest) {
		this.optRequest = optRequest;
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
		try{
			if (txnRequest != null) {
				return txnService.processCommonTxn(txnRequest);
			} else {
				return optService.processCommonOpt(optRequest);
			}
		}catch(NetworkErrorException e){
			return null;
		}
		
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		manager.callBack(result);
	}

}
