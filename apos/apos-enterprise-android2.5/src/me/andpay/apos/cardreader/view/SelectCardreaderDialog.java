package me.andpay.apos.cardreader.view;

import java.util.List;

import me.andpay.apos.R;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectCardreaderDialog implements OnItemClickListener {

	private Dialog dialog;
	private Context context;
	private OnDialogItemClickListener onDialogItemClickListener;
	private List<CardReaderInfo> deviceList;

	private ListView listView;

	private TextView sureText = null;
	private TextView cancelText = null;

	private RelativeLayout sureLay;
	private RelativeLayout cancelLay;

	private LinearLayout buttonView;

	private ProgressBar progressBar;

	private SelectCardReaderDialogAdapter devicesArrayAdapter;

	public SelectCardreaderDialog(Context context,
			List<CardReaderInfo> deviceList) {
		this.context = context;
		this.deviceList = deviceList;
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.com_landi_cardreader_list_layout);
		listView = (ListView) dialog.findViewById(R.id.com_list_view);

		sureText = (TextView) dialog.findViewById(R.id.com_dialog_sure_tv);
		cancelText = (TextView) dialog.findViewById(R.id.com_dialog_cancel_tv);
		buttonView = (LinearLayout) dialog.findViewById(R.id.com_button_view);
		progressBar = (ProgressBar) dialog
				.findViewById(R.id.com_title_processing_pb);
		sureLay = (RelativeLayout) dialog
				.findViewById(R.id.com_dialog_sure_layout);
		cancelLay = (RelativeLayout) dialog
				.findViewById(R.id.com_dialog_cancel_layout);

		devicesArrayAdapter = new SelectCardReaderDialogAdapter(context,
				deviceList);
		listView.setAdapter(devicesArrayAdapter);

		listView.setOnItemClickListener(this);

	}

	public void showProgress() {
		progressBar.setVisibility(View.VISIBLE);
	}

	public void stopProgress() {

		progressBar.setVisibility(View.GONE);

	}

	public void updataListData(CardReaderInfo cardReaderInfo) {
		devicesArrayAdapter.updateData(cardReaderInfo);
		devicesArrayAdapter.notifyDataSetChanged();
	}

	// private TiContext getAppContext() {
	// TiApplication tiApplication = (TiApplication) context
	// .getApplicationContext();
	// return tiApplication.getContextProvider().provider(
	// TiContext.CONTEXT_SCOPE_APPLICATION);
	// }

	public void setShowButton(boolean isShow) {
		if (isShow) {
			buttonView.setVisibility(View.VISIBLE);
		} else {
			buttonView.setVisibility(View.GONE);
		}
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		dialog.dismiss();
		if (onDialogItemClickListener != null) {
			onDialogItemClickListener.onItemClick(parent, view, position, id);
		}
	}

	public interface OnDialogItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id);
	}

	public void setOnDialogItemClickListener(
			OnDialogItemClickListener onDialogItemClickListener) {
		this.onDialogItemClickListener = onDialogItemClickListener;
	}

	public void setSureButtonOnclickListener(OnClickListener onclickListener) {
		sureLay.setOnClickListener(onclickListener);
	}

	public void setCancelButtonOnclickListener(OnClickListener onclickListener) {
		cancelLay.setOnClickListener(onclickListener);
	}

	public void setSureButtonName(String buttonName) {
		sureText.setText(buttonName);
	}

	public void setCancelButtonName(String buttonName) {
		cancelText.setText(buttonName);

	}

	public void setCancelable(boolean flag) {
		dialog.setCancelable(flag);
	}

}
