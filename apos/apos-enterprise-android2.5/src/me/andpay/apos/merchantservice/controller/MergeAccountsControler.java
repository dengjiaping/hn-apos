package me.andpay.apos.merchantservice.controller;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapterController;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.merchantservice.data.MergeOrder;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/*并账控制器*/
public class MergeAccountsControler extends
		BaseAdapterController<MergeOrder> {

	class Holder {
		TextView title;
		TextView describe;
		TextView borrowing;
		TextView loan;

	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Holder holder;
		if (arg1 == null) {

			arg1 = ShowUtil.LoadXmlView(TiApplication.getContext(),
					R.layout.merge_order_item);
			holder = new Holder();
			holder.title = (TextView) arg1
					.findViewById(R.id.merge_order_item_title);
			holder.describe = (TextView) arg1
					.findViewById(R.id.merge_order_item_describe);
			holder.borrowing = (TextView) arg1
					.findViewById(R.id.merge_order_item_borrowing);
			holder.loan = (TextView) arg1
					.findViewById(R.id.merge_order_item_loan);
			arg1.setTag(holder);
		}
		holder = (Holder) arg1.getTag();

		MergeOrder order = getAdpter().getList().get(arg0);
		//holder.title.setText(order.getTitle());
		holder.describe.setText(order.getMemo());
		holder.borrowing.setText("借:"+order.getjAmt());
		holder.loan.setText("贷:"+order.getjAmt());

		return arg1;
	}

	@Override
	public void getEvent(View view, final int position) {
		// TODO Auto-generated method stub
		view.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getAdpter().getAdpterEventListener().onEventListener(new Object[]{
						
						0,getAdpter().getList().get(position)
				});
			}
		});
	}
}
