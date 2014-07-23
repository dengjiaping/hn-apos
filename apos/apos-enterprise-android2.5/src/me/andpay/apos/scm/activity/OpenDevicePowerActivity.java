package me.andpay.apos.scm.activity;


import me.andpay.apos.R;
import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.common.log.OperationDataKeys;
import me.andpay.apos.scm.event.OpenDevicePowerNextEventController;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

@ContentView(R.layout.scm_open_device_layout)
public class OpenDevicePowerActivity extends AposBaseActivity {

	@InjectView(R.id.scm_cardread_open_img)
	public ImageView cardImageView;

	@InjectView(R.id.scm_next_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = OpenDevicePowerNextEventController.class)
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

		int resourceId = CardReaderResourceSelector
				.selectCardreaderOpen(cardReaderSetContext.getCardReaderType());
		cardImageView.setImageResource(resourceId);
		
		
		cardReaderSetContext.getOpLogData().put(OperationDataKeys.OPKEYS_OPEN_DEVICE, String.valueOf(true));


	}
}
