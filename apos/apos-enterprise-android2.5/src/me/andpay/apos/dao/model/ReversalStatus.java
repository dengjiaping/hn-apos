package me.andpay.apos.dao.model;

/**
 * 冲正状态
 * 
 * @author cpz
 *
 */
public class ReversalStatus {

	/**
	 * 冲正中
	 */
	public final static String STATUS_REVERSALING = "0";
	/**
	 * 冲正成功
	 */
	public final static String STATUS_REVERSAL_SUCCESS = "1";
	/**
	 * 冲正失败
	 */
	public final static String STATUS_REVERSAL_FAILD = "2";
}
