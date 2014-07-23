package me.andpay.apos.tam.action;

import me.andpay.ac.term.api.txn.TxnService;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.tam.action.txn.TxnProcessorFactory;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;

import com.google.inject.Inject;

@ActionMapping(domain = "/tam/txn.action")
public class TxnAction extends SessionKeepAction {

	public final static String DOMAIN_NAME = "/tam/txn.action";
	public final static String TXN_ACTION = "txnProcess";
	public final static String QUERY_RESPONSE = "queryResponse";
	public final static String RECOVER_TXN = "recoverTxn";
	public final static String RETRIEVE_TXN = "retrieveTxn";

	@Inject
	private TxnProcessorFactory txnProcessorFactory;
	@Inject
	private TxnControl txnControl;

	protected TxnService txnService;

	@Inject
	protected PayTxnInfoDao payTxnInfoDao;

	public void txnProcess(ActionRequest request) {
		
		TxnForm txnForm = (TxnForm) request.getParameterValue("txnForm");

		txnProcessorFactory.getProcessor(
				txnForm.getTxnType()).processTxn(request);
	}
	
	/**
	 * 重新获取交易
	 */
	public void retrieveTxn(ActionRequest request) {
		
		TxnForm txnForm = (TxnForm) request.getParameterValue("txnForm");
		txnProcessorFactory.getProcessor(
				txnForm.getTxnType()).retrieveTxn(request);
	}


}
