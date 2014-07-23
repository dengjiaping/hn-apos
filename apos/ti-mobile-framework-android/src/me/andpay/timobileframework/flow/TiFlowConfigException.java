package me.andpay.timobileframework.flow;

/**
 * flow control 配置异常
 * @author tinyliu
 *
 */
@SuppressWarnings("serial")
public class TiFlowConfigException extends TiFlowException{
	
	public TiFlowConfigException(String errorCode, String errorDesc, Throwable ex) {
		super(TiFlowException.TIFLOWEX_GROUP_CONFIG, errorCode, errorDesc, ex);
	}

	public TiFlowConfigException(String errorCode, String errorDesc) {
		super(TiFlowException.TIFLOWEX_GROUP_CONFIG, errorCode, errorDesc);
	}
	
}
