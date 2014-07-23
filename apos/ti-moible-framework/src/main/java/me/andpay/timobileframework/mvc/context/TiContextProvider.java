package me.andpay.timobileframework.mvc.context;


/**
 * 上下文提供接口
 * @author tinyliu
 *
 */
public interface TiContextProvider {
	
	public TiContext provider(int contextScope);
}
