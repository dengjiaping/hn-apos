package me.andpay.apos.merchantservice.activity;

import java.util.ArrayList;

import com.NLmpos.Driver.inner.ap;
import com.google.inject.Inject;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.BackOrderController;
import me.andpay.apos.merchantservice.controller.BringOrderController;
import me.andpay.apos.merchantservice.data.BackOrder;
import me.andpay.apos.merchantservice.data.BringOrder;
import me.andpay.apos.merchantservice.flow.FlowNames;
import me.andpay.apos.merchantservice.flow.FlowNote;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.support.TiApplication;

/*银联差错处理*/
@ContentView(R.layout.bank_error_dispose)
public class BankErrorDisposeActivity extends AposBaseActivity implements
		AdpterEventListener {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.bank_error_dispose_back)
	private ImageView back;

	@EventDelegate(type = DelegateType.method, toMethod = "apply", delegateClass = OnClickListener.class)
	@InjectView(R.id.bank_error_dispose_return_apply)
	private TextView apply;

	@EventDelegate(type = DelegateType.method, toMethod = "report", delegateClass = OnClickListener.class)
	@InjectView(R.id.bank_error_dispose_order_report)
	private TextView report;

	@EventDelegate(type = DelegateType.method, toMethod = "add", delegateClass = OnClickListener.class)
	@InjectView(R.id.bank_error_dispose_add)
	private TextView add;

	@InjectView(R.id.bank_error_dispose_listview)
	private ListView listView;

	private BaseAdapter<BackOrder> applyAdapter;
	private BaseAdapter<BringOrder> bringAdapter;

	@Inject
	private BackOrderController backOrderController;

	@Inject
	private BringOrderController bringOrderController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		applyAdapter = new BaseAdapter<BackOrder>();
		applyAdapter.setContext(this);
		applyAdapter.setController(backOrderController);
		applyAdapter.setList(getBackOrders());
		applyAdapter.setAdpterEventListener(this);

		bringAdapter = new BaseAdapter<BringOrder>();
		bringAdapter.setContext(this);
		bringAdapter.setController(bringOrderController);
		bringAdapter.setList(getBringOrders());

		bringAdapter.setAdpterEventListener(this);

		apply(null);
	}

	private ArrayList<BackOrder> getBackOrders() {

		ArrayList<BackOrder> list = new ArrayList<BackOrder>();
		for (int i = 0; i < 5; i++) {
			BackOrder order = new BackOrder();
			order.setTitle("标题");
			order.setTime("2014-09-10 9:09");
			order.setDispose("已处理");
			order.setDescrible("不知道写什么");
			list.add(order);
		}
		return list;
	}

	private ArrayList<BringOrder> getBringOrders() {
		ArrayList<BringOrder> list = new ArrayList<BringOrder>();
		for (int i = 0; i < 5; i++) {
			BringOrder order = new BringOrder();
			order.setTitle("标题11");
			order.setTime("2014-09-10 9:09");
			order.setDispose("已处理");
			order.setDescrible("不知道写什么");
			list.add(order);
		}
		return list;
	}

	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/* 申请 */

	@SuppressLint("NewApi")
	public void apply(View view) {
		apply.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		apply.setTextColor(getResources().getColor(android.R.color.white));
		report.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		report.setTextColor(getResources()
				.getColor(android.R.color.darker_gray));
		listView.setAdapter(applyAdapter);
	}

	/* 订单上报 */
	@SuppressLint("NewApi")
	public void report(View view) {
		report.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		report.setTextColor(getResources().getColor(android.R.color.white));
		apply.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		apply.setTextColor(getResources().getColor(android.R.color.darker_gray));
		listView.setAdapter(bringAdapter);
	}

	/* 添加退单 */
	public void add(View view) {
		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowNote.ADD_BACK_ORDER);

	}

	public boolean onEventListener(Object... objects) {
		// TODO Auto-generated method stub
		switch ((Integer) objects[0]) {
		case 0:// 退货详情
			TiFlowControlImpl.instanceControl().nextSetup(this,
					FlowNote.BACK_ORDER_DETAIL);
			break;

		case 1:// 上报详情
			TiFlowControlImpl.instanceControl().nextSetup(this,
					FlowNote.REPORT_ORDER_DETAIL);
			break;
		}
		return false;
	}

}
