package me.andpay.timobileframework.lnk;

import java.util.HashMap;

import junit.framework.TestCase;
import me.andpay.ac.term.srv.api.apos.LoginResponse;
import me.andpay.ac.term.srv.api.apos.UserAuthService;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.example.server.MyService1;

public class TiLnkClientProxyTest extends TestCase {

	TiRpcClientProxy client;

	public void setUp() {
		long begin = System.currentTimeMillis();
		client = new TiRpcClientProxy(new TiUnitTestConnector());
		client.start();
		System.out.println("init client costTime : "
				+ (System.currentTimeMillis() - begin));
	}

	public void testStart() throws AppBizException {
		try {
			UserAuthService service = (UserAuthService) client
					.getLnkService(UserAuthService.class);
			LoginResponse reponse = service.login("13761786363", "123456",
					new HashMap<String, String>());
		} catch (AppBizException ex) {
			assertNotNull(ex.getCode());
		}
	}

	public void testStop() {
		try {
			UserAuthService service = (UserAuthService) client
					.getLnkService(UserAuthService.class);
			LoginResponse reponse = service.login("13761786363", "123456",
					new HashMap<String, String>());
		} catch (AppBizException ex) {
			assertNotNull(ex.getCode());
		}
		client.stop();
		client.start();
		try {
			UserAuthService service = (UserAuthService) client
					.getLnkService(UserAuthService.class);
			LoginResponse reponse = service.login("13761786363", "123456",
					new HashMap<String, String>());
		} catch (AppBizException ex) {
			assertNotNull(ex.getCode());
		}
	}

	public void testRestart() {
		try {
			UserAuthService service = (UserAuthService) client
					.getLnkService(UserAuthService.class);
			LoginResponse reponse = service.login("13761786363", "123456",
					new HashMap<String, String>());
		} catch (AppBizException ex) {
			assertNotNull(ex.getCode());
		}
		client.restartTransport();
		try {
			UserAuthService service = (UserAuthService) client
					.getLnkService(UserAuthService.class);
			LoginResponse reponse = service.login("13761786363", "123456",
					new HashMap<String, String>());
		} catch (AppBizException ex) {
			ex.printStackTrace();
			assertNotNull(ex.getCode());
		}
	}
}
