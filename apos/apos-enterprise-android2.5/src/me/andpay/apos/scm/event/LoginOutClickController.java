package me.andpay.apos.scm.event;

import me.andpay.apos.R;
import me.andpay.apos.common.util.BackUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import roboguice.inject.InjectResource;
import android.app.Activity;
import android.view.View;

public class LoginOutClickController extends AbstractEventController {

	@InjectResource(R.string.com_sure_logout__apos_str)
	String loginOutPrompt;

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		showBackDialog(refActivty);

	}

	public void showBackDialog(final Activity activity) {
		// final OperationDialog dialog = new OperationDialog(activity, null,
		// loginOutPrompt, true);
		//
		// final Activity inActivity = activity;
		// dialog.setSureButtonOnclickListener(new OnClickListener() {
		// public void onClick(View v) {
		// dialog.dismiss();
		// TiContextProvider provider = ((TiApplication) activity
		// .getApplication()).getContextProvider();
		// provider.provider(TiContext.CONTEXT_SCOPE_APPLICATION)
		// .inValidate();
		// Intent intent = new Intent();
		// intent.putExtra(CommonProvider.COM_STR_RECONN_FLAG, true);
		// intent.setAction(LamProvider.LAM_STARTAPP_ACTIVITY);
		// inActivity.startActivity(intent);
		// inActivity.finish();
		// }
		// });
		//
		// dialog.show();

		BackUtil.showBackDialog(activity);
	}
}
