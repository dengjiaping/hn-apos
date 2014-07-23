package me.andpay.timobileframework.lnk;

import me.andpay.ti.lnk.example.server.MyService;
import me.andpay.ti.lnk.example.server.TxnRequest;
import me.andpay.ti.lnk.example.server.TxnResponse;
import me.andpay.ti.lnk.example.server.TxnResponseHandler;
import me.andpay.ti.lnk.rpc.ClientObjectFactory;

public class TiUnitTestConnector implements TiTestConnector {

	public void testConn(TiChannelAddressVerify verify, String alias, String address,
			Class testClass, ClientObjectFactory factory) {

		MyService serivice = factory.getClientObject(testClass);
		serivice.chainProcessTxn(new TxnRequest(), new CallBack(
				address, verify));
	}

	class CallBack implements TxnResponseHandler {
		String address;

		TiChannelAddressVerify verify;

		Long beginDate;

		public CallBack(String address, TiChannelAddressVerify verify) {
			this.address = address;
			this.verify = verify;
			this.beginDate = System.currentTimeMillis();
		}

		public void onResponse(TxnResponse resp) {
			System.out.println("response address : [" + address
					+ "], cost times : ["
					+ (System.currentTimeMillis() - beginDate) + "]");
			verify.verify(address);

		}

	}

}
