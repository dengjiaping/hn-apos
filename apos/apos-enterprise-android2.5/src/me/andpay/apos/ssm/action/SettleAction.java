package me.andpay.apos.ssm.action;

import me.andpay.ac.term.api.txn.TxnBatch;
import me.andpay.ac.term.api.txn.TxnBatchService;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.ti.base.AppBizException;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import android.util.Log;

@ActionMapping(domain = "/ssm/settle.action")
public class SettleAction extends SessionKeepAction {

	private TxnBatchService batchService;

	public ModelAndView settle(ActionRequest request) throws RuntimeException {
		
		ModelAndView modelAndView =  new ModelAndView();
		TxnBatch batch;
		try {
			batch = batchService.batch();
			modelAndView.addModelValue("batch", batch);
			return modelAndView;
		} catch (AppBizException e) {
			Log.e(this.getClass().getName(), "batch error", e);
			modelAndView.addModelValue("errorMsg", e.getLocalizedMessage());
		}
		return modelAndView;
	}

}
