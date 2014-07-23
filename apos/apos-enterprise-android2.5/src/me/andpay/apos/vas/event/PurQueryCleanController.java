package me.andpay.apos.vas.event;

import me.andpay.apos.vas.activity.PurchaseQueryCondActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class PurQueryCleanController extends AbstractEventController {

	public void onClick(Activity refActivity, FormBean formBean, View v) {
		PurchaseQueryCondActivity condActivity = (PurchaseQueryCondActivity) refActivity;

		condActivity.startDateView.setText("");
		condActivity.endPickerView.setText("");

		condActivity.amtEditText.setText("");

		condActivity.hasCondImg.setVisibility(View.GONE);
	}
}
