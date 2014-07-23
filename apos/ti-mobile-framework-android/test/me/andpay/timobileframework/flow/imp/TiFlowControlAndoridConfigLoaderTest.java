package me.andpay.timobileframework.flow.imp;

import java.util.Map;

import me.andpay.timobileframework.flow.TIFlowDiagram;

import org.junit.Test;

public class TiFlowControlAndoridConfigLoaderTest {

	@Test
	public void test() {
		TiFlowContrlAndroidConfigLoader loader = new TiFlowContrlAndroidConfigLoader();
		Map<String, TIFlowDiagram> diagram = loader.loadFlow();
		if(diagram.size() != 2) {
			throw new RuntimeException("testError");
		}
	}

}
