package me.andpay.apos.tam.activity;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.auth.ExtFuncConfigNames;
import me.andpay.ac.term.api.base.FlexFieldDefine;
import me.andpay.apos.R;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.ExtTypes;
import me.andpay.apos.cmview.AmtEditTextView;
import me.andpay.apos.cmview.NewSolfKeyBoardView;
import me.andpay.apos.cmview.NewSolfKeyBoardView.OnKeyboardListener;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.cmview.calculator.TiCalculatorConfigs;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.TabNames;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.common.event.ShowSliderControl;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tam.TamProvider;
import me.andpay.apos.tam.activity.TiDynamicFieldDialog.DynamicDialogDelegate;
import me.andpay.apos.tam.callback.impl.TxnCallbackImpl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.event.AmtEventControl;
import me.andpay.apos.tam.event.CameraButtonEventControl;
import me.andpay.apos.tam.event.CameraGoodsButtonEventControl;
import me.andpay.apos.tam.event.PurExtInfoClickController;
import me.andpay.apos.tam.event.PurshaseEditWatcherEventControl;
import me.andpay.apos.tam.event.ShowQRScanViewControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.TIFLowSignTask;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import me.andpay.timobileframework.util.BitMapUtil;
import me.andpay.timobileframework.util.FileUtil;
import me.andpay.timobileframework.util.GPSUtil;
import me.andpay.timobileframework.util.StringConvertor;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 交易首页
 * 
 * @author cpz
 */
