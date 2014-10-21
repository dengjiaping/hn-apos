package me.andpay.apos.tam.activity;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import me.andpay.apos.R;
import me.andpay.apos.cardreader.callback.SwipCardReaderCallBack;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.cmview.SolfKeyBoardView;
import me.andpay.apos.cmview.SolfKeyBoardView.OnKeyboardListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.tam.context.ResetActivityStatus;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import me.andpay.apos.tam.event.PasswordEditOnTouchEventControl;
import me.andpay.apos.tam.event.PasswordEditWatcherEventControl;
import me.andpay.apos.tam.event.TxnBackEventControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.timobileframework.flow.TiFlowCallback;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import me.andpay.timobileframework.util.FastDoubleClickUtil;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.tam_txn_layout)
@FormBind(formBean = TxnForm.class)
public class TxnAcitivty extends AposBaseActivity implements ValueContainer,
		OnKeyboardListener, ResetActivityStatus, TiFlowCallback {

	@InjectView(R.id.tam_bottom_pur_imgview)
	public ImageView txnBottomImage;

	/**
	 * 整个交易内容框
	 */
	@InjectView(R.id.tam_content_lay)
	public RelativeLayout txnContentLay;

	/**
	 * 查询余额提示
	 */
	@InjectView(R.id.tam_content_query_show_lay)
	public RelativeLayout queryShowLayout;
	
	
	@InjectView(R.id.tam_content_paycost_show_lay)
	public RelativeLayout payCostShowLayout;
	@InjectView(R.id.tam_content_paycost_show_txt)
	public TextView payCostxt;
	
	@InjectView(R.id.tam_content_topup_show_lay)
	public RelativeLayout topupShowLayout;

	@InjectView(R.id.tam_issuer_query_lay)
	public RelativeLayout queryPanLayout;

	@InjectView(R.id.tam_issuer_query_lay)
	public RelativeLayout queryIssuerLayout;

	/**
	 * 卡号
	 */
	@InjectView(R.id.tam_card_query_txnview)
	public TextView cardQueryTextView;
	@InjectView(R.id.tam_card_query_imgview)
	public ImageView cardQueryImageView;
	@InjectView(R.id.tam_issuer_query_txnview)
	public TextView issuerQueryTextView;

	/**
	 * 查询余额布局
	 */
	@InjectView(R.id.tam_content_query_lay)
	public RelativeLayout queryLayout;

	@InjectView(R.id.tam_content_pur_lay)
	public RelativeLayout purLayout;

	@Inject
	public SwipCardReaderCallBack swipCardReaderCallBack;
	/**
	 * title提示文字
	 */
	@InjectView(R.id.tam_next_top_textview)
	public TextView topTextView;

	/**
	 * gif显示图片
	 */
	@InjectView(R.id.tam_gif_view)
	public GifImageView gifView;

	public GifDrawable gifDrawable;

	/**
	 * 金额显示框
	 */
	@InjectView(R.id.tam_amt_txnview)
	public TextView amtTxnView;

	/**
	 * 卡号显示框
	 */
	@InjectView(R.id.tam_card_txnview)
	public TextView cardTextView;
	/**
	 * 卡号显示布局
	 */
	@InjectView(R.id.tam_card_pur_lay)
	public RelativeLayout cardLay;

	/**
	 * 电话号码显示框
	 */
	@InjectView(R.id.tam_phoneinput_txnview)
	public EditText phoneNo;

	/**
	 * 键盘
	 */
	public SolfKeyBoardView solfKeyBoard;

	/**
	 * 银行卡组织图片
	 */
	@InjectView(R.id.tam_card_pur_imgview)
	public ImageView cardImage;
	//
	// @InjectView(R.id.tam_lbs_btn)
	// public ImageView lbsBtn;

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = TxnBackEventControl.class)
	@InjectView(R.id.tam_top_back_btn)
	public ImageView backButton;

	@Inject
	public TxnControl txnControl;

	@InjectView(R.id.tam_txn_foot)
	public RelativeLayout footLayout;

	@InjectView(R.id.tam_goods_img)
	public ImageView goodsImg;

	@InjectView(R.id.tam_goods_lay)
	public RelativeLayout goodsLay;

	@Inject
	public PayTxnInfoDao payTxnInfoDao;

	@Inject
	private DynamicFieldHelper dyHelper;

	// 必须回收
	public Bitmap goodsMap;

	/**
	 * 密码输入框
	 */
	@InjectView(R.id.tam_input_password_edittext)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = PasswordEditOnTouchEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = PasswordEditWatcherEventControl.class) })
	public EditText pwdTextView;
	@InjectView(R.id.tam_txn_lay)
	public RelativeLayout txnLay;

	@InjectView(R.id.tam_amt_pur_imgview)
	public ImageView amtImageView;

	@InjectView(R.id.tam_input_password_hit)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = PasswordEditOnTouchEventControl.class)
	public EditText pwdTextHint;

	@InjectView(R.id.tam_issuer_txnview)
	public TextView issuerText;

	@Inject
	private LocationService locationService;

	@Inject
	private AposContext aposContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 请求一次位置信息
		locationService.requestLocation();

		solfKeyBoard = SolfKeyBoardView.instance(getApplicationContext(),
				footLayout, this);

		startTxn();

	}

	@Override
	protected void onResumeProcess() {
		super.onResumeProcess();
	}

	protected void startTxn() {
		/**
		 * 设置刷卡机回调callBack器件
		 */
		CardReaderManager.setCurrCallback(swipCardReaderCallBack);
		/**
		 * 接收交易上下文信息
		 */

		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);
		if (txnContext == null) {
			return;
		}

		/**
		 * 根据传递过来的上下文数据，初始化不同交易类型的界面
		 */
		if (ExtTypes.EXT_TYPE_VALUE_CARD_TXN.equals(txnContext.getExtType())) {
			txnControl.changeTxnStatus(TxnStatus.WAIT_PASSWORD, this);
		} else {
			txnControl.changeTxnStatus(TxnStatus.INIT, this);
		}
	}

	/**
	 * 交易请求 等待密码时，触发的事件函数
	 */
	public void sureClick() {
		// 防快速点击重复提交
		if (FastDoubleClickUtil.isFastDoubleClick(solfKeyBoard.getSure_btn()
				.getId())) {
			return;
		}
		// 等待密码输入状态
		if (TxnStatus.WAIT_PASSWORD.equals(txnControl.getTxnContext()
				.getTxnStatus())
				|| TxnStatus.WAIT_BROWSER_PHONENO.equals(txnControl
						.getTxnContext().getTxnStatus())) {
			txnControl.submitTxn(pwdTextView.getText().toString(),this);
		}

	}

	public void resetActivity() {
		// timeTextView.setVisibility(View.GONE);
		cardLay.setVisibility(View.GONE);
		phoneNo.setVisibility(View.GONE);
		gifView.setVisibility(View.GONE);
		solfKeyBoard.hideKeyboard();
		solfKeyBoard.getHintImgeBtn().setVisibility(View.GONE);
		backButton.setEnabled(true);
		// 默认显示的按钮
		pwdTextView.setVisibility(View.GONE);
		pwdTextHint.setVisibility(View.GONE);
		amtImageView.setVisibility(View.GONE);
		backButton.setVisibility(View.VISIBLE);
		txnLay.setBackgroundColor(getResources().getColor(
				R.color.com_bgroud_common_col));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		locationService.unRegisterLocation();
		if (goodsMap != null && !goodsMap.isRecycled()) {
			goodsMap.recycle();
			goodsMap = null;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 失败页面返回重新触发交易
	 */
	public void callback(String sourceNodeName) {
		startTxn();
	}
}
