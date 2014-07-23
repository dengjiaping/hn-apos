package me.andpay.apos.common.util;

import me.andpay.apos.R;
import me.andpay.ti.lnk.transport.websock.common.NetworkErrorException;
import me.andpay.ti.lnk.transport.websock.common.WebSockTimeoutException;
import android.content.Context;

public class ErrorMsgUtil {

	public static String parseError(Context context, Throwable ex) {
		return parseError(context, ex, null);
	}

	public static String parseError(Context context, Throwable ex,
			String defaultStr) {

		if (ErrorMsgUtil.isNetworkError(ex)) {
			return ResourceUtil.getString(context,
					R.string.com_please_check_network_str);
		} else {

			if (defaultStr != null) {
				return defaultStr;
			}
			return ResourceUtil.getString(context, R.string.tam_syserror_str);

		}

	}

	public static boolean isNetworkError(Throwable ex) {
		//Throwable originalThrowable = ErrorMsgUtil.getOriginalThrowable(ex);
		if (ex instanceof NetworkErrorException
				|| ex instanceof WebSockTimeoutException) {
			return true;
		}
		return false;
	}

//	private static Throwable getOriginalThrowable(Throwable ex) {
//		if (ex.getCause() == null
//				|| ex.getCause().getClass().getName()
//						.equalsIgnoreCase(ex.getClass().getName()))
//			return ex;
//		else
//			return getOriginalThrowable(ex.getCause());
//
//	}

}
