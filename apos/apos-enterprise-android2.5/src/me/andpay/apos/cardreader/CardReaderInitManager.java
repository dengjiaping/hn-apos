package me.andpay.apos.cardreader;

import me.andpay.ac.term.api.sec.MsrKeyTypes;
import me.andpay.timobileframework.mvc.context.TiContext;

import com.landicorp.android.mpay.commdriver.util.StringUtil;

public class CardReaderInitManager {

	/**
	 * IC卡应用参数
	 */
	public static final String ININ_KEY_IC_PARAMS = "ic_param";

	/**
	 * IC卡公钥
	 */
	public static final String ININ_KEY_IC_PUBLIC_KEY = "ic_public_key";

	public static boolean isCardReaderInit(TiContext tiConfig,
			String identifier, String paramType) {

		if (identifier == null) {
			return false;
		}
		Object data = tiConfig.getAttribute(identifier + "_" + paramType);
		if (data == null || StringUtil.isEmpty(data.toString())) {
			return false;
		}

		return true;
	}

	public static void setParamInit(TiContext tiConfig, String identifier,
			String paramType) {
		tiConfig.setAttribute(identifier + "_" + paramType, "OK");
	}

	public static boolean checkAllKeyIsInit(TiContext tiConfig,
			String identifier) {

		return isCardReaderInit(tiConfig, identifier, MsrKeyTypes.DATA_KEY)
				&& isCardReaderInit(tiConfig, identifier,
						MsrKeyTypes.MASTER_KEY)
				&& isCardReaderInit(tiConfig, identifier, MsrKeyTypes.PIN_KEY)
				&& isCardReaderInit(tiConfig, identifier, MsrKeyTypes.MAC_KEY);
	}
}
