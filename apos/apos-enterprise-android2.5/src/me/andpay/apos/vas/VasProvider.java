package me.andpay.apos.vas;

public class VasProvider {

	public static String VAS_DOMAIN_QUERY = "/vas/query.action";

	public static String VAS_DOMAIN_QUERY_REMOTE = "/vas/queryRemote.action";

	public static String VAS_DOMAIN_OPEN_CARD = "/vas/opencard.action";

	public static String VAS_DOMAIN_SVC_DEPOSITE = "/vas/svcDeposite.action";

	public static String VAS_ACTION_SVC_DEPOSITE = "deposite";

	public static String VAS_ACTION_SVC_VALIDATE = "validateCard";

	public static String VAS_ACTION_OPEN_CARD_VALIDATE_CARD = "validateCardNo";

	public static String VAS_ACTION_QUERY_GETPOLIST = "queryPoList";

	public static String VAS_ACTION_QUERY_GETPOLISTSTORAGE = "queryPoListAndStorage";

	public static String VAS_ACTION_QUERY_GETREMOTEPOLIST = "queryPoListByRemote";

	public static String VAS_ACTION_QUERY_GETREMOTEPOLISTSTORAGE = "queryPoListByRemoteAndStorge";

	public static Integer VAS_CONST_MAX_COUNTS = 10;

	public static String VAS_CONST_DATETIME_PARTTERN = "yyyy/MM/dd HH:mm:ss";

	public static String VAS_CONST_BEGINTIMES = "00:00:00";

	public static String VAS_CONST_ENDTIMES = "23:59:59";

	public static String VAS_FLOWS_QUERY_COMPLETE_COND = "cond";

	public static String VAS_FLOWS_QUERY_COMPLETE_DETAIL = "detail";
	// po 查询流程
	public static String VAS_FLOWS_QUERY = "vas_po_query_flow";

	/**
	 * 
	 */
	public static final String VAS_PRODUCTSALES_ACTIVITY = "vas.activity.ProductSalesActivity";

	/**
	 * 
	 */
	public static final String VAS_PRODUCT_DETAIL_ACTIVITY = "vas.activity.ProductDetailListActivity";

	public static final String VAS_PRODUCT_EDIT_ACTIVITY = "vas.activity.ProductEditActivity";

	public static final String VAS_INTENT_PURCHASE_INFO_ID_KEY = "pid";

	public static final String VAS_INTENT_SHOWBACK_FLAG_KEY = "isBack";
}
