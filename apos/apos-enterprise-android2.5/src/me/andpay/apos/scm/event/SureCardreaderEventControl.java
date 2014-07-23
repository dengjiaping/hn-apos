package me.andpay.apos.scm.event;

import me.andpay.apos.R;
import me.andpay.apos.cardreader.CardReaderResourceSelector;
import me.andpay.apos.cardreader.callback.DefaultCardReaderCallBack;
import me.andpay.apos.cardreader.util.AposCardReaderUtil;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.lam.callback.impl.LoginCallBackHelper;
import me.andpay.apos.scm.ScmProvider;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.support.TiActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.inject.Inject;

public class SureCardreaderEventControl extends AbstractEventController {

	@Inject
	private DefaultCardReaderCallBack callback;

	@InjectResource(R.string.scm_cardreader_set_succ_str)
	private String succStr;

	@InjectView(R.id.scm_search_btn)
	public Button searchBtn;

	public void onClick(Activity activity, FormBean formBean, View view) {

		final TiActivity crActivity = (TiActivity) activity;

		if (CardReaderManager.getCardReaderType() == 0) {

			Toast.makeText(activity.getApplicationContext(),
					R.string.lam_cardreader_sekect_ok_str, Toast.LENGTH_LONG)
					.show();

			crActivity.getAppConfig().setAttribute(
					ConfigAttrNames.CARD_READER_TYPE,
					crActivity.getAppContext().getAttribute(
							ConfigAttrNames.CARD_READER_TYPE));
			CardReaderManager.resetCardreader();
			CardReaderManager.initCardReader(
					crActivity.getApplicationContext(), AposCardReaderUtil
							.genAposSwiperContext(crActivity.getAppConfig()));
			// 主页
			LoginCallBackHelper.goHome(crActivity);
			return;
		}

		crActivity.getAppConfig().setAttribute(
				ConfigAttrNames.CARD_READER_TYPE,
				crActivity.getAppContext().getAttribute(
						ConfigAttrNames.CARD_READER_TYPE));

		CardReaderManager.resetCardreader();
		// 初始化刷卡器
		CardReaderManager.initCardReader(activity.getApplicationContext(),
				AposCardReaderUtil.genAposSwiperContext(crActivity
						.getAppConfig()));

		if (CardReaderManager.getCardReaderType() == CardReaderTypes.ITRON) {
			final OperationDialog dialog = new OperationDialog(activity, null,
					succStr, true);
			searchBtn.setVisibility(View.GONE);

			dialog.setCancelButtonOnclickListener(new OnClickListener() {

				public void onClick(View v) {
					dialog.dismiss();
					KeyEvent key = new KeyEvent(KeyEvent.ACTION_DOWN,
							KeyEvent.KEYCODE_BACK);
					crActivity.onKeyDown(key.getKeyCode(), key);
					// crActivity.finish();
				}
			});

			dialog.setSureButtonOnclickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction(ScmProvider.SCM_ACTIVITY_CARDREADER_HELPER);
					crActivity.startActivity(intent);
				}
			});

			dialog.setSureButtonName("读卡器帮助");
			dialog.setCancelButtonName("确定");

			dialog.show();
		} else {

			// 主页
			String msg = succStr;
			String name = null;
			if (CardReaderManager.getCardReaderType() == CardReaderTypes.LANDI
					|| CardReaderManager.getCardReaderType() == CardReaderTypes.NEW_LAND_BL) {

				name = CardReaderResourceSelector.getDefaultCardreaderName(
						crActivity.getAppConfig(),
						CardReaderManager.getCardReaderType());

				if (StringUtil.isBlank(name)) {
					msg += ",您当前没有选择设备，如需选择设备，请点击右上角搜索设备按钮";
				} else {
					msg += ",您当前选择的设备为:" + name + ",如需更换设备，请点击右上角搜索设备按钮。";
				}
				searchBtn.setVisibility(View.VISIBLE);
			} else {
				searchBtn.setVisibility(View.GONE);

			}

			final PromptDialog dialog = new PromptDialog(activity, "提示", msg);

			dialog.setSureButtonOnclickListener(new OnClickListener() {
				public void onClick(View v) {
					dialog.dismiss();
					KeyEvent key = new KeyEvent(KeyEvent.ACTION_DOWN,
							KeyEvent.KEYCODE_BACK);
					crActivity.onKeyDown(key.getKeyCode(), key);
					// crActivity.finish();

				}
			});
			dialog.show();
		}

	}
}
