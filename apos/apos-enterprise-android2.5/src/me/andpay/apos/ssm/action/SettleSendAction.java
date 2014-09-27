package me.andpay.apos.ssm.action;

import java.util.ArrayList;
import java.util.List;

import me.andpay.ac.term.api.base.DeliverDest;
import me.andpay.ac.term.api.txn.TxnBatchService;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.ssm.form.SendForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;

@ActionMapping(domain = "/ssm/send.action")
public class SettleSendAction extends SessionKeepAction {

	TxnBatchService txnBatchService;

	/**
	 * 发送结帐信息到邮箱
	 * 
	 * @param request
	 * @return
	 * @throws RuntimeException
	 */
	public ModelAndView sendSettledInfo(ActionRequest request)
			throws RuntimeException {
		ModelAndView mv = new ModelAndView();
		SendForm sendForm = (SendForm) request.getParameterValue("sendForm");

		List<DeliverDest> dests = new ArrayList<DeliverDest>();
		if (!StringUtil.isEmpty(sendForm.getEmail())) {
			DeliverDest dest = new DeliverDest();
			dest.setDeliverType(DeliverDest.TYPE_EMAIL);
			dest.setDestInfo(sendForm.getEmail());
			dests.add(dest);
		}
		if (!StringUtil.isEmpty(sendForm.getPhone())) {
			DeliverDest dest = new DeliverDest();
			dest.setDeliverType(DeliverDest.TYPE_SMS);
			dest.setDestInfo(sendForm.getPhone());
			dests.add(dest);
		}
		txnBatchService.sendBatch(dests, sendForm.getBatchId());
		return mv.addModelValue("sendFlag", true);
	}
}
