package me.andpay.apos.merchantservice.activity;

import java.io.File;
import java.util.ArrayList;

import com.google.inject.Inject;

import me.andpay.ac.consts.VasOptTypes;
import me.andpay.ac.term.api.vas.operation.CommonTermOptRequest;
import me.andpay.ac.term.api.vas.operation.CommonTermOptResponse;
import me.andpay.apos.R;
import me.andpay.apos.base.adapter.AdpterEventListener;
import me.andpay.apos.base.adapter.BaseAdapter;
import me.andpay.apos.base.inteface.PhotoResponse;
import me.andpay.apos.base.requestmanage.FinishRequestInterface;
import me.andpay.apos.base.requestmanage.RequestManager;
import me.andpay.apos.base.tools.FileUtil;
import me.andpay.apos.base.tools.ShowUtil;
import me.andpay.apos.base.view.CustomDialog;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.merchantservice.controller.SelectImageController;
import me.andpay.apos.merchantservice.flow.FlowContants;
import me.andpay.timobileframework.cache.HashMap;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

/*添加退单界面*/
@ContentView(R.layout.add_back_order)
public class AddBackOrderActivity extends AposBaseActivity implements
		AdpterEventListener, PhotoResponse, FinishRequestInterface {
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

	/* 添加按钮 */
	@EventDelegate(type = DelegateType.method, toMethod = "addbackOrder", delegateClass = OnClickListener.class)
	@InjectView(R.id.add_back_order_btn)
	private Button addBtn;

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

	private CommonDialog txnDialog = new CommonDialog(this, "添加中...");
	@Inject
	RequestManager requetManager;

	/* 添加退单 */

	public void addbackOrder(View view) {
		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("subject", titleEdit.getText().toString());
		dataMap.put("description", describeEdit.getText().toString());
		dataMap.put("imagePaths", "image1,image2,image3");
		optRequest.setVasRequestContentObj(dataMap);
		optRequest.setUserName("13838380439");
		optRequest.setOperateType(VasOptTypes.OSS_ERROR_HANDLR_RETURN_ADD);
		requetManager.setOptRequest(optRequest);
		requetManager.addFinishRequestResponse(this);
		txnDialog.show();
		requetManager.startService();

	}

	public void callBack(Object response) {
		// TODO Auto-generated method stub
		if (txnDialog != null && txnDialog.isShowing()) {
			txnDialog.cancel();
		}
		if (response == null) {
			ShowUtil.showShortToast(this, "添加失败");
		} else {
			CommonTermOptResponse optResponse = (CommonTermOptResponse) response;
			if (optResponse.isSuccess()) {
				ShowUtil.showShortToast(this, "添加成功");
			} else {
				ShowUtil.showShortToast(this, "添加失败");
			}
		}
	}
}
