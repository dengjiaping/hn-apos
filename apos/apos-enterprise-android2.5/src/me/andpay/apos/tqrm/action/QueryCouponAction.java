package me.andpay.apos.tqrm.action;

import java.util.List;

import me.andpay.ac.term.api.pas.CouponRedeemList;
import me.andpay.ac.term.api.pas.CouponService;
import me.andpay.ac.term.api.pas.QueryCouponRedeemListCond;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.tqrm.form.QueryCouponCondForm;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.util.BeanUtils;

@ActionMapping(domain = QueryCouponAction.DOMAIN_NAME)
public class QueryCouponAction extends SessionKeepAction {

	public static final String DOMAIN_NAME = "/tqrm/queryCoupon.action";
	public static final String QUERY_COUPON = "queryCouponList";

	public CouponService couponService;

	// @Inject
	// public OrderInfoDao orderInfoDao;

	/**
	 * 查询订单
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView queryCouponList(ActionRequest request)
			throws RuntimeException {
		QueryCouponCondForm condForm = (QueryCouponCondForm) request
				.getParameterValue("couponQueryForm");
		QueryCouponRedeemListCond queryCond = new QueryCouponRedeemListCond();
		BeanUtils.copyProperties(condForm, queryCond);
		List<CouponRedeemList> couponResult = couponService
				.queryCouponRedeemLists(queryCond, 0, 10);
		ModelAndView mav = new ModelAndView();
		mav.addModelValue("couponResult", couponResult);
		mav.addModelValue("couponQueryForm", condForm);
		return mav;

	}
}
