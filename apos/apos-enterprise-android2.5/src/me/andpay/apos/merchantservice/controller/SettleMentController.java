package me.andpay.apos.merchantservice.controller;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapterController;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.merchantservice.data.SelementOrder;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettleMentController extends BaseAdapterController<SelementOrder> {

	class Holder {
		/* 标题 */
		TextView title;
		/* 交易类型 */
		TextView trandingCount;

		/* 交易金额 */
		TextView trandingAccounts;
		/* 商家花费 */
		TextView merchantsCost;
		/* 结算金额 */
		TextView settleMentAccounts;

	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		Holder holder;
		if (arg1 == null){
			arg1 = ShowUtil.LoadXmlView(TiApplication.getContext(),
					R.layout.settlement_order_item);
			holder = new Holder();
			holder.title = (TextView) arg1
					.findViewById(R.id.settlement_order_item_title);
			holder.trandingCount = (TextView) arg1
					.findViewById(R.id.settlement_order_item_tranding_count);

			holder.trandingAccounts = (TextView) arg1
					.findViewById(R.id.settlement_order_item_tranding_accounts);
			holder.merchantsCost = (TextView) arg1
					.findViewById(R.id.settlement_order_item_merchants_cost);
			holder.settleMentAccounts = (TextView) arg1
					.findViewById(R.id.settlement_order_item_settlement_accounts);
			arg1.setTag(holder);
		} else {

			holder = (Holder) arg1.getTag();
		}

		final SelementOrder order = getAdpter().getList().get(arg0);
//		holder.title.setText(order.getTitle());
//		holder.trandingCount.setText(order.getTradingCount());
//
//		holder.trandingAccounts.setText(order.getTradingAccounts());
//		holder.merchantsCost.setText(order.getMerchantsCost());
//		holder.settleMentAccounts.setText(order.getSettlementAccount());

		return arg1;

	}

	@Override
	public void getEvent(View view, final int position) {
		// TODO Auto-generated method stub
		view.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				getAdpter().getAdpterEventListener()
						.onEventListener(
								new Object[] { 1,
										getAdpter().getList().get(position) });

			}
		});
	}

}
