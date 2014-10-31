package me.andpay.apos.message.activity;

import com.google.inject.Inject;

import me.andpay.ac.consts.VasOptTypes;
import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.apos.R;
import me.andpay.apos.base.requestmanage.RequestManager;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.message.data.Message;
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
import android.widget.TextView;

/**
 * 通知详情
 * 
 * @author Administrator
 * 
 */
@ContentView(R.layout.msg_message_deatail)
public class MessageDeatailActivity extends AposBaseActivity {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.msg_message_deatail_back)
	private ImageView back;// 返回

	@InjectView(R.id.msg_message_deatail_content)
	private TextView content;// 内容

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Message message = (Message) TiFlowControlImpl.instanceControl()
				.getFlowContext().get(Message.class.getName());
		if (message != null) {
			content.setText(message.getContent());
		}
		if(!message.getAction().endsWith("OSS-ANNO-R")){
		selectMessage(message.getId(), "OSS-ANNO-R");
		}

	}

	public void back(View v) {

		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	@Inject
	RequestManager requestManager;

	private void selectMessage(String id, String action) {

		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id", id);
		dataMap.put("action", action);
		optRequest.setVasRequestContentObj(dataMap);
		LoginUserInfo logInfo = (LoginUserInfo) this.getAppContext()
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		optRequest.setUserName(logInfo.getUserName());
		optRequest.setOperateType(VasOptTypes.OSS_ANNOUNCEMENT_OPERATE_NOTES);
		requestManager.setOptRequest(optRequest);
		requestManager.startService();
	}
}
