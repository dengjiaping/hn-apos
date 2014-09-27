package me.andpay.apos.cardreader.callback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.ic.ICTxnSettleList;
import me.andpay.ac.term.api.txn.order.OrderRecord;
import me.andpay.apos.R;
import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cardreader.view.SelectCardreaderDialog;
import me.andpay.apos.cardreader.view.SelectCardreaderDialog.OnDialogItemClickListener;
import me.andpay.apos.cdriver.AposSwiperContext;
import me.andpay.apos.cdriver.CardInfo;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.DeviceCommunicationTypes;
import me.andpay.apos.cdriver.callback.CardReaderCallback;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.cdriver.model.AposResultData;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.ResponseCodes;
import me.andpay.apos.common.log.AposDebugLog;
import me.andpay.apos.common.service.ExceptionPayTxnInfoService;
import me.andpay.apos.common.service.ICCardSumbitSettleLListService;
import me.andpay.apos.common.service.TxnConfirmService;
import me.andpay.apos.common.service.TxnReversalService;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.OrderInfoDao;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.dao.model.OrderInfoCond;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.dao.model.QueryPayTxnInfoCond;
import me.andpay.apos.dao.model.TxnConfirm;
import me.andpay.apos.tam.TamProvider;
import me.andpay.apos.tam.action.CardBinAction;
import me.andpay.apos.tam.action.txn.TxnHelper;
import me.andpay.apos.tam.activity.TxnAcitivty;
import me.andpay.apos.tam.callback.impl.ParseBinCallbackImpl;
import me.andpay.apos.tam.callback.impl.TxnCallbackHelper;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.context.TxnStatus;
import me.andpay.apos.tam.flow.model.TxnCancelFlag;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.CardBinRequest;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.ti.lnk.transport.wsock.client.light.http.Base64;
import me.andpay.ti.util.DateUtil;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.HexUtils;
import me.andpay.timobileframework.util.tlv.TlvUtil;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.google.inject.Inject;
import com.landicorp.android.mpay.commdriver.util.StringUtil;

/**
 * 刷卡回调
 * 
 * @author cpz
 * 
 */
public class SwipCardReaderCallBack implements CardReaderCallback {

	@Inject
	private TxnControl txnControl;

	@Inject
	private DefaultCardReaderCallBack defaulCallback;
	@Inject
	private SwipCardReaderCallBack swipCardReaderCallBack;

	@Inject
	private TxnReversalService txnReversalService;

	private PromptDialog mathBluetoothHelpDialog;

	private SelectCardreaderDialog deviceSelectDialog;

	@Inject
	private ICCardSumbitSettleLListService iCCardSumbitSettleLListService;

	@Inject
	private ExceptionPayTxnInfoService exceptionPayTxnInfoService;
	@Inject
	private TxnConfirmService txnConfirmService;

	@Inject
	private PayTxnInfoDao payTxnInfoDao;

	// @Inject
	// private OrderInfoDao orderInfoDao;

