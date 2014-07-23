package me.andpay.timobileframework.saf.annotaion;

import me.andpay.timobileframework.saf.enumeration.TiSafEventPollPolicy;

public @interface TiSafPoll {

	TiSafEventPollPolicy pollPolicy() default TiSafEventPollPolicy.NORMAL;

	int timeMills() default 0;

	int maxPollCount() default -1;
}
