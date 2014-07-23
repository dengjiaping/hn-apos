package me.andpay.apos.scm.activity;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.cdriver.DeviceInfo;
import me.andpay.apos.cdriver.InitMsrKeyResult;
import me.andpay.apos.cdriver.OpenDeivceResult;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.common.log.AposOperationLog;
import me.andpay.apos.common.log.OperationCodes;
import me.andpay.apos.common.log.OperationDataKeys;
import me.andpay.apos.common.service.AudioFileUploadService;
import me.andpay.apos.scm.event.CardReaderAdapterSuccessStartButtonController;
import me.andpay.apos.scm.event.CardReaderTestSwiperController;
import me.andpay.apos.scm.event.GuideButtonController;
import me.andpay.apos.scm.event.RecheckDeviceButtonController;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.AudioUtil;
import me.andpay.timobileframework.util.NetWorkUtil;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.scm_adapter_cardreader_success_layout)
public class CardReaderAdapterSuccessActivity extends AposBaseActivity {

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	public ImageView backButton;

	@InjectView(R.id.scm_cardreader_img)
	public ImageView cardReaderImageView;

	@InjectView(R.id.scm_cardreader_type_content_lay)
	public RelativeLayout cardreaderTypeLay;
	@InjectView(R.id.scm_cardreader_ksn_lay)
	public RelativeLayout ksnLay;
	@InjectView(R.id.scm_cardreader_comm_lay)
	public RelativeLayout commTypeLay;
	@InjectView(R.id.scm_cardreader_bluetoothName_lay)
	public RelativeLayout bluetoothNameLay;

	@InjectView(R.id.scm_cardreader_type_text)
	public TextView cardreaderTypeText;
	@InjectView(R.id.scm_cardreader_ksn_text)
	public TextView ksnText;
	@InjectView(R.id.scm_cardreader_comm_text)
	public TextView commTypeText;
	@InjectView(R.id.scm_cardreader_bluetoothName_text)
	public TextView bluetoothNameText;

	@InjectView(R.id.scm_progress_lay)
	public RelativeLayout progressLay;

	@InjectView(R.id.scm_cardreader_type_content_lay)
	public RelativeLayout contentLay;

