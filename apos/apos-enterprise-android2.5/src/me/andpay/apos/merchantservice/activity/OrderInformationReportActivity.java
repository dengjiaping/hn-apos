package me.andpay.apos.merchantservice.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 调单信息上报详情
 * 
 * @author Administrator
 *
 */
@ContentView(R.layout.order_information_report)
public class OrderInformationReportActivity extends AposBaseActivity implements
		AdpterEventListener, PhotoResponse, FinishRequestInterface,
		UploadAllImageCallback {
	@EventDelegate(type = DelegateType.method, toMethod = "back", delegateClass = OnClickListener.class)
	@InjectView(R.id.report_order_detail_back)
	private ImageView back;

	@InjectView(R.id.order_information_report_title)
	private TextView title;

	@InjectView(R.id.order_information_report_describle)
	private TextView describle;

	@InjectView(R.id.order_information_report_time)
	private TextView time;

	@InjectView(R.id.order_information_report_dispose)
	private TextView dispose;

	@InjectView(R.id.order_information_report_gridview)
	private GridView gridView;

	@EventDelegate(type = DelegateType.method, toMethod = "reportOrder", delegateClass = OnClickListener.class)
	@InjectView(R.id.order_information_report_btn)
	private Button reportBtn;

	private BaseAdapter<String> adapter;

	@Inject
	SelectImageController selectController;

	private BringAndBackOrder order;

	@EventDelegate(type = DelegateType.method, toMethod = "uploadFujian", delegateClass = OnClickListener.class)
	@InjectView(R.id.order_information_fujian)
	private TextView fujian;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/* 加载条 */
		uploadDialog = new CommonDialog(this, "上传附件...");
		txnDialog = new CommonDialog(this, "上报中...");
		/* 设置文本内容 */
		order = (BringAndBackOrder) TiFlowControlImpl.instanceControl()
				.getFlowContext().get(BringAndBackOrder.class.getName());
		title.setText(order.getSubject());
		time.setText(order.getCreateTime());
		describle.setText(order.getDescription());
		/* 设置状态 */
		if (order.getDispose().equals("0")) {
			dispose.setTextColor(getResources().getColor(R.color.auxiliary));
			dispose.setText("待审核");
		} else if (order.getDispose().equals("1")) {
			dispose.setTextColor(getResources().getColor(R.color.auxiliary));
			dispose.setText("已通过");
		} else {
			dispose.setTextColor(getResources().getColor(R.color.red));
			dispose.setText("已拒绝");
		}

		/* 图片适配器设置 */
		adapter = new BaseAdapter<String>();
		adapter.setContext(this);
		adapter.setAdpterEventListener(this);
		adapter.setController(selectController);
		gridView.setAdapter(adapter);

		if (order.getDispose().equals("0")) {// 待审核
			if (StringUtil.isJsonEmpty(order.getImagePaths())) {// 申请
				noAttachment(0);
			} else {
				havaAttachment();
			}
		}else if (StringUtil.isJsonEmpty(order.getImagePaths())) {
			noAttachment(1);
		}else {
			havaAttachment();
		}

	}

	@Inject
	RequestManager requestManager;

	private CommonDialog txnDialog;

	public void reportOrder(View view) {
		CommonTermOptRequest optRequest = new CommonTermOptRequest();
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id", order.getId());
		dataMap.put("imagePaths", uploadSuccessBuffer.toString());
		optRequest.setVasRequestContentObj(dataMap);
		LoginUserInfo logInfo = (LoginUserInfo) this.getAppContext()
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		optRequest.setUserName(logInfo.getUserName());
		optRequest
				.setOperateType(VasOptTypes.OSS_ERROR_HANDLE_ADJUSTABLE_UPLOAD);
		requestManager.setOptRequest(optRequest);
		requestManager.addFinishRequestResponse(this);
		txnDialog.show();
		requestManager.startService();
	}

	/**
	 * 上传附件
	 * 
	 * @param view
	 */
	@Inject
	UploadImageManager uploadImageManager;
	private CommonDialog uploadDialog;

	public void uploadFujian(View view) {
		/* 添加附件 */
		if (adapter.getList().size() > 1) {
			for (int i = 0; i < adapter.getList().size() - 1; i++) {
				UpLoadImage image = new UpLoadImage();
				image.setTitle("图片" + i);
				image.setType(AttachmentTypes.FEEDBACK_PICTURE);
				image.setSuccess(false);
				image.setFileName(adapter.getList().get(i));
				image.setId(FileUtil.getMyUUID());
				uploadImageManager.addImage(image);
				uploadImageManager.addUploadFileCallBack(this);
			}
			uploadDialog.show();
			uploadImageManager.startUpload();
		}
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
			if (data != null) {
				String path = ShowUtil.getAlbumPath(this, data);
				adapter.getList().add(0, path);
				adapter.notifyDataSetChanged();
			}
			break;

		case 1:
			if (resultCode == Activity.RESULT_OK) {
				adapter.getList().add(0, tempFile.getPath());
				adapter.notifyDataSetChanged();
			}
			break;
		}
		if (adapter.getList().size() > 1) {
			fujian.setText("上传附件");
			fujian.setTextColor(getResources().getColor(R.color.blue));
		}
	}

	public void callBack(Object response) {
		// TODO Auto-generated method stub
		if (txnDialog != null && txnDialog.isShowing()) {
			txnDialog.cancel();
		}
		if (response == null) {
			ShowUtil.showShortToast(this, "上报失败");
		} else {
			CommonTermOptResponse optResponse = (CommonTermOptResponse) response;
			if (optResponse.isSuccess()) {
				TiFlowControlImpl.instanceControl().previousSetup(this);
				ShowUtil.showShortToast(this, "上报成功");
			} else {

				ShowUtil.showShortToast(this, "上报失败");
			}
		}

	}

	private StringBuffer uploadSuccessBuffer = new StringBuffer();

	public void callback(final Set<UpLoadImage> images) {
		// TODO Auto-generated method stub
		new Handler(getMainLooper()).post(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				uploadDialog.cancel();
				StringBuffer proBuffer = new StringBuffer();

				for (UpLoadImage image : images) {
					if (!image.isSuccess()){
						proBuffer.append(image.getTitle() + "上传失败\n");
					} else if (image.getUpCount() <= 1) {
						proBuffer.append(image.getTitle() + "上传成功\n");
						uploadSuccessBuffer.append(image.getHttpName() + ",");

					}
				}
				
				uploadSuccessBuffer.deleteCharAt(uploadSuccessBuffer.length() - 1);

				ShowUtil.showShortToast(OrderInformationReportActivity.this,
						proBuffer.toString());

			}
		});

	}

	/**
	 * 无附件
	 * @param state 0添加 1无           
	 */
	private void noAttachment(int state) {
		switch (state) {
		case 0:// 添加附件
			gridView.setNumColumns(4);
			gridView.setVisibility(View.VISIBLE);
			fujian.setText("添加附件");
			reportBtn.setVisibility(View.VISIBLE);
			selectController.setState(0);
			ArrayList<String> attachmentList = new ArrayList<String>();
			attachmentList.add(SelectImageController.TAG);
			adapter.setList(attachmentList);
			adapter.notifyDataSetChanged();

			break;

		case 1:// 无附件

			gridView.setVisibility(View.GONE);
			fujian.setTextColor(getResources().getColor(R.color.red));
			fujian.setText("无附件");
			reportBtn.setVisibility(View.GONE);
			break;
		}
	}

	/**
	 * 拥有附件
	 */
	private void havaAttachment() {
		gridView.setNumColumns(1);
		gridView.setVisibility(View.VISIBLE);
		fujian.setText("附件");
		reportBtn.setVisibility(View.GONE);
		selectController.setState(1);
		ArrayList<String> attachmentList = new ArrayList<String>();
		String imageStrs[] = order.getImagePaths().split(",");
		for (int i = 0; i < imageStrs.length; i++) {
			attachmentList.add(imageStrs[i]);
		}
		adapter.setList(attachmentList);
		adapter.notifyDataSetChanged();
	}

}
