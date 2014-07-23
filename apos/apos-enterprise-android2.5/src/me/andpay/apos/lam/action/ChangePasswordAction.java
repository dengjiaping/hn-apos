package me.andpay.apos.lam.action;

import me.andpay.ac.term.api.auth.UserAuthService;
import me.andpay.apos.R;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.lam.callback.ChangePasswordCallback;
import me.andpay.apos.lam.form.ChangePasswordForm;
import me.andpay.apos.lam.form.FirstChangePasswordForm;
import me.andpay.apos.lam.form.LoginUserForm;
import me.andpay.ti.base.AppBizException;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate.TRANSTYPE;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

@ActionMapping(domain = "/lam/changepwd.action")
@FieldErrorMsgTranslate(transType = TRANSTYPE.RESOURCE, resouceInfo = "properties/changePwdErrorMsg.properties")
public class ChangePasswordAction extends SessionKeepAction {

	private UserAuthService userAuthService;

	@Inject
	private Application application;

	public ModelAndView changePassword(ActionRequest request) {

		ChangePasswordForm loginUserForm = (ChangePasswordForm) request
				.getParameterValue("changePasswordForm");

		ChangePasswordCallback callback = (ChangePasswordCallback) request
				.getHandler();

		changePwd(loginUserForm.getNewPassword(),
				loginUserForm.getOldPasswod(), callback);
		return null;
	}

	public ModelAndView firstChangePassword(ActionRequest request) {

		FirstChangePasswordForm loginUserForm = (FirstChangePasswordForm) request
				.getParameterValue("firstChangePasswordForm");

		ChangePasswordCallback callback = (ChangePasswordCallback) request
				.getHandler();
		changePwd(loginUserForm.getNewPassword(),
				loginUserForm.getOldPasswod(), callback);

		return null;

	}

	private void changePwd(String newPwd, String oldPwd,
			ChangePasswordCallback callback) {
		try {
			String enPassword = userAuthService.changePassword(oldPwd, newPwd);

			TiContext tiConfig = getConfig();
			TiContext tiContext = getContext();

			boolean autoFlag = (Boolean) tiContext
					.getAttribute(RuntimeAttrNames.HIS_AUTOLOGIN_FLAG);
			if (autoFlag) {
				tiConfig.setAttribute(ConfigAttrNames.LOGIN_HIS_PASSWORD,
						enPassword);
			}
			tiContext.setAttribute(RuntimeAttrNames.LOGIN_CURRENT_PASSWORD,
					enPassword);
			callback.changeSuccess();
		} catch (AppBizException e) {
			Log.e(this.getClass().getName(), "changePassword error", e);
			callback.changeFaild(e.getLocalizedMessage());
		} catch (Throwable ex) {
			Log.e(this.getClass().getName(), "system error", ex);
			callback.changeFaild(ErrorMsgUtil.parseError(application, ex));
		}

	}

	public TiContext getContext() {
		TiApplication tiApplication = (TiApplication) application;
		TiContext tiContext = tiApplication.getContextProvider().provider(
				TiContext.CONTEXT_SCOPE_APPLICATION);
		return tiContext;
	}

	public TiContext getConfig() {
		TiApplication tiApplication = (TiApplication) application;
		TiContext tiConfig = tiApplication.getContextProvider().provider(
				TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);
		return tiConfig;
	}

	/**
	 * 验证用户名和密码
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView checkPassWord(ActionRequest request) {
		LoginUserForm loginUserForm = (LoginUserForm) request
				.getParameterValue("loginUserForm");
		boolean result = false;
		String msg = "";
		ModelAndView modelView = new ModelAndView();
		try {
			result = userAuthService.checkPassword(loginUserForm.getPassword());
			if (!result) {
				msg = ResourceUtil.getString(application,
						R.string.trm_txn_dialog_pwd_error_str);
			}
		} catch (AppBizException e) {
			Log.e(this.getClass().getName(), "checkPassword error", e);
			msg = e.getLocalizedMessage();
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "system error", e);
			msg = ErrorMsgUtil.parseError(application, e);
		}
		modelView.addModelValue("errorMsg", msg);
		modelView.addModelValue("checkResult", result);
		return modelView;

	}
}
