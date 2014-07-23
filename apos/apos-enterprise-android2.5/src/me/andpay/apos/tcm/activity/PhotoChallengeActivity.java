package me.andpay.apos.tcm.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.tcm.event.OpenViewfinderEventController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.util.BitMapUtil;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author David.zhang
 * 
 */
@ContentView(R.layout.tcm_photochallenge_layout)
public class PhotoChallengeActivity extends AposBaseActivity {
	@InjectView(R.id.tcm_photochallenge_info_txtView)
	public TextView explanationTextView;

	@InjectView(R.id.tcm_photochallenge_before_shoot_layout)
	RelativeLayout befoteShootLayout;

	@InjectView(R.id.tcm_photochallenge_after_shoot_layout)
	RelativeLayout afterShootLayout;

	@InjectView(R.id.tcm_photochallenge_before_shoot_layout)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = OpenViewfinderEventController.class)
	public RelativeLayout shootImageLayout;

	@InjectView(R.id.tcm_photochallenge_show_photo_img)
	public ImageView showImageView;

	@InjectView(R.id.tcm_photochallenge_retake_photo_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = OpenViewfinderEventController.class)
	public Button retakeButton;

	@InjectView(R.id.tcm_photochallenge_next_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = OpenViewfinderEventController.class)
	public Button nextStepButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		highlightCardName();
		Intent intent = getIntent();
		if (intent != null && intent.hasExtra("file_path")) {
			befoteShootLayout.setVisibility(View.GONE);
			afterShootLayout.setVisibility(View.VISIBLE);
			String path = intent.getStringExtra("file_path");
			Bitmap bitmap = BitMapUtil.getBitmap(path);
			showImageView.setImageBitmap(bitmap);
			nextStepButton.setEnabled(true);
		}
	}

	private void highlightCardName() {
		String explanation = explanationTextView.getText().toString();
		SpannableStringBuilder style = new SpannableStringBuilder(explanation);
		style.setSpan(new ForegroundColorSpan(Color.parseColor("#2e86ca")), 16,
				21, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		explanationTextView.setText(style);
	}
}
