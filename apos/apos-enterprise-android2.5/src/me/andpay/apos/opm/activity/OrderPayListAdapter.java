package me.andpay.apos.opm.activity;

import java.util.LinkedList;

import me.andpay.ac.term.api.txn.order.OrderAttrNameValue;
import me.andpay.ac.term.api.txn.order.OrderRecord;
import me.andpay.apos.R;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.opm.form.QueryOrderCondForm;
import me.andpay.apos.opm.helper.OrderPayHelper;
import me.andpay.apos.tam.action.txn.cloud.CloudPosUtil;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.StringConvertor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderPayListAdapter extends BaseAdapter {

	private LinkedList<OrderInfo> orders;

	private QueryOrderCondForm queryOrderCondForm;

	private OrderPayListActivity activity;

	public OrderPayListAdapter(LinkedList<OrderInfo> orders,
			QueryOrderCondForm queryOrderCondForm, OrderPayListActivity activity) {
		this.orders = orders;
		this.activity = activity;
		this.queryOrderCondForm = queryOrderCondForm;
	}

	public int getCount() {
		return orders.size();
	}

	public Object getItem(int position) {
		return orders.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final OrderInfo orderInfo = orders.get(position);
		OrderItemViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.opm_orderpay_list_item_layout, null);
			holder = new OrderItemViewHolder();
			holder.amtTextView = (TextView) convertView
					.findViewById(R.id.tom_order_item_amount_tv);
			holder.createDateTextView = (TextView) convertView
					.findViewById(R.id.tom_order_item_createDate_tv);
			holder.orderIdTextView = (TextView) convertView
					.findViewById(R.id.tqm_order_item_id_tv);
			holder.payButton = (Button) convertView
					.findViewById(R.id.tom_order_pay_button);

			holder.lineView = (LinearLayout) convertView
					.findViewById(R.id.opm_order_lay);
			holder.payButton.setOnClickListener(new OrderPayClickListener(
					orderInfo, activity));
			convertView.setTag(holder);

		} else {
			holder = (OrderItemViewHolder) convertView.getTag();
			holder.payButton.setOnClickListener(new OrderPayClickListener(
					orderInfo, activity));
		}

		holder.lineView.removeAllViews();
		if (orderInfo.getOrderAttrs1() != null) {

			for (OrderAttrNameValue value : orderInfo.getOrderAttrs1()) {
				View view = LayoutInflater.from(activity).inflate(
						R.layout.opm_orderpay_list_item_subview_layout, null);
				TextView titleText = (TextView) view
						.findViewById(R.id.opm_title_tv);
				TextView dataText = (TextView) view
						.findViewById(R.id.opm_value_tv);
				titleText.setText(value.getName());
				dataText.setText(value.getValue());
				holder.lineView.addView(view);

			}

		} else {
			holder.lineView.removeAllViews();
		}
		holder.amtTextView.setText(StringConvertor.convert2Currency(orderInfo
				.getOrderAmt()));
		holder.orderIdTextView.setText(orderInfo.getOrderId());
		holder.createDateTextView.setText(DateUtil.format(
				"yyyy/MM/dd HH:mm:ss",
				StringUtil.parseToDate("yyyyMMddHHmmss",
						orderInfo.getCreateDate())));

		if (orderInfo.getOrderStatus().equals(OrderRecord.STATUS_PAID)) {
			holder.payButton.setVisibility(View.GONE);
		} else {
			holder.payButton.setVisibility(View.VISIBLE);
		}

		convertView.setBackgroundDrawable((position % 2 != 0) ? activity
				.getResources().getDrawable(
						R.drawable.com_list_item_even_selector) : activity
				.getResources().getDrawable(
						R.drawable.com_list_item_odd_selector));

		return convertView;
	}

	public class OrderPayClickListener implements OnClickListener {

		private OrderInfo orderInfo;

		private OrderPayListActivity meActivity;

		public OrderPayClickListener(OrderInfo orderInfo,
				OrderPayListActivity activity) {
			this.orderInfo = orderInfo;
			this.meActivity = activity;
		}

		public void onClick(View v) {
			if (CloudPosUtil.isCloudPosCardReader(activity)) {
				PromptDialog dialog = new PromptDialog(activity, null,
						ResourceUtil.getString(activity,
								R.string.tam_cloud_txntype_error_str));
				dialog.show();
				return;
			}
			OrderPayHelper.sendTxn(meActivity, orderInfo);

		}

	}

	public Long getMaxId() {
		if (orders != null && orders.size() > 0) {
			return orders.get(orders.size() - 1).getOrderRecordId();
		}
		return null;
	}

	public Long getMinId() {
		if (orders != null && orders.size() > 0) {
			return orders.get(0).getOrderRecordId();
		}
		return null;

	}

	public String getMaxTxnId() {
		if (orders != null && orders.size() > 0) {
			return orders.get(orders.size() - 1).getTxnId();
		}
		return null;
	}

	public String getMinTxnId() {
		if (orders != null && orders.size() > 0) {
			return orders.get(0).getTxnId();
		}
		return null;
	}

	public LinkedList<OrderInfo> getOrders() {
		return orders;
	}

	public void setOrders(LinkedList<OrderInfo> orders) {
		this.orders = orders;
	}

	public QueryOrderCondForm getQueryOrderCondForm() {
		return queryOrderCondForm;
	}

	public void setQueryOrderCondForm(QueryOrderCondForm queryOrderCondForm) {
		this.queryOrderCondForm = queryOrderCondForm;
	}

}
