package me.andpay.timobileframework.flow.imp;

import java.util.Stack;

public class PersistenceStackUtil {

	static final String SPEARTE = ";";

	public static String toPersistenceString(Stack<String> stack) {
		StringBuffer pstr = new StringBuffer();
		for (int i = 0; i < stack.size(); i++) {
			pstr.append(stack.get(i));
			if (i != stack.size() - 1) {
				pstr.append(SPEARTE);
			}
		}
		return pstr.toString();
	}

	public static Stack<String> persistenceToStack(String str) {
		Stack<String> stack = new Stack<String>();
		String[] strs = str.split(SPEARTE);
		for (String temp : strs) {
			stack.push(temp);
		}
		return stack;
	}

}
