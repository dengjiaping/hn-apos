package me.andpay.apos.cdriver;

/**
 * 刷卡状态
 * @author cpz
 *
 */
public class SwiperStatus {

	
	/**
	 * 闲置状态
	 */
	  public static final int STATE_IDLE = 0;
	 
	  /**
	   * 等待设备插入
	   */
	  public static final int STATE_WAITING_FOR_DEVICE =1;
	  
	  /**
	   * 重新解码
	   */
	  public static final int STATE_RECORDING =2;
	  
	  /**
	   * 解码中
	   */
	  public static final int STATE_DECODING =3;
}
