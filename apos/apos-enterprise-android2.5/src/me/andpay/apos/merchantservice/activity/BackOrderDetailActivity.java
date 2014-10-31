package me.andpay.apos.merchantservice.activity;

import java.util.ArrayList;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.base.tools.StringUtil;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.SelectImageController;
import me.andpay.apos.merchantservice.data.BringAndBackOrder;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 退货申请详情
 * 
 * @author Administrator
 *
 */
@ContentView(R.layout.back_order_detail)
public class BackOrderDetailActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.back_order_detail_back)
	private ImageView back;

	@InjectView(R.id.back_order_detail_title)
	private TextView title;

	@InjectView(R.id.back_order_detail_dispose)
	private TextView dispose;

	@InjectView(R.id.back_order_detail_time)
	private TextView time;

	@InjectView(R.id.back_order_detail_describle)
	private TextView describle;

	@InjectView(R.id.back_order_detail_gridview)
	private GridView gridView;

	@InjectView(R.id.back_order_detail_fujian)
	private TextView fujian;

	private BaseAdapter<String> imageAdapter;

	private SelectImageController imageController;

	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		BringAndBackOrder order = (BringAndBackOrder) TiFlowControlImpl
				.instanceControl().getFlowContext()
				.get(BringAndBackOrder.class.getName());
		/* 内容设置 */
		title.setText(order.getSubject());
		time.setText(order.getCreateTime());
		describle.setText(order.getDescription());
		/* 设置状态 */
		if (order.getDispose().equals("0")) {
			dispose.setTextColor(getResources().getColor(R.color.auxiliary));
			dispose.setText("待审核");
		} else if (order.getDispose().equals("1")) {
			dispose.setTextColor(getResources().getColor(R.color.auxiliary));
			dispose.setText("已通过");
		} else {
			dispose.setTextColor(getResources().getColor(R.color.red));
			dispose.setText("已拒绝");
		}

		if (StringUtil.isJsonEmpty(order.getImagePaths())) {
			gridView.setVisibility(View.GONE);
			fujian.setText("无附件");
			fujian.setTextColor(getResources().getColor(R.color.red));
		} else {
			imageAdapter = new BaseAdapter<String>();
			imageAdapter.setContext(this);
			String sqlitStr[] = order.getImagePaths().split(",");
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < sqlitStr.length; i++) {

				list.add(sqlitStr[i]);
			}

			imageAdapter.setList(list);

			imageController = new SelectImageController();
			imageController.setState(1);
			imageAdapter.setController(imageController);
			gridView.setAdapter(imageAdapter);
		}
	}

}
