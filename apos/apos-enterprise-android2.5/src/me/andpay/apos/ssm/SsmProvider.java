package me.andpay.apos.ssm;

public class SsmProvider {

	public static String SSM_ACTIVITY_MAIN = "ssm.main.activity";

	public static String SSM_ACTIVITY_INFO_LIST = "ssm.info.activity";

	public static String SSM_ACTIVITY_SEND_MAIL = "ssm.send.activity";

	public static String SSM_DOMAIN_QUERY = "/ssm/query.action";

	public static String SSM_DOMAIN_MAIL = "/ssm/mail.action";

	public static String SSM_DOMAIN_SETTLE = "/ssm/settle.action";

	public static String SSM_ACTION_SETTLE_SETTLE = "settle";

	public static String SSM_ACTION_MAIN_LOADUNSETTLEINFO = "loadUnSettleInfo";

	public static String SSM_ACTION_MAIN_SENDUNSETTLE2EMAIL = "sendUnSettleInfo2Mail";

	public static String SSM_ACTION_MAIN_SENDSETTLED2MAIL = "sendUnSettleInfo2Mail";

	public static String SSM_ACTION_MAIN_QUERYSETTDETAIL = "querySettleDetail";

	public static String SSM_ACTION_MAIN_QUERYSETTLEINFO = "querySettleInfo";

	/**
	 * 分页每页最大显示条数
	 */
	public static Integer SSM_MAX_COUNTS_CONST = 10;
}
