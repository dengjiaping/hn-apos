package me.andpay.apos.scm.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.scm.event.EmailButtonClickController;
import me.andpay.apos.scm.event.PhoneBtnClickController;
import me.andpay.apos.scm.event.ShowWeiboClickEventControl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Html;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@ContentView(R.layout.scm_about_layout)
public class ScmAboutActivity extends AposBaseActivity {

	@InjectView(R.id.com_version_tv)
	TextView versionTv;

	@InjectView(R.id.com_net_url_tv)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = ShowWeiboClickEventControl.class)
	TextView netTv;

	@InjectView(R.id.scm_help_phone_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PhoneBtnClickController.class)
	Button phoneBtn;

	@InjectView(R.id.scm_help_email_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = EmailButtonClickController.class)
	Button emailBtn;

	@InjectResource(R.string.scm_help_version_str)
	private String version;

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	public ImageView backBtn;

	@InjectView(R.id.scm_weibo_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = ShowWeiboClickEventControl.class)
	public Button weibo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PackageInfo pInfo;
		String versionName = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionName = pInfo.versionName;
		} catch (NameNotFoundException e) {
			versionName = "2.0";
		}
		this.netTv.setText( Html.fromHtml("<u>" + ResourceUtil.getString(this, R.string.config_company_url_str) + "</u>"));
		versionTv.setText(versionTv.getText() + versionName);
	}

}
