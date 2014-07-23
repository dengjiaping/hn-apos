package me.andpay.apos.tam.context;

public class TxnStatus {

	
	/**
	 * 初始状态
	 */
	public final static String INIT = "A";
	
	/**
	 * 等待刷卡器插入
	 */
	public final static String WAIT_CARDREADER = "B";
	
	/**
	 * 等待刷卡
	 */
	public final static String WAIT_SWIPER = "C";
	
	/**
	 * 等待密码输入
	 */
	public final static String WAIT_PASSWORD = "D";
	

	/**
	 * 可输入pin读卡器交易提交
	 */
	public final static String TXN_SUBMIT_WITH_PIN = "F";
	
	/**
	 * 游览器模式，等待密码输入
	 */
	public final static String WAIT_BROWSER_PHONENO = "E";
	
	/**
	 * 交易超时重新查询状态
	 */
	public final static String TXN_TIME_OUT_DOQUERY = "G";
	
	/**
	 * 刷卡器等待密码输入
	 */
	public final static String CARD_READER_WAIT_PASSWORD = "H";
	
	/**
	 * 外部刷卡器输入密码
	 */
	public final static String WAIT_PASSWORD_OUT = "I";
	
	
	/**
	 * 等待刷卡余额查询模式
	 */
	public final static String WAIT_SWIPER_BALANCE = "J";
	
	/**
	 * 设备搜索
	 */
	public final static String SEARCH_DEVICE = "K";
	
	/**
	 * 尝试连接设备
	 */
	public final static String TRY_CONN_DEVICE = "M";

	/**
	 * 显示等待刷卡页面
	 */
	public final static String SHOW_WAIT_SWIPER = "N";


}
