package me.andpay.apos.message.activity;

import java.util.ArrayList;

import me.andpay.ac.consts.VasOptTypes;
import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.base.requestmanage.FinishRequestInterface;
import me.andpay.apos.base.requestmanage.RequestManager;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.activity.HomePageActivity;
import me.andpay.apos.message.controller.MessageAdapterController;
import me.andpay.apos.message.data.Message;
import me.andpay.apos.message.flow.FlowNames;
import me.andpay.timobileframework.cache.HashMap;
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

/**
 * 通知公告首页
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.msg_message_home)
public class MessageActivity extends AposBaseActivity implements
		FinishRequestInterface {
	@EventDelegate(type = DelegateType.method, toMethod = "backMenu", delegateClass = OnClickListener.class)
	@InjectView(R.id.msg_home_show_silder_btn)
	private ImageView menu;// 返回主菜单

	@InjectView(R.id.msg_message_home_listview)
	private ListView listview;// 通知列表

	@Inject
	BaseAdapter<Message> adapter;// 适配器

	@Inject
	MessageAdapterController controller;// 控制器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		adapter.setContext(this);
		adapter.setList(getList());
		adapter.setAdpterEventListener(new AdpterEventListener() {

			public boolean onEventListener(Object... objects) {
				// TODO Auto-generated method stub
				lookMessageDeatail((Message) objects[0]);
				return false;
			}
		});
		adapter.setController(controller);
		listview.setAdapter(adapter);

	}

	/**
	 * 查看详情
	 * 
	 * @param msg
	 */
	private void lookMessageDeatail(Message msg) {

		TiFlowControlImpl.instanceControl().startFlow(this,
				FlowNames.MSG_LOOK_MESSAGE);
		TiFlowControlImpl.instanceControl().getFlowContext()
				.put(Message.class.getName(), msg);
	}

	private ArrayList<Message> getList() {
		ArrayList<Message> list = new ArrayList<Message>();
		for (int i = 0; i < 10; i++) {
			Message msg = new Message();
			msg.setTitle("开会了");
			msg.setTime("2014-02-17");
			msg.setId(i + "");
			if (i % 2 == 0) {
				msg.setReader(true);
			} else {
				msg.setReader(false);
			}
			list.add(msg);
		}
		return list;
	}

	public void backMenu(View v) {
		HomePageActivity hp = (HomePageActivity) this.getParent();
		hp.showSlider();
	}

	/**
	 * 获取通知
	 */
	@Inject
	RequestManager requestManager;

	private void getMessages(String annoType) {
		CommonTermOptRequest optReques = new CommonTermOptRequest();
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("annoType", annoType);
		optReques.setVasRequestContentObj(dataMap);
		optReques.setUserName("13838380439");
		optReques.setOperateType(VasOptTypes.OSS_ANNOUNCEMENT_LIST_QUERY);
		requestManager.setOptRequest(optReques);
		requestManager.addFinishRequestResponse(this);
		requestManager.startService();
		// OSS-ANNO-SYS：系统通知, OSS-ANNO-ACT:活动公告

	}

	private void selectMessage(String id, String action) {
		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id", id);
		dataMap.put("action", action);
		optRequest.setVasRequestContentObj(dataMap);
		optRequest.setUserName("13838380439");
		optRequest.setOperateType(VasOptTypes.OSS_ANNOUNCEMENT_OPERATE_NOTES);
		requestManager.setOptRequest(optRequest);
		requestManager.addFinishRequestResponse(this);

		requestManager.startService();
	}

	public void callBack(Object response) {
		// TODO Auto-generated method stub

	}
}
