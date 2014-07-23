package me.andpay.apos.common.constant;

public class ResponseCodes {
	
	/**
	 * 密码错误
	 */
	public final static String PIN_ERROR = "TXN.013";
	/**
	 * 交易成功
	 */
	public final static String  SUCCESS = "TXN.000";
	
	/**
	 * 系统异常
	 */
	public final static String  SYS_ERROR = "TXN.002";
	
	/**
	 * 找不到原交易
	 */
	public static final String NOT_FOUND_ORIG_TXN = "TXN.010";
	
	/**
	 * 无效的原交易
	 */
	public static final String INVALID_ORIG_TXN = "TXN.011";
	
	/**
	 * 无效的原交易金额
	 */
	public static final String INVALID_ORIG_TXN_AMT = "TXN.012";
	
	/**
	 * 必须强制更新软件
	 */
	public static final String MUST_UPDATE_SOFEWARE_VERSION = "TERM.017";
	
	/**
	 * 交易超时
	 */
	public static final String TXN_TIMEOUT = "LTXN.068";

	
	
	
	
	
}
