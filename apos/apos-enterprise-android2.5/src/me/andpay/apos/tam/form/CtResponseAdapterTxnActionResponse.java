package me.andpay.apos.tam.form;

import me.andpay.ac.term.api.vas.txn.CommonTermTxnResponse;

/**
 * 适配器response
 * 
 * @author Administrator
 *
 */
public class CtResponseAdapterTxnActionResponse extends TxnActionResponse {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 被适配的对象
	 */
	private CommonTermTxnResponse commonTermResponse=null;
	public CommonTermTxnResponse getCommonTermResponse() {
		return commonTermResponse;
	}
	public void setCommonTermResponse(CommonTermTxnResponse commonTermResponse) {
		this.commonTermResponse = commonTermResponse;
	}
	public CtResponseAdapterTxnActionResponse(CommonTermTxnResponse commonTermResponse){
		setCommonTermResponse(commonTermResponse);
	}
	
	

}
