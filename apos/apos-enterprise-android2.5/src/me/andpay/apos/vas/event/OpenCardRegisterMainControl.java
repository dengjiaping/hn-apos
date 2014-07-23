package me.andpay.apos.vas.event;

import me.andpay.ac.consts.ShopProductTypes;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.util.ValidateHelper;
import me.andpay.apos.vas.action.OpenCardAction;
import me.andpay.apos.vas.activity.OpenCardRegisterMainActivity;
import me.andpay.apos.vas.callback.ValidateBlankCardCallbackImpl;
import me.andpay.apos.vas.flow.model.OpenCardContext;
import me.andpay.apos.vas.form.OpenCardForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.validation.ValidateErrorInfo;
import android.app.Activity;
import android.view.View;

public class OpenCardRegisterMainControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		submit(activity);
	}

	public void submit(Activity activity) {
		OpenCardRegisterMainActivity openCardRegisterMainActivity = (OpenCardRegisterMainActivity) activity;

		ValidateErrorInfo validateErrorInfo = validataData(openCardRegisterMainActivity);
		if (validateErrorInfo != null) {
			ValidateHelper.showErrorMsg(activity, validateErrorInfo);
			return;
		}
		
		OpenCardContext openCardContext = openCardRegisterMainActivity.openCardContext;
		openCardContext
				.setStartBlankPartCardNo(openCardRegisterMainActivity.startBlankPartCardNo
						.getWidgetValue().toString());
		openCardContext
				.setEndBlankPartCardNo(openCardRegisterMainActivity.endBlankPartCardNo
						.getWidgetValue().toString());

		OpenCardForm openCardForm = new OpenCardForm();
		openCardForm.setOrderId(openCardRegisterMainActivity.openCardContext.getOrderId());
		openCardForm.setEndBlankPartCardNo(openCardContext
				.getEndBlankPartCardNo());
		openCardForm.setStartBlankPartCardNo(openCardContext
				.getStartBlankPartCardNo());

		EventRequest request = this.generateSubmitRequest(activity);
		request.open(OpenCardAction.DOMAIN_NAME,
				OpenCardAction.VALIDATE_BLANK_CARD, Pattern.async);
		openCardRegisterMainActivity.diCommonDialog = new CommonDialog(activity, "处理中...");
		openCardRegisterMainActivity.diCommonDialog.show();
		request.callBack(new ValidateBlankCardCallbackImpl(openCardRegisterMainActivity));
		request.getSubmitData().put("OpenCardForm", openCardForm);
		request.submit();


	}

	public ValidateErrorInfo validataData(
			OpenCardRegisterMainActivity openCardRegisterMainActivity) {

		ValidateErrorInfo validateErrorInfo = new ValidateErrorInfo(null, null);
		String productType = openCardRegisterMainActivity.openCardContext
				.getProductType();
		int cardQuantity = openCardRegisterMainActivity.openCardContext.getCardQuantity();

		if (ShopProductTypes.PHYSICAL_SVC.equals(productType)) {
			if (cardQuantity == 1) {
				if (StringUtil
						.isBlank(openCardRegisterMainActivity.startBlankPartCardNo
								.getWidgetValue().toString())) {
					validateErrorInfo.setErrorDescription("请输入实物卡后八位。");
					validateErrorInfo
							.setParamId(openCardRegisterMainActivity.startBlankPartCardNo
									.getId());
					return validateErrorInfo;
				}
				if (!openCardRegisterMainActivity.startBlankPartCardNo
						.getWidgetValue()
						.toString()
						.equals(openCardRegisterMainActivity.endBlankPartCardNo
								.getWidgetValue().toString())) {
					validateErrorInfo.setErrorDescription("两次输入的实物卡后八位不同。");
					validateErrorInfo
							.setParamId(openCardRegisterMainActivity.startBlankPartCardNo
									.getId());
					return validateErrorInfo;
				}

			} else {

				if (StringUtil
						.isBlank(openCardRegisterMainActivity.startBlankPartCardNo
								.getWidgetValue().toString())) {
					validateErrorInfo.setErrorDescription("请输入第一张数卡后八位。");
					validateErrorInfo
							.setParamId(openCardRegisterMainActivity.startBlankPartCardNo
									.getId());
					return validateErrorInfo;
				}
				if (StringUtil
						.isBlank(openCardRegisterMainActivity.startBlankPartCardNo
								.getWidgetValue().toString())) {
					validateErrorInfo.setErrorDescription("请输入第二张数卡后八位。");
					validateErrorInfo
							.setParamId(openCardRegisterMainActivity.endBlankPartCardNo
									.getId());
					return validateErrorInfo;
				}

			}
		}
		return null;

	}

}
