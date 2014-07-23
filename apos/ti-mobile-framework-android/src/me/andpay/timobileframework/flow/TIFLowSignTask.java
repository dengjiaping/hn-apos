package me.andpay.timobileframework.flow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在流程中声明为单例的，不需要被回收
 * @author cpz
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.TYPE})
public @interface TIFLowSignTask {

}
