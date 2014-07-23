package me.andpay.apos.tam.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@ContentView(R.layout.tam_settlement_rules_layout)
public class SettlementRulesActivity extends AposBaseActivity {
	@InjectView(R.id.tam_settlement_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	public Button backButton;

	@InjectView(R.id.tam_settlement_rule1_txt)
	public TextView rulesTextView1;

	@InjectView(R.id.tam_settlement_rule2_txt)
	public TextView rulesTextView2;

	@InjectView(R.id.tam_settlement_rule3_txt)
	public TextView rulesTextView3;

	@InjectView(R.id.tam_settlement_rule4_txt)
	public TextView rulesTextView4;

	@InjectView(R.id.tam_settlement_rule5_txt)
	public TextView rulesTextView5;

	@InjectView(R.id.tam_settlement_rule6_txt)
	public TextView rulesTextView6;

	@InjectView(R.id.tam_settlement_rule7_txt)
	public TextView rulesTextView7;

	private static int[] startBlodIndexs = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };

	private static int[] endBlodIndexs = new int[] { 0, 8, 8, 11, 0, 19, 0 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView[] textViews = new TextView[] { rulesTextView1, rulesTextView2,
				rulesTextView3, rulesTextView4, rulesTextView5, rulesTextView6,
				rulesTextView7 };
		for (int i = 0; i < textViews.length; i++) {
			this.boldPartialOfText(textViews[i], startBlodIndexs[i],
					endBlodIndexs[i]);
		}
	}

	private void boldPartialOfText(TextView textView, int startBlodIndex,
			int endBlodIndex) {
		if(endBlodIndex==0)
			return;
		String text = textView.getText().toString();
		SpannableString spannableString = new SpannableString(text);
		spannableString.setSpan(new StyleSpan(Typeface.BOLD),
				startBlodIndex, endBlodIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(spannableString);
	}

}
