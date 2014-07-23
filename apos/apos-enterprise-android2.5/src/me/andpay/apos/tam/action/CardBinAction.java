package me.andpay.apos.tam.action;

import me.andpay.ac.term.api.txn.CardBinService;
import me.andpay.ac.term.api.txn.ParseBinRequest;
import me.andpay.ac.term.api.txn.ParseBinResponse;
import me.andpay.apos.R;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tam.callback.ParseBinCallback;
import me.andpay.apos.tam.form.CardBinRequest;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.util.BeanUtils;
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

@ActionMapping(domain="/tam/cardbin.action")
public class CardBinAction extends SessionKeepAction{

	public final static String DOMAIN_NAME = "/tam/cardbin.action";
	public final static String ACTION_NAME_PARSE = "parseBin";
	/**
	 * 卡号解析信息
	 */
	public static final String CARDBIN_INFO = "cardBin_info";
	public static final String CARDBIN_RESP = "cardBin_resp";

	@Inject
	public Application application;

	private CardBinService cardBindService;

	public ModelAndView parseBin(ActionRequest request) {
		CardBinRequest cardBinRequest = (CardBinRequest) request
				.getParameterValue(CARDBIN_INFO);
		ParseBinRequest parseRequest = BeanUtils.copyProperties(
				ParseBinRequest.class, cardBinRequest);

		ParseBinCallback callback = (ParseBinCallback) request.getHandler();

		ParseBinResponse parseResponse = new ParseBinResponse();

		try {
			parseResponse = cardBindService.parseCardBin(parseRequest);
			if(StringUtil.isBlank(parseResponse.getCardAssoc())) {
				parseResponse.setCardAssoc("UP");
			}
			callback.dealSuccess(parseResponse);
			
		}catch (Throwable ex) {
			Log.e(this.getClass().getName(), "system error", ex);
			callback.dealFailed(ErrorMsgUtil.parseError(application, ex,
					ResourceUtil.getString(application,
							R.string.tam_card_parse_error_str)));
		}

		return null;
	}

}
