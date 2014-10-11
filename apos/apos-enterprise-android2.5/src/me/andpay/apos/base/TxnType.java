package me.andpay.apos.base;
/**
 * 消费类型
 * @author Administrator
 *
 */
public class TxnType {
	/**
	 * 充值消费
	 */
	public static final String MPOS_TOPUP = "mposTopup";
	/**
	 * 缴电费
	 */
	public static final String MPOS_PAYCOST_ELE="mposPaycostEle";
	/**
	 * 缴水费
	 */
	public static final String MPOS_PAYCOST_WATER="mposPaycostWater";
	/**
	 * 信用卡还款
	 */
	public static final String MPOS_PAY_CREDIT_CARD="mposPayCreditcard";
	/**
	 * 转账
	 */
	public static final String MPOS_TRANSFER_ACCOUNT="mposTransferAccount";

}
