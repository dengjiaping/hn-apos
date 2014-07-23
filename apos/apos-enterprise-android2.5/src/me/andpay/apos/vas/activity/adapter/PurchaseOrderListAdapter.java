package me.andpay.apos.vas.activity.adapter;

import java.util.LinkedList;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.ac.term.api.shop.PurchaseOrderItem;
import me.andpay.apos.R;
import me.andpay.apos.cmview.TiSectionListAdapter;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.ti.util.DateUtil;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Purchase Order ListView 数据适配器
 * 
 * @author tinyliu
 * 
 */
public class PurchaseOrderListAdapter extends TiSectionListAdapter<PurchaseOrderInfo> {

	static final String DATE_PARTTERN = "yyyy-MM-dd";

	static final String TIME_PARTTERN = "HH:mm";

	static final Integer MAXDESCSIZE = 20;

	static final String ELLIPSIS = "...";

	private Context applicationContext;

	public PurchaseOrderListAdapter(Activity activity) {
		this.applicationContext = activity.getApplicationContext();
		all = new LinkedList<Pair<String, LinkedList<PurchaseOrderInfo>>>();

	}

	@Override
	protected void onNextPageRequested(int page) {
	}

	@Override
	protected void bindSectionHeader(View view, int sectionIndex,
			boolean displaySectionHeader) {
		if (displaySectionHeader) {
			view.findViewById(me.andpay.apos.R.id.section_header).setVisibility(
					View.VISIBLE);
			TextView lSectionTitle = (TextView) view
					.findViewById(R.id.vas_purchase_order_section_tv);
			lSectionTitle.setText(getSections()[sectionIndex].toString());
		} else {
			view.findViewById(R.id.section_header).setVisibility(View.GONE);
		}
	}

	public void configureSectionView(View header, int section, int alpha) {
		TextView lSectionTitle = (TextView) header
				.findViewById(R.id.vas_purchase_order_section_tv);
		lSectionTitle.setText(getSections()[section].toString());
	}

	@Override
	public long getItemId(int section, int itemIndex) {
		return all.get(section).second.get(itemIndex).getOrderId();
	}

	@Override
	public View getSectionItemView(int sectionIndex, int itemIndex, View convertView,
			ViewGroup parent) {
		PurchaseOrderItemViewHolder holder = null;
		PurchaseOrderInfo info = getSectionItem(sectionIndex, itemIndex);
		if (convertView == null) {
			convertView = LayoutInflater.from(applicationContext).inflate(
					R.layout.vas_purchaseorder_item_layout, null);
			holder = new PurchaseOrderItemViewHolder();
			holder.amountView = (TextView) convertView
					.findViewById(R.id.vas_purchase_order_item_amount_tv);
			holder.descView = (TextView) convertView
					.findViewById(R.id.vas_purchase_order_item_desc_tv);
			holder.payTypeImg = (ImageView) convertView
					.findViewById(R.id.vas_purchase_order_item_img);
			holder.timeView = (TextView) convertView
					.findViewById(R.id.vas_purchase_order_item_time_tv);
			holder.refundImg = (ImageView) convertView
					.findViewById(R.id.vas_purchase_order_item_refund_img);
			holder.unFulfillLayout = (RelativeLayout) convertView
					.findViewById(R.id.vas_purchase_order_unfuifill_layout);
			convertView.setTag(holder);
		} else {
			holder = (PurchaseOrderItemViewHolder) convertView.getTag();
		}
		holder.payTypeImg.setImageResource(PaymentMethods.CASH.equalsIgnoreCase(info
				.getPaymentMethod()) ? R.drawable.com_icon_cash_img
				: R.drawable.com_icon_swipe_img);
		StringBuffer descStr = new StringBuffer();
		for (int i = 0; i < info.getItems().size(); i++) {
			PurchaseOrderItem item = info.getItems().get(i);
			descStr.append(item.getProductName()==null?"":item.getProductName());
			descStr.append(item.getPrice());
			descStr.append("元x");
			descStr.append(item.getUnit());
			if (i != info.getItems().size() - 1) {
				descStr.append(",");
			}
		}
		if(info.getStatus().equals(PurchaseOrder.STATUS_PAID)) {
			holder.unFulfillLayout.setVisibility(View.VISIBLE);
		} else {
			holder.unFulfillLayout.setVisibility(View.GONE);
		}
		holder.descView.setText(descStr.length() > MAXDESCSIZE ? descStr.toString()
				.substring(0, MAXDESCSIZE) + ELLIPSIS : descStr.toString());
		holder.amountView.setText(StringConvertor.convert2Currency(info.getSalesAmt()));
		holder.timeView.setText(DateUtil.format(TIME_PARTTERN, info.getOrderTime()));
		if (PurchaseOrder.STATUS_REFUND.equals(info.getStatus())) {
			holder.refundImg.setVisibility(View.VISIBLE);
			holder.amountView.setTextColor(applicationContext.getResources().getColor(
					R.color.com_cccccc_col));
			holder.timeView.setTextColor(applicationContext.getResources().getColor(
					R.color.com_cccccc_col));
		} else {
			holder.refundImg.setVisibility(View.GONE);
			holder.amountView.setTextColor(applicationContext.getResources().getColor(
					R.color.com_666666_col));
			holder.timeView.setTextColor(applicationContext.getResources().getColor(
					R.color.com_666666_col));
		}
		return convertView;
	}

	public Long getMaxOrderId() {
		return all.get(0).second.getFirst().getOrderId();

	}

	public Long getMinOrderId() {
		return all.get(all.size() - 1).second.getLast().getOrderId();

	}

	@Override
	public String getSectionDesc(PurchaseOrderInfo info) {
		return DateUtil.format(DATE_PARTTERN, info.getOrderTime())
				+ " "
				+ me.andpay.timobileframework.util.DateUtil.getWeekChineseDesc(info
						.getOrderTime());
	}

}
