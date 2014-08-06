package me.andpay.apos.tam.context.handler;

import me.andpay.apos.tam.context.ResetActivityStatus;
import me.andpay.apos.tam.context.TxnControl;

public class GenChangeStatusHander {

	public void processHandle(TxnControl txnControl) {
		
		if( txnControl.getCurrActivity()!=null){
			ResetActivityStatus resetActivityStatus = (ResetActivityStatus) txnControl
					.getCurrActivity();
			resetActivityStatus.resetActivity();	
		}else {
			return;
		}
		
		if(preAction(txnControl) ) {
			return;
		}
		changeUI(txnControl);
		changeAction(txnControl);
	}

	/**
	 * 处理界面改变
	 * 
	 * @param txnControl
	 */
	protected void changeUI(TxnControl txnControl) {

	}

	/**
	 * 
	 * 服务提交和资源变更
	 */
	protected void changeAction(TxnControl txnControl) {

	}
	
	/**
	 * 前置处理器
	 */
	protected boolean preAction(TxnControl txnControl) {
		return false;
	}
	



}
