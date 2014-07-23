package me.andpay.apos.tam.action.txn;

import java.util.Date;

import me.andpay.ac.consts.ProductCodes;
import me.andpay.ac.term.api.txn.InquiryBalanceRequest;
import me.andpay.ac.term.api.txn.InquiryBalanceResponse;
import me.andpay.ac.term.api.txn.TxnResponse;
import me.andpay.apos.R;
import me.andpay.apos.common.constant.ResponseCodes;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.tam.action.txn.model.PinData;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.ti.util.SleepUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import android.util.Log;

/**
 * 余额查询
 * @author cpz
 */
public class CardBalanceProcessor extends GenTxnProcessor {

	@Override
	public void reEntryTxn(TxnForm txnForm,TxnCallback callBack) {
		if (txnForm.getProcessStatus() == TxnForm.PRSTATUS_RESPONSE) {
			return;
		}
		
		SleepUtil.sleep(RE_ENTRY_SLEEPTIME);
		try {
			InquiryBalanceResponse inResponse = txnService
					.syncInquiryBalance((InquiryBalanceRequest)txnForm.getReEntryTxnRequest());
			dealResponse(inResponse, txnForm, callBack, null);
		} catch (Exception ex) {
			dealException(ex, txnForm, callBack);
		}

	}

	
	
	
	public void processTxn(ActionRequest request) {

		TxnForm txnForm = (TxnForm) request.getParameterValue("txnForm");
		txnForm.setTimeoutTime(System.currentTimeMillis());
		TxnCallback callBack = (TxnCallback) request.getHandler();
		
		try {
			super.processTxn(request);
			InquiryBalanceRequest queryRequest = createRequest(txnForm);
			txnForm.setReEntryTxnRequest(queryRequest);
			
			sendTxn(txnForm, queryRequest,callBack);
		} catch (Exception ex) {
			sysError(txnForm, callBack, ex);
		}

	}

	public void sendTxn(TxnForm txnForm,InquiryBalanceRequest queryRequest, TxnCallback callBack) {
		try {
			InquiryBalanceResponse inquiryBalanceResponse = txnService
					.syncInquiryBalance(queryRequest);
			dealResponse(inquiryBalanceResponse, txnForm, callBack, null);
		} catch (Exception ex) {
			dealException(ex, txnForm, callBack);
		}
	}

	public void dealResponse(TxnResponse txnResponse, TxnForm txnForm,
			TxnCallback callBack, String errorMsg) {
		if (txnForm.getProcessStatus() == TxnForm.PRSTATUS_RESPONSE) {
			return;
		} else {
			txnForm.setProcessStatus(TxnForm.PRSTATUS_RESPONSE);
		}

		if (StringUtil.isNotBlank(errorMsg)) {
			txnForm.getResponse().setResponMsg(errorMsg);
			callBack.showFaild(txnForm.getResponse());
			return;
		}

		InquiryBalanceResponse inquiryBalanceResponse = null;
		if (txnResponse == null) {
			inquiryBalanceResponse = new InquiryBalanceResponse();
		}
		inquiryBalanceResponse = (InquiryBalanceResponse) txnResponse;

		
		setParseBinInfo(inquiryBalanceResponse, txnForm);

		String msg = getStringResource(R.string.tam_syserror_str);
		if (inquiryBalanceResponse.getRespCode() == null) {
			inquiryBalanceResponse.setRespMessage(msg);
			inquiryBalanceResponse.setRespCode(ResponseCodes.SYS_ERROR);
		}

		if (StringUtil.isBlank(inquiryBalanceResponse.getRespMessage())) {
			inquiryBalanceResponse.setRespMessage(msg);
		}
		

		responseProcess(inquiryBalanceResponse, callBack,txnForm);
	}

