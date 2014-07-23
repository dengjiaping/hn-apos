package me.andpay.apos.cdriver.callback;

import java.util.List;

import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;

import com.NLmpos.Controller.ICCardTxnController;



public interface CardReaderCallback {
		
	
	/**
	 * 设备插入
	 */
	public void onDevicePlugged();
	
	/**
	 * 设备拔出
	 */
	public void onDeviceUnplugged();
	
	/**
	 * 刷卡超时
	 */
	public void onTimeout();

	/**
	 * 等待刷卡
	 */
	public void onWaitingForCardSwipe();
	
	
	/**
	 * 刷卡完成
	 * @param cardInfo
	 */
	public void onDecodeCompleted(CardInfo cardInfo);
	/**
	 * 卡号解析异常
	 */
	public void onDecodeError(String msg);
	
	/**
	 * 设备异常
	 */
	public void onDeviceError(String Msg,String errorCode);
	
	/**
	 * 刷卡被取消
	 */
	public void onCancel();
	
	/**
	 * 卡号解析开始
	 */
	public void  onDecodingStart();
	
	/**
	 * 数据接受开始
	 */
	public void onReceiveDataStart();
	
	/**
	 * 等待密码输入
	 */
	public void OnWaitingPin();
	/**
	 * 设备搜索完成
	 */
	public void seachDeviceComplete(List<CardReaderInfo> cardReaderInfos);
	
	/**
	 * 搜索发现设备
	 * @param cardReaderInfo
	 */
	public void seachFoundDevice(CardReaderInfo cardReaderInfo,List<CardReaderInfo> cardReaderInfos);
	/**
	 * 搜索设备
	 */
	public void onSeachDevice();
	/**
	 * 显示等待刷卡页面
	 */
	public void onShowSwiper();
	
	/**
	 * 获取交易失败
	 */
	public void getTxnError();
	
	/**
	 * 发送数据异常
	 */
	public void onSendDataError();
	
	/**
	 * 蓝牙匹配帮助
	 */
	public void matchBluetoothHelp();
	
	/**
	 * 设备连接失败
	 */
	public void onConnectError();
	
	/**
	 * key初始化
	 */
	public void initKeyError(String errorMsg);
	
	/**
	 * ic交易完成
	 * @param finish
	 * @param icCardDataInfo
	 */
	public void onICFinished(boolean finish, AposICCardDataInfo icCardDataInfo);
	/**
	 * ic卡要求联机交易
	 * @param arg0
	 * @param icCardDataInfo
	 */
	public void onICRequestOnline(AposICCardDataInfo icCardDataInfo);
		
	/**
	 * 刷卡拒绝
	 */
	public void onICSwipeRefuse();
	
	/**
	 * 二次授权失败
	 */
	public void onSecondIssuanceError();


}
