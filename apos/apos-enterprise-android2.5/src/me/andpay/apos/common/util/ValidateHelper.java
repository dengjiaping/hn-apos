package me.andpay.apos.common.util;

import java.util.ArrayList;
import java.util.List;

import me.andpay.apos.cmview.PromptDialog;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.validation.ValidateErrorInfo;
import android.app.Activity;
import android.view.View;

public class ValidateHelper {

	public static boolean validate(Activity activity, FormBean formBean,
			List<Integer> filterViewIds) {

		List<ValidateErrorInfo> errors = filterError(formBean.getErrors(),
				filterViewIds);
		if (errors != null && !errors.isEmpty()) {
			ValidateErrorInfo error = errors.get(0);
			showErrorMsg(activity, error);
			return true;
		}

		return false;

	}

	private static List<ValidateErrorInfo> filterError(
			ValidateErrorInfo[] errors, List<Integer> filterViewIds) {

		if (filterViewIds == null) {
			filterViewIds = new ArrayList<Integer>();
		}
		List<ValidateErrorInfo> tempErrors = new ArrayList<ValidateErrorInfo>();
		if (errors != null && errors.length > 0) {
			for (ValidateErrorInfo error : errors) {
				if (!filterViewIds.contains(error.getParamId())) {
					tempErrors.add(error);
				}
			}
		}
		return tempErrors;

	}

	public static void showErrorMsg(Activity activity, ValidateErrorInfo error) {
		PromptDialog dialog = new PromptDialog(activity, null,
				error.getErrorDescription());
		View view = activity.findViewById(error.getParamId());
		view.requestFocus();
		dialog.show();
	}

}
