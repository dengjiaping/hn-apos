package me.andpay.apos.merchantservice.data;

import java.util.Map;

import me.andpay.apos.base.BaseData;

/*结算明细*/
public class SelementOrder implements BaseData {
	private String termNo="";//终端号
	private String txnCnt="";//交易笔数
	private String txnAmt="";//交易金额
	private String mchtFee="";//商户费用
	private String outAmt="";//结算金额
	private String beginDate="";//清算日期开始
	private String endDate="";//清算日期结束
	
	
	public void parse(Map map) {
		// TODO Auto-generated method stub
		
	}
	public Map page() {
		// TODO Auto-generated method stub
		return null;
	}


}
