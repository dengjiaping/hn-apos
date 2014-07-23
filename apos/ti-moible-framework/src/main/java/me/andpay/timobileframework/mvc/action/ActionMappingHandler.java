package me.andpay.timobileframework.mvc.action;

import java.util.HashMap;
import java.util.Map;

public class ActionMappingHandler {

	static Map<String, Action> allMappings = new HashMap<String, Action>();

	public static Action getAction(String domain) {
		return allMappings.get(domain);
	}

	public static void registerMappings(String domain, Action action) {
		allMappings.put(domain, action);
	}
}
