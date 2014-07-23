package me.andpay.apos.scm.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.update.DefaultUpdateCallback;
import me.andpay.apos.common.update.UpdateManager;
import me.andpay.timobileframework.bugsense.ThrowableInfo;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import roboguice.inject.InjectResource;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class UpdateClickController extends AbstractEventController {

	@InjectResource(R.string.scm_help_update_operation_str)
	String promptMsg;

	@InjectResource(R.string.scm_help_update_new_str)
	String noUpdateMsg;

	CommonDialog dialog;

	public boolean onTouch(Activity refActivty, FormBean formBean, View v,
			MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP
				|| event.getAction() == MotionEvent.ACTION_OUTSIDE
				|| event.getAction() == MotionEvent.ACTION_CANCEL) {

			v.setPressed(false);
			RelativeLayout rel = (RelativeLayout) v;
			for (int i = 0; i < rel.getChildCount(); i++) {
				rel.getChildAt(i).setPressed(false);
			}

			dialog = new CommonDialog(refActivty, promptMsg);
			UpdateManager manager = new UpdateManager(refActivty);
			manager.setCallBack(new ScmUpdateCallback(refActivty, manager));
			manager.checkUpdate();
			dialog.show();

		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			RelativeLayout rel = (RelativeLayout) v;
			v.setPressed(true);
			for (int i = 0; i < rel.getChildCount(); i++) {
				rel.getChildAt(i).setPressed(true);
			}
		}
		return true;
	}

	class ScmUpdateCallback extends DefaultUpdateCallback {

		private Activity activity;

		public ScmUpdateCallback(Activity activity, UpdateManager manager) {
			super(activity, manager);
			this.activity = activity;
		}

		@Override
		public void checkUpdateCompleted(Boolean hasUpdate,
				CharSequence updateInfo) {
			dialog.cancel();
			if (!hasUpdate) {
				PromptDialog pDialog = null;
				pDialog = new PromptDialog(activity, null, noUpdateMsg);
				pDialog.show();
				return;
			}
			super.checkUpdateCompleted(hasUpdate, updateInfo);
		}

		@Override
		public void processThrowable(ThrowableInfo info) {
			dialog.cancel();
			super.processThrowable(info);
		}

	}
}
