package me.andpay.apos.tam.action;

import me.andpay.ac.consts.GeoCooTypes;
import me.andpay.ac.term.api.pas.Coupon;
import me.andpay.ac.term.api.pas.CouponService;
import me.andpay.ac.term.api.pas.GetCouponRequest;
import me.andpay.ac.term.api.pas.RedeemCouponRequest;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.common.service.model.TiLocation;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.apos.tam.callback.GetCouponCallback;
import me.andpay.apos.tam.callback.RedeemCouponCaillback;
import me.andpay.ti.base.AppBizException;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.context.TiContext;
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

@ActionMapping(domain = CouponAction.DOMAIN_NAME)
public class CouponAction extends SessionKeepAction {

	public final static String DOMAIN_NAME = "/tam/coupon.action";

	public final static String GET_COUPON = "getCoupon";
	public final static String REDEEM_COUPON = "redeemCoupon";

	private CouponService couponService;

	@Inject
	Application application;

	@Inject
	private LocationService locationService;

	public void getCoupon(ActionRequest request) {

		GetCouponCallback caCouponCallback = (GetCouponCallback) request
				.getHandler();
		try {

			String couponInfo = (String) request
					.getParameterValue("couponInfo");
			GetCouponRequest couponRequest = new GetCouponRequest();
			couponRequest.setCode2d(couponInfo);
			Coupon coupon = couponService.getCoupon(couponRequest);
			caCouponCallback.getCouponSuccess(coupon);
		} catch (AppBizException e) {
			Log.e(this.getClass().getName(), "get coupon error!", e);
			caCouponCallback.getCouponFaild(e.getLocalizedMessage());
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "get coupon error!", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				caCouponCallback.getCouponNetworkError(ErrorMsgUtil.parseError(
						application, ex));
				return;
			}
			caCouponCallback.getCouponFaild("获取信息失败。");
		}
	}

	public void redeemCoupon(ActionRequest request) {

		RedeemCouponCaillback redeemCouponCaillback = (RedeemCouponCaillback) request
				.getHandler();
		try {

			String couponInfo = (String) request
					.getParameterValue("couponInfo");
			RedeemCouponRequest redeemCouponRequest = new RedeemCouponRequest();
			redeemCouponRequest.setCode2d(couponInfo);

			// 设置位置信息
			if (locationService.hasLocation()) {

				TiLocation tiLocation = locationService.getLocation();
				redeemCouponRequest.setLatitude(tiLocation.getLatitude());
				redeemCouponRequest.setLongitude(tiLocation.getLongitude());
				if (tiLocation.getSpecLatitude() != 0) {
					redeemCouponRequest.setSpecLatitude(tiLocation
							.getSpecLatitude());
					redeemCouponRequest.setSpecLongitude(tiLocation
							.getSpecLongitude());
					redeemCouponRequest.setSpecCordType(GeoCooTypes.BD_09);
					redeemCouponRequest.setTxnLocation(tiLocation.getAddress());
				}
			}

			int count = couponService.redeemCoupon(redeemCouponRequest);

			request.getContext(TiContext.CONTEXT_SCOPE_APPLICATION)
					.setAttribute(RuntimeAttrNames.FRESH_COUPON_FLAG,
							RuntimeAttrNames.FRESH_COUPON_FLAG);
			redeemCouponCaillback.redeemSuccess(count);

		} catch (AppBizException e) {
			Log.e(this.getClass().getName(), "get coupon error!", e);
			redeemCouponCaillback.redeemFail(e.getLocalizedMessage());
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "get coupon error!", ex);
			if (ErrorMsgUtil.isNetworkError(ex)) {
				redeemCouponCaillback.redeemNetwork(ErrorMsgUtil.parseError(
						application, ex));
				return;
			}
			redeemCouponCaillback.redeemFail("兑换失败。");
		}
	}

	public void queryCouponList(ActionRequest request) {

	}

}
