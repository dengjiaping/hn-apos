package me.andpay.apos.merchantservice.controller;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapterController;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.merchantservice.data.Describe;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*描述对象控制器*/
public class DescribeController extends BaseAdapterController<Describe> {

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (arg1 == null) {
			arg1 = ShowUtil.getLayoutView(R.layout.describe_item);
			holder = new Holder();
			holder.title = (TextView) arg1
					.findViewById(R.id.describe_item_title);
			holder.describle = (TextView) arg1
					.findViewById(R.id.describe_item_describe);
			arg1.setTag(holder);
		} else {

			holder = (Holder) arg1.getTag();
		}
		Describe describe = getAdpter().getList().get(arg0);
		holder.title.setText(describe.getTitle());
		holder.describle.setText(describe.getDescribe());
		return arg1;
	}

	@Override
	public void getEvent(View view, int position) {
		// TODO Auto-generated method stub

	}

	class Holder {
		/* 标题 */
		TextView title;
		/* 描述 */
		TextView describle;
	}

}
