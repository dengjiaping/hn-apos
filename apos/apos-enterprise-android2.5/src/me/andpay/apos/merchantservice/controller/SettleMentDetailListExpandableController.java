package me.andpay.apos.merchantservice.controller;

import java.util.Date;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseExpandableAdapterController;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.base.tools.TimeUtil;
import me.andpay.apos.merchantservice.data.SettlementDetailOrder;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettleMentDetailListExpandableController extends
		BaseExpandableAdapterController<SettlementDetailOrder> {

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		// TODO Auto-generated method stub

		Holder holder;
		if (arg3 == null) {
			arg3 = ShowUtil.LoadXmlView(TiApplication.getContext(),
					R.layout.settle_ment_detail_list_item);

			holder = new Holder();
			holder.title = (TextView) arg3
					.findViewById(R.id.settlement_order_item_title);

			holder.trandingAccounts = (TextView) arg3
					.findViewById(R.id.settlement_order_item_tranding_accounts);
			holder.merchantsCost = (TextView) arg3
					.findViewById(R.id.settlement_order_item_merchants_cost);
			holder.settleMentAccounts = (TextView) arg3
					.findViewById(R.id.settlement_order_item_settlement_accounts);
			arg3.setTag(holder);
		} else {

			holder = (Holder) arg3.getTag();
		}
		final SettlementDetailOrder order = getAdapter().getList().get(arg0)
				.get(arg1);
		holder.title.setText("系统跟踪号:" + order.getSsn());

		holder.trandingAccounts.setText("交易金额:" + order.getTxnAmt());
		holder.merchantsCost.setText("商户花费:" + order.getMchtFee());
		holder.settleMentAccounts.setText("结算金额:" + order.getOutAmt());

		arg3.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				getAdapter().getListener().onEventListener(
						new Object[] { order });
			}
		});
		return arg3;

	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		// TODO Auto-generated method stub
		if (arg2 == null) {
			arg2 = ShowUtil.LoadXmlView(TiApplication.getContext(),
					R.layout.head_data_item);
		}
		TextView time = (TextView) arg2.findViewById(R.id.head_data_item_time);
		SettlementDetailOrder order = getAdapter().getList().get(arg0).get(0);
		Date tempDate = TimeUtil.getInstance().formatString(
				order.getCreateDate(), TimeUtil.DATE_PATTERN_11);
		String transferTime = TimeUtil.getInstance().formatDate(tempDate,
				TimeUtil.DATE_PATTERN_2);

		time.setText(transferTime);

		return arg2;
	}

	class Holder {
		/* 标题 */
		TextView title;

		/* 交易金额 */
		TextView trandingAccounts;
		/* 商家花费 */
		TextView merchantsCost;
		/* 结算金额 */
		TextView settleMentAccounts;

	}

}
