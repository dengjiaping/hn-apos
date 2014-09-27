package me.andpay.apos.lam.action;

import java.util.Set;

import me.andpay.ac.term.api.sec.GenMsrKeyResponse;
import me.andpay.ac.term.api.sec.TermSecurityService;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.ti.base.AppBizException;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.action.MultiAction;

import com.crashlytics.android.Crashlytics;

@ActionMapping(domain = GenMsrKeysAction.DOMAIN_NAME)
public class GenMsrKeysAction extends MultiAction {

	public final static String DOMAIN_NAME = "/lam/genMsrKeys.action";

	public final static String GEN_MSRKEYS = "genWorkkeys";

	private TermSecurityService termSecurityService;

	public ModelAndView genWorkkeys(ActionRequest request) {

		String ksn = (String) request.getParameterValue("ksn");
		Set<String> keyTypes = (Set<String>) request
				.getParameterValue("keyTypes");
		ModelAndView modelAndView = new ModelAndView();

		try {
			GenMsrKeyResponse genMsrKeyResponse = termSecurityService
					.genMsrKeys(ksn,
							keyTypes.toArray(new String[keyTypes.size()]));

			modelAndView.addModelValue("mrsKeys",
					genMsrKeyResponse.getMsrKeys());
			return modelAndView;
		} catch (AppBizException e) {
			return modelAndView;
		} catch (Exception ex) {
			if (!ErrorMsgUtil.isNetworkError(ex)) {
				modelAndView.addModelValue("errorMsg", "获取密钥失败,请联系和付。");
				Crashlytics.log("get msrKey error ksn=" + ksn);
				Crashlytics.logException(ex);
			} else {
				modelAndView.addModelValue("errorMsg", "网络异常，请稍后再试。");
			}
			return modelAndView;
		}

	}
}
