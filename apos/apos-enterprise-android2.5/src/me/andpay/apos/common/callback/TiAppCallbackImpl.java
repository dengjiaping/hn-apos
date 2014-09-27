package me.andpay.apos.common.callback;

import me.andpay.apos.R;
import me.andpay.apos.common.util.BackUtil;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.timobileframework.mvc.support.TiAppCallback;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import android.app.Application;
import android.os.Handler;
import android.widget.Toast;

import com.google.inject.Inject;

public class TiAppCallbackImpl implements TiAppCallback {

	@Inject
	private Application application;

	public void callback() {

		Handler handler = new Handler(application.getMainLooper());
		handler.postAtFrontOfQueue(new Runnable() {
			public void run() {
				Toast.makeText(
						application,
						ResourceUtil.getString(application,
								R.string.com_system_retry_str),
						Toast.LENGTH_LONG).show();
				BackUtil.loginOut(TiAndroidRuntimeInfo.getCurrentActivity());

			}
		});

	}

}
