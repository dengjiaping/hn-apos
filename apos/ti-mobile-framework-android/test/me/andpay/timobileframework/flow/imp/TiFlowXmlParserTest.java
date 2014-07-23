package me.andpay.timobileframework.flow.imp;

import java.net.URL;
import java.util.Map;

import me.andpay.timobileframework.flow.TIFlowDiagram;

import org.junit.Test;


public class TiFlowXmlParserTest {
	
	@Test
	public void testXmlParser() {
		
		TIFlowDiagram diagram = TiFlowXmlParser.parseXML("testFlow", Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("flows/testFlow.xml"));
		if(diagram == null) {
			throw new RuntimeException("ERROR");
		}
		if(diagram.getNodes().size() != 5) {
			throw new RuntimeException("ERROR");
		}
		Map<String, String> value = diagram.getNodes().get("steup5").getComplete("success").getUnTransferData();
		if(value == null || value.isEmpty()) {
			throw new RuntimeException("ERROR");
		}
		//Assert.assertNotNull(diagram);
	}
}
