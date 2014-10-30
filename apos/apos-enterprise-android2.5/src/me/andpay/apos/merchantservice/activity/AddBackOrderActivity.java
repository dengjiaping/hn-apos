package me.andpay.apos.merchantservice.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import me.andpay.ac.consts.AttachmentTypes;
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
import me.andpay.apos.base.tools.StringUtil;
import me.andpay.apos.base.tools.TimeUtil;
import me.andpay.apos.base.upimage.UpLoadImage;
import me.andpay.apos.base.upimage.UploadAllImageCallback;
import me.andpay.apos.base.upimage.UploadImageManager;
import me.andpay.apos.base.view.CustomDialog;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.merchantservice.controller.SelectImageController;
import me.andpay.apos.merchantservice.data.BringAndBackOrder;
import me.andpay.apos.merchantservice.flow.FlowContants;
import me.andpay.timobileframework.cache.HashMap;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

/*添加退单界面*/
@ContentView(R.layout.add_back_order)
public class AddBackOrderActivity extends AposBaseActivity implements
		AdpterEventListener, PhotoResponse, FinishRequestInterface,
		UploadAllImageCallback {
	public static AddSuccessCallBack onSuccessCallBack;
	
	public static AddSuccessCallBack getOnSuccessCallBack() {
		return onSuccessCallBack;
	}

	public static void setOnSuccessCallBack(AddSuccessCallBack onSuccessCallBack) {
		AddBackOrderActivity.onSuccessCallBack = onSuccessCallBack;
	}

	public interface AddSuccessCallBack{
		public void onAddSuccessCallBack(BringAndBackOrder order);
	}
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

	/* 添加附件 */
	@EventDelegate(type = DelegateType.method, toMethod = "addForecheadFile", delegateClass = OnClickListener.class)
	@InjectView(R.id.add_back_order_forehead_file)
	private TextView addForecheadFile;

	private BaseAdapter<String> addImageAdapter;

	private SelectImageController selectController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		uploadDialog = new CommonDialog(this, "图片上传中...");
		txnDialog = new CommonDialog(this, "添加中...");
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
			if (data != null) {
				String path = ShowUtil.getAlbumPath(this, data);
				addImageAdapter.getList().add(0, path);
			}
			break;

		case FlowContants.TAKING_PICTURES:// 拍照
			if (resultCode == Activity.RESULT_OK) {
				addImageAdapter.getList().add(0, tempFile.getPath());
			}
			break;
		}
		if (addImageAdapter.getList().size() > 1) {
			addForecheadFile.setText("上传附件");
			addForecheadFile
					.setTextColor(getResources().getColor(R.color.blue));
		}
		addImageAdapter.notifyDataSetChanged();
	}

	private CommonDialog txnDialog;
	@Inject
	RequestManager requetManager;

	/**
	 * 添加附件
	 * 
	 * @param view
	 */
	@Inject
	UploadImageManager imageManager;
	private CommonDialog uploadDialog;

	public void addForecheadFile(View view) {
		/* 添加附件 */
		if (addImageAdapter.getList().size() > 1) {
			for (int i = 0; i < addImageAdapter.getList().size() - 1; i++) {
				UpLoadImage image = new UpLoadImage();
				image.setTitle("图片" + i);
				image.setType(AttachmentTypes.FEEDBACK_PICTURE);
				image.setSuccess(false);
				image.setFileName(addImageAdapter.getList().get(i));
				image.setId(FileUtil.getMyUUID());
				imageManager.addImage(image);
				imageManager.addUploadFileCallBack(this);
			}
			uploadDialog.show();
			imageManager.startUpload();
		}
	}

	/* 添加退单 */

	public void addbackOrder(View view) {
		String titStr = titleEdit.getText().toString();
		String desStr = describeEdit.getText().toString();
		if(StringUtil.isEmpty(titStr)){
			ShowUtil.showShortToast(this,"请填写标题");
			return;
		}
		if(StringUtil.isEmpty(desStr)){
			ShowUtil.showShortToast(this,"请填写描述");
			return;
		}
        if(StringUtil.isEmpty(uploadSuccessBuffer.toString())){
        	ShowUtil.showShortToast(this,"请上传附件");
			return;
        }
		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("subject", titleEdit.getText().toString());
		dataMap.put("description", describeEdit.getText().toString());
		dataMap.put("imagePaths", uploadSuccessBuffer.toString());
		optRequest.setVasRequestContentObj(dataMap);
		LoginUserInfo logInfo = (LoginUserInfo) this.getAppContext()
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		optRequest.setUserName(logInfo.getUserName());
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
				if(onSuccessCallBack!=null){
					BringAndBackOrder order = new BringAndBackOrder();
					order.setDispose("0");
					order.setSubject(titleEdit.getText().toString());
					order.setDescription(describeEdit.getText().toString());
					order.setImagePaths(uploadSuccessBuffer.toString());
					order.setCreateTime(TimeUtil.getInstance().formatDate(new Date(), TimeUtil.DATE_PATTERN_11));
					onSuccessCallBack.onAddSuccessCallBack(order);
				}
				TiFlowControlImpl.instanceControl().previousSetup(this);

			} else {
				ShowUtil.showShortToast(this, "添加失败");
			}
		}
	}

	/**
	 * 全部上传完回调
	 */
	StringBuffer uploadSuccessBuffer = new StringBuffer();

	public void callback(final Set<UpLoadImage> images) {
		// TODO Auto-generated method stub
		new Handler(getMainLooper()).post(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				uploadDialog.cancel();

				StringBuffer proBuffer = new StringBuffer();

				for (UpLoadImage image : images) {
					if (!image.isSuccess()) {
						proBuffer.append(image.getTitle() + "上传失败\n");
					} else if (image.getUpCount() <= 1) {

						proBuffer.append(image.getTitle() + "上传成功\n");
						uploadSuccessBuffer.append(image.getHttpName() + ",");

					}
				}
				if(!StringUtil.isEmpty(uploadSuccessBuffer.toString())){
				   uploadSuccessBuffer.deleteCharAt(uploadSuccessBuffer.length() - 1);
				}

				ShowUtil.showShortToast(AddBackOrderActivity.this, proBuffer.toString());
			}
		});

	}
}
