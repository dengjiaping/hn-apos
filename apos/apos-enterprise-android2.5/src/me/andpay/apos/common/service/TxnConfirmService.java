package me.andpay.apos.common.service;

import java.util.Date;
import java.util.List;

import me.andpay.ac.term.api.txn.TxnService;
import me.andpay.apos.dao.TxnConfirmDao;
import me.andpay.apos.dao.model.QueryTxnConfirmCond;
import me.andpay.apos.dao.model.TxnConfirm;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.SleepUtil;
import me.andpay.timobileframework.util.NetWorkUtil;
import android.app.Application;

import com.google.inject.Inject;


public class TxnConfirmService {
	
	@Inject
	private TxnConfirmDao txnConfirmDao;
	
	private TxnService txnService;
	@Inject
	private Application application;
	
	private Thread thread = null;
	
	
	
	public void addCpmfirmTxn(String txnId) {
		
		
		TxnConfirm txnConfirm = new TxnConfirm();
		txnConfirm.setCreateTime(DateUtil.format("yyyyMMddHHmmss",
				new Date()));
		txnConfirm.setTxnId(txnId);
		txnConfirm.setRetryCount(0);
		txnConfirmDao.insert(txnConfirm);
	}


	public void sendConfirmTxn() {
		
		if (!NetWorkUtil.isNetworkConnected(application.getApplicationContext())) {
			return;
		}
		
		if (thread != null) {
			thread.interrupt();
		}
		thread = new Thread(new SendRunner());
		thread.start();
	}
	
	
	public class SendRunner implements Runnable {
		public void run() {
			try{
				List<TxnConfirm> tnxList =  txnConfirmDao.query(new QueryTxnConfirmCond(), 0, -1);
				txnConfirm(tnxList);
			}catch(Throwable e) {
				
			}
		}

		private void txnConfirm(List<TxnConfirm> txnList)  {
			for (TxnConfirm txnConfirm : txnList) {
				try {
					txnService.acknowledgeTxnResponse(txnConfirm.getTxnId());
					txnConfirmDao.delete(txnConfirm.getId());
				}catch(Exception e) {
					txnConfirm.setUpdateTime(DateUtil.format("yyyyMMddHHmmss", new Date()));
					txnConfirm.setRetryCount(txnConfirm.getRetryCount()+1);
					txnConfirmDao.update(txnConfirm);
				}
			}
			List<TxnConfirm> txnListTemp =  txnConfirmDao.query(new QueryTxnConfirmCond(), 0, -1);
			if(txnListTemp.size()>0) {
				SleepUtil.sleep(20000);
				txnConfirm(txnListTemp);
			}
		}
		
	}
}
