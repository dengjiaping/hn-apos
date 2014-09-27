package me.andpay.apos.vas.action;

import me.andpay.ac.term.api.shop.OpenSvcRequest;
import me.andpay.ac.term.api.shop.SendSvcEcardRequest;
import me.andpay.ac.term.api.shop.SvcLifecycleService;
import me.andpay.ac.term.api.shop.ValidateSvcBlankCardRequest;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.apos.dao.PurchaseOrderInfoDao;
import me.andpay.apos.vas.callback.OpenCardCallback;
import me.andpay.apos.vas.callback.SendEcardCallback;
import me.andpay.apos.vas.callback.ValidateBlankCardCallback;
import me.andpay.apos.vas.form.OpenCardForm;
import me.andpay.ti.base.AppBizException;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

@ActionMapping(domain = OpenCardAction.DOMAIN_NAME)
public class OpenCardAction extends SessionKeepAction {

	public static final String DOMAIN_NAME = "/vas/opencard.action";
	public static final String OPEN_CARD = "openCard";
	public static final String SEND_SVC_ECARD = "sendSvcEcard";
	public static final String VALIDATE_BLANK_CARD = "validateBlankCard";

	public SvcLifecycleService svcLifecycleService;

	@Inject
	public PurchaseOrderInfoDao purchaseOrderInfoDao;

	@Inject
	private Application application;

	public ModelAndView openCard(ActionRequest request) {

		OpenCardCallback openCardCallback = (OpenCardCallback) request
				.getHandler();

		OpenSvcRequest openReq = new OpenSvcRequest();

		OpenCardForm oprCardForm = (OpenCardForm) request
				.getParameterValue("OpenCardForm");
		openReq.setStartBlankPartCardNo(oprCardForm.getStartBlankPartCardNo());
		openReq.setEndBlankPartCardNo(oprCardForm.getEndBlankPartCardNo());
		openReq.setHolderName(oprCardForm.getHolderName());
		openReq.setIdNo(oprCardForm.getIdNo());
		openReq.setIdType(oprCardForm.getIdType());
		openReq.setMobileNo(oprCardForm.getPhoneNo());
		openReq.setOrderId(oprCardForm.getOrderId());

		try {
			svcLifecycleService.openSvc(openReq);
			purchaseOrderInfoDao.updatePayTxnInfo2Fulfill(oprCardForm
					.getOrderId());

			openCardCallback.openCardSuccess();
		} catch (AppBizException ex) {
			Log.e(this.getClass().getName(), "check order error", ex);
			openCardCallback.openCardFaild(ex.getLocalizedMessage());
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "check order error", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				openCardCallback.openCardFaild(ErrorMsgUtil.parseError(
						application, ex));
				return null;
			}
			openCardCallback.openCardFaild("开卡失败！");
		}

		return null;
	}

	public ModelAndView sendSvcEcard(ActionRequest request) {

		Long orderId = (Long) request.getParameterValue("orderId");
		SendEcardCallback sendEcardCallback = (SendEcardCallback) request
				.getHandler();

		SendSvcEcardRequest reEcardRequest = new SendSvcEcardRequest();
		reEcardRequest.setOrderId(orderId);
		try {
			svcLifecycleService.sendSvcEcard(reEcardRequest);
			sendEcardCallback.sendSuccess();
		} catch (AppBizException ex) {
			Log.e(this.getClass().getName(), "check order error", ex);
			sendEcardCallback.sendFaild(ex.getLocalizedMessage());
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "check order error", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				sendEcardCallback.netWorkError();
				return null;
			}
			sendEcardCallback.sendFaild("发送失败");
		}
		return null;
	}

	/**
	 * 校验空白卡
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView validateBlankCard(ActionRequest request) {

		ValidateBlankCardCallback cheBlankCardCallback = (ValidateBlankCardCallback) request
				.getHandler();
		try {
			OpenCardForm openCardForm = (OpenCardForm) request
					.getParameterValue("OpenCardForm");
			ValidateSvcBlankCardRequest req = new ValidateSvcBlankCardRequest();
			req.setStartBlankPartCardNo(openCardForm.getStartBlankPartCardNo());
			req.setOrderId(openCardForm.getOrderId());
			req.setEndBlankPartCardNo(openCardForm.getEndBlankPartCardNo());

			svcLifecycleService.validateSvcBlankCard(req);
			cheBlankCardCallback.validateSuccess();
		} catch (AppBizException ex) {
			Log.e(this.getClass().getName(), "validate card error", ex);
			cheBlankCardCallback.validateFaild(ex.getLocalizedMessage());
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "validate card error", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				cheBlankCardCallback.netWorkError();
				return null;
			}
			cheBlankCardCallback.validateFaild("发送失败");
		}

		return null;

	}
}
