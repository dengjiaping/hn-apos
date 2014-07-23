package me.andpay.timobileframework.flow.imp;

import java.util.Stack;

import junit.framework.Assert;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.TiFlowAdmin;
import me.andpay.timobileframework.flow.TiFlowControl;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;

import org.junit.Test;

public class TxnControlTest {

	protected void controlForward(int intValue) {
		TiFlowAdmin admin = TiFlowAdmin.singletonInstance();
		TiFlowControl control = admin.getFlowControl("testFlow");
		control.start();
		Stack<String> stack = null;
		for (int i = 0; i < intValue; i++) {
			stack = new Stack<String>();
			for (String str : admin.getRecorder().getFlows()) {
				stack.add(str);
			}
			TiFlowNodeComplete complete = control.nextStepWithIdentity("success");
			if (!StringUtil.isEmpty(complete.getSubFlowName())) {
				control = admin.getFlowControl("testFlow2");
				control.start("testFlow");
			}
		}
	}

	@Test
	public void testControl() {
		TiFlowAdmin admin = TiFlowAdmin.singletonInstance();
		int expectStackValue = 9;
		String expectFlowValues = "[testFlow, testFlow2]";
		controlForward(8);
		TiFlowControl control = admin.getFlowControl("testFlow2");
		Stack<String> stack = new Stack<String>();
		for (String str : admin.getRecorder().getFlows()) {
			stack.add(str);
		}
		if (!(expectFlowValues.equals(stack.toString()) && expectStackValue == control
				.getFlowStack().size())) {
			throw new RuntimeException("test is failed");
		}
	}

	@Test
	public void testPervious() {
		TiFlowAdmin admin = TiFlowAdmin.singletonInstance();
		TiFlowControl control = admin.getFlowControl("testFlow");
		control.start();

		controlForward(4);

		for (int i = 0; i < 5; i++) {
			control.previousStep();
		}
		if (!(admin.getRecorder().getFlows() == null && admin.getRecorder().getCurrentStack().size() == 0)) {
			throw new RuntimeException("test is failed");
		}
	}

	@Test
	public void testPerviousFlow() {
		TiFlowAdmin admin = TiFlowAdmin.singletonInstance();
		String expectStackValue = "testFlow";
		String expectFlowValues = "[steup1, steup2, steup3, steup4, steup5]";
		TiFlowControl control = admin.getFlowControl("testFlow");
		control.start();

		controlForward(8);

		for (int i = 0; i < 5; i++) {
			control.previousStep();
		}
		if (!(admin.getRecorder().getFlows()[0].equals(expectStackValue) && admin.getRecorder().getCurrentStack().size() == 4)) {
			throw new RuntimeException("test is failed");
		}
	}
}
