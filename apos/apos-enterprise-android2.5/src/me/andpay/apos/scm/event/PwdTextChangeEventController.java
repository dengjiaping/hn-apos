package me.andpay.apos.scm.event;

import me.andpay.apos.scm.activity.ScmChangePwdActivity;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

/**
 * 密码修改页面输入框焦点改变事件
 * 
 * @author tinyliu
 * 
 */
public class PwdTextChangeEventController extends AbstractEventController {

	public void onTextChanged(Activity refActivity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		ScmChangePwdActivity activity = (ScmChangePwdActivity) refActivity;
		if (!StringUtil.isEmpty(activity.getOldPwd().getText().toString())
				&& !StringUtil.isEmpty(activity.getNewPwd().getText()
						.toString())
				&& !StringUtil.isEmpty(activity.getRepeatPwd().getText()
						.toString())) {
			activity.getSureButton().setEnabled(true);
		} else {
			activity.getSureButton().setEnabled(false);
		}
	}

	public void afterTextChanged(Activity refActivity, FormBean formBean,
			Editable s) {
		return;
	}

	public void beforeTextChanged(Activity refActivity, FormBean formBean,
			CharSequence s, int start, int count, int after) {
		return;
	}
}
