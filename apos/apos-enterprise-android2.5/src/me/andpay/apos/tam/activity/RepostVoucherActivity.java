package me.andpay.apos.tam.activity;

import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.event.RepostVcEditWatcherEventControl;
import me.andpay.apos.tam.event.RepostVcEventControl;
import me.andpay.apos.tam.flow.model.PostVoucherContext;
import me.andpay.apos.tam.form.PostVoucherForm;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.inject.Inject;

@ContentView(R.layout.tam_repost_voucher_layout)
@FormBind(formBean = PostVoucherForm.class)
public class RepostVoucherActivity extends AposBaseActivity implements
		ValueContainer {

	@EventDelegate(type = DelegateType.eventController,isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = RepostVcEditWatcherEventControl.class)	
	@InjectView(R.id.tam_repost_phone_edit)
	public EditText phoneEditText;

	@InjectView(R.id.tam_repost_send_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = true,delegateClass = OnClickListener.class, toEventController = RepostVcEventControl.class)
	public Button sendBtn;
	
	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	public ImageView backButton;
	
	public CommonDialog postDialog;

	/**
	 * 重发凭证标志
	 */
	public PostVoucherContext postVoucherContext;
	
	@Inject
	public TxnControl txnControl;

	public String realPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		postVoucherContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(PostVoucherContext.class);

		Map<String, String> contactInfos = postVoucherContext.getContactInfos();

		if (contactInfos != null && contactInfos.size() > 0) {
			String phone = contactInfos.get("0");
			if (phone != null) {
				realPhone = phone;
				phone = "*******" + phone.substring(7);
			}
			phoneEditText.setText(phone);
		} else {
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
