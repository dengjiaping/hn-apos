package me.andpay.timobileframework.lnk;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import me.andpay.ti.lnk.sc.SimpleClientBuilder;
import me.andpay.ti.lnk.sc.SimpleClientConfig;

public class DefaultChannelSelector implements TiChannelSelector,
		TiChannelAddressVerify {

	private BlockingQueue<AddressInfo> queue = new LinkedBlockingQueue<AddressInfo>();

	private TiTestConnector testConnector;

	private boolean hasSelect = false;

	private Long default_select_timeout = 6 * 1000l;
	

	public DefaultChannelSelector(TiTestConnector testConnector,
			Long selectTimeout) {
		this.testConnector = testConnector;
		if (default_select_timeout == null) {
			this.default_select_timeout = selectTimeout;
		}
	}

	public DefaultChannelSelector(TiTestConnector testConnector) {
		this(testConnector, null);
	}

	public AddressInfo selectFastChannel(RpcParam param) {
		Long begintime = System.currentTimeMillis();
		queue.clear();
		hasSelect = false;
		SimpleClientBuilder builder = new SimpleClientBuilder();
		SimpleClientConfig config = new SimpleClientConfig();
		config.setServerAddressByServiceGroup(param.getServiceGroup());
		config.setTrustAll(true);
		builder.build(config);
		System.out.println("build cost time : "
				+ (System.currentTimeMillis() - begintime));
//		for (AddressInfo address : param.getAddressInfos()) {
//			try {
//				testConnector.testConn(this, address, builder.getRpc()
//						.getClientObjectFactory());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		try {
			return queue.poll(default_select_timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			throw new RuntimeException("take the fast address happend error", e);
		}
	}

	public synchronized void verify(AddressInfo info) {
		if (!hasSelect) {
//			System.out.println("Frist response address : [" + info.getAddress()
//					+ "]");
			hasSelect = true;
			queue.add(info);
		}

	}

}
