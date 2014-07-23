package me.andpay.apos.common.log;

public class OperationDataKeys {
	
	/**
	 * 手机环境变量
	 */
	public static final String PHONE_EVN_PREFIX= "PA_";
	/**
	 * 操作步骤
	 */
	public static final String OP_EVN_PREFIX= "PB_";
	/**
	 * 返回结果
	 */
	public static final String RESULT_PREFIX= "PC_";
	
	/**
	 * 打开电源 true false
	 */
	public static final String OPKEYS_OPEN_DEVICE = OperationDataKeys.OP_EVN_PREFIX+ "openDeovce";
	/**
	 * 插入设备 true false
	 */
	public static final String OPKEYS_INSERT_DEVICE = OperationDataKeys.OP_EVN_PREFIX+ "insertDevice";
	/**
	 * 搜索设备 true false
	 */
	public static final String OPKEYS_SEARCH_DEVICE = OperationDataKeys.OP_EVN_PREFIX+ "searchDevice";
	/**
	 * 设置杜比 true false
	 */
	public static final String OPKEYS_SET_DOLBY = OperationDataKeys.OP_EVN_PREFIX+ "setDolby";

	
	/**
	 * 是否根据向导 true false
	 */
	public static final String OPKEYS_IS_GUIDE = OperationDataKeys.RESULT_PREFIX+ "isGuide";

	/**
	 * 重新检测 true false
	 */
	public static final String OPKEYS_RECHECK_FLAG = OperationDataKeys.RESULT_PREFIX+ "recheckFlag";
	/**
	 * 提交时间
	 */
	public static final String OPKEYS_SUBDATE = OperationDataKeys.RESULT_PREFIX+ "subDate";
	/**
	 * 跟踪编号
	 */
	public static final String OPKEYS_OP_TRACENO = OperationDataKeys.RESULT_PREFIX+"opTraceNo";
	
	/**
	 * 产品型号
	 */
	public static final String OPKEYS_CARDREADER_TYPE =  OperationDataKeys.RESULT_PREFIX+"cardReaderType";
	
	/**
	 * 设备编号
	 */
	public static final String OPKEYS_KSN =  OperationDataKeys.RESULT_PREFIX+"ksn";
	
	/**
	 * 通讯类型 0 音频 1 蓝牙 2云
	 */
	public static final String OPKEYS_COMM_TYPE =  OperationDataKeys.RESULT_PREFIX+"commType";

	/**
	 * 蓝牙设备名称
	 */
	public static final String OPKEYS_BLUETOOTH_NAME =  OperationDataKeys.RESULT_PREFIX+"bluetoothName";

	/**
	 * 检测结果 0 失败 1成功
	 */
	public static final String  OPKEYS_CHECKSTATUS =  OperationDataKeys.RESULT_PREFIX+"checkStatus";
	
	/**
	 * 错误信息
	 */
	public static final String OPKEYS_ERROR_MSG = OperationDataKeys.RESULT_PREFIX+ "errorMsg";
	
	/**
	 * 错误编码
	 */
	public static final String OPKEYS_ERROR_CODE = OperationDataKeys.RESULT_PREFIX+ "errorCode";


	/**
	 * 蓝牙搜索结果列表 逗号分隔
	 */
	public static final String OPKEYS_BLUE_TOOTHT_LIST = OperationDataKeys.RESULT_PREFIX+"bluetoothList";
	
	/**
	 * 杜比状态
	 * close 关闭 open 打开  unknow 未知
	 */
	public static final String OPKEYS_DOLBY_STATUS = OperationDataKeys.PHONE_EVN_PREFIX+"dolbyStatus";
	/**
	 * 是否是杜比手机
	 * true 是  false 不是
	 */
	public static final String OPKEYS_IS_MOBILE_DOLBY =  OperationDataKeys.PHONE_EVN_PREFIX+"isDolbyMobile";
	/**
	 * 耳机状态  0 没插入 1插入
	 */
	public static final String OPKEYS_HEADSET_STATUS = OperationDataKeys.PHONE_EVN_PREFIX+ "headsetStatus";
	/**
	 * 麦克风状态 0 没有麦克风 1有麦克风
	 */
	public static final String OPKEYS_MIC_STATUS =  OperationDataKeys.PHONE_EVN_PREFIX+"microphoneStatus";
	
	/**
	 * 手机音乐音量 整数值
	 */
	public static final String OPKEYS_VOLUME =  OperationDataKeys.PHONE_EVN_PREFIX+"volume";
	
	/**
	 * 手机最大音量 整数值
	 */
	public static final String OPKEYS_MAX_VOLUME =  OperationDataKeys.PHONE_EVN_PREFIX+"maxVolume";


	/**
	 * 蓝牙状态 10关闭状态  11 开启中  12 开启  13 关闭中
	 */
	public static final String OPKEYS_BLUETOOTH_STATUS =  OperationDataKeys.PHONE_EVN_PREFIX+"bluetoothStatus";




}