	public void onDevicePlugged() {
		TxnAcitivty tiActivity = (TxnAcitivty) txnControl.getCurrActivity();
		if (tiActivity == null || tiActivity.isFinishing()) {
			return;
		}

		Toast toast = Toast.makeText(tiActivity, "", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		toastView.setBackgroundColor(tiActivity.getResources().getColor(
				R.color.com_translucent_col));

		ImageView imageCodeProject = new ImageView(tiActivity);
		imageCodeProject.setImageResource(CardReaderResourceSelector
				.selectShowImg(CardReaderManager.getCardReaderType(),
						CardReaderResourceSelector.CONNECT));

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		toastView.addView(imageCodeProject, params);
		toast.show();

		txnControl.changeTxnStatus(TxnStatus.WAIT_SWIPER, tiActivity);

	}

	public void onDeviceUnplugged() {
		final TiActivity tiActivity = txnControl.getCurrActivity();
		if (tiActivity.isFinishing()) {
			return;
		}

		tiActivity.runOnUiThread(new Runnable() {

			public void run() {
				Toast toast = Toast
						.makeText(tiActivity, "", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView = (LinearLayout) toast.getView();
				toastView.setBackgroundColor(tiActivity.getResources()
						.getColor(R.color.com_translucent_col));
				ImageView imageCodeProject = new ImageView(tiActivity);
				imageCodeProject.setImageResource(CardReaderResourceSelector
						.selectShowImg(CardReaderManager.getCardReaderType(),
								CardReaderResourceSelector.SWIPER));
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
				toastView.addView(imageCodeProject, params);
				toast.show();

				txnControl.changeTxnStatus(TxnStatus.WAIT_CARDREADER,
						tiActivity);
				CardReaderManager.setCurrCallback(swipCardReaderCallBack);
			}
		});

	}

	@SuppressWarnings("unchecked")
	public void onDecodeCompleted(final CardInfo cardInfo) {

		AposDebugLog.log("DEBUG", null, "onDecodeCompleted");

		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		if (tiActivity.isFinishing()) {
			return;
		}
		final TxnContext txnContext = txnControl.getTxnContext();

		if (!setMac(txnContext, cardInfo, null)) {
			return;
		}

		final SwipCardReaderCallBack callback = this;

		tiActivity.runOnUiThread(new Runnable() {

			public void run() {
				CardReaderManager.setDefaultCallBack();
				tiActivity.amtTxnView.setText(txnControl.getTxnContext()
						.getAmtFomat());

				txnContext.setCardInfo(cardInfo);

				CardBinRequest binRequest = new CardBinRequest();
				binRequest.setKsn(cardInfo.getKsn());
				binRequest.setRandomNum(cardInfo.getRandomNumber());
				binRequest.setTrackAll(cardInfo.getEncTracks());

				if (cardInfo.getEncTracks() != null
						&& CardReaderManager.getInputType() == AposSwiperContext.INPUT_KEY_BOARD) {
					EventRequest request = tiActivity
							.generateSubmitRequest(tiActivity);
					request.open(CardBinAction.DOMAIN_NAME,
							CardBinAction.ACTION_NAME_PARSE, Pattern.async);
					request.getSubmitData().put(CardBinAction.CARDBIN_INFO,
							binRequest);

					request.callBack(new ParseBinCallbackImpl(tiActivity,
							callback));
					request.submit();
				} else {
					// 等待密码输入
					txnControl.changeTxnStatus(TxnStatus.WAIT_PASSWORD,
							txnControl.getCurrActivity());
				}

			}
		});

	}

	private boolean setMac(TxnContext txnContext, CardInfo cardInfo,
			AposICCardDataInfo aposICCardDataInfo) {
		if (CardReaderManager.getCardReaderType() == CardReaderTypes.NEW_LAND_BL) {
			Log.e(this.getClass().getName(), "start mac");
			String sourceData = genMacSourceData(txnContext.getSalesAmt(),
					cardInfo, aposICCardDataInfo);

			AposResultData aposResultData = CardReaderManager.calculateMac(
					cardInfo.getPinRandNumber(), sourceData);

			if (aposResultData.isSuccess()) {
				txnContext.setMac(aposResultData.getData());
			} else {
				onDecodeError("发送数据异常!");
				return false;
			}

			Log.e(this.getClass().getName(), "end mac");

		}

		return true;

	}

	public void onDecodeError(String msg) {

		final TxnAcitivty txnAcitivty = (TxnAcitivty) txnControl
				.getCurrActivity();
		if (txnAcitivty.isFinishing()) {
			return;
		}
		final String errorMsg = msg;

		txnAcitivty.runOnUiThread(new Runnable() {

			public void run() {

				if (CardReaderManager.getCardReaderType() == CardReaderTypes.NEW_LAND) {

					Toast.makeText(txnAcitivty.getApplicationContext(),
							errorMsg, Toast.LENGTH_SHORT).show();
				} else {
					final OperationDialog dialog = new OperationDialog(
							txnAcitivty, ResourceUtil.getString(txnAcitivty,
									R.string.tam_cardreader_error_str),
							errorMsg, true);
					dialog.setCancelButtonName(ResourceUtil.getString(
							txnAcitivty, R.string.com_back_str));
					dialog.setSureButtonName(ResourceUtil.getString(
							txnAcitivty, R.string.tam_reswiper_str));
					dialog.setCancelable(false);
					dialog.setCancelButtonOnclickListener(new OnClickListener() {

						public void onClick(View v) {
							dialog.dismiss();
							txnAcitivty.txnControl.reInput();
						}
					});

					dialog.setSureButtonOnclickListener(new OnClickListener() {

						public void onClick(View v) {
							dialog.dismiss();
							txnAcitivty.txnControl.retrySwiper();
						}
					});
					dialog.show();
				}

			}
		});

	}

	/**
	 * 刷卡超时
	 */
	public void onTimeout() {

		final TxnAcitivty txnAcitivty = (TxnAcitivty) txnControl
				.getCurrActivity();
		if (txnAcitivty.isFinishing()) {
			return;
		}

		txnAcitivty.runOnUiThread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				if (txnControl.isDead(txnControl.getCurrActivity())) {
					return;
				}

				final OperationDialog dialog = new OperationDialog(txnControl
						.getCurrActivity(), txnControl.getCurrActivity()
						.getApplicationContext().getResources()
						.getString(R.string.com_show_str), ResourceUtil
						.getString(txnControl.getCurrActivity()
								.getApplicationContext(),
								R.string.tam_card_reader_timeout_str), true);
				dialog.setCancelable(false);
				dialog.setCancelButtonOnclickListener(new OnClickListener() {

					public void onClick(View v) {
						CardReaderManager.setCurrCallback(defaulCallback);
						txnControl.reInput();
					}
				});

				dialog.setSureButtonOnclickListener(new OnClickListener() {

					public void onClick(View v) {
						dialog.dismiss();

						CardReaderManager
								.setCurrCallback(swipCardReaderCallBack);
						txnControl.changeTxnStatus(TxnStatus.WAIT_SWIPER,
								txnControl.getCurrActivity());

					}
				});

				dialog.show();
			}

		});

	}

	private void cloudWaitForCardSwipe(TxnAcitivty tiActivity) {

		tiActivity.topTextView.setText(ResourceUtil.getString(tiActivity,
				R.string.tam_top_cloud_order_upload_str));
		txnControl.getTxnContext().setCloudOrder(true);
		txnControl.getTxnContext().setCardInfo(new CardInfo());
		// 设置交易取消标识
		TxnCancelFlag flag = new TxnCancelFlag();
		flag.startTxn();
		txnControl.getTxnContext().setTxnCancelFlag(flag);
		txnControl.submitNoCardTxn(tiActivity);
	}

	public void onWaitingForCardSwipe() {

		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		tiActivity.runOnUiThread(new Runnable() {
			public void run() {

				if (CardReaderManager.getDeviceCommType() == DeviceCommunicationTypes.COMM_CLOUD) {
					cloudWaitForCardSwipe(tiActivity);
				} else {
					tiActivity.topTextView.setText(ResourceUtil.getString(
							tiActivity.getApplicationContext(),
							R.string.tam_top_swip_str));
				}

			}
		});
	}

	private void cloudPosCancel(TxnAcitivty tiActivity) {

		TxnContext context = txnControl.getTxnContext();
		EventRequest request = tiActivity.generateSubmitRequest(tiActivity);
		request.open(TamProvider.TAM_DOMAIN_CLOUD,
				TamProvider.TAM_ACTION_CLOUD_CANCEL, Pattern.async);
		request.getSubmitData().put(TamProvider.TAM_ACTION_PARAM_CLOUDORDERID,
				context.getCloudOrderId());
		request.getSubmitData().put(TamProvider.TAM_ACTION_PARAM_TXNCANCELFLAG,
				context.getTxnCancelFlag());
		request.submit();
	}

	public void onCancel() {
		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();

		tiActivity.runOnUiThread(new Runnable() {

			public void run() {
				tiActivity.topTextView.setText(ResourceUtil.getString(
						tiActivity, R.string.tam_card_reader_canceling_str));

			}
		});

		tiActivity.runOnUiThread(new Runnable() {

			public void run() {
				if (tiActivity.isFinishing()) {
					return;
				}
				CardReaderManager.setCurrCallback(defaulCallback);

				if (CardReaderManager.getDeviceCommType() == DeviceCommunicationTypes.COMM_CLOUD) {
					cloudPosCancel(tiActivity);
				} else {
					CardReaderManager.stopSwiper();

				}

				if (tiActivity.isFinishing()) {
					return;
				}
				Toast.makeText(
						txnControl.getCurrActivity().getApplicationContext(),
						ResourceUtil.getString(tiActivity,
								R.string.tam_card_reader_cancel_str),
						Toast.LENGTH_LONG).show();
				txnControl.reInput();

			}
		});

	}

	public void onDecodingStart() {
		TxnAcitivty tiActivity = (TxnAcitivty) txnControl.getCurrActivity();
		tiActivity.topTextView.setText(ResourceUtil.getString(tiActivity,
				R.string.tam_card_parsing_str));

	}

	public void onReceiveDataStart() {
		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		tiActivity.topTextView.post(new Runnable() {
			public void run() {
				tiActivity.topTextView.setText(ResourceUtil.getString(
						tiActivity, R.string.tam_card_data_receive_str));
			}
		});

	}

	public void OnWaitingPin() {
		// TODO Auto-generated method stub

	}

	public void seachDeviceComplete(List<CardReaderInfo> cardReaderInfos) {

		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();

		if (cardReaderInfos.size() == 0) {

			final OperationDialog dialog = new OperationDialog(tiActivity,
					"没有找到蓝牙刷卡器", "请确定设备是否开机,或者断开与其他的连接。", true);
			dialog.setCancelable(false);
			dialog.setCancelButtonOnclickListener(new OnClickListener() {
				public void onClick(View v) {
					dialog.dismiss();
					CardReaderManager.setCurrCallback(defaulCallback);
					txnControl.reInput();
				}
			});
			dialog.setCancelButtonName("返回");
			dialog.setSureButtonOnclickListener(new OnClickListener() {

				public void onClick(View v) {
					dialog.dismiss();
					CardReaderManager.setCurrCallback(swipCardReaderCallBack);
					CardReaderManager.searchDevice();
				}
			});
			dialog.setSureButtonName("重新搜索");
			dialog.show();
			return;
		} else {
			// showDeviceList(tiActivity, cardReaderInfos);
			if (deviceSelectDialog != null) {
				deviceSelectDialog.stopProgress();
			}
		}
	}

	private void showDeviceList(final TxnAcitivty tiActivity,
			List<CardReaderInfo> cardReaderInfos) {

		deviceSelectDialog = new SelectCardreaderDialog(tiActivity,
				cardReaderInfos);
		deviceSelectDialog
				.setOnDialogItemClickListener(new OnDialogItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						CardReaderInfo cardReaderInfo = (CardReaderInfo) parent
								.getItemAtPosition(position);
						startBluetoothSwiper(cardReaderInfo, tiActivity);

					}
				});
		deviceSelectDialog.setCancelable(false);

		deviceSelectDialog.setCancelButtonName("返回");
		deviceSelectDialog
				.setCancelButtonOnclickListener(new OnClickListener() {
					public void onClick(View v) {
						deviceSelectDialog.dismiss();
						txnControl.reInput();
					}
				});
		deviceSelectDialog.setSureButtonName("重新搜索");
		deviceSelectDialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				deviceSelectDialog.dismiss();
				CardReaderManager.searchDevice();
			}
		});

		deviceSelectDialog.show();

	}

	private void startBluetoothSwiper(CardReaderInfo cardReaderInfo,
			TiActivity tiActivity) {
		if (CardReaderTypes.NEW_LAND_BL == CardReaderManager
				.getCardReaderType()) {
			tiActivity.getAppConfig().setAttribute(
					ConfigAttrNames.NEWLAND_DEFAULT_BLUETOOTH_IDENTIFIER,
					cardReaderInfo.getmIdentifier());
			tiActivity.getAppConfig().setAttribute(
					ConfigAttrNames.NEWLAND_DEFAULT_BLUETOOTH_NAME,
					cardReaderInfo.getmName());

		} else {
			tiActivity.getAppConfig().setAttribute(
					ConfigAttrNames.LANDI_DEFAULT_BLUETOOTH_IDENTIFIER,
					cardReaderInfo.getmIdentifier());
			tiActivity.getAppConfig().setAttribute(
					ConfigAttrNames.LANDI_DEFAULT_BLUETOOTH_NAME,
					cardReaderInfo.getmName());

		}
		CardReaderManager.setCurrCallback(txnControl
				.getSwipCardReaderCallBack());
		CardReaderManager.startSwiper(AposCardReaderUtil.convertSwiperRequest(
				txnControl.getTxnContext(), tiActivity.getAppConfig()));
	}

	public void onSeachDevice() {

		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		tiActivity.topTextView.post(new Runnable() {
			public void run() {
				txnControl.changeTxnStatus(TxnStatus.SEARCH_DEVICE,
						txnControl.getCurrActivity());
			}
		});

	}

	public void onShowSwiper() {

		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		tiActivity.runOnUiThread(new Runnable() {

			public void run() {
				txnControl.changeTxnStatus(TxnStatus.SHOW_WAIT_SWIPER,
						txnControl.getCurrActivity());

			}
		});
	}

	public void getTxnError() {

		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		tiActivity.runOnUiThread(new Runnable() {

			public void run() {
				tiActivity.topTextView.setText("交易获取失败");
				final OperationDialog dialog = new OperationDialog(txnControl
						.getCurrActivity(), txnControl.getCurrActivity()
						.getApplicationContext().getResources()
						.getString(R.string.com_show_str),
						"交易获取失败，请在蓝牙设备上完成刷卡后再点击重新获取按钮", true);
				dialog.setCancelable(false);
				dialog.setCancelButtonOnclickListener(new OnClickListener() {
					public void onClick(View v) {
						dialog.dismiss();
						CardReaderManager.setCurrCallback(defaulCallback);
						txnControl.reInput();
					}
				});
				dialog.setCancelButtonName("返回");
				dialog.setSureButtonOnclickListener(new OnClickListener() {
					public void onClick(View v) {
						dialog.dismiss();
						CardReaderManager
								.setCurrCallback(swipCardReaderCallBack);
						CardReaderManager.startSwiper(AposCardReaderUtil
								.convertSwiperRequest(
										txnControl.getTxnContext(),
										tiActivity.getAppConfig()));
					}
				});
				dialog.setSureButtonName("重新获取");
				dialog.show();

			}
		});

		return;
	}

	public void onSendDataError() {

		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		final TxnContext txnContext = txnControl.getTxnContext();
		tiActivity.runOnUiThread(new Runnable() {
			public void run() {
				CardReaderManager.setCurrCallback(defaulCallback);

				final OperationDialog dialog = new OperationDialog(txnControl
						.getCurrActivity(), txnControl.getCurrActivity()
						.getApplicationContext().getResources()
						.getString(R.string.com_show_str), "与刷卡器的通讯失败，请重试！",
						false);
				dialog.setCancelable(false);
				dialog.setCancelButtonOnclickListener(new OnClickListener() {

					public void onClick(View v) {
						dialog.dismiss();
						CardReaderManager.setCurrCallback(defaulCallback);
						txnControl.reInput();
					}
				});
				dialog.setCancelButtonName("返回");

				dialog.setSureButtonOnclickListener(new OnClickListener() {
					final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
							.getCurrActivity();

					public void onClick(View v) {
						dialog.dismiss();
						tiActivity.topTextView.setText(ResourceUtil.getString(
								tiActivity, R.string.tam_device_check_str));
						CardReaderManager
								.setCurrCallback(swipCardReaderCallBack);
						CardReaderManager.startSwiper(AposCardReaderUtil
								.convertSwiperRequest(txnContext,
										tiActivity.getAppConfig()));
					}
				});

				dialog.show();

			}
		});

	}

	public void matchBluetoothHelp() {
		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		tiActivity.runOnUiThread(new Runnable() {
			public void run() {
				mathBluetoothHelpDialog = new PromptDialog(tiActivity, "匹配提示",
						"请在android的消息框处理");
				mathBluetoothHelpDialog.show();
			}
		});

	}

	public void onConnectError() {

		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();

		if (CardReaderManager.getDeviceCommType() == DeviceCommunicationTypes.COMM_AUDIO) {

			tiActivity.runOnUiThread(new Runnable() {

				public void run() {

					final OperationDialog dialog = new OperationDialog(
							tiActivity, "失败", "连接读卡器失败，请重新尝试插拔耳机线，确认正确连接后再重试。",
							true);
					dialog.setCancelable(false);
					dialog.setCancelButtonName("重试");

					dialog.setCancelButtonOnclickListener(new OnClickListener() {
						public void onClick(View v) {
							dialog.dismiss();

							CardReaderManager.setCurrCallback(txnControl
									.getSwipCardReaderCallBack());
							CardReaderManager.startSwiper(AposCardReaderUtil
									.convertSwiperRequest(
											txnControl.getTxnContext(),
											tiActivity.getAppConfig()));
						}
					});

					dialog.setSureButtonName("返回");
					dialog.setSureButtonOnclickListener(new OnClickListener() {

						public void onClick(View v) {
							dialog.dismiss();
							txnControl.reInput();
						}
					});
					dialog.show();
					return;

				}
			});

		} else {
			tiActivity.runOnUiThread(new Runnable() {

				public void run() {
					String cardreaderName = CardReaderResourceSelector
							.getDefaultCardreaderName(
									tiActivity.getAppConfig(),
									CardReaderManager.getCardReaderType());

					final OperationDialog dialog = new OperationDialog(
							tiActivity, "失败", "连接读卡器" + cardreaderName
									+ "失败,请开机后重试，如果想使用其他设备，请选择搜索设备。", true);
					dialog.setCancelable(false);
					dialog.setCancelButtonName("重试");

					dialog.setCancelButtonOnclickListener(new OnClickListener() {
						public void onClick(View v) {
							dialog.dismiss();

							CardReaderManager.setCurrCallback(txnControl
									.getSwipCardReaderCallBack());
							CardReaderManager.startSwiper(AposCardReaderUtil
									.convertSwiperRequest(
											txnControl.getTxnContext(),
											tiActivity.getAppConfig()));
						}
					});

					dialog.setSureButtonName("搜索设备");
					dialog.setSureButtonOnclickListener(new OnClickListener() {

						public void onClick(View v) {
							dialog.dismiss();
							CardReaderManager.searchDevice();
						}
					});
					dialog.show();
					return;

				}
			});
		}

	}

	public void seachFoundDevice(final CardReaderInfo cardReaderInfo,
			final List<CardReaderInfo> cardReaderInfos) {
		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		tiActivity.runOnUiThread(new Runnable() {
			public void run() {

				if (cardReaderInfos.size() == 1) {
					List<CardReaderInfo> deCardReaderInfos = new ArrayList<CardReaderInfo>();
					deCardReaderInfos.add(cardReaderInfo);
					showDeviceList(tiActivity, deCardReaderInfos);
				} else {
					deviceSelectDialog.updataListData(cardReaderInfo);
				}
				deviceSelectDialog.showProgress();
			}
		});

	}

	public void initKeyError(final String errorMsg) {

		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		tiActivity.runOnUiThread(new Runnable() {
			public void run() {
				final OperationDialog dialog = new OperationDialog(tiActivity,
						"设备初始化失败", errorMsg, true);
				dialog.setCancelable(false);
				dialog.setCancelButtonName("重试");

				dialog.setCancelButtonOnclickListener(new OnClickListener() {
					public void onClick(View v) {
						dialog.dismiss();

						CardReaderManager.setCurrCallback(txnControl
								.getSwipCardReaderCallBack());
						CardReaderManager.startSwiper(AposCardReaderUtil
								.convertSwiperRequest(
										txnControl.getTxnContext(),
										tiActivity.getAppConfig()));
					}
				});

				dialog.setSureButtonName("返回");
				dialog.setSureButtonOnclickListener(new OnClickListener() {
					public void onClick(View v) {
						dialog.dismiss();
						txnControl.reInput();
					}
				});
				dialog.show();
				return;
			}
		});

	}

	public void onDeviceError(final String msg, String errorCode) {
		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		TxnCallbackHelper.clearAc(txnControl);

		tiActivity.runOnUiThread(new Runnable() {
			public void run() {
				CardReaderManager.setCurrCallback(defaulCallback);

				String errorString = "";
				if (StringUtil.isEmpty(msg)) {
					if (CardReaderManager.getDeviceCommType() == DeviceCommunicationTypes.COMM_AUDIO) {
						errorString = "设备未插紧或者连接的非"
								+ CardReaderResourceSelector
										.selectCardreaderCHName(CardReaderManager
												.getCardReaderType()) + "设备。";
					} else {
						errorString = "请确认设备正常开机后，点击重试按钮。";
					}
				} else {
					errorString = msg;
				}

				final OperationDialog dialog = new OperationDialog(txnControl
						.getCurrActivity(), txnControl.getCurrActivity()
						.getApplicationContext().getResources()
						.getString(R.string.com_show_str), errorString, false);
				dialog.setCancelable(false);
				dialog.setCancelButtonOnclickListener(new OnClickListener() {

					public void onClick(View v) {
						dialog.dismiss();
						CardReaderManager.setCurrCallback(defaulCallback);
						txnControl.reInput();
					}
				});

				dialog.setSureButtonOnclickListener(new OnClickListener() {
					final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
							.getCurrActivity();

					public void onClick(View v) {
						dialog.dismiss();

						tiActivity.topTextView.setText(ResourceUtil.getString(
								tiActivity, R.string.tam_device_check_str));
						CardReaderManager
								.setCurrCallback(swipCardReaderCallBack);
						txnControl.changeTxnStatus(TxnStatus.WAIT_SWIPER,
								txnControl.getCurrActivity());

					}
				});
				dialog.setSureButtonName("重试");

				dialog.show();

			}
		});

	}

	public void onICFinished(boolean finish, AposICCardDataInfo icCardDataInfo) {

		if (finish) {
			if (txnControl != null && txnControl.getTxnDialog().isShowing()) {
				txnControl.getTxnDialog().cancel();
			}
			String txnId = txnControl.getTxnContext().getTxnActionResponse()
					.getTxnId();
			iCCardSumbitSettleLListService.submitICTxnSettleList(
					icCardDataInfo, txnId);
			// IC卡手动发送ACK
			txnConfirmService.addCpmfirmTxn(txnId);
			txnConfirmService.sendConfirmTxn();

			TxnCallbackHelper.txnSuccess(txnControl.getCurrActivity(),
					txnControl.getTxnContext().getTxnActionResponse());

		}

	}

	public String formatNullString(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	/**
	 * 获得银行卡和相应交易金额信息
	 * 
	 * @param amt
	 * @param cardInfo
	 * @param aposICCardDataInfo
	 * @return
	 */
	public String genMacSourceData(BigDecimal amt, CardInfo cardInfo,
			AposICCardDataInfo aposICCardDataInfo) {
		StringBuffer macDataBuff = new StringBuffer();

		macDataBuff.append(formatNullString(cardInfo.getEncTracks()));
		macDataBuff.append(formatNullString(HexUtils.bytesToHexString(cardInfo
				.getPin())));

		if (amt != null) {
			amt = amt.multiply(new BigDecimal("100")).setScale(0);
			macDataBuff.append(HexUtils.bytesToHexString(amt.toString()
					.getBytes()));
		}
		macDataBuff.append(formatNullString(cardInfo.getPinRandNumber()));
		macDataBuff.append(formatNullString(cardInfo.getKsn()));

		if (aposICCardDataInfo != null) {
			String tlvString = TlvUtil.encodeTvl(aposICCardDataInfo);

			String hexData = HexUtils.bytesToHexString(Base64.encode(
					HexUtils.hexString2Bytes(tlvString)).getBytes());

			macDataBuff.append(hexData);
		}

		return macDataBuff.toString();
	}

	public void onICRequestOnline(final AposICCardDataInfo icCardDataInfo) {
		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		if (tiActivity.isFinishing()) {
			return;
		}
		final TxnContext txnContext = txnControl.getTxnContext();

		txnContext.setAposICCardDataInfo(icCardDataInfo);
		// ic卡pin数据
		final CardInfo cardInfo = new CardInfo();
		cardInfo.setKsn(icCardDataInfo.getKsn());
		cardInfo.setPinRandNumber(icCardDataInfo.getTerminalTraceNo());
		cardInfo.setRandomNumber(icCardDataInfo.getTerminalTraceNo());
		if (icCardDataInfo.getPinData() != null) {
			cardInfo.setPin(HexUtils.hexString2Bytes(icCardDataInfo
					.getPinData()));
		}
		if (!StringUtil.isEmpty(icCardDataInfo.getTrackAll())) {
			cardInfo.setEncTracks(icCardDataInfo.getTrackAll());
		}

		if (!setMac(txnContext, cardInfo, icCardDataInfo)) {
			return;
		}

		tiActivity.runOnUiThread(new Runnable() {
			public void run() {
				tiActivity.amtTxnView.setText(txnControl.getTxnContext()
						.getAmtFomat());

				txnContext.setCardInfo(cardInfo);
				txnContext.setIcCardTxn(true);
				txnControl.changeTxnStatus(TxnStatus.WAIT_PASSWORD,
						txnControl.getCurrActivity());

			}
		});
	}

	public void onICSwipeRefuse() {

		final PromptDialog diPromptDialog = new PromptDialog(
				txnControl.getCurrActivity(), "提示", "您的银行卡带有IC卡芯片,请用插卡的方式完成交易。");

		diPromptDialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				txnControl.retrySwiper();
				diPromptDialog.dismiss();
			}
		});
		diPromptDialog.show();
	}

	private PayTxnInfo queryTxnInfo(TxnActionResponse actionResponse) {
		QueryPayTxnInfoCond cond = new QueryPayTxnInfoCond();
		cond.setTermTraceNo(actionResponse.getTermTraceNo());
		cond.setTermTxnTime(actionResponse.getTermTxnTime());
		List<PayTxnInfo> payList = payTxnInfoDao.query(cond, 0, 1);
		PayTxnInfo payTxnInfo = null;
		if (payList.size() > 0) {
			payTxnInfo = payList.get(0);
		}

		return payTxnInfo;
	}

	public void onSecondIssuanceError() {

		TxnActionResponse actionResponse = txnControl.getTxnContext()
				.getTxnActionResponse();

		PayTxnInfo payTxnInfo = queryTxnInfo(actionResponse);
		// 如果交易已经成功，需要发送冲正
		if (payTxnInfo != null
				&& payTxnInfo.getTxnFlag().equals(
						PayTxnInfoStatus.STATUS_SUCCESS)) {

			payTxnInfo.setTxnStatus(PayTxnInfoStatus.STATUS_FAIL);
			payTxnInfo.setRespMsg("交易失败");
			payTxnInfo.setTxnFlagMessage("失败");
			payTxnInfoDao.update(payTxnInfo);

			exceptionPayTxnInfoService.changeExceptionStatus(
					actionResponse.getTermTraceNo(),
					actionResponse.getTermTxnTime(),
					ExceptionPayTxnInfo.EXP_STATUS_WAIT_REVERSE);

			txnReversalService.reversalTxn();
		}

		final TxnAcitivty tiActivity = (TxnAcitivty) txnControl
				.getCurrActivity();
		TxnCallbackHelper.clearAc(txnControl);
		txnControl.getTxnContext().getTxnActionResponse()
				.setResponMsg("IC卡没有插好或者读取失败。");
		if (txnControl.getTxnContext().getTxnActionResponse().getTxnResponse() != null) {
			txnControl.getTxnContext().getTxnActionResponse().getTxnResponse()
					.setRespCode(ResponseCodes.SYS_ERROR);
		}
		TxnCallbackHelper.txnFailed(txnControl.getTxnContext(), txnControl
				.getTxnContext().getTxnActionResponse(), tiActivity);
	}
}
