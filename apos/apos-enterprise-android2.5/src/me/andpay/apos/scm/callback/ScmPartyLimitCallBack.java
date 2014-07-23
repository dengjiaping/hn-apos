package me.andpay.apos.scm.callback;

import me.andpay.ac.term.api.rcs.PartyTxnAmtQuotaInfo;

public interface ScmPartyLimitCallBack {
	
	public void querySuccess(PartyTxnAmtQuotaInfo  partyLimit);
	
	public void queryError(String errorMsg);
}
