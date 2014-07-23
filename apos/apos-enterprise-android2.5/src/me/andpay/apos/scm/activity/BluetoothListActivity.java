package me.andpay.apos.scm.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.callback.CardreaderSetSearchCallback;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.control.CardReaderInfo;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.common.log.OperationDataKeys;
import me.andpay.apos.scm.activity.adapter.BluetoothCardReaderListAdapter;
import me.andpay.apos.scm.event.BluetoothListItemClickController;
import me.andpay.apos.scm.event.SearchEventController;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

@ContentView(R.layout.scm_bluetooth_list_layout)
public class BluetoothListActivity extends AposBaseActivity {

	private BluetoothCardReaderListAdapter bluetoothCardReaderListAdapter;

	@InjectView(R.id.scm_list_view)
	@EventDelegate(delegateClass = OnItemClickListener.class, toEventController = BluetoothListItemClickController.class)
	private ListView listView;

	@Inject
	private CardreaderSetSearchCallback cardreaderSetSearchCallback;

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	public ImageView backButton;

	@InjectView(R.id.scm_title_processing_pb)
	public ProgressBar progressBar;

	@InjectView(R.id.scm_search_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = SearchEventController.class)
	public Button searchBtn;

	@InjectView(R.id.scm_no_device)
	public RelativeLayout noDeviceLayout;

	private CardReaderSetContext cardReaderSetContext;

	@InjectView(R.id.scm_progress_lay)
	public RelativeLayout progressLay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		cardReaderSetContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(CardReaderSetContext.class);

		bluetoothCardReaderListAdapter = new BluetoothCardReaderListAdapter(
				this, new ArrayList<CardReaderInfo>());
		listView.setAdapter(bluetoothCardReaderListAdapter);

		startSearch();

	}

	@Override
	protected void onStop() {
		super.onStop();
		stopSearch();
	}

	public void noDevice() {
		noDeviceLayout.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
	}

	public void stopSearch() {
		progressBar.setVisibility(View.GONE);
		searchBtn.setVisibility(View.VISIBLE);
		CardReaderManager.getSearchBluetoothService().stopSearch();
	}

	public void startSearch() {

		bluetoothCardReaderListAdapter.clear();
		cardreaderSetSearchCallback.setBluetoothListActivity(this);
		CardReaderManager.setCurrCallback(cardreaderSetSearchCallback);
		CardReaderManager.searchDevice();
		progressBar.setVisibility(View.VISIBLE);
		searchBtn.setVisibility(View.GONE);
		noDeviceLayout.setVisibility(View.GONE);
		listView.setVisibility(View.VISIBLE);
		progressLay.setVisibility(View.VISIBLE);

		cardReaderSetContext.getOpLogData().put(
				OperationDataKeys.OPKEYS_SEARCH_DEVICE, String.valueOf(true));
	}

	public BluetoothCardReaderListAdapter getBluetoothCardReaderListAdapter() {
		return bluetoothCardReaderListAdapter;
	}

	public CardReaderSetContext getCardReaderSetContext() {
		return cardReaderSetContext;
	}

}
