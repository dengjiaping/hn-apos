package me.andpay.apos.scm.activity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.common.event.FinishFlowController;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.log.OperationDataKeys;
import me.andpay.apos.scm.event.SelectCardreaderEventController;
import me.andpay.apos.scm.event.QRScanNextEventController;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.util.DisplayUtil;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

@ContentView(R.layout.scm_cardreader_select_layout)
public class ScmCardReaderSelectActivity extends AposBaseActivity {

	@InjectView(R.id.scm_button_one)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SelectCardreaderEventController.class)
	public Button oneBt;

	@InjectView(R.id.scm_button_two)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SelectCardreaderEventController.class)
	public Button twoBt;

	@InjectView(R.id.scm_button_three)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SelectCardreaderEventController.class)
	public Button threeBt;

	@InjectView(R.id.scm_button_four)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SelectCardreaderEventController.class)
	public Button fourBt;

	@InjectView(R.id.scm_button_five)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SelectCardreaderEventController.class)
	public Button fiveBt;

	@InjectView(R.id.scm_button_six)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SelectCardreaderEventController.class)
	public Button sixBt;


	public Map<String, View> cardreaderTypeLays = new HashMap<String, View>();

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = FinishFlowController.class)
	public ImageView backButton;

//	@InjectView(R.id.com_skip_btn)
//	@EventDelegate(delegateClass = OnClickListener.class, toEventController = CardReaderSetSkipController.class)
//	public Button skipButton;
	
//	

	private CardReaderSetContext cardReaderSetContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

//	@Override@InjectView(R.id.scm_qr_scan_btn)
//	@EventDelegate(delegateClass = OnClickListener.class, toEventController = QRScanNextEventController.class)
//	public ImageView qrScanButton;
	protected void onResumeProcess() {
		super.onResumeProcess();

		cardReaderSetContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(CardReaderSetContext.class);

		if (cardReaderSetContext.isHasSelectCardreader()) {
			TiFlowControlImpl.instanceControl().nextSetup(this,
					FlowConstants.SUCCESS_STEP4);
			return;
		}

		if (cardReaderSetContext.isShowBackButton()) {
			backButton.setVisibility(View.VISIBLE);
		} else {
			backButton.setVisibility(View.GONE);
		}
		
		cardReaderSetContext.getOpLogData().put(OperationDataKeys.OPKEYS_IS_GUIDE, String.valueOf(true));

//		if (cardReaderSetContext.isShowSkipButton()) {
//			skipButton.setVisibility(View.VISIBLE);
//		} else {
//			skipButton.setVisibility(View.GONE);
//		}
			
		//暂时不要动态显示
//		resizeLayout();
//		setCardReaderTypeButton();
	}

	private void setCardReaderTypeButton() {

		PartyInfo partyInfo = (PartyInfo) getAppContext().getAttribute(
				RuntimeAttrNames.PARTY_INFO);

		Set<String> msrTypes = new HashSet<String>();
		msrTypes.add("1");
		msrTypes.add("2");
		msrTypes.add("3");
		msrTypes.add("4");
		msrTypes.add("5");
		msrTypes.add("6");

		Set<String> remoteMsrTypes = partyInfo.getMsrTypes();
		if (remoteMsrTypes != null && !remoteMsrTypes.isEmpty()) {
			for (String type : remoteMsrTypes) {
				msrTypes.remove(type);
			}
		} else {
			msrTypes.clear();
		}
	
	}

	private void resizeLayout() {
		DisplayMetrics displayMetrics = DisplayUtil.getDisplayMetrics(this);
		int width = Float.valueOf(
				displayMetrics.widthPixels - 26 * displayMetrics.density)
				.intValue();
		
		setButtonSize(oneBt, width, displayMetrics);
		setButtonSize(twoBt, width, displayMetrics);
		setButtonSize(threeBt, width, displayMetrics);
		setButtonSize(fourBt, width, displayMetrics);
		setButtonSize(fiveBt, width, displayMetrics);
		setButtonSize(sixBt, width, displayMetrics);

		oneBt.setBackgroundResource(R.drawable.scm_button_cardreader_selector);
		twoBt.setBackgroundResource(R.drawable.scm_button_cardreader_selector);
		threeBt.setBackgroundResource(R.drawable.scm_button_cardreader_selector);
		fourBt.setBackgroundResource(R.drawable.scm_button_cardreader_selector);
		fiveBt.setBackgroundResource(R.drawable.scm_button_cardreader_selector);
		sixBt.setBackgroundResource(R.drawable.scm_button_cardreader_selector);

	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (cardReaderSetContext.isShowBackButton()) {
			super.onKeyDown(keyCode, event);
		} else {
			return true;
		}

		return false;

	}

	public void setButtonSize(Button button, int width,
			DisplayMetrics displayMetrics) {
		android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(
				button.getLayoutParams());
		layoutParams.width = Float.valueOf(
				width / 4 - 5 * displayMetrics.density).intValue();
		button.setLayoutParams(layoutParams);

	}

//	public void setViewSize(View view, int width, DisplayMetrics displayMetrics) {
//		LayoutParams layoutParams = new LayoutParams();
//		layoutParams.width = width / 4;
//		view.setLayoutParams(layoutParams);
//	}

}
