package me.andpay.apos.tam.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackHomeEventControl;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.tam.CardOrgImageMap;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnDetailContext;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 余额查询结构反馈
 * 
 * @author cpz
 *
 */
@ContentView(R.layout.tam_balance_detail_layout)
public class BalanceDetailActivity extends AposBaseActivity implements
		ValueContainer {

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = BackHomeEventControl.class)
	@InjectView(R.id.com_back_btn)
	public ImageView backHome;

	@InjectView(R.id.tam_amt_txnview)
	public TextView amtTextView;

	@InjectView(R.id.tam_issuer_txnview)
	public TextView issuerTextView;

	@InjectView(R.id.tam_pan_txnview)
	public TextView paneTextView;

	@InjectView(R.id.tam_card_balance_imgview)
	public ImageView carImageView;

	@Inject
	public TxnControl txnControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		TxnDetailContext txnDetailContext = getTiFlowControl()
				.getFlowContextData(TxnDetailContext.class);

		carImageView.setImageResource(CardOrgImageMap.getId(txnDetailContext
				.getCardAssoc()));
		issuerTextView.setText(txnDetailContext.getCardIssuerName());
		paneTextView.setText(TxnUtil.hidePan(txnDetailContext.getCardNo()));
		amtTextView.setText(txnDetailContext.getSalesAmtFomat());

	}
}
