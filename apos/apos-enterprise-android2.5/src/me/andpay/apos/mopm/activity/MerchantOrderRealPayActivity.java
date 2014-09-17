package me.andpay.apos.mopm.activity;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.InitMsrKeyServiceImpl;
import me.andpay.apos.cardreader.callback.DefaultCardReaderCallBack;
import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.util.APOSAlarmUtil;
import me.andpay.apos.lam.LamProvider;
import me.andpay.apos.lam.action.LoginAction;
import me.andpay.apos.lam.form.LoginUserForm;
import me.andpay.apos.lam.util.DeviceInfoUtil;
import me.andpay.apos.mopm.callback.impl.MOPMBackgroudLoginCallbackImpl;
import me.andpay.apos.mopm.callback.impl.MerchantOrderPayCallbackImpl;
import me.andpay.apos.mopm.callback.impl.OrderPayUtil;
import me.andpay.apos.mopm.order.OrderPayContext;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.orderpay.OrderPayRequest;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import roboguice.inject.ContentView;
import android.os.Bundle;

import com.google.inject.Inject;

@ContentView(R.layout.lam_firstpage_layout)
public class MerchantOrderRealPayActivity extends AposBaseActivity {

	public CommonDialog checkDialog;

	@Inject
	public TxnControl txnControl;

	@Inject
	private TiRpcClient tiRpcClient;

	@Inject
	private InitMsrKeyServiceImpl initMsrKeyServiceImpl;
	@Inject
	private DefaultCardReaderCallBack defaultCardReaderCallBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Intent intent = new Intent(MerchantOrderRealPayActivity.this,
		// AposMainService.class);
		// startService(intent);
		APOSAlarmUtil.startMainAlarm(getApplicationContext());
		// 初始化读卡器
		CardReaderManager.initCardReader(getApplicationContext(),
				AposCardReaderUtil.genAposSwiperContext(getAppConfig()));
		CardReaderManager.setInitMsrKeyService(initMsrKeyServiceImpl);
		CardReaderManager.setDefaultCallBack(defaultCardReaderCallBack);
	}

	@Override
	protected void onResumeProcess() {
		DeviceInfoUtil.setDeviceInfo(this);
		OrderPayContext orderPayContext = OrderPayUtil.getOrderPayContext();
		if (orderPayContext.isNeedAutoLogin())
			submitRequest(orderPayContext.getOrderPayRequest());
		else
			TiFlowControlImpl.instanceControl().nextSetup(this,
					FlowConstants.FAILED);
	}

	public void submitRequest(OrderPayRequest orderPayRequest) {
		checkDialog = new CommonDialog(this, "订单正在提交...");
		checkDialog.setCancelable(false);
		checkDialog.show();
		EventRequest request = MerchantOrderRealPayActivity.this
				.generateSubmitRequest(MerchantOrderRealPayActivity.this);
		request.open(LoginAction.DOMAIN_NAME,
				LoginAction.ACTION_ORDER_SUBMIT_CHCEK, Pattern.async);
		request.callBack(new MerchantOrderPayCallbackImpl(
				MerchantOrderRealPayActivity.this));

		getAppContext().setAttribute(RuntimeAttrNames.CLIENT_ORDER_PAY_FLAG,
				RuntimeAttrNames.CLIENT_ORDER_PAY_FLAG);
		request.submit();
	}

	@SuppressWarnings("unchecked")
	public void checkAndLoginOnBackgroud() {
		if (tiRpcClient.isConfigSSL())
			return;
		String hisUser = (String) getAppConfig().getAttribute(
				ConfigAttrNames.LOGIN_HIS_USER);
		String hisPassword = (String) getAppConfig().getAttribute(
				ConfigAttrNames.LOGIN_HIS_PASSWORD);
		if (StringUtil.isEmpty(hisUser) || StringUtil.isEmpty(hisPassword)) {
			return;
		}
		LoginUserForm form = new LoginUserForm();
		form.setUserName(hisUser);
		form.setPassword(hisPassword);
		OrderPayContext orderPayContext = OrderPayUtil.getOrderPayContext();
		form.setAutoFlag(orderPayContext.isNeedAutoLogin());
		form.setEncryptedPwd(true);
		EventRequest request = this.generateSubmitRequest(this);
		request.open(LamProvider.LAM_DOMAIN_LOGIN,
				LamProvider.LAM_ACTION_LOGIN_DEFAULT, Pattern.async);
		request.getSubmitData().put(LoginUserForm.FORM_NAME, form);
		request.callBack(new MOPMBackgroudLoginCallbackImpl());
		request.submit();
	}

}
