package me.andpay.timobileframework.saf.annotaion;

public @interface TiSafSchedule {
	
	int delayMills() default -1;
	
	int periodMills() default -1;
	
}
