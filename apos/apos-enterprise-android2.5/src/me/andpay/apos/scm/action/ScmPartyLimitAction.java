package me.andpay.apos.scm.action;

import me.andpay.ac.term.api.rcs.PartyTxnAmtQuotaInfo;
import me.andpay.ac.term.api.rcs.RcsService;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.apos.scm.callback.ScmPartyLimitCallBack;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import android.app.Application;

import com.google.inject.Inject;

@ActionMapping(domain = "/scm/partyLimit.action")
public class ScmPartyLimitAction extends SessionKeepAction {

	public final static String QUERY_PARTY_LIMIT = "queryPartyLimit";
	public final static String DOMAIN_NAME = "/scm/partyLimit.action";

	private RcsService rcsService;

	@Inject
	private Application application;

	public ModelAndView queryPartyLimit(ActionRequest request) {

		ScmPartyLimitCallBack scmPartyLimitCallBack = (ScmPartyLimitCallBack) request
				.getHandler();

		try {
			PartyTxnAmtQuotaInfo partyLimit = rcsService
					.queryPartyTxnAmtQuota();
			scmPartyLimitCallBack.querySuccess(partyLimit);
		} catch (Exception ex) {
			scmPartyLimitCallBack.queryError(ErrorMsgUtil.parseError(
					application, ex));
		}

		return null;
	}
}
