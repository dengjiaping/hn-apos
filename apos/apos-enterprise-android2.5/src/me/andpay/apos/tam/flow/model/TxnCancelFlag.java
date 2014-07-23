package me.andpay.apos.tam.flow.model;

public class TxnCancelFlag {

	private boolean isCancel = false;
	/**
	 * 交易开始
	 */
	public void startTxn() {
		synchronized (this) {
			isCancel = false;
		}
	}
	/**
	 * 交易结束
	 */
	public void cancelTxn() {
		synchronized (this) {
			isCancel = true;
		}
	}
	/**
	 * 交易是否结束
	 * @return
	 */
	public boolean isCancelTxn() {
		synchronized (this) {
			return isCancel;
		}
	}
}