@ContentView(R.layout.tam_purchase_first_layout)
@FormBind(formBean = TxnForm.class)
@TIFLowSignTask
public class PurchaseFirstActivity extends AposBaseActivity implements
		ValueContainer, OnKeyboardListener, DynamicDialogDelegate {

	@InjectView(R.id.tam_foot_lay)
	public RelativeLayout rootView;

	public NewSolfKeyBoardView solfKeyBoard;

	@InjectView(R.id.tam_goods_img)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CameraGoodsButtonEventControl.class)
	public ImageView goodsImgbt;

	@InjectView(R.id.tam_camera_img)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = CameraButtonEventControl.class)
	public ImageView cameraImgView;

	@InjectView(R.id.tam_goods_lay)
	public RelativeLayout goodsLay;

	@InjectView(R.id.tam_amt_txnview)
	@EventDelegateArray({
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnTouchListener.class, toEventController = AmtEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnFocusChangeListener.class, toEventController = AmtEventControl.class),
			@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegate = "addTextChangedListener", delegateClass = TextWatcher.class, toEventController = PurshaseEditWatcherEventControl.class) })
	public AmtEditTextView amtEditText;

	@Inject
	public DynamicFieldHelper dyHelper;

	public Bitmap goodsMap = null;

	public static boolean isInit = false;

	public String goodFileUrl = null;

	@InjectView(R.id.com_show_silder_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowSliderControl.class)
	public ImageView showSilder;

	public GestureDetector mGestureDetector;

	@Inject
	private TxnControl txnControl;

	@InjectView(R.id.com_wrap_pur_lay)
	public RelativeLayout mainLayout;

	@InjectView(R.id.com_qr_scan_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowQRScanViewControl.class)
	public ImageView qrScanImage;

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = PurExtInfoClickController.class)
	@InjectView(R.id.tam_memo_tv)
	public TextView extInfoTv;

	public String extTraceNo;

	public String memo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		solfKeyBoard = NewSolfKeyBoardView.instance(this, rootView, this);
		solfKeyBoard.showKeyboard(amtEditText);
		// 判断是否有动态字段
		if (dyHelper.getFieldDefine(FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO,
				TxnTypes.PURCHASE) == null
				&& dyHelper.getFieldDefine(FlexFieldDefine.FIELD_NAME_MEMO,
						TxnTypes.PURCHASE) == null) {
			this.extInfoTv.setVisibility(View.GONE);
		}
		FileUtil.getExtDir();

	}

	@Override
	protected void onResumeProcess() {
		fleshView();
	}

	private void fleshView() {
		Object flag = getAppContext().getAttribute(RuntimeAttrNames.NEXT_TXN);
		if (flag != null && flag.toString().equals(RuntimeAttrNames.NEXT_TXN)) {
			getAppContext().removeAttribute(RuntimeAttrNames.NEXT_TXN);
			clear();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("goodFileUrl", goodFileUrl);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		String uri = savedInstanceState.getString("goodFileUrl");
		if (StringUtil.isNotBlank(uri)) {
			Bitmap bitmap = BitMapUtil.getBitmap(uri);
			goodsImgbt.setBackgroundDrawable(new BitmapDrawable(getResources(),
					bitmap));
			goodsLay.setVisibility(View.VISIBLE);
			cameraImgView.setVisibility(View.GONE);
			goodFileUrl = uri;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (requestCode == TamProvider.CAMERA_REQUEST_CODE) {
			try {

				if (!(Activity.RESULT_OK == resultCode)) {
					return;
				}

				// String path = (String) intent.getExtras().get("picturePath");
				// // 文件存储私有目录
				// Bitmap bitMap = BitMapUtil.getBitmap(path, 1280, 720);

				Bundle bundle = intent.getExtras();
				Bitmap bitMap = (Bitmap) bundle.get("data");

				goodsImgbt.setBackgroundDrawable(new BitmapDrawable(
						getResources(), bitMap));
				goodsLay.setVisibility(View.VISIBLE);
				cameraImgView.setVisibility(View.GONE);

				String fileName = FileUtil.getMyUUID() + ".jpg";
				goodFileUrl = FileUtil.bitMapSaveFile(bitMap,
						this.getApplicationContext(), fileName);

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else if (requestCode == TamProvider.TXN_REQUEST_CODE) {
			if (resultCode == TamProvider.TXN_RESULT_CLOSE) {
				onCreate(null);
			}
		} else if (requestCode == TiCalculatorConfigs.CALCULATOR_REQUEST_CODE) {

			String caResult = (String) intent
					.getStringExtra(TiCalculatorConfigs.CALCULATOR_RESULT);

			BigDecimal amtBig = BigDecimal.ZERO;
			try {
				amtBig = new BigDecimal(caResult);
			} catch (Exception ex) {
			}

			if (amtBig.compareTo(BigDecimal.ZERO) < 0) {
				amtBig = BigDecimal.ZERO;
			}

			if (amtBig.compareTo(BigDecimal.ZERO) > 0
					&& amtBig.compareTo(new BigDecimal("99999999")) < 0) {
				amtEditText.setText(amtBig.toString());
			}
		}

	}

	public void sureClick() {
		// test Dialog

		this.txnSunmit();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {

		// TODO Auto-generated method stub
		super.onDestroy();

		if (goodsMap != null && !goodsMap.isRecycled()) {
			goodsMap.recycle();
			System.gc();
		}
	}

	public void cleanPic() {

		if (goodFileUrl == null) {
			return;
		}
		File file = new File(goodFileUrl);
		file.delete();
		goodFileUrl = null;

	}

	public void clear() {

		amtEditText.setText("");

		amtEditText.clear();
		amtEditText.requestFocus();
		Selection.setSelection(amtEditText.getEditableText(),
				amtEditText.length());
		goodsImgbt.setImageBitmap(null);
		goodsLay.setVisibility(View.GONE);
		cameraImgView.setVisibility(View.VISIBLE);

		if (goodsMap != null && !goodsMap.isRecycled()) {
			goodsMap.recycle();
			System.gc();
		}

		goodFileUrl = null;
		goodsMap = null;
		memo = null;
		extTraceNo = null;
		this.extInfoTv.setText(getResources().getString(
				R.string.tam_pur_ext_info_str));
		solfKeyBoard.showKeyboard(amtEditText);

	}

	public boolean checkGpsEnable() {
		if (!GPSUtil.isGPSOpen(this.getApplicationContext())) {

			final OperationDialog dialog = new OperationDialog(this,
					ResourceUtil.getString(this, R.string.com_show_str),
					ResourceUtil.getString(this, R.string.tam_gps_unopen_str),
					ResourceUtil.getString(this, R.string.com_setting_str),
					null, false);

			dialog.setSureButtonOnclickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(intent);
					dialog.dismiss();
				}
			});
			dialog.show();
			return false;
		}

		return true;
	}

	public void txnSunmit() {

		if (!checkGpsEnable()) {
			return;
		}

		TxnContext txnContext = txnControl.init();
		txnControl.setTxnCallback(new TxnCallbackImpl());
		txnContext.setNeedPin(true);
		txnContext.setTxnType(TxnTypes.PURCHASE);
		if (this.goodFileUrl != null) {
			txnContext.setGoodsFileURL(this.goodFileUrl);
			txnContext.setGoodsUpload(true);
		}
		txnContext.setSignUplaod(true);
		// 可回主页
		txnContext.setBackTagName(TabNames.TXN_PAGE);
		txnContext.setAmtFomat(StringConvertor.filterEmptyString(amtEditText
				.getText().toString()));

		// 超额照片限制
		if (amtLimit(txnContext.getSalesAmt())) {
			return;
		}

		txnContext.setExtTraceNo(extTraceNo);
		txnContext.setMemo(memo);
		txnContext.setTxnType(TxnTypes.PURCHASE);
		txnContext.setRePostFlag(false);
		txnContext.setHasNewTxnButton(true);

		if (CardReaderManager.getCardReaderType() == CardReaderTypes.LANDI
				&& txnContext.getSalesAmt().compareTo(BigDecimal.ZERO) == 0) {
			txnContext.setExtType(ExtTypes.EXT_TYPE_TXN_GET);
		}
		TiFlowControlImpl.instanceControl().startFlow(this, FlowNames.TXN_FLOW);
		setFlowContextData(txnContext);

	}

	public boolean amtLimit(BigDecimal salesAmt) {

		PartyInfo partyInfo = (PartyInfo) getAppContext().getAttribute(
				RuntimeAttrNames.PARTY_INFO);
		Map<String, String> extFuncConfigs = partyInfo.getExtFuncConfigs();
		if (extFuncConfigs == null || extFuncConfigs.isEmpty()) {
			return false;
		}
		String amt = extFuncConfigs
				.get(ExtFuncConfigNames.TAKE_PHOTO_AMT_THRESHOLD);
		if (StringUtil.isEmpty(amt)) {
			return false;
		}

		if (salesAmt == null) {
			return false;
		}

		BigDecimal amtBig = new BigDecimal(amt);
		if (StringUtil.isBlank(goodFileUrl) && salesAmt.compareTo(amtBig) >= 0) {
			final PromptDialog dialog = new PromptDialog(this,
					ResourceUtil.getString(this, R.string.com_show_str),
					"本交易属于大额交易，请拍照后再完成交易。");
			dialog.setSureButtonOnclickListener(new OnClickListener() {
				public void onClick(View v) {
					dialog.dismiss();
					startCamera();
				}
			});
			dialog.show();
			return true;
		}
		return false;
	}

	@Override
	protected void onPause() {
		// super.overridePendingTransition(R.anim.slide_left_out,R.anim.slide_right_in);
		super.onPause();
	}

	public void showDialog(String msg) {
		final PromptDialog dialog = new PromptDialog(this,
				ResourceUtil.getString(this, R.string.com_show_str), msg);
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public void startCamera() {

		// Intent intent = new Intent();
		// intent.setAction("com.camera.activity");

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, TamProvider.CAMERA_REQUEST_CODE);

	}

	public void sureButtonClick(Map<String, String> values) {
		this.extTraceNo = values.get(FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO);
		this.memo = values.get(FlexFieldDefine.FIELD_NAME_MEMO);
		// this.extInfoTv =
		String desc = StringUtil.defaultString(memo, null) + " "
				+ StringUtil.defaultString(extTraceNo, null);
		if (desc.startsWith(" ") || desc.endsWith(" ")) {
			desc = desc.replace(" ", "");
		}
		if (StringUtil.isEmpty(desc)) {
			this.extInfoTv.setText(getResources().getString(
					R.string.tam_pur_ext_info_str));
		} else {
			float textWidth = extInfoTv.getPaint().measureText("中");
			float with = extInfoTv.getWidth();
			float textCount = with / textWidth;
			if (desc.length() > textCount) {
				this.extInfoTv.setText(desc.substring(0, ((int) textCount) - 1)
						+ "...");
			} else {
				this.extInfoTv.setText(desc);
			}
		}
	}

	public void amtChangeEvent() {
		String etext = amtEditText.getText().toString();

		if (CardReaderManager.getCardReaderType() == CardReaderTypes.LANDI) {

			if (etext.length() > 0
					&& !etext.toString().equals(
							getResources().getString(R.string.com_amt_str))) {

				solfKeyBoard.changeSureButton(true, "请刷卡");
			} else {
				solfKeyBoard.changeSureButton(true, "获取交易信息");
			}
		} else {
			if (etext.length() > 0
					&& !etext.toString().equals(
							getResources().getString(R.string.com_amt_str))) {

				solfKeyBoard.changeSureButton(true, "请刷卡");
			} else {
				solfKeyBoard.changeSureButton(false, "请刷卡");
			}
		}
	}

	public void showCalculator() {
		Intent intent = new Intent();
		intent.setAction(CommonProvider.COM_CALCULATOR_ACTIVITY);
		intent.putExtra(TiCalculatorConfigs.CALCULATOR_RESULT, "");
		this.startActivityForResult(intent,
				TiCalculatorConfigs.CALCULATOR_REQUEST_CODE);

	}

}
