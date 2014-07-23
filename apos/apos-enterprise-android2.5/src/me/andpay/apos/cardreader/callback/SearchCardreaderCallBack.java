package me.andpay.apos.cardreader.callback;

import java.util.ArrayList;
import java.util.List;

import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cardreader.view.SelectCardreaderDialog;
import me.andpay.apos.cardreader.view.SelectCardreaderDialog.OnDialogItemClickListener;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cdriver.callback.CardReaderCallback;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.timobileframework.mvc.support.TiActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.inject.Inject;

public class SearchCardreaderCallBack extends DefaultCardReaderCallBack {

	private TiActivity tiActivity;

	@Inject
	private CardReaderManager cardReaderManager;

	private CommonDialog commonDialog;

	private SelectCardreaderDialog deviceSelectDialog;

	public void setTiActivity(TiActivity tiActivity) {
		this.tiActivity = tiActivity;
	}

	public void seachFoundDevice(final CardReaderInfo cardReaderInfo,
			final List<CardReaderInfo> cardReaderInfos) {

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

	private void showDeviceList(final TiActivity tiActivity,
			List<CardReaderInfo> deCardReaderInfos) {
		deviceSelectDialog = new SelectCardreaderDialog(tiActivity,
				deCardReaderInfos);
		deviceSelectDialog
				.setOnDialogItemClickListener(new OnDialogItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						CardReaderInfo cardReaderInfo = (CardReaderInfo) parent
								.getItemAtPosition(position);
						setCardreader(cardReaderInfo);
						Toast.makeText(tiActivity.getApplicationContext(),
								"设备" + cardReaderInfo.getmName() + "已选择",
								Toast.LENGTH_LONG).show();

					}
				});

		deviceSelectDialog.setShowButton(false);

		deviceSelectDialog.show();

		commonDialog.cancel();

	}

	@Override
	public void onSeachDevice() {
		tiActivity.runOnUiThread(new Runnable() {

			public void run() {
				commonDialog = new CommonDialog(tiActivity, "设备搜索中...");
				commonDialog.show();
			}
		});

	}

	@Override
	public void seachDeviceComplete(final List<CardReaderInfo> cardReaderInfos) {

		final CardReaderCallback callback = this;

		tiActivity.runOnUiThread(new Runnable() {
			public void run() {
				commonDialog.cancel();
				if (cardReaderInfos == null || cardReaderInfos.size() <= 0) {

					final OperationDialog dialog = new OperationDialog(
							tiActivity, "没有找到蓝牙刷卡器", "请确定设备是否开机,或者断开与其他的连接。",
							true);
					dialog.setCancelable(false);
					dialog.setCancelButtonOnclickListener(new OnClickListener() {
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					dialog.setCancelButtonName("取消");
					dialog.setSureButtonOnclickListener(new OnClickListener() {
						public void onClick(View v) {
							dialog.dismiss();
							CardReaderManager.setCurrCallback(callback);
							CardReaderManager.searchDevice();
						}
					});
					dialog.setSureButtonName("重新搜索");
					dialog.show();
					return;
				} else {
					deviceSelectDialog.stopProgress();
				}

			}
		});

	}

	public void setCardreader(CardReaderInfo cardReaderInfo) {
		tiActivity.getAppConfig().setAttribute(
				CardReaderResourceSelector.getBluetoothIdKey(CardReaderManager
						.getCardReaderType()), cardReaderInfo.getmIdentifier());
		tiActivity
				.getAppConfig()
				.setAttribute(
						CardReaderResourceSelector.getBluetoothNameKey(CardReaderManager
								.getCardReaderType()),
						cardReaderInfo.getmName());

	}

}
