package me.andpay.apos.scm.activity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.common.log.OperationDataKeys;
import me.andpay.apos.scm.event.MakeQRCodeNextEventController;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;

@ContentView(R.layout.scm_make_qrcode_layout)
public class MakeQRCodeActivity extends AposBaseActivity{
	
	@InjectView(R.id.scm_cardread_open_img)
	public ImageView cardImageView;

	@InjectView(R.id.scm_next_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = MakeQRCodeNextEventController.class)
	public Button nextButton;

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	public ImageView backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CardReaderSetContext cardReaderSetContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(
						CardReaderSetContext.class);

		cardReaderSetContext.getOpLogData().put(OperationDataKeys.OPKEYS_OPEN_DEVICE, String.valueOf(true));
	}
}
