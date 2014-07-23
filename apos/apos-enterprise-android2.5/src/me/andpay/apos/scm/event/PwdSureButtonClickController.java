package me.andpay.apos.scm.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.util.ValidateHelper;
import me.andpay.apos.lam.callback.ChangePasswordCallback;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.DialogUtil;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 用户点击确定,更改密码
 * 
 * @author tinyliu
 * 
 */
public class PwdSureButtonClickController extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		
		if(ValidateHelper.validate(activity, formBean,null)) {
			return;
		}
		
		CommonDialog dialog = new CommonDialog(activity, activity
				.getResources().getString(R.string.com_progress_prompt_str));
		DialogUtil.setDialogAllowClose(dialog.getDialog(), false);
		EventRequest request = this.generateSubmitRequest(activity);
		request.open(formBean, Pattern.async);
		request.callBack(new PwdChangeCallBack(activity, dialog));
		request.submit();
		dialog.show();
	}

	private void showErrorMsg(Activity activity, String msg) {
		PromptDialog dialog = new PromptDialog(activity, null, msg);
		dialog.show();
	}

	@CallBackHandler
	class PwdChangeCallBack implements ChangePasswordCallback {

		private Activity activity;

		private CommonDialog dialog;

		PwdChangeCallBack(Activity activity, CommonDialog dialog) {
			this.activity = activity;
			this.dialog = dialog;
		}

		public void changeSuccess() {
			DialogUtil.setDialogAllowClose(dialog.getDialog(), true);
			dialog.cancel();
			PromptDialog pDialog = new PromptDialog(activity, null, activity
					.getResources().getString(
							R.string.lam_change_password_success_str));
			pDialog.setSureButtonOnclickListener(new OnClickListener() {

				public void onClick(View v) {
					KeyEvent key = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
					activity.onKeyDown(key.getKeyCode(), key);
					activity.finish();
				}
			});
			pDialog.show();
		}

		public void changeFaild(String errorCode) {
			DialogUtil.setDialogAllowClose(dialog.getDialog(), true);
			dialog.cancel();
			showErrorMsg(activity, errorCode);
		}

	}
}
