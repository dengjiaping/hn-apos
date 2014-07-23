package me.andpay.apos.lam.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.cmview.ResizeLayout;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.common.util.APOSGifUtil;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.lam.callback.impl.LoginCallbackImpl;
import me.andpay.apos.lam.event.ForgetPasswordEventControl;
import me.andpay.apos.lam.event.LoginSumitButtonEventControl;
import me.andpay.apos.lam.event.LoginTextWatcherEventControl;
import me.andpay.apos.lam.event.PasswordTextEventControl;
import me.andpay.apos.lam.event.ShowHelpClickControl;
import me.andpay.apos.lam.form.LoginUserForm;
import me.andpay.apos.mopm.callback.impl.MOPMLoginCallbackImpl;
import me.andpay.apos.mopm.callback.impl.OrderPayUtil;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import me.andpay.timobileframework.mvc.support.TiActivity;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 用户登录
 * 
 * @author cpz
 * 
 */
@ContentView(R.layout.lam_login_layout)
@FormBind(formBean = LoginUserForm.class)
public class LoginActivity extends TiActivity implements ValueContainer {

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = true, delegateClass = OnClickListener.class, toEventController = LoginSumitButtonEventControl.class)
	@InjectView(R.id.lam_login_btn)
	public Button loginBtn;

	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = PasswordTextEventControl.class),
			@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", isNeedFormBean = false, delegateClass = TextWatcher.class, toEventController = LoginTextWatcherEventControl.class) })
	@InjectView(R.id.lam_username_autotext)
	public EditText userNameText;

	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = PasswordTextEventControl.class),
			@EventDelegate(type = DelegateType.eventController, delegate = "addTextChangedListener", isNeedFormBean = false, delegateClass = TextWatcher.class, toEventController = LoginTextWatcherEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = true, delegateClass = OnKeyListener.class, toEventController = LoginSumitButtonEventControl.class) })
	@InjectView(R.id.lam_passwd_edittext)
	public EditText passwordText;

	@InjectView(R.id.lam_foot_lay)
	public RelativeLayout loginFoot;

	@InjectView(R.id.com_app_copyright_textv)
	public TextView copyRightText;

	// @InjectView(R.id.lam_service_introduce_text)
	// private TextView serviceTxnView;

	@InjectView(R.id.lam_forget_password_text)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = true, delegateClass = OnClickListener.class, toEventController = ForgetPasswordEventControl.class)
	private TextView forgetPassTxnView;

	@Inject
	public TxnControl txnControl;

	public CommonDialog loginDialog;

	// @Inject
	// private FormProcesser formProcesser;

	@InjectView(R.id.lam_login_content_lay)
	public RelativeLayout loginContent;

	@Inject
	public PayTxnInfoDao payTxnInfoDao;
	@Inject
	public LocationService locationService;

	@InjectView(R.id.lam_login_content_scroll)
	public ScrollView mainScroll;

	@InjectView(R.id.lam_login_main_lay)
	public ResizeLayout mainLay;

	@InjectView(R.id.lam_save_pwd_checkbox)
	public CheckBox autoLoginCheckBox;

	// @Inject
	// private TiRpcClientProxy proxy;

	@InjectView(R.id.lam_help_gif_view)
	@EventDelegate(type = DelegateType.eventController, delegateClass = OnClickListener.class, toEventController = ShowHelpClickControl.class)
	private GifImageView helpGif;

	@Inject
	private TiRpcClient tiRpcClient;

	private GifDrawable gifDrawable;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		tiRpcClient.restartTransport();
		userNameText.requestFocus();
		// Intent sIntent = new Intent(this, AposMainService.class);
		// startService(sIntent);

		PackageInfo pinfo;
		try {
			pinfo = getPackageManager().getPackageInfo("me.andpay.apos",
					PackageManager.GET_CONFIGURATIONS);
			copyRightText.setText(copyRightText.getText().toString()
					.replace("$VERSION$", pinfo.versionName));
		} catch (NameNotFoundException e) {

		}

		APOSGifUtil.startGif(gifDrawable, helpGif, getResources(),
				R.drawable.icon_help_gif);

		// DisplayMetrics metric = new DisplayMetrics();
		// helpGif.setShowDimension(Float.valueOf((60 *
		// metric.density)).intValue(), Float
		// .valueOf((60 * metric.density)).intValue());
		// helpGif.setVisibility(View.VISIBLE);
		// helpGif.setGifImage(R.drawable.icon_help_gif);

		userNameText.requestFocus();
		String hisUser = (String) getAppConfig().getAttribute(
				ConfigAttrNames.LOGIN_HIS_USER);
		if (StringUtil.isNotBlank(hisUser)) {
			userNameText.setText(hisUser);
			Selection.setSelection(userNameText.getEditableText(),
					userNameText.length());
		}
		String logoutTag = ResourceUtil.getIntentStr(getIntent(),
				CommonProvider.LOGIN_OUT);
		if (StringUtil.isNotBlank(logoutTag))
			passwordText.setText("");
		else {
			String hisPassword = (String) getAppConfig().getAttribute(
					ConfigAttrNames.LOGIN_HIS_PASSWORD);
			if (StringUtil.isNotBlank(hisPassword)) {
				passwordText.setText(hisPassword);
			}
		}
		String errorMsg = getIntent().getStringExtra("errorMsg");
		if (StringUtil.isNotBlank(errorMsg)) {
			PromptDialog dialog = new PromptDialog(this, "提示", errorMsg);
			dialog.show();
			return;
		}

		// String loginOutFlag = getIntent().getStringExtra(
		// CommonProvider.LOGIN_OUT);

		/*
		 * if (!MobileUtil.isNetworkConnected(this.getApplicationContext())) {
		 * final PromptDialog dialog = new PromptDialog(this,
		 * ResourceUtil.getString( this, R.string.com_show_str),
		 * ResourceUtil.getString(this,
		 * R.string.com_please_check_network_set_str));
		 * dialog.setSureButtonOnclickListener(new OnClickListener() { public
		 * void onClick(View v) { dialog.dismiss(); } }); dialog.show(); }
		 */// else {
			// autoLogin(loginOutFlag);
		// }

	}

	/*
	 * private void autoLogin(String loginOutFlag) {
	 * 
	 * if (passwordText.length() > 0 && StringUtil.isBlank(loginOutFlag)) { try
	 * { FormBean formBean = formProcesser.loadFormBean(this,
	 * FormProcessPattern.ANDROID); submitLogin(formBean); } catch
	 * (FormException e) { Log.e(this.getClass().getName(), "login error", e); }
	 * }
	 * 
	 * }
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 返回退出
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showBackDialog(this);
			return true;
		}

		return true;

	}

	public void showBackDialog(Activity activity) {
		final OperationDialog dialog = new OperationDialog(activity,
				getResources().getString(R.string.com_show_str), getResources()
						.getString(R.string.com_sure_logout__apos_str), true);
		final Activity inActivity = activity;
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				inActivity.finish();
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
				
			}
		});

		dialog.setCancelButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public void submitLogin(FormBean formBean) {

		LoginUserForm userForm = (LoginUserForm) formBean.getData();
		if (autoLoginCheckBox.isChecked()) {
			userForm.setAutoFlag(true);
		}
		String hisPassword = (String) getAppConfig().getAttribute(
				ConfigAttrNames.LOGIN_HIS_PASSWORD);
		if (hisPassword.equals(userForm.getPassword())) {
			userForm.setEncryptedPwd(true);
		}

		EventRequest request = this.generateSubmitRequest(this);
		request.open(formBean, Pattern.async);

		loginDialog = new CommonDialog(this, getResources().getString(
				R.string.lam_logining_dialog_str));
		loginDialog.setCancelable(false);
		loginDialog.show();

		if (OrderPayUtil.isOrderPay(getAppContext())){
			request.callBack(new MOPMLoginCallbackImpl((this)));
		} else {
			request.callBack(new LoginCallbackImpl((this)));
		}

		request.submit();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (gifDrawable != null) {
			gifDrawable.stop();
			gifDrawable.recycle();
		}
	}
}
