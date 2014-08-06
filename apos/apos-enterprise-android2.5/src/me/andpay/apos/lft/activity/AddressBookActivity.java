package me.andpay.apos.lft.activity;

import java.util.ArrayList;

import com.google.inject.Inject;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.lft.adapter.AdpterEventListener;
import me.andpay.apos.lft.adapter.BaseAdapter;
import me.andpay.apos.lft.adapter.BaseAdapterController;
import me.andpay.apos.lft.adapter.controller.PhoneNumberController;
import me.andpay.apos.lft.data.PhoneNumber;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;

/**
 * 手机通讯录
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.lft_address_book)
public class AddressBookActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.lft_address_book_back)
	private ImageView back;// 返回

	@Inject
	private BaseAdapter<PhoneNumber> adapter;// 适配器
	@Inject
	private PhoneNumberController controller;// 控制器

	private ArrayList<PhoneNumber> list;// 手机联系人数组
	
	@InjectView(R.id.lft_address_book_listview)
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listView.setAdapter(adapter);
		adapter.setContext(this);
		list = new ArrayList<PhoneNumber>();
		adapter.setList(list);
		adapter.setController(controller);
		AdpterEventListener listener = new AdpterEventListener() {

			public boolean onEventListener(Object... objects) {
				// TODO Auto-generated method stub
				
				
				selected((PhoneNumber)objects[0]);
				return false;
			}
		};
		adapter.setAdpterEventListener(listener);
		/**
		 * 拉取联系人
		 */
		updatePhone();

	}
	/**
	 * 选中
	 */
	private void selected(PhoneNumber phoneNumber){
		TiFlowControlImpl.instanceControl().getFlowContext().put(PhoneNumber.class.getName(),phoneNumber);
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

	/**
	 * 更新联系人数据
	 */
	private void updatePhone() {
		PhoneAysTask phoneAysTask = new PhoneAysTask();

		phoneAysTask.execute();
	}

	/* 获得通信录 */
	private ArrayList<PhoneNumber> getPhoneContacts() {
		ArrayList<PhoneNumber> list = new ArrayList<PhoneNumber>();
		ContentResolver resolver = this.getContentResolver();

		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, new String[] {
				Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER }, null,
				null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String number = phoneCursor.getString(2);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (null == number || "".equals(number) || " ".equals(number))
					continue;
				int index = number.indexOf("+86");
				if (index != -1) {
					number = number.substring(index + 3);
				}
				// 得到联系人名称
				PhoneNumber pNumber = new PhoneNumber();

				String username = phoneCursor.getString(1);

				pNumber.setDisplayName(username);
				pNumber.setDisplayNumber(number);
				list.add(pNumber);
			}
			phoneCursor.close();
		}
		return list;
	}

	private class PhoneAysTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			list = getPhoneContacts();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			adapter.setList(list);
			adapter.notifyDataSetChanged();

		}

	}
}
