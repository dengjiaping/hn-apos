package me.andpay.apos.common.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.andpay.ac.term.api.auth.ExtTermParaNames;
import me.andpay.ac.term.api.auth.ExtTermParaValues;
import me.andpay.ac.term.api.ga.TerminalStatsService;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.ti.base.AppRtException;
import me.andpay.ti.util.StringUtil;

import com.google.inject.Inject;

public class AposDebugLog {

	private TerminalStatsService statsService;
	private static TerminalStatsService terminalStatsService;
	private static String deviceId;
	private static boolean isLog;
	@Inject
	private AposContext aposContext;

	public void init() {
		AposDebugLog.terminalStatsService = statsService;
		AposDebugLog.deviceId = (String) aposContext.getAppConfig()
				.getAttribute(ConfigAttrNames.DEVICE_ID);
		Object parmObj = aposContext.getAppContext().getAttribute(
				RuntimeAttrNames.EXT_TERM_PARA);
		if (parmObj == null) {
			AposDebugLog.isLog = false;
		} else {
			Map<String, String> parms = HashMap.class.cast(parmObj);
			String submitDebug = parms.get(ExtTermParaNames.SUBMIT_DEBUG_DATA);
			if (ExtTermParaValues.TRUE.equals(submitDebug)) {
				AposDebugLog.isLog = true;
			} else {
				AposDebugLog.isLog = false;
			}
		}

	}

	public static void log(String category, String log) {
		log(category, null, log);
	}

	public static void log(String category, String traceNo, String log) {
		if (!isEnable()) {
			return;
		}
		log = StringUtil.format("yyyy-MM-dd HH:mm:ss.SSS", new Date()) + "  "
				+ log;
		List<String> logs = new ArrayList<String>();
		logs.add(log);
		if (traceNo == null) {
			traceNo = getThreadId();
		}
		try {
			terminalStatsService.submitDebugData(deviceId, category, traceNo,
					logs);
		} catch (AppRtException ex) {
		}

	}

	public static boolean isEnable() {
		return isLog;
	}

	public static String getThreadId() {

		return String.valueOf(Thread.currentThread().getId());
	}

}
