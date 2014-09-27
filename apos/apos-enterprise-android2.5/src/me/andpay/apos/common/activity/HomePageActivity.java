package me.andpay.apos.common.activity;

import java.util.List;
import java.util.Map;

import me.andpay.ac.consts.OqpSchemes;
import me.andpay.ac.term.api.auth.PrivilegeConfig;
import me.andpay.ac.term.api.auth.PrivilegeConfigNames;
import me.andpay.ac.term.api.auth.Privileges;
import me.andpay.apos.R;
import me.andpay.apos.cmview.slider.simonvt.menudrawer.MenuDrawer;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.MemoryRecorder;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.common.service.DownloadICCardParamsService;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.lft.activity.LeftServeActivity;
import me.andpay.apos.merchantservice.activity.MerchantsServiceActivity;
import me.andpay.apos.merchantsmaking.activity.MerchantsMakingActivity;
import me.andpay.apos.message.activity.MessageActivity;
import me.andpay.apos.opm.activity.InputOrderNoActivity;
import me.andpay.apos.opm.activity.OrderPayListActivity;
import me.andpay.apos.scm.activity.ScmMainActivity;
import me.andpay.apos.stl.activity.SettleListActivity;
import me.andpay.apos.tam.action.txn.cloud.CloudPosUtil;
import me.andpay.apos.tam.activity.PurchaseFirstActivity;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tqm.activity.TxnBatchQueryActivity;
import me.andpay.apos.tqrm.activity.CouponListActivity;
import me.andpay.apos.trf.activity.PayeeInfomationActivity;
import me.andpay.apos.trm.activity.RefundBatchQueryActivity;
import me.andpay.apos.vas.activity.VasMainActivity;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.TIFLowSignTask;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import roboguice.inject.ContentView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.google.inject.Inject;

