package me.andpay.apos.test;

import java.util.List;

import android.util.Log;
import me.andpay.ac.consts.AttachmentTypes;
import me.andpay.apos.dao.WaitUploadImageDao;
import me.andpay.apos.dao.model.QueryWaitUploadImageCond;
import me.andpay.apos.dao.model.WaitUploadImage;
import me.andpay.apos.tam.callback.TxnCallback;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.RoboGuiceUtil;

@CallBackHandler
public class TestTxnCallback implements TxnCallback {

	public static final String TAG = TestTxnCallback.class.getName();

	private TiActivity tiActivity;

	public TestTxnCallback(TiActivity tiActivity) {
		this.tiActivity = tiActivity;
	}

	private WaitUploadImageDao waitUploadImageDao;

	public void txnSuccess(TxnActionResponse actionResponse) {
		Log.i(TAG, "txn callback success");
		if (waitUploadImageDao == null) {
			waitUploadImageDao = RoboGuiceUtil.getInjectObject(
					WaitUploadImageDao.class, tiActivity);
		}

		// 更新上传数据表
		QueryWaitUploadImageCond cond = new QueryWaitUploadImageCond();
		cond.setItemType(AttachmentTypes.SIGNATURE_PICTURE);
		cond.setTermTraceNo(actionResponse.getTermTraceNo());
		cond.setTermTxnTime(actionResponse.getTermTxnTime());
		List<WaitUploadImage> imges = waitUploadImageDao.query(cond, 0, -1);
		if (!imges.isEmpty()) {
			WaitUploadImage waitImge = imges.get(0);
			waitImge.setFilePath(TestTxnProcessor.createTempFile(
					"test_sign.jpg", tiActivity));
			waitImge.setReadyUpload(true);
			waitUploadImageDao.update(waitImge);
		}
	}

	public void showFaild(TxnActionResponse actionResponse) {
		Log.i(TAG, "txn faild");

	}

	public void onTimeout(TxnActionResponse actionResponse) {
		Log.i(TAG, "txn timeout");

	}

	public void showInputPassword(TxnActionResponse actionResponse) {
		Log.i(TAG, "txn input password");

	}

	public void networkError(TxnActionResponse actionResponse) {
		Log.i(TAG, "txn networkError");

	}

	public void initCallBack(TxnControl txnControl) {

	}

	public TxnContext getTxnContext() {
		// TODO Auto-generated method stub
		return null;
	}

}
