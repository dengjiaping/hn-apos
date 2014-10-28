package me.andpay.apos.tam.event;

import java.io.File;
import java.util.Date;
import java.util.List;

import me.andpay.ac.consts.AttachmentTypes;
import me.andpay.apos.R;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.service.ExceptionPayTxnInfoService;
import me.andpay.apos.common.service.UpLoadFileServce;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.WaitUploadImageDao;
import me.andpay.apos.dao.model.QueryWaitUploadImageCond;
import me.andpay.apos.dao.model.WaitUploadImage;
import me.andpay.apos.tam.activity.SignActivity;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.SignContext;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.util.FileUtil;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import com.google.inject.Inject;

public class SignNextEventControl extends AbstractEventController {

	@Inject
	private TxnControl txnControl;

	@Inject
	private UpLoadFileServce upLoadFileServce;

	@Inject
	private WaitUploadImageDao waitUploadImageDao;
	@Inject
	private PayTxnInfoDao payTxnInfoDao;

	@Inject
	private ExceptionPayTxnInfoService exceptionPayTxnInfoService;

	public void onClick(Activity activity, FormBean formBean, View view) {

		final SignActivity signActivity = (SignActivity) activity;
		SignContext signContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(SignContext.class);

		if (signContext.getGesturesCount() < 2
				|| signContext.getGesturesLength() < 16) {

			Toast.makeText(
					signActivity.getApplicationContext(),
					ResourceUtil.getString(activity.getApplicationContext(),
							R.string.tam_sign_tooeasy_str), Toast.LENGTH_SHORT)
					.show();
			return;
		}

		submit(signActivity);
	}

	public void submit(SignActivity activity) {
		SignContext signContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(SignContext.class);

		// Bitmap bitMap = Bitmap.createBitmap(activity.signature.getWidth(),
		// activity.signature.getHeight(), Config.ARGB_8888);
		// activity.signature.setBackgroundColor(activity.getResources().getColor(
		// android.R.color.white));
		// Canvas cv = new Canvas(bitMap);
		// activity.signature.draw(cv);
		// activity.signature.setBackgroundColor(activity.getResources().getColor(
		// android.R.color.transparent));

		/* 获得位图，和存储的临时文件 */
		Bitmap bitMap = activity.signature.getSignatureBitmap();
		String fileName = FileUtil.getMyUUID() + ".jpg";

		String filePath = FileUtil.bitMapSaveFile(bitMap,
				activity.getApplicationContext(), fileName, 10);
		/**
		 * 回收位图
		 */
		if (!bitMap.isRecycled()) {
			bitMap.recycle();
			System.gc();
		}

		// 临时文件用户后面显示
		String tempFilePath = FileUtil.getFilePath(FileUtil.getMyUUID(),
				activity.getApplicationContext());
		FileUtil.doCopyFile(new File(filePath), new File(tempFilePath));
		signContext.setSignFileURL(tempFilePath);

		// 更新上传数据表
		QueryWaitUploadImageCond cond = new QueryWaitUploadImageCond();
		cond.setItemType(AttachmentTypes.SIGNATURE_PICTURE);
		cond.setTermTraceNo(signContext.getTermTraceNo());
		cond.setTermTxnTime(signContext.getTermTxnTime());
		List<WaitUploadImage> imges = waitUploadImageDao.query(cond, 0, -1);
		if (!imges.isEmpty()) {
			WaitUploadImage waitImge = imges.get(0);
			waitImge.setFilePath(filePath);
			waitImge.setReadyUpload(true);
			waitUploadImageDao.update(waitImge);
		} else {
			if (!StringUtil.isEmpty(signContext.getIdUnderType())) {
				WaitUploadImage waitImg = new WaitUploadImage();
				waitImg.setCreateDate(StringUtil.format("yyyyMMddHHmmss",
						new Date()));
				waitImg.setItemType(AttachmentTypes.SIGNATURE_PICTURE);
				waitImg.setTermTraceNo(signContext.getTermTraceNo());
				waitImg.setTermTxnTime(signContext.getTermTxnTime());
				waitImg.setItemId(signContext.getIdUnderType());
				waitImg.setTimes(0);
				waitImg.setReadyUpload(true);
				waitImg.setFilePath(filePath);
				waitUploadImageDao.insert(waitImg);
			}
		}
		// 上传当前文件
		upLoadFileServce.uploadFile();

		// 删除快照
		exceptionPayTxnInfoService.removeExceptionTxn(
				signContext.getTermTraceNo(), signContext.getTermTxnTime());

		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.SUCCESS);
	}
}