	private void responseProcess(InquiryBalanceResponse queryResponse,
			TxnCallback callBack, TxnForm txnForm) {

		
		TxnActionResponse actionResponse = txnForm.getResponse();
		
		actionResponse.setTxnResponse(queryResponse);
		setParseBinInfo(queryResponse, txnForm);

		actionResponse.setIcCardTxn(txnForm.isIcCardTxn());
		actionResponse.setAposICCardDataInfo(covertAposICCardDataInfo(queryResponse,txnForm));
		
		if (queryResponse.getRespCode().equals(ResponseCodes.SUCCESS)) {
			txnForm.setSalesAmt(queryResponse.getBalance());
			txnForm.getResponse().setResponMsg(queryResponse.getRespMessage());
			txnForm.getResponse().setBalanceAmt(queryResponse.getBalance());
			callBack.txnSuccess(txnForm.getResponse());
		} else if (queryResponse.getRespCode().equals(ResponseCodes.PIN_ERROR)) {
			if (txnForm.getPinErrorCount() == 2) {
				actionResponse.setPinErrorCount(0);
				String msg = getStringResource(R.string.tam_pinerror_three_str);
				txnForm.getResponse().setResponMsg(msg);
				callBack.showFaild(txnForm.getResponse());
			} else {
				int pinCount = txnForm.getPinErrorCount();
				actionResponse.setPinErrorCount(pinCount + 1);
				String msg = getStringResource(R.string.tam_pin_error1_str)
						+ (2 - pinCount)
						+ getStringResource(R.string.tam_pin_error2_str);
				txnForm.getResponse().setResponMsg(msg);
				callBack.showInputPassword(txnForm.getResponse());
			}
		} else {
			txnForm.getResponse().setResponMsg(queryResponse.getRespMessage());
			callBack.showFaild(txnForm.getResponse());
		}

	}

	private InquiryBalanceRequest createRequest(TxnForm txnForm) {
		
		InquiryBalanceRequest queryRequest = new InquiryBalanceRequest();
		queryRequest.setKsn(txnForm.getCardInfo().getKsn());
		queryRequest.setTermTxnTime(new Date());
		queryRequest.setTermTraceNo(TxnUtil.getTermTraceNo(tiConfig));
		TxnUtil.updateTermTraceNo(tiConfig);
		
		txnForm.setTermTraceNo(queryRequest.getTermTraceNo());
		txnForm.setTermTxnTime(StringUtil.format("yyyyMMddHHmmss", queryRequest.getTermTxnTime()));
		
		queryRequest.setTrackAll(txnForm.getCardInfo().getEncTracks());
		queryRequest.setKsn(txnForm.getCardInfo().getKsn());
		queryRequest.setTrackRandomFactor(txnForm.getCardInfo()
				.getRandomNumber());
		queryRequest.setProductCode(ProductCodes.APOS_ACQUIRE);
		queryRequest.setCardNo(txnForm.getParseBinResp().getCardNo());

		PinData pinData = genPinData(txnForm);
		queryRequest.setPinblock(pinData.getPinblock());
		queryRequest.setPinEncryptAdditionData(pinData
				.getPinEncryptAdditionData());
		
		TxnHelper.setICCardInfo(txnForm.getAposICCardDataInfo(), queryRequest);
		if(txnForm.isIcCardTxn()) {
			queryRequest.setCardNo(txnForm.getAposICCardDataInfo().getCardNo());
		}
		setMac(txnForm.getMac(),queryRequest);

		queryRequest.setPinEncryptMethod(pinData.getPinEncryptMethods());
		return queryRequest;

	}


	@Override
	public void txnTimeout(TxnForm txnForm, TxnCallback txnCallback) {

		Log.i(this.getClass().getName(), "txn timeout");

		InquiryBalanceResponse inResponse = new InquiryBalanceResponse();
		inResponse.setRespCode(ResponseCodes.TXN_TIMEOUT);
		inResponse.setRespMessage("网络不给力，交易超时了。");
		dealResponse(inResponse, txnForm, txnCallback, null);
		return;

	}
	
	
	

	public void retrieveTxn(ActionRequest request) {
		//igore
	}

}
