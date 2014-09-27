package me.andpay.apos.tam.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tam.activity.PurchaseFirstActivity;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.inject.Inject;

public class CameraGoodsButtonEventControl extends AbstractEventController {

	@Inject
	private TxnControl txnControl;

	public void onClick(Activity activity, FormBean formBean, View view) {

		final PurchaseFirstActivity tiActivity = (PurchaseFirstActivity) activity;

		final OperationDialog dialog = new OperationDialog(
				activity,
				ResourceUtil.getString(tiActivity, R.string.com_show_str),
				ResourceUtil.getString(tiActivity, R.string.tam_delete_pic_str),
				true);
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				tiActivity.goodsImgbt.setImageBitmap(null);
				tiActivity.goodsLay.setVisibility(View.GONE);
				tiActivity.cameraImgView.setVisibility(View.VISIBLE);
				tiActivity.cleanPic();
			}
		});

		dialog.show();

	}
}
