package me.andpay.apos.common.activity;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.auth.Privileges;
import me.andpay.apos.R;
import me.andpay.apos.cmview.slider.simonvt.menudrawer.MenuDrawer;
import me.andpay.apos.cmview.slider.simonvt.menudrawer.MenuDrawer.OnDrawerStateChangeListener;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.common.util.BackUtil;
import me.andpay.apos.tam.callback.impl.QueryBalanceCallBackImpl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuViewController implements OnClickListener,
		OnDrawerStateChangeListener {

	private HomePageActivity homeActivity;
	private MenuDrawer mMenuDrawer;

	public RelativeLayout purLayout;
	// public RelativeLayout transferLayout;
	public RelativeLayout orderLayout;
	public RelativeLayout queryLayout;
	public RelativeLayout refundLayout;
	//public RelativeLayout balanceLayout;
	// public RelativeLayout vaserviceLayout;
	// public RelativeLayout couponLayout;
	//public RelativeLayout settleLayout;

	private RelativeLayout leftServeLayout;// 生活服务
	private RelativeLayout messageLayout;// 通知公告

	private RelativeLayout mserviceLayout;// 特约商户服务
	private RelativeLayout mMarkingLayout;// 特约商户营销

	private ImageView purImageView;
	// private ImageView transferImageView;
	private ImageView orderImageView;
	private ImageView queryImageView;
	private ImageView refundImageView;
	// private ImageView vaserviceImageView;
	private ImageView balanceImageView;
	// private ImageView couponImageView;
	private ImageView settleImageView;
	private ImageView lefeServeImageView;// 生活服务
	private ImageView messageImageView;// 消息

	private ImageView mserviceImageView;// 特约商户服务
	private ImageView mMarkingImageView;// 特约商户营销

	public Map<String, RelativeLayout> layoutMaps = new HashMap<String, RelativeLayout>();

	public Map<String, RelativeLayout> privilegeMaps = new HashMap<String, RelativeLayout>();

	public Map<String, ImageView> imagesMaps = new HashMap<String, ImageView>();

	private TextView titleText;

	private RelativeLayout oldLayout;

	private boolean isShowMorePage = false;

	private String firstTab = TabNames.MSERVICE_PAGE;

	public void changeViewState(View v) {
		if (v == null) {
			return;
		}
		if (v.getId() == R.id.com_memu_more_set_img
				|| v.getId() == R.id.com_memu_title_text
				|| v.getId() == R.id.com_memu_title_lay) {
			oldLayout.setSelected(false);
			Log.e(this.getClass().getName(), "oldLayout is selector"
					+ oldLayout.isSelected());
			isShowMorePage = true;
			return;
		}
		isShowMorePage = false;
		v.setSelected(true);
		if (oldLayout != null && oldLayout.getId() != v.getId()) {
			oldLayout.setSelected(false);
		}
		oldLayout = (RelativeLayout) v;
		mMenuDrawer.closeMenu();

	}

	public void showView(String tagName) {
		changeViewState(layoutMaps.get(tagName));
	}

	public void onClick(View v) {

		if (v == null) {
			return;
		}

		if (!isShowMorePage && oldLayout != null
				&& v.getId() == oldLayout.getId()) {
			return;
		}

		if (v.getId() == purLayout.getId()) {
			homeActivity.changeView(TabNames.TXN_PAGE);
			changeViewState(v);
			// } else if (v.getId() == transferLayout.getId()) {
			// homeActivity.changeView(TabNames.TRANSFER_PAGE);
			// changeViewState(v);
		} else if (v.getId() == orderLayout.getId()) {
			homeActivity.changeView(TabNames.ORDERPAY_PAGE);
			changeViewState(v);

		} else if (v.getId() == queryLayout.getId()) {
			homeActivity.changeView(TabNames.QUERY_PAGE);
			changeViewState(v);

		} else if (v.getId() == refundLayout.getId()) {
			homeActivity.changeView(TabNames.REFUND_PAGE);
			changeViewState(v);

		} else if (v.getId() == leftServeLayout.getId()) {
			homeActivity.changeView(TabNames.LEFT_PAGE);// 切换页面
			changeViewState(v);// 标志状态
		} else if (v.getId() == messageLayout.getId()) {
			homeActivity.changeView(TabNames.MESSAGE_PAGE);// 切换页面
			changeViewState(v);// 标志状态

		} else if (v.getId() == mserviceLayout.getId()) {// 商户特约服务
			homeActivity.changeView(TabNames.MSERVICE_PAGE);
			changeViewState(v);// 标志状态

		} else if (v.getId() == mMarkingLayout.getId()) {// 商户特约营销
			homeActivity.changeView(TabNames.MMAKING_PAGE);
			changeViewState(v);// 标志状态
		}

		// else if (v.getId() == vaserviceLayout.getId()) {
		// homeActivity.changeView(TabNames.VA_SERVICE);
		// changeViewState(v);
		// }
		// else if (v.getId() == couponLayout.getId()) {
		// homeActivity.changeView(TabNames.COUPON_PAGE);
		// changeViewState(v);
		// }
//		else if (v.getId() == balanceLayout.getId()) {
//
//			TxnContext txnContext = homeActivity.txnControl.init();
//
//			txnContext.setNeedPin(false);
//			txnContext.setTxnType(TxnTypes.INQUIRY_BALANCE);
//			txnContext.setBackTagName(TabNames.BALANCE_PAGE);
//			homeActivity.txnControl
//					.setTxnCallback(new QueryBalanceCallBackImpl());
//
//			TiFlowControlImpl.instanceControl().startFlow(homeActivity,
//					FlowNames.TAM_BALANCE_QUERY_FLOW);
//			TiFlowControlImpl.instanceControl().setFlowContextData(txnContext);
//		} else 
//		else if (v.getId() == settleLayout.getId()) {
//			homeActivity.changeView(TabNames.SETTLE_PAGE);
//			changeViewState(v);
//		}

	}

	public MenuViewController(final HomePageActivity homeActivity) {
		this.homeActivity = homeActivity;
		mMenuDrawer = homeActivity.mMenuDrawer;
		mMenuDrawer.setOnDrawerStateChangeListener(this);
		View menuView = mMenuDrawer.getMenuView();

		leftServeLayout = (RelativeLayout) menuView
				.findViewById(R.id.com_menu_left_serve_lay);
		messageLayout = (RelativeLayout) menuView
				.findViewById(R.id.com_menu_message_lay);
		mserviceLayout = (RelativeLayout) menuView
				.findViewById(R.id.com_menu_mservice_lay);
		mMarkingLayout = (RelativeLayout) menuView
				.findViewById(R.id.com_menu_mMarketing_lay);
		purLayout = (RelativeLayout) menuView
				.findViewById(R.id.com_menu_pur_lay);
		// transferLayout = (RelativeLayout) menuView
		// .findViewById(R.id.com_menu_transfer_lay);
		orderLayout = (RelativeLayout) menuView
				.findViewById(R.id.com_menu_order_lay);
		queryLayout = (RelativeLayout) menuView
				.findViewById(R.id.com_menu_query_lay);
		refundLayout = (RelativeLayout) menuView
				.findViewById(R.id.com_menu_refund_lay);
//		balanceLayout = (RelativeLayout) menuView
//				.findViewById(R.id.com_menu_balance_lay);
		// couponLayout = (RelativeLayout) menuView
		// .findViewById(R.id.com_menu_coupon_lay);
//		settleLayout = (RelativeLayout) menuView
//				.findViewById(R.id.com_menu_settle_lay);

		// vaserviceLayout = (RelativeLayout) menuView
		// .findViewById(R.id.com_menu_vaserivce_lay);
		layoutMaps.put(TabNames.MESSAGE_PAGE, messageLayout);
		layoutMaps.put(TabNames.LEFT_PAGE, leftServeLayout);
		layoutMaps.put(TabNames.TXN_PAGE, purLayout);
		// layoutMaps.put(TabNames.TRANSFER_PAGE, transferLayout);
		layoutMaps.put(TabNames.ORDERPAY_PAGE, orderLayout);
		layoutMaps.put(TabNames.QUERY_PAGE, queryLayout);
		layoutMaps.put(TabNames.REFUND_PAGE, refundLayout);
		// layoutMaps.put(TabNames.MORE_PAGE, moreLayout);
		// layoutMaps.put(TabNames.COUPON_PAGE, couponLayout);
		// layoutMaps.put(TabNames.VA_SERVICE, vaserviceLayout);
		//layoutMaps.put(TabNames.SETTLE_PAGE, settleLayout);

		messageImageView = (ImageView) menuView
				.findViewById(R.id.com_memu_message_img);
		lefeServeImageView = (ImageView) menuView
				.findViewById(R.id.com_memu_left_serve_img);

		mserviceImageView = (ImageView) menuView
				.findViewById(R.id.com_memu_mservice_img);
		mMarkingImageView = (ImageView) menuView
				.findViewById(R.id.com_memu_mMarketing_img);

		purImageView = (ImageView) menuView
				.findViewById(R.id.com_memu_pur_line_img);
		// transferImageView = (ImageView) menuView
		// .findViewById(R.id.com_memu_transfer_line_img);
		orderImageView = (ImageView) menuView
				.findViewById(R.id.com_memu_order_line_img);
		queryImageView = (ImageView) menuView
				.findViewById(R.id.com_memu_query_line_img);
		refundImageView = (ImageView) menuView
				.findViewById(R.id.com_memu_refund_line_img);
		// vaserviceImageView = (ImageView) menuView
		// .findViewById(R.id.com_memu_vaservice_line_img);
//		balanceImageView = (ImageView) menuView
//				.findViewById(R.id.com_memu_balance_line_img);
		// couponImageView = (ImageView) menuView
		// .findViewById(R.id.com_memu_coupon_line_img);
		settleImageView = (ImageView) menuView
				.findViewById(R.id.com_memu_settle_line_img);
		setPrivilege();

		titleText = (TextView) menuView.findViewById(R.id.com_memu_title_text);
		TextView userTextView = (TextView) menuView
				.findViewById(R.id.com_menu_text);
		LoginUserInfo logInfo = (LoginUserInfo) homeActivity.getAppContext()
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		PartyInfo partyInfo = (PartyInfo) homeActivity.getAppContext()
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		titleText.setText(partyInfo.getPartyName());
		userTextView.setText("用户"
				+ logInfo.getUserName()
				+ " "
				+ homeActivity.getResources().getString(
						R.string.com_login_out_user_str));

		ImageView logoutImageView = (ImageView) menuView
				.findViewById(R.id.com_logout_imgview);

		logoutImageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BackUtil.showBackDialog(homeActivity);
			}
		});

		userTextView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BackUtil.showBackDialog(homeActivity);
			}
		});

		ImageView moreSetImageView = (ImageView) menuView
				.findViewById(R.id.com_memu_more_set_img);
		moreSetImageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				homeActivity.changeView(TabNames.MORE_PAGE);
				changeViewState(v);
				mMenuDrawer.closeMenu();
			}
		});
		titleText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				homeActivity.changeView(TabNames.MORE_PAGE);
				changeViewState(v);
				mMenuDrawer.closeMenu();
			}
		});

		purLayout.setOnClickListener(this);
		// transferLayout.setOnClickListener(this);
		orderLayout.setOnClickListener(this);
		queryLayout.setOnClickListener(this);
		refundLayout.setOnClickListener(this);
		// vaserviceLayout.setOnClickListener(this);
		//balanceLayout.setOnClickListener(this);
		// couponLayout.setOnClickListener(this);
		//settleLayout.setOnClickListener(this);
		leftServeLayout.setOnClickListener(this);
		messageLayout.setOnClickListener(this);

		mserviceLayout.setOnClickListener(this);// 特约商户服务
		mMarkingLayout.setOnClickListener(this);// 特约商户营销

	}

	protected void setPrivilege() {
		PartyInfo partyInfo = (PartyInfo) homeActivity.getAppContext()
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		Map<String, String> privileges = partyInfo.getPrivileges();

//		if (!privileges.containsKey(Privileges.QUERY_SETTLE_ORDER)) {
//			settleLayout.setVisibility(View.GONE);
//			settleImageView.setVisibility(View.GONE);
//		} else {
//			privilegeMaps.put(Privileges.QUERY_SETTLE_ORDER, settleLayout);
//			imagesMaps.put(Privileges.QUERY_SETTLE_ORDER, settleImageView);
//			firstTab = TabNames.SETTLE_PAGE;
//		}
//		if (!privileges.containsKey(Privileges.INQUIRY_BALANCE)) {
//			balanceLayout.setVisibility(View.GONE);
//			balanceImageView.setVisibility(View.GONE);
//		} else {
//			privilegeMaps.put(Privileges.INQUIRY_BALANCE, balanceLayout);
//			imagesMaps.put(Privileges.INQUIRY_BALANCE, balanceImageView);
//			firstTab = TabNames.BALANCE_PAGE;
//
//		}

		if (!privileges.containsKey(Privileges.REFUND)) {
			refundLayout.setVisibility(View.GONE);
			refundImageView.setVisibility(View.GONE);
		} else {
			privilegeMaps.put(Privileges.REFUND, refundLayout);
			imagesMaps.put(Privileges.REFUND, refundImageView);
			firstTab = TabNames.REFUND_PAGE;

		}

		if (!privileges.containsKey(Privileges.QUERY_TXN)) {
			queryLayout.setVisibility(View.GONE);
			queryImageView.setVisibility(View.GONE);
		} else {
			privilegeMaps.put(Privileges.QUERY_TXN, queryLayout);
			imagesMaps.put(Privileges.QUERY_TXN, queryImageView);
			firstTab = TabNames.QUERY_PAGE;

		}
		if (!privileges.containsKey(Privileges.ORDER_PURCHASE)) {
			orderLayout.setVisibility(View.GONE);
			orderImageView.setVisibility(View.GONE);
		} else {
			privilegeMaps.put(Privileges.ORDER_PURCHASE, orderLayout);
			imagesMaps.put(Privileges.ORDER_PURCHASE, orderImageView);
			firstTab = TabNames.ORDERPAY_PAGE;

		}

		if (!privileges.containsKey(Privileges.PURCHASE)) {
			purLayout.setVisibility(View.GONE);
			purImageView.setVisibility(View.GONE);
			// vaserviceLayout.setVisibility(View.GONE);
			// vaserviceImageView.setVisibility(View.GONE);
			// couponLayout.setVisibility(View.GONE);
			// couponImageView.setVisibility(View.GONE);

		} else {
			privilegeMaps.put(Privileges.PURCHASE, purLayout);
			imagesMaps.put(Privileges.PURCHASE, purImageView);
			firstTab = TabNames.TXN_PAGE;

		}

	}

	/**
	 * 设置并获取第一个显示的view
	 */
	public void setFirstView() {
		RelativeLayout firstLayout = null;
		PartyInfo partyInfo = (PartyInfo) homeActivity.getAppContext()
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		Map<String, String> privileges = partyInfo.getPrivileges();

//		if (privileges.containsKey(Privileges.QUERY_SETTLE_ORDER)){
//			if (privilegeMaps.get(Privileges.QUERY_SETTLE_ORDER)
//					.getVisibility() == View.VISIBLE)
//				firstLayout = this.settleLayout;
//		}

//		if (privileges.containsKey(Privileges.INQUIRY_BALANCE)) {
//			if (privilegeMaps.get(Privileges.INQUIRY_BALANCE).getVisibility() == View.VISIBLE)
//				firstLayout = this.balanceLayout;
//		}

		if (privileges.containsKey(Privileges.REFUND)) {
			if (privilegeMaps.get(Privileges.REFUND).getVisibility() == View.VISIBLE)
				firstLayout = refundLayout;
		}

		if (privileges.containsKey(Privileges.QUERY_TXN)){
			if (privilegeMaps.get(Privileges.QUERY_TXN).getVisibility() == View.VISIBLE)
				firstLayout = queryLayout;
		}
		if (privileges.containsKey(Privileges.ORDER_PURCHASE)) {
			if (privilegeMaps.get(Privileges.ORDER_PURCHASE).getVisibility() == View.VISIBLE)
				firstLayout = orderLayout;
		}

		if (privileges.containsKey(Privileges.PURCHASE)) {
			if (privilegeMaps.get(Privileges.PURCHASE).getVisibility() == View.VISIBLE)
				firstLayout = purLayout;
		}
		onClick(firstLayout);
	}

	public void onDrawerStateChange(int oldState, int newState) {
		String tagName = homeActivity.getTabHost().getCurrentTabTag();

		Context context = homeActivity.getTabHost().getCurrentView()
				.getContext();

		if (context instanceof MenuChangeEvent) {
			MenuChangeEvent event = (MenuChangeEvent) context;
			event.onMenuStateChange(oldState, newState);
		}

		// if(newState == MenuDrawer.STATE_OPEN && isShowMorePage) {
		// oldLayout.setSelected(false);
		// Log.e(this.getClass().getName(),"oldLayout is selector"+oldLayout.isSelected());
		//
		// }

	}

	/**
	 * 隐藏menu
	 * 
	 * @param privilege
	 */
	public void hideMenu(String privilege) {
		if (privilegeMaps.containsKey(privilege)) {
			privilegeMaps.get(privilege).setVisibility(View.GONE);
			imagesMaps.get(privilege).setVisibility(View.GONE);
		}
	}

	public void showMenu(String privilege) {
		if (privilegeMaps.containsKey(privilege)) {
			privilegeMaps.get(privilege).setVisibility(View.VISIBLE);
			imagesMaps.get(privilege).setVisibility(View.VISIBLE);
		}
	}

	public String getFirstTab() {
		return firstTab;
	}

}
