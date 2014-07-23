package me.andpay.apos.tam.activity;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.base.FlexFieldDefine;
import me.andpay.apos.R;
import me.andpay.apos.cmview.TiTimeTextView;
import me.andpay.apos.cmview.TiTimeTextView.OnTimeoutListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.tam.CardOrgImageMap;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.event.DetailNextButtonEventControl;
import me.andpay.apos.tam.flow.model.TxnDetailContext;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.util.BitMapUtil;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 交易完成详细页面
 * 
 * @author cpz
 * 
 */

@ContentView(R.layout.tam_txn_detail_layout)
public class TxnDetailActivity extends AposBaseActivity implements
		ValueContainer {

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = DetailNextButtonEventControl.class)
	@InjectView(R.id.com_back_btn)
	public ImageView backHome;

	@InjectView(R.id.tam_amt_txnview)
	public TextView amtView;
	@InjectView(R.id.tam_order_txnView)
	public TextView orderView;
	@InjectView(R.id.tam_order_pur_lay)
	public RelativeLayout orderLay;
	@InjectView(R.id.tam_order_pur_imgview)
	public ImageView orderImageView;

	@InjectView(R.id.tam_memo_txnview)
	public TextView memoView;
	@InjectView(R.id.tam_memo_pur_lay)
	public RelativeLayout memoLay;
	@InjectView(R.id.tam_memo_pur_imgview)
	public ImageView memoImageView;

	@InjectView(R.id.tam_pan_txnview)
	public TextView panView;
	@InjectView(R.id.tam_issuer_txnview)
	public TextView issuerView;
	@InjectView(R.id.tam_time_txnview)
	public TextView timeView;
	@InjectView(R.id.tam_txntype_txnview)
	public TextView txnTypeView;
	@InjectView(R.id.tam_txnaddr_txnview)
	public TextView txnAddrView;
	@InjectView(R.id.tam_sign_imgview)
	public ImageView signImgeView;
	@InjectView(R.id.tam_goods_img)
	public ImageView goodsImg;
	@InjectView(R.id.tam_goods_lay)
	public RelativeLayout goodsLay;
	@InjectView(R.id.tam_card_pur_imgview)
	public ImageView carImageView;

	@InjectView(R.id.tam_memoname_txnview)
	public TextView memoNameTextView;
	@InjectView(R.id.tam_ordername_txnView)
	public TextView orderNameTextView;

	@InjectView(R.id.tam_sign_txnview)
	public TextView signTextView;

	@InjectView(R.id.tam_sign_foot_textview)
	public TextView signFootTextView;

	@Inject
	private DynamicFieldHelper dyHelper;

	@Inject
	public TxnControl txnControl;

	public Bitmap goodbitMap;
	public Bitmap signBitMap;

	@Inject
	public LocationService locationService;

	@InjectView(R.id.tam_time_textView)
	public TiTimeTextView timeTextView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TxnDetailContext txnDetailContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnDetailContext.class);
		intiView(txnDetailContext);
		timeTextView.startTimer(10);

		final Activity activity = this;
		timeTextView.setOnTimeoutListener(new OnTimeoutListener() {
			public void onTimeout() {
				if (activity.isFinishing()) {
					return;
				}
				txnControl.backHomePage(activity);
			}
		});
	}

	private void intiView(TxnDetailContext txnDetailContext) {

		amtView.setText(txnDetailContext.getSalesAmtFomat());
		orderView.setText(txnDetailContext.getExtTraceNo());
		memoView.setText(txnDetailContext.getMemo());
		panView.setText(TxnUtil.hidePan(txnDetailContext.getCardNo()));
		issuerView.setText(txnDetailContext.getCardIssuerName());
		timeView.setText(StringUtil.format("yyyy/MM/dd HH:mm:ss",
				txnDetailContext.getTxnTime()));
		txnTypeView.setText(txnDetailContext.getTxnTypeName());

		txnAddrView.setText(txnDetailContext.getTxnAddress());

		if (txnDetailContext.getTxnType().equals(TxnTypes.PURCHASE)
				|| txnDetailContext.getTxnType().equals(
						TxnTypes.INQUIRY_BALANCE)) {
			amtView.setTextColor(getResources().getColor(
					R.color.tqm_list_item_amount_col));
		} else {
			amtView.setTextColor(getResources().getColor(
					R.color.com_title_red_color));
		}

		// 设置图片文件
		if (StringUtil.isNotBlank(txnDetailContext.getGoodsFileURL())) {
			goodbitMap = BitMapUtil.getBitmap(txnDetailContext
					.getGoodsFileURL(), 5);
			goodsImg.setImageBitmap(null);
			goodsImg.setBackgroundDrawable(new BitmapDrawable(goodbitMap));
		} else {
			goodsLay.setVisibility(View.GONE);
		}

		// 设置图片文件
		if (StringUtil.isNotBlank(txnDetailContext.getSignFileURL())) {
			signBitMap = BitMapUtil.getBitmap(txnDetailContext
					.getSignFileURL(), 5);
			signImgeView.setImageBitmap(null);
			signImgeView.setBackgroundDrawable(new BitmapDrawable(signBitMap));
		} else {
			signTextView.setVisibility(View.GONE);
			signImgeView.setVisibility(View.GONE);
			signFootTextView.setVisibility(View.GONE);

		}
		carImageView.setImageResource(CardOrgImageMap.getId(txnDetailContext
				.getCardAssoc()));
		dynamicField(txnDetailContext.getTxnType());
	}
	

	
	

	private void dynamicField(String txnType) {

		if (TxnTypes.isVoidTxnType(txnType)) {
			txnType = TxnTypes.REFUND;
		}

		dyHelper.setTextView(orderNameTextView,
				FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO, txnType);
		dyHelper.setTextView(memoNameTextView, FlexFieldDefine.FIELD_NAME_MEMO,
				txnType);

		dyHelper.setViewShowStatus(FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO,
				txnType, orderLay, orderImageView);
		dyHelper.setViewShowStatus(FlexFieldDefine.FIELD_NAME_MEMO, txnType,
				memoLay, memoImageView);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (goodbitMap != null && !goodbitMap.isRecycled()) {
			if (goodbitMap != null && !goodbitMap.isRecycled()) {
				goodbitMap.recycle();
				goodbitMap = null;
			}

		}

		if (signBitMap != null && !signBitMap.isRecycled()) {

			if (signBitMap != null && !signBitMap.isRecycled()) {
				signBitMap.recycle();
				signBitMap = null;
			}

		}
		System.gc();

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
