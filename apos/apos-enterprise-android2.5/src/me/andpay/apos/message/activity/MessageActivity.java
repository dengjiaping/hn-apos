package me.andpay.apos.message.activity;

import java.util.ArrayList;

import me.andpay.ac.consts.VasOptTypes;
import me.andpay.ac.consts.ac.vas.ops.VasOptPropNames;
import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.operation.CommonTermOptResponse;
import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.base.requestmanage.FinishRequestInterface;
import me.andpay.apos.base.requestmanage.RequestManager;
import me.andpay.apos.base.tools.StringUtil;
import me.andpay.apos.cmview.CommonDialog;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 通知公告首页
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.msg_message_home)
public class MessageActivity extends AposBaseActivity implements
		FinishRequestInterface, OnClickListener {
	@EventDelegate(type = DelegateType.method, toMethod = "backMenu", delegateClass = OnClickListener.class)
	@InjectView(R.id.msg_home_show_silder_btn)
	private ImageView menu;// 返回主菜单

	@InjectView(R.id.msg_message_home_listview)
	private ListView listview;// 通知列表

	@EventDelegate(type = DelegateType.method, toMethod = "selectMessage", delegateClass = OnClickListener.class)
	@InjectView(R.id.msg_message_home_message)
	private TextView message;

	@EventDelegate(type = DelegateType.method, toMethod = "selectNote", delegateClass = OnClickListener.class)
	@InjectView(R.id.msg_message_home_note)
	private TextView note;

	private View faile;

	private View empty;

	private Button refreshBtn1;
	private Button refreshBtn2;

	@Inject
	BaseAdapter<Message> messageAdapter;// 适配器
	@Inject
	MessageAdapterController messageController;// 控制器

	@Inject
	BaseAdapter<Message> noteAdapter;
	@Inject
	MessageAdapterController noteController;

	private int currentState = 0;// 0系统，1活动公告

	public void selectMessage(View view) {
		selectDataStatus(1);
		currentState = 0;
		message.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		message.setTextColor(getResources().getColor(android.R.color.white));

		note.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		note.setTextColor(getResources().getColor(android.R.color.darker_gray));
		listview.setAdapter(messageAdapter);
		if (messageAdapter.getList().size() <= 0) {
			messagePage = 1;
			txnDialog.show();

			getMessages(pageSize, messagePage, "OSS-ANNO-ACT");
		}

	}

	public void selectNote(View view) {
		selectDataStatus(1);
		currentState = 1;
		note.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_blue_img));
		note.setTextColor(getResources().getColor(android.R.color.white));

		message.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.com_button_img));
		message.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
		listview.setAdapter(noteAdapter);
		if (noteAdapter.getList().size() <= 0) {
			notePage = 1;
			txnDialog.show();
			getMessages(pageSize, notePage, "OSS-ANNO-SYS");
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		txnDialog = new CommonDialog(this, "获取中...");

		faile = findViewById(R.id.msg_message_home_faile);

		empty = findViewById(R.id.msg_message_home_empty);

		refreshBtn1 = (Button) faile.findViewById(R.id.refresh_btn);
		refreshBtn2 = (Button) empty.findViewById(R.id.refresh_btn);
		refreshBtn1.setOnClickListener(this);
		refreshBtn2.setOnClickListener(this);
		messageAdapter.setContext(this);

		messageAdapter.setAdpterEventListener(new AdpterEventListener() {

			public boolean onEventListener(Object... objects) {
				// TODO Auto-generated method stub
				lookMessageDeatail((Integer) objects[0], (Message) objects[1]);
				return false;
			}
		});
		messageAdapter.setController(messageController);

		noteAdapter.setContext(this);
		noteAdapter.setAdpterEventListener(new AdpterEventListener() {

			public boolean onEventListener(Object... objects) {
				// TODO Auto-generated method stub
				lookMessageDeatail((Integer) objects[0], (Message) objects[1]);
				return false;
			}
		});
		noteAdapter.setController(noteController);
		// selectNote(null);
		selectMessage(null);

	}

	/**
	 * 查看详情
	 * 
	 * @param msg
	 */
	private void lookMessageDeatail(int state, final Message msg) {
		switch (state) {
		case 0:

			TiFlowControlImpl.instanceControl().startFlow(this,
					FlowNames.MSG_LOOK_MESSAGE);
			TiFlowControlImpl.instanceControl().getFlowContext()
					.put(Message.class.getName(), msg);
			selectMessage(msg.getId(), "OSS-ANNO-R");
			break;

		case 1:
			new AlertDialog.Builder(this)
					.setTitle("你确定要删除这条消息吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									selectMessage(msg.getId(), "OSS-ANNO-D");
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							}).create().show();
			break;
		}

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
	private CommonDialog txnDialog;
	private int pageSize = 10;
	private int messagePage = 1;
	private int notePage = 1;

	private void getMessages(int pageSize, int page, String annoType) {
		CommonTermOptRequest optReques = new CommonTermOptRequest();
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("annoType", annoType);
		optReques.setVasRequestContentObj(dataMap);
		optReques.setUserName("13838380439");
		optReques.setPageSize(pageSize);

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
		requestManager.startService();
	}

	/**
	 * 0 空数据1有数据 -1 失败
	 * 
	 * @param state
	 */
	private void selectDataStatus(int state) {
		switch (state) {
		case 0:
			empty.setVisibility(View.VISIBLE);
			faile.setVisibility(View.GONE);
			break;

		case 1:
			empty.setVisibility(View.GONE);
			faile.setVisibility(View.GONE);
			break;
		case -1:
			empty.setVisibility(View.GONE);
			faile.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * 刷新
	 * 
	 * @param view
	 */
	public void refresh() {
		selectDataStatus(1);
		switch (currentState) {
		case 0:
			txnDialog.show();
			messagePage = 1;
			getMessages(pageSize, messagePage, "OSS-ANNO-ACT");
			break;

		case 1:
			txnDialog.show();
			notePage = 1;
			getMessages(pageSize, notePage, "OSS-ANNO-SYS");
			break;
		}

	}

	public void callBack(Object response) {
		// TODO Auto-generated method stub
		if (txnDialog != null && txnDialog.isShowing()) {
			txnDialog.cancel();
		}
		if (response == null
				&& (currentState == 0 ? messagePage : notePage) == 1) {
			selectDataStatus(-1);
			return;
		}
		if (response == null) {
			selectDataStatus(1);
			return;
		}
		CommonTermOptResponse optResponse = (CommonTermOptResponse) response;
		if (!optResponse.isSuccess()
				&& (currentState == 0 ? messagePage : notePage) == 1) {
			selectDataStatus(-1);
			return;
		}
		if (!optResponse.isSuccess()) {
			selectDataStatus(1);
			return;
		}

		String jsonStr = (String) optResponse
				.getVasRespContentObj(VasOptPropNames.UNRPT_RES);
		if (StringUtil.isEmpty(jsonStr)
				&& (currentState == 0 ? messagePage : notePage) == 1) {
			selectDataStatus(0);
			return;
		}

		if (StringUtil.isEmpty(jsonStr)) {

			selectDataStatus(1);
			return;
		}

		selectDataStatus(1);
		ArrayList<Message> list = Message.getArrays(jsonStr);
		switch (currentState) {
		case 0:
			messagePage++;
			messageAdapter.getList().addAll(list);
			messageAdapter.notifyDataSetChanged();
			break;

		case 1:
			notePage++;
			noteAdapter.getList().addAll(list);
			noteAdapter.notifyDataSetChanged();
			break;
		}

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.refresh_btn) {
			refresh();
		}
	}

}