@SuppressLint("NewApi")
@ContentView(R.layout.com_homepage_layout)
@TIFLowSignTask
public class HomePageActivity extends AposBaseTabActivity implements
		ValueContainer {

	private static final String STATE_MENUDRAWER = "net.simonvt.menudrawer.samples.WindowSample.menuDrawer";

	public View oldView;

	TabSpec ts1;
	TabSpec ts2;
	TabSpec ts3;
	TabSpec ts4;
	TabSpec ts5;
	TabSpec ts6;
	TabSpec ts7;
	TabSpec ts8;
	TabSpec ts9;
	TabSpec ts10;
	TabSpec ts11;
	TabSpec ts12;
	TabSpec ts13;
	@Inject
	private LocationService locationService;

	public MenuDrawer mMenuDrawer;

	@Inject
	public TxnControl txnControl;

	private MenuViewController viewController;

	@Inject
	private DownloadICCardParamsService downloadICCardParamsService;

	/**
	 * 刷头类型选择的是云POS，隐藏余额查询
	 */
	@Override
	protected void onResumeProcess() {
		// 云pos不支持余额查询操作
		filterCloudPosMenu();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		downloadICCardParamsService.downloadICparams();
		// 初始化apos调试日志
		if (!MemoryRecorder.isRecordMemory()) {
			MemoryRecorder.reset(this);
		} else {
			mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_WINDOW);
			mMenuDrawer.setMenuView(R.layout.com_menu_slider_layout);
			viewController = new MenuViewController(this);
			filterCloudPosMenu();
			viewController.setFirstView();
			// checkUpdate();
			this.getAppContext().removeAttribute(RuntimeAttrNames.OLD_PASSWORD);
			// aposNetworkChangeReceiver.register(getApplicationContext());
		}

	}

	private void filterCloudPosMenu() {
		if (CloudPosUtil.isCloudPosCardReader(this.getAppConfig())) {// 如果是云pos
			viewController.hideMenu(Privileges.INQUIRY_BALANCE);
			viewController.hideMenu(Privileges.ORDER_PURCHASE);
		} else {
			viewController.showMenu(Privileges.INQUIRY_BALANCE);
			viewController.showMenu(Privileges.ORDER_PURCHASE);
		}

	}

	private void init(Intent intent) {

		TabHost mth = getTabHost();

		String tagName = "";
		if (intent == null) {
			tagName = viewController.getFirstTab();
		} else {
			tagName = intent.getStringExtra(CommonProvider.TAGNAME);
			if (StringUtil.isBlank(tagName)) {
				tagName = viewController.getFirstTab();
			}
		}

		mth.setCurrentTabByTag(tagName);
		viewController.showView(tagName);
		changeView(tagName);

	}

	public void showSlider() {
		mMenuDrawer.toggleMenu(true);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		init(intent);
	}

	public void changeView(String tagName) {

		TabHost mth = getTabHost();

		if (tagName.endsWith(TabNames.BALANCE_PAGE)) {
			return;
		} else if (tagName.endsWith(TabNames.MSERVICE_PAGE)) {
			if (ts12 == null) {
				ts12 = mth.newTabSpec(TabNames.MSERVICE_PAGE).setIndicator(
						TabNames.MSERVICE_PAGE);
				ts12.setContent(new Intent(this, MerchantsServiceActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts12);
			}
			getTabHost().setCurrentTabByTag(TabNames.MSERVICE_PAGE);
		} else if (tagName.endsWith(TabNames.MMAKING_PAGE)) {
			if (ts13 == null) {
				ts13 = mth.newTabSpec(TabNames.MMAKING_PAGE).setIndicator(
						TabNames.MMAKING_PAGE);
				ts13.setContent(new Intent(this, MerchantsMakingActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts13);
			}
			getTabHost().setCurrentTabByTag(TabNames.MMAKING_PAGE);
		} else if (tagName.endsWith(TabNames.MESSAGE_PAGE)) {
			if (ts11 == null) {
				ts11 = mth.newTabSpec(TabNames.MESSAGE_PAGE).setIndicator(
						TabNames.MESSAGE_PAGE);
				ts11.setContent(new Intent(this, MessageActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts11);
			}
			getTabHost().setCurrentTabByTag(TabNames.MESSAGE_PAGE);
		} else if (tagName.endsWith(TabNames.LEFT_PAGE)) {
			if (ts10 == null) {
				ts10 = mth.newTabSpec(TabNames.LEFT_PAGE).setIndicator(
						TabNames.LEFT_PAGE);
				ts10.setContent(new Intent(this, LeftServeActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts10);
			}
			getTabHost().setCurrentTabByTag(TabNames.LEFT_PAGE);
		} else if (tagName.endsWith(TabNames.TXN_PAGE)) {
			if (ts1 == null) {
				ts1 = mth.newTabSpec(TabNames.TXN_PAGE).setIndicator(
						TabNames.TXN_PAGE);
				ts1.setContent(new Intent(this, PurchaseFirstActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts1);
			}
			getTabHost().setCurrentTabByTag(TabNames.TXN_PAGE);

		} else if (tagName.endsWith(TabNames.TRANSFER_PAGE)) {
			if (ts9 == null) {
				ts9 = mth.newTabSpec(TabNames.TRANSFER_PAGE).setIndicator(
						TabNames.TRANSFER_PAGE);
				ts9.setContent(new Intent(this, PayeeInfomationActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts9);
			}
			getTabHost().setCurrentTabByTag(TabNames.TRANSFER_PAGE);

		} else if (tagName.endsWith(TabNames.REFUND_PAGE)) {
			if (ts2 == null) {
				ts2 = mth.newTabSpec(TabNames.REFUND_PAGE).setIndicator(
						TabNames.REFUND_PAGE);
				ts2.setContent(new Intent(this, RefundBatchQueryActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts2);
			}
			getTabHost().setCurrentTabByTag(TabNames.REFUND_PAGE);

		} else if (tagName.endsWith(TabNames.QUERY_PAGE)) {

			if (ts3 == null) {
				ts3 = mth.newTabSpec(TabNames.QUERY_PAGE).setIndicator(
						TabNames.QUERY_PAGE);
				ts3.setContent(new Intent(this, TxnBatchQueryActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts3);
			}

			getTabHost().setCurrentTabByTag(TabNames.QUERY_PAGE);

		}
		if (tagName.endsWith(TabNames.MORE_PAGE)) {

			if (ts4 == null) {
				ts4 = mth.newTabSpec(TabNames.MORE_PAGE).setIndicator(
						TabNames.MORE_PAGE);
				ts4.setContent(new Intent(this, ScmMainActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts4);
			}

			getTabHost().setCurrentTabByTag(TabNames.MORE_PAGE);

		} else if (tagName.endsWith(TabNames.ORDERPAY_PAGE)) {

			PartyInfo partyInfo = (PartyInfo) getAppContext().getAttribute(
					RuntimeAttrNames.PARTY_INFO);
			Map<String, String> privileges = partyInfo.getPrivileges();
			String cfgStr = privileges.get(Privileges.ORDER_PURCHASE);

			if (cfgStr != null) {
				// 允许订单支付
				PrivilegeConfig cfg = PrivilegeConfig.parse(cfgStr);

				if (OqpSchemes.QUERY.equals(cfg
						.getString(PrivilegeConfigNames.ORD_PUR_INQ_SCHEME))) {
					// 订单实时查询模式
					if (ts5 == null) {
						ts5 = mth.newTabSpec(TabNames.ORDERPAY_PAGE)
								.setIndicator(TabNames.ORDERPAY_PAGE);
						ts5.setContent(new Intent(this,
								InputOrderNoActivity.class)
								.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
						mth.addTab(ts5);
					}

					getTabHost().setCurrentTabByTag(TabNames.ORDERPAY_PAGE);

					// 提示 (可能为null)
					String tips = cfg
							.getString(PrivilegeConfigNames.ORD_PUR_INQ_TIPS);
					getAppContext().setAttribute(
							RuntimeAttrNames.ORDER_NO_INPUT_TIPS, tips);

				} else {
					// 订单推送模式
					if (ts5 == null) {
						ts5 = mth.newTabSpec(TabNames.ORDERPAY_PAGE)
								.setIndicator(TabNames.ORDERPAY_PAGE);
						ts5.setContent(new Intent(this,
								OrderPayListActivity.class)
								.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
						mth.addTab(ts5);
					}

					getTabHost().setCurrentTabByTag(TabNames.ORDERPAY_PAGE);

				}

				// 主要属性名集合 (可能为null)
				List<String> keyAttrNames = cfg
						.getList(PrivilegeConfigNames.ORD_PUR_INQ_KEY_ATTRS);

			} else {
				// 无订单支付权限
			}

		} else if (tagName.endsWith(TabNames.VA_SERVICE)) {
			if (ts6 == null) {
				ts6 = mth.newTabSpec(TabNames.VA_SERVICE).setIndicator(
						TabNames.VA_SERVICE);
				ts6.setContent(new Intent(this, VasMainActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts6);
			}

			getTabHost().setCurrentTabByTag(TabNames.VA_SERVICE);
			VasMainActivity vasMainActivity = (VasMainActivity) mth
					.getCurrentView().getContext();
		} else if (tagName.endsWith(TabNames.COUPON_PAGE)) {
			if (ts7 == null) {
				ts7 = mth.newTabSpec(TabNames.COUPON_PAGE).setIndicator(
						TabNames.COUPON_PAGE);
				ts7.setContent(new Intent(this, CouponListActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts7);
			}

			getTabHost().setCurrentTabByTag(TabNames.COUPON_PAGE);

		} else if (tagName.endsWith(TabNames.SETTLE_PAGE)) {
			if (ts8 == null) {
				ts8 = mth.newTabSpec(TabNames.SETTLE_PAGE).setIndicator(
						TabNames.SETTLE_PAGE);
				ts8.setContent(new Intent(this, SettleListActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				mth.addTab(ts8);
			}
			getTabHost().setCurrentTabByTag(TabNames.SETTLE_PAGE);
		}

	}

	@Override
	protected void onRestoreInstanceState(Bundle inState) {
		super.onRestoreInstanceState(inState);
		if (mMenuDrawer == null) {
			return;
		}
		mMenuDrawer.restoreState(inState.getParcelable(STATE_MENUDRAWER));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mMenuDrawer == null) {
			return;
		}
		outState.putParcelable(STATE_MENUDRAWER, mMenuDrawer.saveState());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mMenuDrawer.toggleMenu();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		final int drawerState = mMenuDrawer.getDrawerState();
		if (drawerState == MenuDrawer.STATE_OPEN
				|| drawerState == MenuDrawer.STATE_OPENING) {
			mMenuDrawer.closeMenu();
			return;
		}

		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		// super.overridePendingTransition(R.anim.slide_left_out,R.anim.slide_right_in);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
