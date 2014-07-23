package me.andpay.apos.tcm.event;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.andpay.apos.R;
import me.andpay.apos.cmview.ToastTools;
import me.andpay.apos.tcm.activity.InfoChallengeActivity;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.IDCardUtil;
import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * @author David.zhang
 * 
 */

public class CheckChallengeInfoEventController extends AbstractEventController {
	private static final String PHONE_EXPRESSION = "^1\\d{10}$";
	private static final String CVV2_EXPRESSION = "^\\d{3}$";
	private static boolean isVerifiedPhone = false;
	private static boolean isVerifiedIdentity = false;
	private static boolean isVerifiedCvv2 = false;

	public void onFocusChange(Activity activity, FormBean formBean, View view,
			boolean hasFocus) {
		if (hasFocus == false) {
			InfoChallengeActivity authActivity = (InfoChallengeActivity) activity;
			switch (view.getId()) {
			case R.id.tcm_infochallenge_phone_edit:
				validatePhone(authActivity);
				break;
			case R.id.tcm_infochallenge_identity_edit:
				validateIdentity(authActivity);
				break;
			case R.id.tcm_infochallenge_cvv2_edit:
				validateCvv2(authActivity);
				break;
			default:
				break;
			}
		} else {
			resetEditText((EditText) view);
		}
	}

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		InfoChallengeActivity authActivity = (InfoChallengeActivity) activity;
		if (authActivity.cvv2EditText.length() == 3) {
			validateCvv2(authActivity);
			authActivity.sureButton.requestFocus();
			authActivity.sureButton.requestFocusFromTouch();
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}

	private void validatePhone(InfoChallengeActivity authActivity) {
		EditText phoneEditText = authActivity.phoneEditText;
		String phone = phoneEditText.getText().toString();
		if (validate(PHONE_EXPRESSION, phone)) {
			isVerifiedIdentity = true;
			refreshSureButtonStatus(authActivity);
		} else {
			isVerifiedIdentity = false;
			String error = "您的手机号格式有误，请重新输入。";
			showErrorIcon(authActivity, phoneEditText, error);
		}
	}

	private void validateIdentity(InfoChallengeActivity authActivity) {
		EditText identityEditText = authActivity.identityEditText;
		String identityNum = identityEditText.getText().toString();
		if (IDCardUtil.validate(identityNum)) {
			isVerifiedPhone = true;
			refreshSureButtonStatus(authActivity);
		} else {
			isVerifiedPhone = false;
			String error = "您的身份证号格式有误，请重新输入。";
			showErrorIcon(authActivity, identityEditText, error);
		}
	}

	private void validateCvv2(InfoChallengeActivity authActivity) {
		EditText cvv2EditText = authActivity.cvv2EditText;
		String cvv2 = cvv2EditText.getText().toString();
		if (validate(CVV2_EXPRESSION, cvv2)) {
			isVerifiedCvv2 = true;
			refreshSureButtonStatus(authActivity);
		} else {
			isVerifiedCvv2 = false;
			String error = "您的CVV2码格式有误，请重新输入。";
			showErrorIcon(authActivity, cvv2EditText, error);
		}
	}

	private boolean validate(String expression, String value) {
		if (value == null || StringUtil.isEmpty(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(value.trim());
		return matcher.find();
	}

	private void refreshSureButtonStatus(InfoChallengeActivity authActivity) {
		if (isVerifiedPhone && isVerifiedIdentity && isVerifiedCvv2) {
			authActivity.sureButton.setEnabled(true);
			isVerifiedPhone = false;
			isVerifiedIdentity = false;
			isVerifiedCvv2 = false;
		}
	}

	private void showErrorIcon(InfoChallengeActivity authActivity,
			EditText editText, String error) {
		authActivity.sureButton.setEnabled(false);
		editText.setTextColor(Color.parseColor("#e34d39"));
		ToastTools.toastMsg(authActivity, error,
				ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	private void resetEditText(EditText editText) {
		editText.setTextColor(Color.parseColor("#444444"));
	}
}
