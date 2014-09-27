package me.andpay.apos.merchantservice.activity;

import java.io.File;
import java.util.ArrayList;

import com.google.inject.Inject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.base.inteface.PhotoResponse;
import me.andpay.apos.base.tools.FileUtil;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.base.view.CustomDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.SelectImageController;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;

/**
 * 调单信息上报详情
 * 
 * @author Administrator
 *
 */
@ContentView(R.layout.order_information_report)
public class OrderInformationReportActivity extends AposBaseActivity implements
		AdpterEventListener,PhotoResponse {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.report_order_detail_back)
	private ImageView back;

	@InjectView(R.id.order_information_report_title)
	private TextView title;

	@InjectView(R.id.order_information_report_describle)
	private TextView describle;

	@InjectView(R.id.order_information_report_gridview)
	private GridView gridView;

	private BaseAdapter<String> adapter;

	@Inject
	SelectImageController selectController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		adapter = new BaseAdapter<String>();
		adapter.setContext(this);
		adapter.setAdpterEventListener(this);
		ArrayList<String> list = new ArrayList<String>();
		list.add(SelectImageController.TAG);
		adapter.setList(list);
		adapter.setController(selectController);

		gridView.setAdapter(adapter);

	}

	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	public boolean onEventListener(Object... objects) {
		// TODO Auto-generated method stub
		CustomDialog dialog = ShowUtil.getPhotoView(this, "选择照片", this);
		dialog.show();
		return false;
	}
	public void selectAlbum() {
		// TODO Auto-generated method stub
		ShowUtil.selectAlbum(this, 0);
	}

	private File tempFile;
	private int count;

	public void selectTakingPictures() {
		// TODO Auto-generated method stub
		tempFile = new File(FileUtil.getSDPath() + "/image" + count + ".jpg");
		count++;
		ShowUtil.selectTakingPictures(this, tempFile, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 0:
			String path = ShowUtil.getAlbumPath(this, data);
			adapter.getList().add(0, path);
			adapter.notifyDataSetChanged();
			break;

		case 1:
			adapter.getList().add(0, tempFile.getPath());
			adapter.notifyDataSetChanged();
			break;
		}
	}

}
