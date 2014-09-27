package me.andpay.apos.lft.activity;

import java.util.ArrayList;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.lft.controller.OftenPersonController;
import me.andpay.apos.lft.data.OftenUser;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.inject.Inject;

@ContentView(R.layout.lft_transfer_accouts_often_person)
public class OftenPersonActivity extends AposBaseActivity {
	@InjectView(R.id.lft_transfer_accounts_often_person_back)
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	private ImageView back;// 返回

	@InjectView(R.id.lft_transfer_accounts_often_person_listview)
	private ListView listView;// 联系人列表视图

	@Inject
	private BaseAdapter<OftenUser> adapter;

	@Inject
	private OftenPersonController oftenPersonController;

	private AdpterEventListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置Adapter
		adapter.setContext(this);
		adapter.setController(oftenPersonController);
		adapter.setList(getList());
		listener = new AdpterEventListener() {

			public boolean onEventListener(Object... objects) {
				// TODO Auto-generated method stub
				slectBack((OftenUser) objects[0]);
				return true;
			}
		};
		adapter.setAdpterEventListener(listener);

		listView.setAdapter(adapter);

	}

	public ArrayList<OftenUser> getList() {
		ArrayList<OftenUser> list = new ArrayList<OftenUser>();

		OftenUser oftenUser = new OftenUser();
		oftenUser.setUserName("单小萍");
		oftenUser.setBankCardNumber("6222 8029 2043 1035 784");
		oftenUser.setBankName("招商银行");

		OftenUser oftenUser1 = new OftenUser();
		oftenUser1.setUserName("张赛");
		oftenUser1.setBankCardNumber("6222 8029 2043 1035 784");
		oftenUser1.setBankName("招商银行");

		OftenUser oftenUser2 = new OftenUser();
		oftenUser2.setUserName("唐英华");
		oftenUser2.setBankCardNumber("6222 8029 2043 1035 784");
		oftenUser2.setBankName("招商银行");

		OftenUser oftenUser3 = new OftenUser();
		oftenUser3.setUserName("范强");
		oftenUser3.setBankCardNumber("6222 8029 2043 1035 784");
		oftenUser3.setBankName("招商银行");

		OftenUser oftenUser4 = new OftenUser();
		oftenUser4.setUserName("刘大伟");
		oftenUser4.setBankCardNumber("6222 8029 2043 1035 784");
		oftenUser4.setBankName("招商银行");

		OftenUser oftenUser5 = new OftenUser();
		oftenUser5.setUserName("王亮波");
		oftenUser5.setBankCardNumber("6222 8029 2043 1035 784");
		oftenUser5.setBankName("招商银行");
		list.add(oftenUser);
		list.add(oftenUser1);
		list.add(oftenUser2);
		list.add(oftenUser3);
		list.add(oftenUser4);
		list.add(oftenUser5);
		return list;
	}

	/*
	 * 
	 * ===================================================================事件触发====
	 * ========================================================
	 */
	/**
	 * 选中联系人返回
	 */
	private void slectBack(OftenUser oftenUser) {
		TiFlowControlImpl.instanceControl().setFlowContextData(oftenUser);
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	/**
	 * 返回
	 * 
	 * @param v
	 */
	public void back(View v) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

}
