package me.andpay.timobileframework.util;

import java.util.HashMap;
import java.util.Map;

public class FastDoubleClickUtil {

	public static Map<Integer, Long> clickTimes = new HashMap<Integer, Long>();

	public static boolean isFastDoubleClick(Integer viewId) {

		Long lastClickTime = clickTimes.get(viewId);
		if (lastClickTime == null) {
			clickTimes.put(viewId, System.currentTimeMillis());
			return false;
		}
	    long time = System.currentTimeMillis(); 
        long timeD = time - lastClickTime; 
        if ( 0 < timeD && timeD < 2000) {    
//        	clickTimes.put(viewId, time);
            return true;    
        }else {
            clickTimes.put(viewId, time);
        }
        return false;
	}
	
	public  static void clearFastView() {
		clickTimes.clear();
	}
}
