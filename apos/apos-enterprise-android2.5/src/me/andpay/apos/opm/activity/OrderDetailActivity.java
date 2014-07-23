package me.andpay.apos.opm.activity;

import me.andpay.ac.term.api.txn.order.OrderAttrNameValue;
import me.andpay.ac.term.api.txn.order.OrderRecord;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.opm.event.OrderDetailBackController;
import me.andpay.apos.opm.event.OrderDetailPayController;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.opm_orderpay_detail_layout)
public class OrderDetailActivity extends AposBaseActivity {

	@InjectView(R.id.opm_txn_detail_amount_tv)
	public TextView amtLabel;

	@InjectView(R.id.opm_txn_detail_order_tv)
	public TextView orderLabel;

	@InjectView(R.id.opm_order_main_layout)
	public LinearLayout mainLayout;

	@InjectView(R.id.opm_order_detail_layout)
	public LinearLayout detailLayout;

	public OrderInfo orderInfo;

	@InjectView(R.id.opm_order_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = OrderDetailBackController.class)
	public ImageView backImageView;

	@InjectView(R.id.opm_pay_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = OrderDetailPayController.class)
	public Button payButton;

	@Inject
	public TxnControl txnControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		orderInfo = JacksonSerializer.newPrettySerializer().deserialize(
				OrderInfo.class,
				(byte[]) getIntent().getExtras().get("orderInfo"));
		amtLabel.setText(StringConvertor.convert2Currency(orderInfo
				.getOrderAmt()));
		orderLabel.setText(orderInfo.getOrderId());

		if (OrderRecord.STATUS_PAID.equals(orderInfo.getOrderStatus())) {
			payButton.setVisibility(View.GONE);
		} else {
			payButton.setVisibility(View.VISIBLE);
		}

		if (orderInfo.getOrderAttrs1() != null) {
			for (OrderAttrNameValue value : orderInfo.getOrderAttrs1()) {
				View view = LayoutInflater.from(this).inflate(
						R.layout.opm_orderpay_detail_item_ayout, null);
				TextView titleText = (TextView) view
						.findViewById(R.id.omp_title_tv);
				TextView dataText = (TextView) view
						.findViewById(R.id.omp_data_tv);
				titleText.setText(value.getName());
				dataText.setText(value.getValue());
				mainLayout.addView(view);

			}
		}

		if (orderInfo.getOrderAttrs2() != null) {
			for (OrderAttrNameValue value : orderInfo.getOrderAttrs2()) {
				View view = LayoutInflater.from(this).inflate(
						R.layout.opm_orderpay_detail_item_ayout, null);
				TextView titleText = (TextView) view
						.findViewById(R.id.omp_title_tv);
				TextView dataText = (TextView) view
						.findViewById(R.id.omp_data_tv);
				titleText.setText(value.getName());
				dataText.setText(value.getValue());
				detailLayout.addView(view);
			}
		}

	}

}
