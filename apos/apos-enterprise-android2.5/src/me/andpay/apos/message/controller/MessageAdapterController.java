package me.andpay.apos.message.controller;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapterController;
import me.andpay.apos.message.data.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 消息通知控制器
 * 
 * @author Administrator
 *
 */
public class MessageAdapterController extends BaseAdapterController<Message> {
	private LayoutInflater inflater = null;

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (inflater == null) {
			inflater = LayoutInflater.from(getAdpter().getContext());
		}
		Holder holder = null;
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.msg_message_item, null);
			holder = new Holder();
			holder.title = (TextView) arg1
					.findViewById(R.id.msg_message_item_title);
			holder.time = (TextView) arg1
					.findViewById(R.id.msg_message_item_time);
			holder.isReader = (TextView) arg1
					.findViewById(R.id.msg_message_item_isreader);
			arg1.setTag(holder);
		} else {
			holder = (Holder) arg1.getTag();
		}
		Message msg = getAdpter().getList().get(arg0);
		holder.title.setText(msg.getTitle());
		holder.time.setText(msg.getTime());
		holder.isReader.setTextColor(getAdpter()
				.getContext()
				.getResources()
				.getColor(
						msg.isReader() ? R.color.tqm_list_item_amount_col
								: R.color.com_red_color));
		holder.isReader.setText(msg.isReader() ? "已读" : "未读");

		return arg1;
	}

	@Override
	public void getEvent(View view, final int position) {
		// TODO Auto-generated method stub
		view.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (getAdpter().getAdpterEventListener() != null) {
					getAdpter().getAdpterEventListener()
							.onEventListener(
									new Object[] { getAdpter().getList().get(
											position) });
				}
			}
		});
	}

	class Holder {
		TextView title;// 标题
		TextView time;// 时间
		TextView isReader;// 是否已读
	}

}
