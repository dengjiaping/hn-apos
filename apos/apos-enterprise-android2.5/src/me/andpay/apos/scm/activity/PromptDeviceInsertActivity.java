package me.andpay.apos.scm.activity;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.common.log.OperationDataKeys;
import me.andpay.apos.scm.event.PromptDeviceInsertNextEventController;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.util.AudioUtil;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@ContentView(R.layout.scm_prompt_device_insert_layout)
public class PromptDeviceInsertActivity extends AposBaseActivity {

	@InjectView(R.id.scm_cardread_open_img)
	public ImageView cardImageView;

	@InjectView(R.id.scm_next_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PromptDeviceInsertNextEventController.class)
	public Button nextButton;

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	public ImageView backButton;

	@InjectView(R.id.scm_text_view)
	public TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CardReaderSetContext cardReaderSetContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(
						CardReaderSetContext.class);
		int resourceId = CardReaderResourceSelector
				.selectCardreaderConnect(cardReaderSetContext
						.getCardReaderType());
		cardImageView.setImageResource(resourceId);

		int cardReaderType = cardReaderSetContext.getCardReaderType();

		if (cardReaderType == CardReaderTypes.ITRON
				|| cardReaderType == CardReaderTypes.NEW_LAND_AD) {
			if (AudioUtil.isHeadsetInsert(this)) {
				textView.setText("请根据图示，把音频线插入读卡器");
			} else {
				textView.setText("请根据图示，把音频线插入读卡器和手机");
			}
		}

		if (cardReaderType == CardReaderTypes.NEW_LAND) {
			textView.setText("请根据图示,把读卡器插入手机音频口");
		}

		cardReaderSetContext.getOpLogData().put(
				OperationDataKeys.OPKEYS_INSERT_DEVICE, String.valueOf(true));

	}
}
