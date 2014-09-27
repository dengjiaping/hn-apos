package me.andpay.apos.tam;

public class TamProvider {

	/**
	 * 消费首页
	 */
	public static final String TAM_PURCHASE_FIRST_ACTIVITY = "tam.purchase.first.activity";
	/**
	 * 消费第二页
	 */
	public static final String TAM_TXN_ACTIVITY = "tam.txn.activity";

	/**
	 * 签名
	 */
	public static final String TAM_SIGN_ACTIVITY = "tam.sign.activity";

	/**
	 * 发送凭证
	 */
	public static final String TAM_POSTVOUCHER_ACTIVITY = "tam.postVoucher.activity";

	/**
	 * 交易明细
	 */
	public static final String TAM_TXNDETAIL_ACTIVITY = "tam.txnDetail.activity";
	/**
	 * 余额查询明细
	 */
	public static final String TAM_BALANCE_DETAIL_ACTIVITY = "tam.balanceDetail.activity";
	/**
	 * 优惠劵明细
	 */
	public static final String TAM_COUPON_DETAIL_ACTIVITY = "tam.activity.CouponDeailActivity";

	public static final String TAM_INPUT_VALUE_CARD_TXNAMT_ACTIVITY = "tam.activity.InputValueCardTxnAmtActivity";

	/**
	 * 相机请求代码
	 */
	public static final int CAMERA_REQUEST_CODE = 1;

	/**
	 * 交易页面返回代码
	 */
	public static final int TXN_REQUEST_CODE = 2;

	/**
	 * 返回页面
	 */
	public static final int TXN_RESULT_BACK = 0;

	/**
	 * 返回销毁本身
	 */
	public static final int TXN_RESULT_CLOSE = 1;

	/**
	 * 云订单Domain
	 */
	public static final String TAM_DOMAIN_CLOUD = "/tam/cloudOrder.action";
	/**
	 * 取消云订单
	 */
	public static final String TAM_ACTION_CLOUD_CANCEL = "cancelOrder";
	/**
	 * Action参数：云订单编号
	 */
	public static final String TAM_ACTION_PARAM_CLOUDORDERID = "cloudOrderId";
	/**
	 * Action参数：交易撤销标示
	 */
	public static final String TAM_ACTION_PARAM_TXNCANCELFLAG = "txnCancelFlag";
}
