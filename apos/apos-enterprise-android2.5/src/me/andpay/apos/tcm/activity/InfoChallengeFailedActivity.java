package me.andpay.apos.tcm.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.tcm.event.DealErrorMsgEventController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author David.zhang
 * 
 */
@ContentView(R.layout.tcm_infochallenge_failure_layout)
public class InfoChallengeFailedActivity extends AposBaseActivity {
	@InjectView(R.id.tcm_infochallenge_failure_info_txtView)
	public TextView failedInfoTextView;

	@InjectView(R.id.tam_infochallenge_failure_quit_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	public Button backButton;
	
	@InjectView(R.id.tcm_infochallenge_failure_modify_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = DealErrorMsgEventController.class)
	public Button nextButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		if (intent != null && intent.hasExtra("errorMsg")) {
			String errorMsg = intent.getStringExtra("errorMsg");
			failedInfoTextView.setText(errorMsg);
		}
	}

}
