package me.andpay.apos.lft.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.base.view.CustomDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.lft.controller.CitySelectController;
import me.andpay.apos.lft.even.PayElectricityTextWatcherEventControl;
import me.andpay.apos.lft.flow.CityTable;
import me.andpay.apos.lft.flow.FlowConstants;
import me.andpay.apos.lft.flow.PayCostType;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 缴电费
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_pay_electricity)
public class PayElectricityCostActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_pay_electricity_back)
	private ImageView back;// 返回

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = PayElectricityTextWatcherEventControl.class)
	@InjectView(R.id.lft_pay_electricity_city)
	public EditText city;// 城市

	@EventDelegate(type = DelegateType.method, toMethod = "citySelect", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_pay_electricity_city_select)
	private View citySelect;// 城市选择

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = PayElectricityTextWatcherEventControl.class)
	@InjectView(R.id.lft_pay_electricity_company)
	public EditText company;// 单位

	@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = PayElectricityTextWatcherEventControl.class)
	@InjectView(R.id.lft_pay_electricity_customer_id)
	public EditText customer;// 客户

	@EventDelegate(type = DelegateType.method, toMethod = "next", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_pay_electricity_next)
	public Button next;// 下一步

	/**
	 * 返回
	 * 
	 * @param v
	 */
	public void back(View v) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/**
	 * 城市选择
	 * 
	 * @param v
	 */
	private CustomDialog dialog = null;

	public void citySelect(View v) {
		if (dialog == null) {
			dialog = ShowUtil.getCustomDialog(this, R.layout.lft_select_dailog,
					1);
			TextView title = (TextView) dialog.getcView().findViewById(
					R.id.lft_select_dailog_title);
			ListView listView = (ListView) dialog.getcView().findViewById(
					R.id.lft_select_dailog_listview);

			title.setText("请选择城市");
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < CityTable.city.length; i++) {
				list.add(i + "");
			}
			BaseAdapter<String> adapter = new BaseAdapter<String>();
			adapter.setContext(this);
			adapter.setList(list);
			adapter.setAdpterEventListener(new AdpterEventListener() {

				public boolean onEventListener(Object... objects) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					int index = (Integer) objects[0];
					city.setText(CityTable.city[index][0]);
					company.setText(CityTable.city[index][1]);
					return true;
				}
			});
			CitySelectController controller = new CitySelectController();
			adapter.setController(controller);
			listView.setAdapter(adapter);
		}
		dialog.show();
	}

	/**
	 * 下一步
	 */
	public void next(View v) {
		if (!isCorrectCustom()) {
			ShowUtil.showShortToast(this, "客户单号不合法");
			return;
		}

		TiFlowControlImpl.instanceControl().getFlowContext()
				.put("type", PayCostType.ELECTRICITY);

		Map<String, String> data = new HashMap<String, String>();
		data.put("unit", company.getText().toString());
		data.put("serialNumber", customer.getText().toString());

		TiFlowControlImpl.instanceControl().nextSetup(this,
				FlowConstants.PAY_COST_QUERY, data);
	}

	/**
	 * 正确的
	 * 
	 * @return
	 */
	private boolean isCorrectCustom() {
		String content = customer.getText().toString();
		if (content.length() < 2) {
			return false;
		}
		/**
		 * 判定前两位是否正确
		 */
		int number = 0;
		try {
			String startTwo = content.substring(0, 2);
			number = Integer.parseInt(startTwo);
		} catch (Exception ex) {
			return false;
		}
		if (!((number >= 11 && number <= 24) || (number >= 61 && number <= 74))) {
			return false;
		}
		return true;

	}

}
