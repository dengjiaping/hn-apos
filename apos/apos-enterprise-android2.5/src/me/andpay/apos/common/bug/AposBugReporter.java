package me.andpay.apos.common.bug;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.ApplicationCodes;
import me.andpay.ac.consts.OSCodes;
import me.andpay.ac.term.api.auth.ReservedEnvPropertyNames;
import me.andpay.ac.term.api.ga.BugReportService;
import me.andpay.ti.util.JSONObject;
import me.andpay.timobileframework.bugsense.ThrowableRecorder;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;

import com.google.inject.Inject;

public class AposBugReporter implements ThrowableReporter {

	private BugReportService bugReportService;

	@Inject
	private ThrowableRecorder recorder;

	private String genExceptionString(Throwable ex) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		ex.printStackTrace(printWriter);
		return result.toString();
	}

	private Map<String, String> genDevEnv() {
		Map<String, String> devEnv = new HashMap<String, String>();
		devEnv.put(ReservedEnvPropertyNames.APP_VERSION_CODE,
				TiAndroidRuntimeInfo.getAppVersion());
		devEnv.put(ReservedEnvPropertyNames.DEVICE_ID,
				TiAndroidRuntimeInfo.getDeviceId());
		devEnv.put(ReservedEnvPropertyNames.APP_CODE, ApplicationCodes.APOS);
		devEnv.put(ReservedEnvPropertyNames.IMEI,
				TiAndroidRuntimeInfo.getIMEI());
		devEnv.put(ReservedEnvPropertyNames.OS_CODE,
				OSCodes.ANDROID);
		devEnv.put(ReservedEnvPropertyNames.OS_VERSION,
				TiAndroidRuntimeInfo.getOsVersion());
		devEnv.put(ReservedEnvPropertyNames.MAC,
				TiAndroidRuntimeInfo.getWifiMac());
		devEnv.put("sdk_version", TiAndroidRuntimeInfo.getSdkVersion());
		devEnv.put("phone_model", TiAndroidRuntimeInfo.getMobileModel());
		return devEnv;
	}

	public void submitError(Throwable ex) {
		try {
			bugReportService.reportException(ApplicationCodes.APOS,genExceptionString(ex),
					genDevEnv());
		} catch (Exception e) {
			recorder.recordThrowable(ex);
		}
	}

	public void submitError(Throwable ex, Object data) {

		try {
			Object jsonObj = JSONObject.wrap(data);
			String exString = genExceptionString(ex);
			bugReportService.reportException(ApplicationCodes.APOS,"excepiton:" + exString + " data:"
					+ jsonObj.toString(), genDevEnv());
		} catch (Exception e) {
			recorder.recordThrowable(ex);
		}

	}

	public void submitError(String errorStr, Map<String, String> devEnv) {

		try {
			bugReportService.reportException(ApplicationCodes.APOS,errorStr, devEnv);
		} catch (Exception ex) {
			recorder.recordThrowable(ex);
		}
	}
}
