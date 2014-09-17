package me.andpay.apos.stl.activity.adapter;

import java.util.Date;
import java.util.LinkedList;

import me.andpay.ac.term.api.settle.SettleOrder;
import me.andpay.apos.R;
import me.andpay.apos.cmview.TiSectionListAdapter;
import me.andpay.apos.stl.activity.SettleListActivity;
import me.andpay.apos.stl.form.QuerySettleCondForm;
import me.andpay.ti.util.DateUtil;
import me.andpay.timobileframework.util.StringConvertor;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettleListAdapter extends TiSectionListAdapter<SettleOrder> {
	
	private QuerySettleCondForm querySettleCondForm;

	private SettleListActivity activity;

	public SettleListAdapter(LinkedList<SettleOrder> settleOrders,
			QuerySettleCondForm querySettleCondForm, SettleListActivity activity) {
		all = new LinkedList<Pair<String, LinkedList<SettleOrder>>>();
		this.activity = activity;
		this.addValues(settleOrders);
		this.querySettleCondForm = querySettleCondForm;
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public Date getMaxSettleTime() {
		return all.get(0).second.getFirst().getSettleTime();
	}

	public Date getMinSettleTime() {
		return all.get(all.size() - 1).second.getLast().getSettleTime();
	}



	public QuerySettleCondForm getQuerySettleCondForm() {
		return querySettleCondForm;
	}

	public void setQuerySettleCondForm(QuerySettleCondForm querySettleCondForm) {
		this.querySettleCondForm = querySettleCondForm;
	}

	@Override
	protected void bindSectionHeader(View view, int sectionIdex,
			boolean displaySectionHeader) {
		if (displaySectionHeader) {
			view.findViewById(me.andpay.apos.R.id.section_header)
					.setVisibility(View.VISIBLE);
			TextView lSectionTitle = (TextView) view
					.findViewById(R.id.vas_purchase_order_section_tv);
			lSectionTitle.setText(getSections()[sectionIdex].toString());
		} else {
			view.findViewById(R.id.section_header).setVisibility(
					View.GONE);
		}
	}
	
	@Override
	public void configureSectionView(View header, int section, int alpha) {
		TextView lSectionTitle = (TextView) header
				.findViewById(R.id.vas_purchase_order_section_tv);
		lSectionTitle.setText(getSections()[section].toString());
	}


	@Override
	public long getItemId(int section, int itemIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getSectionItemView(int sectionIndex, int itemIndex, View convertView,
			ViewGroup parent) {
		final SettleOrder settleOrder = this.getSectionItem(
				sectionIndex, itemIndex);
		SettleItemViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.stl_settle_list_item_layout, null);
			holder = new SettleItemViewHolder();
			holder.settleAmtText = (TextView) convertView
					.findViewById(R.id.stl_settle_amt_tv);
			//holder.redeemDateTextView = (TextView) convertView
			//		.findViewById(R.id.tqrm_coupin_redeem_date_tv);
			holder.settleTimeText = (TextView) convertView
					.findViewById(R.id.stl_settle_time_tv);
			holder.settleCountText = (TextView) convertView
					.findViewById(R.id.stl_settle_count_tv);
			convertView.setTag(holder);

		} else {
			holder = (SettleItemViewHolder) convertView.getTag();
		}
		holder.settleAmtText.setText( StringConvertor.convert2Currency(settleOrder.getSettleAmt()));
		holder.settleTimeText.setText(DateUtil.format("MM-dd",
				settleOrder.getSettleTime()));
		holder.settleCountText.setText(settleOrder.getTxnCount()+"笔交易");
		return convertView;
	}
	
	@Override
	public String getSectionDesc(SettleOrder settleOrder) {
		String orderTimeStr = DateUtil.format("yyyy年MM月",
				settleOrder.getSettleTime());
		return orderTimeStr;
	}
}
