package me.andpay.apos.scm.activity;

import java.io.File;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.scm.ScmProvider;
import me.andpay.apos.scm.event.BackfeedCancelOnclickController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

@ContentView(R.layout.scm_feedback_alert_layout)
public class ScmFeedBackAlertActivity extends AposBaseActivity {

	@InjectView(R.id.scm_alert_feedback_lay)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackfeedCancelOnclickController.class)
	public RelativeLayout mainLay;

	@InjectView(R.id.scm_cancel_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackfeedCancelOnclickController.class)
	public Button cancelBtn;

	@InjectView(R.id.scm_sure_btn)
	public Button feedbackBtn;

	private String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		filePath = getIntent().getStringExtra(ScmProvider.SCREEN_FILE_PATH);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		getAppContext().removeAttribute(RuntimeAttrNames.IS_BACKFEED);

		File file = new File(filePath);
		if (file != null) {
			file.delete();
		}

	}
}
