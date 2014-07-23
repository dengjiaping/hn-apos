package me.andpay.apos.lam.callback;


/**
 * 无更新或者取消更新后续处理流程
 * @author tinyliu
 *
 */
public interface AfterUpdateCallback {
	
	public void processAfterCancelUpdateOrNoUpdate();
	
}
