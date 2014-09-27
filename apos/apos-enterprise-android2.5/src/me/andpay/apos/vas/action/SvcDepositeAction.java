package me.andpay.apos.vas.action;

import java.math.BigDecimal;

import me.andpay.ac.term.api.shop.GetSvcDepositCtrlsRequest;
import me.andpay.ac.term.api.shop.GetSvcDepositCtrlsResponse;
import me.andpay.ac.term.api.shop.SvcDepositRequest;
import me.andpay.ac.term.api.shop.SvcDepositService;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.apos.dao.PurchaseOrderInfoDao;
import me.andpay.apos.vas.callback.SvcDepositeCallback;
import me.andpay.apos.vas.callback.SvcValidateCallback;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.ti.base.AppBizException;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

/**
 * 预付费卡充值
 * 
 * @author tinyliu
 * 
 */
@ActionMapping(domain = "/vas/svcDeposite.action")
public class SvcDepositeAction extends SessionKeepAction {

	private String serviceEntryMode = "012";

	private SvcDepositService svcDepositService;

	@Inject
	private Application application;

	@Inject
	public PurchaseOrderInfoDao purchaseOrderInfoDao;

	public ModelAndView deposite(ActionRequest request) {
		SvcDepositeCallback callback = (SvcDepositeCallback) request
				.getHandler();
		SvcDepositeContext dContext = (SvcDepositeContext) request
				.getParameterValue("depositeContext");

		SvcDepositRequest dRequest = new SvcDepositRequest();
		dRequest.setOrderId(dContext.getPurchaseOrderId());

		try {
			BigDecimal balance = svcDepositService.deposit(dRequest);
			purchaseOrderInfoDao.updatePayTxnInfo2Fulfill(dContext
					.getPurchaseOrderId());
			callback.depositeSucc(balance);
		} catch (AppBizException ex) {
			Log.e(this.getClass().getName(), "check order error", ex);
			callback.depositeFail(ex.getLocalizedMessage());
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "check order error", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				callback.depositeFail(ErrorMsgUtil.parseError(application, ex));
				return null;
			}
			callback.depositeFail(null);
		}
		return null;
	}

	public ModelAndView validateCard(ActionRequest request) {
		SvcValidateCallback callback = (SvcValidateCallback) request
				.getHandler();
		String cardNo = (String) request.getParameterValue("cardNo");
		GetSvcDepositCtrlsRequest sRequest = new GetSvcDepositCtrlsRequest();
		sRequest.setCardNo(cardNo);
		sRequest.setServiceEntryMode(serviceEntryMode);

		try {
			GetSvcDepositCtrlsResponse response = svcDepositService
					.getDepositCtrls(sRequest);
			callback.validateSucc(response);
		} catch (AppBizException ex) {
			Log.e(this.getClass().getName(), "check order error", ex);
			callback.validateFailed(ex.getLocalizedMessage());
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "check order error", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				callback.validateFailed(ErrorMsgUtil
						.parseError(application, ex));
				return null;
			}
			callback.validateFailed(null);
		}
		return null;
	}
}
