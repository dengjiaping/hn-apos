package me.andpay.apos.opm.activity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.Html;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;

@ContentView(R.layout.opm_order_no_help_layout)
public class InputOrderNoHelpActivity extends AposBaseActivity {

	@InjectView(R.id.opm_help_text_view)
	private TextView helpText;

	@InjectView(R.id.opm_top_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	private ImageView backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Object tipsText = getAppContext().getAttribute(
				RuntimeAttrNames.ORDER_NO_INPUT_TIPS);
		if (tipsText != null) {
			helpText.setText(Html.fromHtml(tipsText.toString()));
		}

	}

}
