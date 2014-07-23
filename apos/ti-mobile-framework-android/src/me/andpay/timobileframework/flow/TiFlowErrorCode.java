package me.andpay.timobileframework.flow;
/**
 * Ti Flow 异常代码表
 * @author tinyliu
 *
 */
public class TiFlowErrorCode {
	
	/**
	 * 没有定位到beginnode
	 */
	public static String PROCESS_NOT_FOUND_BEGINNODE = "err.process.001";
	/**
	 * 反射activity class失败
	 */
	public static String PROCESS_REF_CLASS_REFLECT = "err.process.002";
	/**
	 * 流程没有结束，不允许重新启动流程
	 */
	public static String PROCESS_NOT_START = "err.process.003";
	/**
	 * 流程状态不一致 
	 */
	public static String PROCESS_FLOW_STATUS_ERROR = "err.process.004";
	/**
	 * 对应跳转标签找不到
	 */
	public static String PROCESS_IDENTITY_NOTFOUND_ERROR = "err.process.005";
	/**
	 * 当前activity不在流程中
	 */
	public static String PROCESS_IDENTITY_NOTIN_CONTEXT = "err.process.006";
	/**
	 * 当前activity不在流程中
	 */
	public static String PROCESS_SUBFLOW_ISNULL = "err.process.007";
	/**
	 * 配置文件不存在
	 */
	public static String CONFIG_FLOWS_CONFIG_NOTEXISTS = "error.conf.001";
	/**
	 * 配置文件不存在
	 */
	public static String CONFIG_FLOWS_SAX_PARSEERROR = "error.conf.002";
	/**
	 * 配置Forward不存在
	 */
	public static String CONFIG_FLOWS_FORWARD_NOTEXISTS = "error.conf.003";
	/**
	 * 配置Node不存在
	 */
	public static String CONFIG_FLOWS_NODES_NOTEXISTS = "error.conf.004";
	/**
	 * Node配置错误
	 */
	public static String CONFIG_FLOWS_NODES_CONFIGERROR = "error.conf.005";
	/**
	 * Complete配置错误
	 */
	public static String CONFIG_FLOWS_COMPLETE_CONFIGERROR = "error.conf.006";
}
