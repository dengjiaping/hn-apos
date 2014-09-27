package me.andpay.apos.trm.event;

import java.math.BigDecimal;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.lam.form.LoginUserForm;
import me.andpay.apos.tqm.activity.TxnDetailActivity;
import me.andpay.apos.trm.TrmProvider;
import me.andpay.apos.trm.flow.model.RefundInputContext;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class RefundPwdDialogSureClick implements OnClickListener {

	private Dialog pwdDialog = null;

	private EditText password = null;

	private String userid = null;

	private TiActivity activity = null;

	private CommonDialog dialog = null;

	public RefundPwdDialogSureClick(Dialog pwdDialog, TiActivity refActivity) {

		this.password = (EditText) pwdDialog
				.findViewById(R.id.trm_refund_dialog_pwd_ev);
		this.userid = ((TextView) pwdDialog
				.findViewById(R.id.trm_refund_dialog_userid_tv)).getText()
				.toString();
		this.dialog = new CommonDialog(refActivity, refActivity.getResources()
				.getString(R.string.com_progress_prompt_str));
		dialog.setCancelable(false);
		this.pwdDialog = pwdDialog;
		this.activity = refActivity;
	}

	public void onClick(View v) {
		String pwd = password.getText().toString();
		FormBean formBean = new FormBean();
		LoginUserForm loginUserForm = new LoginUserForm();
		loginUserForm.setPassword(pwd);
		loginUserForm.setUserName(userid);
		formBean.setData(loginUserForm);
		formBean.setDomain(TrmProvider.TRM_DOMAIN_CHECKPASSWORD);
		formBean.setAction(TrmProvider.TRM_ACTION_CHECKPASSWORD);
		formBean.setFormName("loginUserForm");
		EventRequest request = activity.generateSubmitRequest(activity);
		request.open(formBean, Pattern.async);
		request.callBack(new RefundPwdCallBack(activity));
		request.submit();
		pwdDialog.dismiss();
		dialog.show();
	}

	class RefundPwdCallBack extends AfterProcessWithErrorHandler {

		public RefundPwdCallBack(Activity activity) {
			super(activity);
		}

		@Override
		public void afterRequest(ModelAndView mv) {
			boolean result = (Boolean) mv.getValue("checkResult");
			String errorMsg = (String) mv.getValue("errorMsg");
			if (result) {
				loginSuccess();
			} else {
				loginFaild(errorMsg);
			}
		}

		@Override
		protected void refreshAfterNetworkError() {
			dialog.cancel();
			onClick(null);
		}

		public void loginSuccess() {
			dialog.cancel();
			PayTxnInfo info = ((TxnDetailActivity) activity).getTxnInfo();

			RefundInputContext refundInputContext = new RefundInputContext();
			refundInputContext.setHasRefundAmt(info.getRefundAmt());
			refundInputContext.setRefundAmt(info.getSalesAmt().subtract(
					info.getRefundAmt()));
			refundInputContext.setExTraceNO(info.getExTraceNO());
			refundInputContext.setMemo(info.getMemo());
			refundInputContext.setPayTxnInfo(info);

			TiFlowControlImpl.instanceControl().startFlow(activity,
					FlowNames.TQRM_REFUND_FLOW);
			TiFlowControlImpl.instanceControl().setFlowContextData(
					refundInputContext);
			activity.finish();
		}

		public void loginFaild(String errorMsg) {
			dialog.cancel();
			final PromptDialog pdialog = new PromptDialog(activity, activity
					.getApplicationContext().getResources()
					.getString(R.string.com_check_error_str), errorMsg);
			pdialog.setSureButtonOnclickListener(new OnClickListener() {

				public void onClick(View v) {
					pdialog.dismiss();
					pwdDialog.show();
				}
			});
			pdialog.show();
		}

	}

}
