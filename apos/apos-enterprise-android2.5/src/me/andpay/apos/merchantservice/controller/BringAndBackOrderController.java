package me.andpay.apos.merchantservice.controller;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapterController;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.merchantservice.data.BringAndBackOrder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/*调单控制器*/
public class BringAndBackOrderController extends BaseAdapterController<BringAndBackOrder> {

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Holder holder;
		if (arg1 == null) {
			arg1 = ShowUtil.getLayoutView(R.layout.order_item);
			holder = new Holder();

			holder.title = (TextView) arg1.findViewById(R.id.order_item_title);
			holder.time = (TextView) arg1.findViewById(R.id.order_item_time);
			holder.dispose = (TextView) arg1
					.findViewById(R.id.order_item_dispose);
			arg1.setTag(holder);
		} else {

			holder = (Holder) arg1.getTag();
		}
		BringAndBackOrder order = getAdpter().getList().get(arg0);
		holder.title.setText(order.getSubject());
		holder.time.setText(order.getCreateTime());
		if(order.getDispose().equals("0")){
			holder.dispose.setTextColor(getAdpter().getContext().getResources().getColor(R.color.red));
			holder.dispose.setText("待审核");
		}else if(order.getDispose().equals("1")){
			holder.dispose.setTextColor(getAdpter().getContext().getResources().getColor(R.color.main_show_font_color));
			holder.dispose.setText("已通过");
		}else{
			holder.dispose.setTextColor(getAdpter().getContext().getResources().getColor(R.color.red));
			holder.dispose.setText("已拒绝");
		}
		
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
						new Object[]{0,
								getAdpter().getList().get(position)});
				
			}
		});
	}

	class Holder {
		TextView title;
		TextView time;
		TextView dispose;
	}

}
