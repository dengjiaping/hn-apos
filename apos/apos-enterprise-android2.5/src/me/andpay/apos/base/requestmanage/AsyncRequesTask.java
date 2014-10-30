package me.andpay.apos.base.requestmanage;

import java.util.ArrayList;

import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.operation.VasOptService;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnRequest;
import me.andpay.ac.term.api.vas.txn.VasTxnService;
import android.os.AsyncTask;

public class AsyncRequesTask extends AsyncTask<Void, Void, Object> {
	protected VasTxnService txnService;

	protected VasOptService optService;

	private ArrayList<FinishRequestInterface> list;

	public void addFinishRequestInterface(FinishRequestInterface fi) {
		if (list == null) {
			list = new ArrayList<FinishRequestInterface>();
		}
		for (int i = 0; i < list.size(); i++) {
			FinishRequestInterface fint = list.get(i);
			if (fint == fi) {
				return;
			}
		}
		list.add(fi);

	}

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

	@Override
	protected Object doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			if (txnRequest != null) {
				return txnService.processCommonTxn(txnRequest);
			} else {
				return optService.processCommonOpt(optRequest);
			}
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				FinishRequestInterface fi = list.get(i);
				fi.callBack(result);
			}
		}
	}

}
