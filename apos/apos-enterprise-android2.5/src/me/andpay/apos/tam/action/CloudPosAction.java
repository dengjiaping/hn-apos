package me.andpay.apos.tam.action;

import me.andpay.ac.term.api.txn.order.CloudOrderService;
import me.andpay.apos.tam.TamProvider;
import me.andpay.apos.tam.flow.model.TxnCancelFlag;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.action.MultiAction;

/**
 * 云pos操作类
 * 
 * @author tinyliu
 * 
 */
@ActionMapping(domain = TamProvider.TAM_DOMAIN_CLOUD)
public class CloudPosAction extends MultiAction {

	CloudOrderService cloudOrderService;

	/**
	 * 云订单撤销
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView cancelOrder(ActionRequest request) {
		String cloudOrderId = (String) request
				.getParameterValue(TamProvider.TAM_ACTION_PARAM_CLOUDORDERID);
		TxnCancelFlag flag = (TxnCancelFlag) request
				.getParameterValue(TamProvider.TAM_ACTION_PARAM_TXNCANCELFLAG);
		if (StringUtil.isEmpty(cloudOrderId) || flag.isCancelTxn()) {
			return null;
		}
		flag.cancelTxn();
		try {
			cloudOrderService.cancelCloudOrder(cloudOrderId);
		} catch (AppBizException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

}
