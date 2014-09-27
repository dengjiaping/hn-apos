package me.andpay.apos.tam.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.signature.SignatureView;
import me.andpay.apos.cmview.signature.SignatureView.OnGestureListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.event.SignBackControl;
import me.andpay.apos.tam.event.SignClearEventControl;
import me.andpay.apos.tam.event.SignGestureControl;
import me.andpay.apos.tam.event.SignNextEventControl;
import me.andpay.apos.tam.event.SignPromptControl;
import me.andpay.apos.tam.flow.model.SignContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.tam_sign_layout)
public class SignActivity extends AposBaseActivity implements ValueContainer {

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = SignNextEventControl.class)
	@InjectView(R.id.tam_sign_next_btn)
	public Button nextBtn;

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = SignClearEventControl.class)
	@InjectView(R.id.tam_sign_clear_btn)
	public Button clearBtn;

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "setOnSignedListener", delegateClass = OnGestureListener.class, toEventController = SignGestureControl.class)
	@InjectView(R.id.tam_sign_content)
	public SignatureView signature;

	@Inject
	public TxnControl txnControl;

	@InjectView(R.id.tam_sign_amt_text)
	public TextView amtText;

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = SignPromptControl.class)
	@InjectView(R.id.tam_sign_prompt_lay)
	public RelativeLayout signPromptLay;

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = SignBackControl.class)
	@InjectView(R.id.com_back_btn)
	public ImageView backButton;

	public SignContext signContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewTreeObserver viewTreeObserver = signature.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			public boolean onPreDraw() {
				signature.ensureSignatureBitmap();
				return true;
			}
		});
	}

	@Override
	protected void onResumeProcess() {
		// 自动横屏
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		signContext = TiFlowControlImpl.instanceControl().getFlowContextData(
				SignContext.class);
		amtText.setTextColor(signContext.getAmtTextColor());
		amtText.setText(signContext.getShowAmt());

		if (signContext.isShowBackBtn()) {
			backButton.setVisibility(View.VISIBLE);
		} else {
			backButton.setVisibility(View.GONE);

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void signClear() {

		SignContext signContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(SignContext.class);
		nextBtn.setEnabled(false);
		// signature.setFadeOffset(10);// 清除前设置时间间隔缩小
		signature.clear();
		signature.ensureSignatureBitmap();
		// signature.setFadeOffset(3600000);// 清楚后恢复时间间隔
		signContext.setGesturesCount(0);
		signContext.setGesturesLength(0);
		signPromptLay.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onSigned() {
		SignContext signContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(SignContext.class);
		signContext.setGesturesCount(signContext.getGesturesCount() + 1);
		signContext.setGesturesLength(signContext.getGesturesLength() + 1);
		if (signContext.getGesturesCount() > 2) {
			clearBtn.setEnabled(true);
			nextBtn.setEnabled(true);
		}
	}

	public void onClear() {
		this.signClear();
	}

}
