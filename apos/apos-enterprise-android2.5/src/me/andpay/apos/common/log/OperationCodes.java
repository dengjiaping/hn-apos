package me.andpay.apos.common.log;

/**
 * 操作吗
 * @author cpz
 *
 */
public class OperationCodes {
	
	/**
	 * 读卡器检测
	 */
	public static final String OP_PREFIX_CARDREADER_SET = "A1_";

	
	/** 
	 * 设备检测成功
	 */
	public static final String OPCODE_CHECK_SUCCESS = OperationCodes.OP_PREFIX_CARDREADER_SET+"A001";
	/**
	 * 设备检测失败
	 */
	public static final String OPCODE_CHECH_FAILD =  OperationCodes.OP_PREFIX_CARDREADER_SET+"A002";

}
