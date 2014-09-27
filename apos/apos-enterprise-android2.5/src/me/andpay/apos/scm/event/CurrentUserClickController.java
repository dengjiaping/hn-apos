package me.andpay.apos.scm.event;

import me.andpay.apos.R;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CurrentUserClickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {

		final TiActivity tiActivity = (TiActivity) refActivty;

		final Dialog currUserDialog = new Dialog(refActivty,
				R.style.Theme_dialog_style);

		currUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		currUserDialog.setContentView(R.layout.scm_current_user_dialog_layout);

		LoginUserInfo userInfo = (LoginUserInfo) tiActivity.getAppContext()
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		PartyInfo partyInfo = (PartyInfo) tiActivity.getAppContext()
				.getAttribute(RuntimeAttrNames.PARTY_INFO);

		TextView userView = (TextView) currUserDialog
				.findViewById(R.id.com_dialog_user_textview);
		TextView partyView = (TextView) currUserDialog
				.findViewById(R.id.com_dialog_party_textview);
		Button suButton = (Button) currUserDialog
				.findViewById(R.id.com_dialog_sure_btn);

		userView.setText(userInfo.getUserName());
		partyView.setText(partyInfo.getPartyName());
		suButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				currUserDialog.dismiss();
			}
		});

		currUserDialog.show();

	}

}
