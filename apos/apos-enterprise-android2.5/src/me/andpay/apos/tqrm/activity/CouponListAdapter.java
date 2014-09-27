package me.andpay.apos.tqrm.activity;

import java.text.DateFormat;
import java.util.LinkedList;

import me.andpay.ac.term.api.pas.CouponRedeemList;
import me.andpay.apos.R;
import me.andpay.apos.cmview.TiSectionListAdapter;
import me.andpay.apos.tqrm.form.QueryCouponCondForm;
import me.andpay.ti.util.DateUtil;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CouponListAdapter extends TiSectionListAdapter<CouponRedeemList> {

	private QueryCouponCondForm queryCouponCondForm;

	private CouponListActivity activity;

	public CouponListAdapter(LinkedList<CouponRedeemList> coupons,
			QueryCouponCondForm queryCouponCondForm, CouponListActivity activity) {
		all = new LinkedList<Pair<String, LinkedList<CouponRedeemList>>>();
		this.activity = activity;
		this.addValues(coupons);
		this.queryCouponCondForm = queryCouponCondForm;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public Long getMaxRedeemId() {
		return all.get(0).second.getFirst().getRedeemId();
	}

	public Long getMinRedeemId() {
		return all.get(all.size() - 1).second.getLast().getRedeemId();
	}

	public QueryCouponCondForm getQueryCouponCondForm() {
		return queryCouponCondForm;
	}

	public void setQueryCouponCondForm(QueryCouponCondForm queryCouponCondForm) {
		this.queryCouponCondForm = queryCouponCondForm;
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
			view.findViewById(R.id.section_header).setVisibility(View.GONE);
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
	public View getSectionItemView(int sectionIndex, int itemIndex,
			View convertView, ViewGroup parent) {
		final CouponRedeemList couponInfo = this.getSectionItem(sectionIndex,
				itemIndex);
		CouponItemViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.tqrm_coupon_list_item_layout, null);
			holder = new CouponItemViewHolder();
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.tqrm_coupon_name_tv);
			// holder.redeemDateTextView = (TextView) convertView
			// .findViewById(R.id.tqrm_coupin_redeem_date_tv);
			holder.redeemTimeTextView = (TextView) convertView
					.findViewById(R.id.tqrm_coupin_redeem_time_tv);
			convertView.setTag(holder);

		} else {
			holder = (CouponItemViewHolder) convertView.getTag();
		}

		holder.nameTextView.setText(couponInfo.getCouponName());
		holder.redeemTimeTextView.setText(DateUtil.format("HH:mm:ss",
				couponInfo.getRedeemTime()));
		return convertView;
	}

	@Override
	public String getSectionDesc(CouponRedeemList info) {
		String orderTimeStr = DateFormat.getDateInstance(DateFormat.FULL)
				.format(info.getRedeemTime());
		return orderTimeStr;
	}
}
