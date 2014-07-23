package me.andpay.timobileframework.lnk;

import me.andpay.ti.lnk.rpc.ClientObjectFactory;

/**
 * 连接测试类，用于测试网络地址连接速度。 完成地址连接测试后，必须调用TiChannelAddressVerify的verify方法 将结果返回。
 * 谁最先返回，即为默认地址
 * 
 * @author tinyliu
 * 
 */
public interface TiTestConnector {

	public void testConn(TiChannelAddressVerify verify, AddressInfo address,
			ClientObjectFactory factory);

}
