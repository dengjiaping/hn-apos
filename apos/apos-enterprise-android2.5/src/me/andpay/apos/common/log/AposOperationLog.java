package me.andpay.apos.common.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.term.api.ga.TerminalStatsService;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.RoboGuiceUtil;
import android.content.Context;
import android.util.Log;

/**
 * 操作日志
 * 
 * @author cpz
 * 
 */
public class AposOperationLog {
	
	public static final String TAG = AposOperationLog.class.getName();

	private static TerminalStatsService terminalStatsService;
	private static AposContext aposContext;

	private static String deviceId;
	
	

	public  static void init(Context context) {

		if(terminalStatsService == null) {
			terminalStatsService = RoboGuiceUtil.getInjectObject(TerminalStatsService.class,context );
		}
		if(aposContext == null) {
			aposContext =  RoboGuiceUtil.getInjectObject(AposContext.class,context );
		}
	}
	
	public static void asynLog(String opCode,String opTraceNO,
			Map<String, String> opDesc) {
		
		final  String opCodeIn = opCode;
		final  String opDescin = opTraceNO;
		final  Map<String, String> opDescIn = opDesc;

		Thread thread = new Thread(new Runnable() {
			public void run() {
				AposOperationLog.log(opCodeIn, opDescin,opDescIn);
			}
		});
		thread.start(); 
	}

	private static void log(String opCode,String opTraceNO,
			Map<String, String> opDesc) {
		try {
			if(StringUtil.isBlank(deviceId)) {
				deviceId = (String) aposContext.getAppConfig()
						.getAttribute(ConfigAttrNames.DEVICE_ID);
			}
			
			if(opDesc == null) {
				opDesc = new HashMap<String, String>();
			}
			if(StringUtil.isNotBlank(opTraceNO)) {
				opDesc.put(OperationDataKeys.OPKEYS_OP_TRACENO, opTraceNO);
			}
			opDesc.put(OperationDataKeys.OPKEYS_SUBDATE,StringUtil.format("yyyy-MM-dd HH:mm:ss.SSS", new Date()));
			
			Log.d(TAG, opCode+":"+opTraceNO+":" +opDesc.toString() );

			terminalStatsService.submitOpLog(deviceId, opCode, opDesc);
		}catch(Exception ex) {
			Log.e(TAG, "log error", ex);
		}
	
	}
	
	
	
}
