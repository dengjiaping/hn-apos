package me.andpay.apos.lam.callback;

import me.andpay.ac.term.api.sec.GenMsrKeyResponse;

public interface GenMsrKeysCallback {
		
	public void genMrsKeysSuccess(GenMsrKeyResponse genMsrKeyResponse);
	
	public void genMsrKesFaild();
	
	public void genMsrKeysNetworkerror();
}
