package me.andpay.apos.common.activity;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.QrScanType;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.event.LightTurnOnOffClickEventController;
import me.andpay.apos.common.event.QRScanBackEventControl;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.scm.event.BluetoothListItemClickController;
import me.andpay.apos.tam.CardValueTxnHelper;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.util.HexUtils;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.CaptureActivityHandler;
import com.google.zxing.client.android.ViewfinderView;
import com.google.zxing.client.android.camera.CameraManager;

@ContentView(R.layout.com_qr_layout)
public class AposQRActivity extends AposBaseActivity implements
		SurfaceHolder.Callback {

	private static final String TAG = AposQRActivity.class.getSimpleName();

	public boolean hasSurface;
	public BeepManager beepManager;
	public CameraManager cameraManager;
	public CaptureActivityHandler handler;
	public Collection<BarcodeFormat> decodeFormats;

	public ViewfinderView viewfinderView;
	@InjectView(R.id.com_framView_view)
	public FrameLayout frameLayout;

	private SurfaceView surfaceView;
	@Inject
	public TxnControl txnControl;

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = QRScanBackEventControl.class)
	@InjectView(R.id.qr_top_back_btn)
	public ImageView backButton;

	private String scanType;

	@InjectView(R.id.com_top_progress)
	public ProgressBar progress;

	@InjectView(R.id.com_top_title)
	public TextView topTextView;

	@InjectView(R.id.com_top_pur_lay)
	private RelativeLayout topRelativeLayout;

	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = LightTurnOnOffClickEventController.class)
	@InjectView(R.id.qr_light_btn)
	private ImageView lightBtn;
	private boolean lightOnOff = false; // false 默认闪光灯关闭

	@Inject
	private AposContext aposContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hasSurface = false;
		beepManager = new BeepManager(this);

		scanType = getIntent().getExtras().getString("scanType");

		Display disPlay = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		disPlay.getMetrics(outMetrics);
		int topHeight = (int) (outMetrics.density * 75);

		CameraManager.init(getApplication());
		cameraManager = CameraManager.get();
		cameraManager.setTopHeight(topHeight);

		surfaceView = new SurfaceView(getApplicationContext());
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		frameLayout.addView(surfaceView);

		viewfinderView = new ViewfinderView(getApplicationContext(), null);
		frameLayout.addView(viewfinderView);

		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
	}

	@Override
	protected void onResumeProcess() {
		progress.setVisibility(View.GONE);
		topTextView.setText("二维码扫描");

		Object flag = getAppContext().getAttribute(RuntimeAttrNames.NEXT_TXN);
		if (flag != null && flag.toString().equals(RuntimeAttrNames.NEXT_TXN)) {
			previousSetup();
		}

		CameraManager.get().startPreview();
		lightOnOff = false;
		lightBtn.setSelected(false);
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			Log.w(TAG,
					"initCamera() while already open -- late SurfaceView callback?");
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
			if (handler == null) {
				handler = new CaptureActivityHandler(this, decodeFormats,
						"utf-8");
			}
		} catch (IOException ioe) {
			Log.w(TAG, ioe);
		} catch (RuntimeException e) {
			Log.w(TAG, "Unexpected error initializing camera", e);
		}

	}

	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		// inactivityTimer.onPause();
		cameraManager.closeDriver();
		if (!hasSurface) {
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(this);
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// inactivityTimer.shutdown();
		super.onDestroy();
	}

	public void finishSelf() {
		if (QrScanType.ST_BLUETOOTH.equals(scanType))
			this.getControl().previousSetup(this);
		else
			this.finish();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {
			Log.e(TAG,
					"*** WARNING *** surfaceCreated() gave us a null surface!");
		}
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	public void turnOnOffLight() {
		if (lightOnOff) {
			// turn off light
			cameraManager.turnOnOffFlash(false);
			lightBtn.setSelected(false);
		} else {
			// turn on light
			cameraManager.turnOnOffFlash(true);
			lightBtn.setSelected(true);
		}
		lightOnOff = !lightOnOff;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	public void handleDecode(Result rawResult, Bitmap barcode) {

		beepManager.playBeepSoundAndVibrate();

		progress.setVisibility(View.VISIBLE);
		topTextView.setText("扫描结果处理中");
		String couponResult = "";
		String base64Str = "";
		try {
			base64Str = rawResult.getText();
			byte[] base64Result = Base64.decode(base64Str, Base64.DEFAULT);
			couponResult = HexUtils.bytesToHexString(base64Result);
			if (couponResult.indexOf("f") + 1 == couponResult.length()
					|| couponResult.indexOf("F") + 1 == couponResult.length()) {
				couponResult = couponResult.replace("f", "");
				couponResult = couponResult.replace("F", "");
			}
			couponResult = couponResult.replace("a", "=");
			couponResult = couponResult.replace("A", "=");
		} catch (Exception e) {
			goToErrorQr(rawResult.getText());
			return;
		}

		if (QrScanType.ST_COUPON.equals(scanType)) {
			// 只扫描优惠劵
			if (couponResult.substring(0, 2).equals("97")) {
				Map<String, String> intentData = new HashMap<String, String>();
				intentData.put("couponInfo", base64Str);
				TiFlowControlImpl.instanceControl().nextSetup(this,
						FlowConstants.SUCCESS_STEP1, intentData);
			} else {
				goToErrorQr(rawResult.getText());
				return;
			}
		} else if (QrScanType.ST_ECARD.equals(scanType)) {
			// 只扫描储值卡
			if (couponResult.substring(0, 1).equals("9")
					&& !couponResult.substring(0, 2).equals("97")) {
				String txnAmt = (String) getIntent().getExtras().get("txnAmt");
				String goodFileUrl = (String) getIntent().getExtras().get(
						"goodFileUrl");

				CardValueTxnHelper.sendTxn(txnAmt, couponResult, txnControl,
						goodFileUrl, this);
			} else {
				goToErrorQr(rawResult.getText());
			}
		} else if (QrScanType.ST_BLUETOOTH.equals(scanType)) {
			// 只扫5型机匹配蓝牙二维码
			if (base64Str.substring(0, 1).equals("5")) {
				String[] chars = base64Str.split(",");
				if (chars.length == 3) {
					byte b[] = chars[2].getBytes();
					String identifier = bytesToHexString(b);
					BluetoothListItemClickController.setCardreaderContent(
							aposContext, identifier, chars[1]);
				}
				TiFlowControlImpl.instanceControl().nextSetup(this,
						FlowConstants.SUCCESS);
			} else {
				goToErrorQr(rawResult.getText());
			}
		}

	}

	public void goToErrorQr(String rawResult) {
		Intent intent = new Intent();
		intent.setAction(CommonProvider.COM_SHOW_QRRESULT_ACTIVITY);
		intent.putExtra("couponInfo", rawResult);
		startActivity(intent);
	}

	// byte转化成16进制，以:连接，如：52:62:3F:6C
	public String bytesToHexString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (b == null || b.length <= 0) {
			return "";
		}
		for (int i = b.length - 1; i >= 0; i--) {
			int v = b[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			if (i == (b.length - 1))
				stringBuilder.append(hv.toUpperCase());
			else
				stringBuilder.append(":" + hv.toUpperCase());
		}
		return stringBuilder.toString();
	}

}
