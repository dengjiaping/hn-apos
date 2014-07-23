package me.andpay.timobileframework.lnk;

/**
 * 渠道选择器
 * 
 * @author tinyliu
 * 
 */
public interface TiChannelSelector {
	
	public AddressInfo selectFastChannel(RpcParam param);
}
