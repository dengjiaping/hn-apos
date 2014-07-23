package me.andpay.apos.common.action;

import me.andpay.ac.term.api.lbs.LocateRequest;
import me.andpay.ac.term.api.lbs.LocateResult;
import me.andpay.ac.term.api.lbs.LocationService;
import me.andpay.apos.common.callback.LbsCallback;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.action.MultiAction;

@ActionMapping(domain = LbsAction.DOMAIN_NAME)
public class LbsAction extends MultiAction {

	private LocationService locationService;

	public final static String DOMAIN_NAME = "/tam/lbs.action";
	public final static String ACTION_NAME_QUERYLBS = "queryLbsInfo";

	// 数据
	public final static String LBS_INFO_REQUEST = "lbs_info_re";

	/**
	 * 获取lbs信息
	 * 
	 * @param request
	 */
	public void queryLbsInfo(ActionRequest request) {

		
		LbsCallback callback = (LbsCallback) request.getHandler();
		try {
			
			LocateRequest lbsRequest = (LocateRequest) request
					.getParameterValue(LBS_INFO_REQUEST);
			LocateResult result = locationService.locate(lbsRequest);
			callback.dealResult(result);
		} catch (Throwable e) {
			//处理所有异常
			callback.excptionHandle();
		}

	}
}