	@InjectView(R.id.scm_recheck_button)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = RecheckDeviceButtonController.class)
	public Button recheckButton;

	@InjectView(R.id.scm_top_right_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = GuideButtonController.class)
	public Button topRightButton;

	@InjectView(R.id.scm_set_ok_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = CardReaderAdapterSuccessStartButtonController.class)
	public Button setOkButton;

	@InjectView(R.id.scm_test_swiper_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = CardReaderTestSwiperController.class)
	public Button testSwiper;

	@InjectView(R.id.scm_faild_lay)
	public RelativeLayout failedLayout;

	@InjectView(R.id.com_title_tv)
	public TextView tileTextView;

	@InjectView(R.id.scm_failed_msg)
	private TextView errorMsg;

	private CardReaderSetContext cardReaderSetContext;

	@Inject
	private AudioFileUploadService audioFileUploadService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cardReaderSetContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(CardReaderSetContext.class);

		int cardReaderType = cardReaderSetContext.getCardReaderType();
		setCardRreaderImage(cardReaderType);

		initView(cardReaderType);
		showGuide();

		if (cardReaderType == CardReaderTypes.CLOUD_POS) {
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setKsn("null");
			deviceInfo.setSuccess(true);
			checkResult(deviceInfo);
		} else {
			checkDevice();
		}
	}

	public void showGuide() {
		if (cardReaderSetContext.isShowCardreaderGuide()) {
			topRightButton.setVisibility(View.VISIBLE);
		} else {
			topRightButton.setVisibility(View.GONE);
		}
	}

	/**
	 * 音频设备未插入，直接报错
	 * 
	 * @return
	 */
	public boolean headSetNotInsert() {
		if (!AudioUtil.isHeadsetInsert(this)
				&& CardReaderManager.getDeviceCommType() == DeviceCommunicationTypes.COMM_AUDIO) {
			checkResult(new DeviceInfo());
			return true;
		}

		return false;
	}

	@Override
	protected void onResumeProcess() {
		super.onResumeProcess();

	}

	private void setCardRreaderImage(int cardReaderType) {
		int resourceId = CardReaderResourceSelector
				.selectCardreaderShow(cardReaderType);
		cardReaderImageView.setImageResource(resourceId);

	}

	private boolean isCanReacord() {

		return (cardReaderSetContext.getCardReaderType() == CardReaderTypes.NEW_LAND_AD && cardReaderSetContext
				.getTryTimes() == 1);
	}

	public void checkDevice() {
		if (headSetNotInsert()) {
			// checkResult(new DeviceInfo());
			return;
		}
		topRightButton.setVisibility(View.GONE);
		showProgress();
		final TiActivity tempActivity = this;
		Thread thread = new Thread(new Runnable() {
			public void run() {

				if (isCanReacord()) {
					CardReaderManager.startRecord(cardReaderSetContext
							.getOpTraceNo());
				}

				String defaultIdentifier = (String)tempActivity.getAppConfig()
						.getAttribute(
								CardReaderResourceSelector
										.getBluetoothIdKey(CardReaderManager
												.getCardReaderType()));
				OpenDeivceResult openDeivceResult = CardReaderManager.openDevice(defaultIdentifier);
				DeviceInfo deviceInfo = null;
				if(openDeivceResult.isSuccess()) {
					deviceInfo = CardReaderManager.getDeviceInfo();
				}else {
					deviceInfo = new DeviceInfo();
					deviceInfo.setSuccess(false);
				}
				
				

				if (isMrsInit() && deviceInfo.isSuccess()) {
					InitMsrKeyResult initMsrKeyResult = CardReaderManager
							.getInitMsrKeyService().initMsrKey(
									deviceInfo.getKsn());

					if (!initMsrKeyResult.isSuccess()) {
						deviceInfo.setErrorMsg(initMsrKeyResult.getErrorMsg());
						deviceInfo.setSuccess(false);
						checkResult(deviceInfo);
					}
				}

				if (isCanReacord()) {
					CardReaderManager.stopRecord();
					if (!deviceInfo.isSuccess()) {

						if (NetWorkUtil.isWifi(getApplicationContext())) {
							audioFileUploadService
									.asynUploadFile(cardReaderSetContext
											.getOpTraceNo());
						} else {

							tempActivity.runOnUiThread(new Runnable() {

								public void run() {
									final OperationDialog operationDialog = new OperationDialog(
											tempActivity, "提示",
											"刷卡器错误报告大约为1M,您当前在2g/3g网络,确定上传吗?");
									operationDialog
											.setSureButtonOnclickListener(new OnClickListener() {
												public void onClick(View v) {

													audioFileUploadService
															.asynUploadFile(cardReaderSetContext
																	.getOpTraceNo());
													operationDialog.dismiss();
												}
											});
									operationDialog.show();

								}
							});

						}

					}
				}
				checkResult(deviceInfo);
			}

			private boolean isMrsInit() {
				if (CardReaderManager.getCardReaderType() == CardReaderTypes.NEW_LAND_AD
						|| CardReaderManager.getCardReaderType() == CardReaderTypes.NEW_LAND_BL) {
					return true;
				}
				return false;
			}
		});
		thread.start();
	}

	public void checkSuccess(DeviceInfo deviceInfo) {

		if (this.isFinishing()) {
			return;
		}

		cardReaderSetContext.setKsn(deviceInfo.getKsn());
		int cardReaderType = cardReaderSetContext.getCardReaderType();

		if (cardReaderType == CardReaderTypes.NEW_LAND
				|| cardReaderType == CardReaderTypes.ITRON
				|| cardReaderType == CardReaderTypes.NEW_LAND_AD
				|| cardReaderType == CardReaderTypes.LANDI
				|| cardReaderType == CardReaderTypes.NEW_LAND_BL) {

			ksnLay.setVisibility(View.VISIBLE);
			ksnText.setText(deviceInfo.getKsn());

			if (cardReaderSetContext.isHasSelectCardreader()) {
				testSwiper.setVisibility(View.VISIBLE);
			} else {
				setOkButton.setVisibility(View.VISIBLE);
			}

		} else if (cardReaderType == CardReaderTypes.CLOUD_POS) {
			commTypeLay
					.setBackgroundResource(R.drawable.com_input_bottomline_img);
			setOkButton.setVisibility(View.VISIBLE);
		}

		checkSuccessLog();
	}

	public void setCommTypeText() {
		commTypeLay.setVisibility(View.VISIBLE);
		if (DeviceCommunicationTypes.COMM_AUDIO == CardReaderManager
				.getDeviceCommType()) {
			commTypeText.setText("音频");
		} else if (DeviceCommunicationTypes.COMM_BLUETOOTH == CardReaderManager
				.getDeviceCommType()) {
			commTypeText.setText("蓝牙");
			String bluetoothName = CardReaderResourceSelector
					.getDefaultCardreaderName(getAppConfig(),
							CardReaderManager.getCardReaderType());
			bluetoothNameLay.setVisibility(View.VISIBLE);
			bluetoothNameText.setText(bluetoothName);
			bluetoothNameLay
					.setBackgroundResource(R.drawable.com_input_bottomline_img);
		} else {
			commTypeText.setText("云");
		}
	}

	public void checkResult(final DeviceInfo deviceInfo) {

		this.runOnUiThread(new Runnable() {
			public void run() {
				topRightButton.setVisibility(View.VISIBLE);
				showGuide();
				resetView();
				contentLay.setVisibility(View.VISIBLE);
				setCommTypeText();
				if (deviceInfo.isSuccess()) {
					checkSuccess(deviceInfo);
				} else {
					checkFaild(deviceInfo);
				}
			}
		});
	}

	public void checkFaild(DeviceInfo deviceInfo) {
		if (this.isFinishing()) {
			return;
		}

		recheckButton.setVisibility(View.VISIBLE);
		failedLayout.setVisibility(View.VISIBLE);

		if (deviceInfo.getErrorMsg() != null) {
			errorMsg.setText(deviceInfo.getErrorMsg());
		} else {
			errorMsg.setText("适配失败");

		}

		if (CardReaderManager.getDeviceCommType() == DeviceCommunicationTypes.COMM_AUDIO) {
			checkDolby();
		}
		checkFaildLog(deviceInfo);

	}

	public void checkDolby() {

		if (CardReaderManager.isSupportDolby() || !AudioUtil.isDolbymobile()) {
			return;
		}

		if (AudioUtil.dolbyStatus().equals(AudioUtil.DB_STATUS_OPEN)) {
			showDolbyCheckDialog("您的手机开启了杜比音效,可能对刷卡器造成干扰，请通过设置关闭杜比音效。");
			return;
		}

		if (!cardReaderSetContext.isDolbyHasPrompt()
				&& !AudioUtil.dolbyStatus().equals(AudioUtil.DB_STATUS_CLOSE)) {
			showDolbyCheckDialog("您的手机为杜比音效手机,可能对刷卡器造成干扰，请通过设置关闭杜比音效。");
			return;
		}
	}

	public void showDolbyCheckDialog(String message) {
		final OperationDialog operationDialog = new OperationDialog(this, "提示",
				message);
		operationDialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				operationDialog.dismiss();
				cardReaderSetContext.setDolbyHasPrompt(true);
				Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
				startActivity(intent);
				cardReaderSetContext.getOpLogData().put(
						OperationDataKeys.OPKEYS_SEARCH_DEVICE,
						String.valueOf(true));
			}
		});

		operationDialog.setSureButtonName("设置");
		if (!this.isFinishing()) {
			operationDialog.show();
		}

	}

	public void showProgress() {
		resetView();
		progressLay.setVisibility(View.VISIBLE);
	}

	public void resetView() {
		setOkButton.setVisibility(View.GONE);
		contentLay.setVisibility(View.GONE);
		progressLay.setVisibility(View.GONE);
		testSwiper.setVisibility(View.GONE);
		recheckButton.setVisibility(View.GONE);
		failedLayout.setVisibility(View.GONE);
	}

	private void initView(int cardreaderType) {
		cardreaderTypeLay.setVisibility(View.VISIBLE);
		cardreaderTypeText.setText(CardReaderResourceSelector
				.selectCardreaderCHName(cardreaderType));
		ksnLay.setVisibility(View.GONE);
		commTypeLay.setVisibility(View.GONE);
		bluetoothNameLay.setVisibility(View.GONE);

		if (cardReaderSetContext.isHasSelectCardreader()) {
			tileTextView.setText("当前设备");
		} else {
			tileTextView.setText("设备适配");
		}

	}

	private void checkSuccessLog() {
		Map<String, String> opLogData = checkLog();
		opLogData.put(OperationDataKeys.OPKEYS_CHECKSTATUS, "1");
		AposOperationLog.asynLog(OperationCodes.OPCODE_CHECK_SUCCESS,
				cardReaderSetContext.getOpTraceNo(), opLogData);

	}

	private void checkFaildLog(DeviceInfo deviceInfo) {
		Map<String, String> opLogData = checkLog();
		opLogData.put(OperationDataKeys.OPKEYS_CHECKSTATUS, "0");
		opLogData.put(OperationDataKeys.OPKEYS_ERROR_CODE,
				deviceInfo.getResponseCode());

		AposOperationLog.asynLog(OperationCodes.OPCODE_CHECH_FAILD,
				cardReaderSetContext.getOpTraceNo(), opLogData);

	}

	public Map<String, String> checkLog() {

		// 基本上下文
		Map<String, String> opLogData = cardReaderSetContext.getOpLogData();
		Map<String, String> logData = new HashMap<String, String>();

		for (String key : opLogData.keySet()) {
			logData.put(key, opLogData.get(key));
		}

		logData.put(OperationDataKeys.OPKEYS_CARDREADER_TYPE,
				String.valueOf(cardReaderSetContext.getCardReaderType()));
		logData.put(OperationDataKeys.OPKEYS_KSN, cardReaderSetContext.getKsn());
		logData.put(OperationDataKeys.OPKEYS_COMM_TYPE,
				String.valueOf(CardReaderManager.getDeviceCommType()));

		if (DeviceCommunicationTypes.COMM_BLUETOOTH == CardReaderManager
				.getDeviceCommType()) {
			String bluetoothName = CardReaderResourceSelector
					.getDefaultCardreaderName(getAppConfig(),
							CardReaderManager.getCardReaderType());
			logData.put(OperationDataKeys.OPKEYS_BLUETOOTH_NAME, bluetoothName);
			BluetoothAdapter bluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			logData.put(OperationDataKeys.OPKEYS_BLUETOOTH_STATUS,
					String.valueOf(bluetoothAdapter.getState()));
		} else if (DeviceCommunicationTypes.COMM_AUDIO == CardReaderManager
				.getDeviceCommType()) {
			// 手机环境
			logData.put(OperationDataKeys.OPKEYS_DOLBY_STATUS,
					AudioUtil.dolbyStatus());
			logData.put(OperationDataKeys.OPKEYS_IS_MOBILE_DOLBY,
					String.valueOf(AudioUtil.isDolbymobile()));
			logData.put(OperationDataKeys.OPKEYS_MIC_STATUS,
					String.valueOf(AudioUtil.microphoneState(this)));
			logData.put(OperationDataKeys.OPKEYS_HEADSET_STATUS,
					String.valueOf(AudioUtil.headsetStatus(this)));
			AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			logData.put(OperationDataKeys.OPKEYS_VOLUME,
					String.valueOf(current));
			logData.put(OperationDataKeys.OPKEYS_MAX_VOLUME, String.valueOf(am
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC)));
		}

		return logData;
	}

}
