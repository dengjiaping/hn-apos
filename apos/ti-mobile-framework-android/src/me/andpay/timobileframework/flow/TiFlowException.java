package me.andpay.timobileframework.flow;

import me.andpay.ti.util.StringUtil;

/**
 * 流程控制器异常
 * 
 * @author tinyliu
 * 
 */
@SuppressWarnings("serial")
public class TiFlowException extends RuntimeException {

	public static final String TIFLOWEX_GROUP_CONFIG = "Flow Config";

	public static final String TIFLOWEX_GROUP_PROCESS = "Flow Process";

	private String group;

	private String errorCode;

	private String errorDesc;

	public TiFlowException() {
		super();
	}

	public TiFlowException(String group, String errorCode, String errorDesc) {
		super(errorDesc);
		this.group = group;
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public TiFlowException(String group, String errorCode, String errorDesc,
			Throwable ex) {
		super(ex);
		this.group = group;
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String toString() {
		return String
				.format("exception type is [%s], group is [%s], errorCode is [%s], errorDesc is [%s]",
						getClass().getName(),
						StringUtil.defaultString(this.group, "null"),
						StringUtil.defaultString(this.errorCode, "null"),
						StringUtil.defaultString(this.errorDesc, "null"));
	}

}
