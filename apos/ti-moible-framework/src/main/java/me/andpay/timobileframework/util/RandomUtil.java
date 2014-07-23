package me.andpay.timobileframework.util;

import java.util.Random;

public class RandomUtil {
	
	
	public static String getFixLenthString(int strLength) {
		Random rm = new Random();
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
		String fixLenthString = String.valueOf(pross);
		return fixLenthString.substring(1, strLength + 1);
	}
}
