package me.andpay.apos.tam.action.txn;

import java.util.ArrayList;
import java.util.Map;

import com.google.inject.Inject;

import me.andpay.ac.consts.AttachmentTypes;
import me.andpay.ac.consts.VasTxnTypes;
import me.andpay.ac.term.api.txn.TxnResponse;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnRequest;
import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;
import me.andpay.ac.term.api.vas.txn.VasTxnService;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.CtResponseAdapterTxnActionResponse;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.timobileframework.mvc.action.ActionRequest;

/**
 * 信用卡还款操作
 * @author Administrator
 *
 */
public class CreditPaymentProcessor extends GenTxnProcessor{
	protected VasTxnService txnService;
	@Inject
	TxnControl txnControl;
	@Override
	public void processTxn(ActionRequest request){
		// TODO Auto-generated method stub
		TxnForm txnForm = (TxnForm) request.getParameterValue("txnForm");
		txnForm.setTimeoutTime(System.currentTimeMillis());

		/* 创建请求 */

		super.processTxn(request);
		CommonTermTxnRequest txnRequest = createTermTxnRequest(txnForm,
				VasTxnTypes.CREDIT_CARD_CONFIRM, creatContentObject(txnForm));
		ArrayList<String> attachList = new ArrayList<String>();
		attachList.add(AttachmentTypes.SIGNATURE_PICTURE);
		attachList.add(AttachmentTypes.TXN_RECEIPT_PICTURE);
		txnRequest.setAttachmentTypes(attachList);

		/* 请求应答 */

		CommonTermTxnResponse txnResponse = txnService
				.processCommonTxn(txnRequest);
		/* 应答处理 */
		CtResponseAdapterTxnActionResponse response = new CtResponseAdapterTxnActionResponse(
				txnResponse);
		TxnCallback callBack = txnControl.getTxnCallback();
		if (txnResponse != null) {
			callBack.txnSuccess(response);
		} else {
			callBack.showFaild(response);
		}
	}

	@Override
	protected Map<String,String> creatContentObject(TxnForm txnForm) {
		// TODO Auto-generated method stub
		return txnForm.getMap();
	}
	
	
	
	public void retrieveTxn(ActionRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dealResponse(TxnResponse purResponse, TxnForm txnForm,
			TxnCallback callBack, String errorMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reEntryTxn(TxnForm txnForm, TxnCallback callBack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void txnTimeout(TxnForm txnForm, TxnCallback txnCallback) {
		// TODO Auto-generated method stub
		
	}

}
