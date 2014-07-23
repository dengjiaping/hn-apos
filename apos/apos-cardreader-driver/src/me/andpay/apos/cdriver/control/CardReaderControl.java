package me.andpay.apos.cdriver.control;

import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.DeviceInfo;
import me.andpay.apos.cdriver.OpenDeivceResult;
import me.andpay.apos.cdriver.model.AposICAppParam;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.cdriver.model.AposIcPublicKey;
import me.andpay.apos.cdriver.model.AposResultData;
import me.andpay.apos.cdriver.model.AposTerminalParam;
import android.content.Context;

public interface CardReaderControl {

	/**
	 * 初始化刷卡器
	 * 
	 * @param context
	 * @param cardReadCallback
	 * @return
	 */
	public void initCardReader(Context context);

	/**
	 * 读取刷卡器类型
	 * 
	 * @return
	 */
	public int getCardReaderType();

	/**
	 * 刷卡器是否插入
	 * 
	 * @return
	 */
	public boolean isInput();

	/**
	 * 驱动刷卡
	 */
	public boolean startSwiper(AposSwiperContext swiperRequest);

	/**
	 * 停止刷卡
	 */
	public void stopSwiper();

	/**
	 * 获取刷卡状态
	 * 
	 * @return
	 */
	public int getSwiperState();

	/**
	 * 初始化读卡器是否连接
	 * 
	 * @return
	 */
	public boolean isInitConnect();

	/**
	 * 检测设备是否正常，例如一些设备的上电检测，无源同isInput
	 * 
	 * @return
	 */
	public boolean isDevicePresent();

	/**
	 * 停止刷卡器监听
	 */
	public void stopCardReader();

	public void setInitConnect(boolean initConnect);

	/**
	 * 搜索设备
	 */
	public void searchDevice();

	/**
	 * 开启设备
	 */
	public OpenDeivceResult openDevice(String defaultIdentifier);

	/**
	 * 获取设备信息
	 * 
	 * @return
	 */
	public DeviceInfo getDeviceInfo();

	/**
	 * 灌装密钥
	 * 
	 * @param keyType
	 * @param keyData
	 * @param checkValue
	 */
	public boolean loadKey(String keyType, byte[] keyData, byte[] checkValue);

	public boolean isSupportDolby();

	/**
	 * 开始录音
	 */
	public void startRecord(String traceNo);

	/**
	 * 停止录音
	 */
	public void stopRecord();

	/**
	 * 密码输入方式
	 * 
	 * @return
	 */
	public int getInputType();

	/**
	 * 增加IC卡参数
	 * 
	 * @return
	 */
	public AposResultData addICAppParam(AposICAppParam aposICAppParam);

	/**
	 * 增加公钥参数
	 * 
	 * @return
	 */
	public AposResultData addICPublicKey(AposIcPublicKey aposIcPublicKey);
	
	/**
	 * 二次授权
	 * @param aposICCardDataInfo
	 */
	public void secondIssuance(AposICCardDataInfo aposICCardDataInfo );

	/**
	 * 添加终端参数
	 * @param aposTerminalParam
	 * @return
	 */
	public AposResultData addTerminalParams(AposTerminalParam aposTerminalParam);
	
	/**
	 * 显示屏幕内容
	 * @param showMsg
	 * @param showTime
	 */
	public void showLCD(String showMsg,int showTime);
	/**
	 * 清屏
	 */
	public void clearScreen();
	/**
	 * 计算密钥
	 * @param traceNo
	 * @param data
	 * @return
	 */
	public AposResultData calculateMac(String traceNo,String data);
	
	/**
	 * 获取磁道密文
	 * @param traceNo
	 * @return
	 */
	public String fetchEncryptSecTrackInfo(String traceNo);

}
