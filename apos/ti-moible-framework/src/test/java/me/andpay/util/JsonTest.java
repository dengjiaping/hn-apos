package me.andpay.util;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ti.util.JSONObject;

import org.junit.Test;

public class JsonTest {
	
	

	@Test
	public void testJson() {
	
		
		JsonData jsonData = new JsonData();
		jsonData.setAge(11l);
		jsonData.setId("12321");
		jsonData.setName("well");
		
		Map<String,String> configs = new HashMap<String,String>();
		configs.put("sss", "123123");
		configs.put("good", "asdasd");
		jsonData.setConfigs(configs);
		
		Object obj = JSONObject.wrap(jsonData);
		System.out.println(obj.toString());
			
		
	}
}
