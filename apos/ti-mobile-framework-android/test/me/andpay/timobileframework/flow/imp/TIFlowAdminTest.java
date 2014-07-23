package me.andpay.timobileframework.flow.imp;

import me.andpay.timobileframework.flow.TiFlowAdmin;

import org.junit.Test;

public class TIFlowAdminTest {
	
	@Test
	public void test() {
		TiFlowAdmin admin = TiFlowAdmin.singletonInstance();
		if(admin.getFlowControl("testFlow") == null) {
			throw new RuntimeException("testError");
		}
	}
}
