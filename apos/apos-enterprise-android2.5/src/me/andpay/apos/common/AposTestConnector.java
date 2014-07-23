package me.andpay.apos.common;

import me.andpay.ac.term.api.info.ServerStatusCollector;
import me.andpay.ti.lnk.rpc.ClientObjectFactory;
import me.andpay.timobileframework.lnk.AddressInfo;
import me.andpay.timobileframework.lnk.TiChannelAddressVerify;
import me.andpay.timobileframework.lnk.TiTestConnector;
import android.util.Log;

public class AposTestConnector implements TiTestConnector {

	public void testConn(TiChannelAddressVerify verify, AddressInfo address,
			ClientObjectFactory factory) {
		ServerStatusCollector statusService = null;

		try {

			//
			// statusService = (ServerStatusCollector) factory
			// .getClientObject(Class.forName(address.getSelectClass()));
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "get Selector Class", e);
		}

	}

	// class StatusCallBack implements ServerStatusSubscriber {
	//
	// TiChannelAddressVerify verify;
	//
	// AddressInfo address;
	//
	// public StatusCallBack(TiChannelAddressVerify verify, AddressInfo address)
	// {
	// this.verify = verify;
	// this.address = address;
	// }
	//
	// public void onReport(ServerStatus status) {
	// verify.verify(address);
	// }
	//
	// }

}
