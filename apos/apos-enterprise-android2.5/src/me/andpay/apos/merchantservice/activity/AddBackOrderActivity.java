package me.andpay.apos.merchantservice.activity;

import java.io.File;
import java.util.ArrayList;

import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.base.inteface.PhotoResponse;
import me.andpay.apos.base.tools.FileUtil;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.base.view.CustomDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.SelectImageController;
import me.andpay.apos.merchantservice.flow.FlowContants;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

/*添加退单界面*/
@ContentView(R.layout.add_back_order)
public class AddBackOrderActivity extends AposBaseActivity implements
		AdpterEventListener, PhotoResponse {
	/* 返回 */
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.add_back_order_back)
	private ImageView back;

	/* 标题 */
	@InjectView(R.id.add_back_order_title)
	private EditText titleEdit;

	/* 描述 */
	@InjectView(R.id.add_back_order_describe)
	private EditText describeEdit;

	/* 添加图片 */
	@InjectView(R.id.add_back_order_photo)
	private GridView gridView;

	private BaseAdapter<String> addImageAdapter;

	private SelectImageController selectController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addImageAdapter = new BaseAdapter<String>();
		addImageAdapter.setContext(this);
		ArrayList<String> list = new ArrayList<String>();
		list.add(SelectImageController.TAG);
		addImageAdapter.setList(list);
		addImageAdapter.setAdpterEventListener(this);
		selectController = new SelectImageController();
		addImageAdapter.setController(selectController);

		gridView.setAdapter(addImageAdapter);
	}

	public void back(View view) {
		TiFlowControlImpl.instanceControl().previousSetup(this);
	}

	public boolean onEventListener(Object... objects) {
		// TODO Auto-generated method stub
		switch ((Integer) objects[0]) {
		case 0:
			CustomDialog dg = ShowUtil.getPhotoView(this, "选择照片", this);
			dg.show();
			break;

		}
		return false;
	}
	public void selectAlbum() {
		// TODO Auto-generated method stub
		ShowUtil.selectAlbum(this, FlowContants.SELECT_ALBUM);
	}

	private File tempFile;
	private int count = -1;

	public void selectTakingPictures() {
		// TODO Auto-generated method stub
		count++;
		String photoPath = FileUtil.getSDPath() + "/temp" + count + ".jpg";
		tempFile = new File(photoPath);
		ShowUtil.selectTakingPictures(this, tempFile,
				FlowContants.TAKING_PICTURES);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case FlowContants.SELECT_ALBUM:// 图片
			String path = ShowUtil.getAlbumPath(this, data);
			addImageAdapter.getList().add(0, path);
			break;

		case FlowContants.TAKING_PICTURES:// 拍照
			addImageAdapter.getList().add(0, tempFile.getPath());
			break;
		}
		addImageAdapter.notifyDataSetChanged();
	}

}
