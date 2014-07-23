package me.andpay.apos.tqm;

public class TqmProvider {

	public static String TQM_ACTIVITY_REFUND_TXN_LIST = "trm.list.activity";

	public static String TQM_ACTIVITY_TXN_LIST = "tqm.list.activity";

	public static String TQM_ACTIVITY_DETAIL_ACTION = "tqm.detail.activity";

	public static String TQM_ACTIVITY_DETAIL_MAP_ACTION = "tqm.detail.map.activity";

	public static String TQM_ACTIVITY_LIST_CONDITION_ACTION = "tqm.list.condition.activity";

	public static String TQM_DOMAIN_QUERY = "/tqm/query.action";

	public static String TQM_DOMAIN_QUERY_REMOTE = "/tqm/queryRemote.action";

	public static String TQM_ACTION_QUERY_GETTXNLIST = "queryTxnList";

	public static String TQM_ACTION_QUERY_GETTXNLIST_STORAGE = "queryTxnListAndStorageRemote";

	public static String TQM_ACTION_QUERY_REMOTE_GETTXNLIST_REMOTE = "queryTxnListByRemote";
	
	public static String TQM_ACTION_QUERY_REMOTE_GETTXNLIST_STORAGE_REMOTE = "queryTxnListAndStorgeByRemote";
	
	public static String TQM_ACTION_QUERY_REMOTE_SINGETXN_REMOTE = "querySingleTxnByRemote";

	public static Integer TQM_CONST_MAX_COUNTS = 10;
	/**
	 * 交易查询条件页面屏蔽txntype选项
	 */
	public static String TQM_CONST_HIDE_TXNTYPE = "txnTypeHide";
	
	public static String TQM_QUERY_COND_FORM = "queryCondForm";

	public static String TQM_PARTTERN_DATE = "yyyy/MM/dd";

	public static String TQM_PARTTERN_DATETIME = "yyyy/MM/dd HH:mm:ss";

	public static String TQM_PARTTERN_COMMOM_DATE = "yyyyMMddHHmmss";
	
	public static String TQM_PARTTERN_COMMOM_TIME = "HH:mm:ss";

	public static Integer TQM_RESULT_CODE_CONDITION = 100;
}
