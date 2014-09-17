package me.andpay.apos.scm.event;

import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.scm.ScmProvider;
import me.andpay.apos.scm.activity.ScmFeedbackActivity;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 反馈发送按钮响应事件
 * 
 * @author tinyliu
 * 
 */
public class FeedBackSendButtonController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		ScmFeedbackActivity activity = (ScmFeedbackActivity) refActivty;
		String feedBackStr = activity.getText().getText().toString();
		if (StringUtil.isEmpty(feedBackStr)) {
			PromptDialog pDialog = new PromptDialog(refActivty, null,
					activity.getErrorMsg());
			pDialog.show();
			return;
		}
		final CommonDialog dialog = new CommonDialog(refActivty,
				activity.getOperationMsg());
		dialog.show();
		EventRequest request = this.generateSubmitRequest(refActivty);
		request.open(ScmProvider.SCM_DOMAIN_FEEDBACK,
				ScmProvider.SCM_ACTION_FEEDBACK, Pattern.async);
		request.getSubmitData().put("feedback", feedBackStr);
		request.callBack(new AfterFeedBackSendProcess(activity, dialog));
		request.submit();
	}

	class AfterFeedBackSendProcess extends AfterProcessWithErrorHandler {

		private CommonDialog dialog = null;

		private ScmFeedbackActivity refActivty = null;

		public AfterFeedBackSendProcess(ScmFeedbackActivity refActivty,
				CommonDialog dialog) {
			super(refActivty);
			this.dialog = dialog;
			this.refActivty = refActivty;
		}

		@Override
		protected void refreshAfterNetworkError() {
			dialog.cancel();
			onClick(TiAndroidRuntimeInfo.getCurrentActivity(), null, null);
		}

		@Override
		public void afterRequest(ModelAndView mv) {
			dialog.cancel();
			Boolean sendFlag = (Boolean) mv.getValue("sendSucc");
			if (sendFlag == null || !sendFlag) {
				PromptDialog error = new PromptDialog(refActivty, null,
						refActivty.getErrorSendMsg());
				error.show();
				return;
			} else {
				final PromptDialog succ = new PromptDialog(refActivty, null,
						refActivty.getSuccSendMsg());
				succ.setSureButtonOnclickListener(new OnClickListener() {

					public void onClick(View v) {
						succ.dismiss();
						Intent homePageIntent = new Intent(
								CommonProvider.COM_HOMEPAGE_ACTIVITY);
						homePageIntent.putExtra(CommonProvider.TAGNAME,
								TabNames.MORE_PAGE);
						refActivty.startActivity(homePageIntent);
						refActivty.finish();

					}
				});
				succ.show();
			}
		}
	}
}
