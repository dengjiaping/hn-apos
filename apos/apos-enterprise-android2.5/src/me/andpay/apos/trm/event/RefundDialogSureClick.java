package me.andpay.apos.trm.event;

import me.andpay.apos.R;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class RefundDialogSureClick implements OnClickListener {

	private Dialog pwdDialog = null;

	private Dialog preDialog = null;

	public RefundDialogSureClick(TiActivity refActivty, Dialog dialog) {
		preDialog = dialog;

		LoginUserInfo info = (LoginUserInfo) refActivty.getAppContext()
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		pwdDialog = new Dialog(refActivty, R.style.Theme_dialog_style);
		pwdDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		pwdDialog.setContentView(R.layout.trm_refund_dialog_password_layout);
		TextView userid = (TextView) pwdDialog
				.findViewById(R.id.trm_refund_dialog_userid_tv);
		userid.setText(info.getUserName());
		pwdDialog.findViewById(R.id.trm_txn_refund_sure_btn)
				.setOnClickListener(
						new RefundPwdDialogSureClick(pwdDialog, refActivty));
		pwdDialog.findViewById(R.id.trm_txn_refund_cancel_btn)
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						pwdDialog.dismiss();
					}
				});
	}

	public void onClick(View v) {
		preDialog.dismiss();
		pwdDialog.show();
	}

}