package me.andpay.apos.common.activity;

import me.andpay.apos.R;
import me.andpay.apos.common.CommonProvider;
import me.andpay.timobileframework.mvc.support.TiApplication;
import me.andpay.timobileframework.util.FileUtil;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * android 数据清除
 * 
 * @author cpz
 *
 */
public class AposCleanAppDataActivity extends Activity {

	public Button cleanBtn;

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.com_clean_appdata_layout);
		cleanBtn = (Button) findViewById(R.id.com_clearData_btn);

		cleanBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				SharedPreferences sharedPreferences = getApplication()
						.getSharedPreferences(
								TiApplication.class.getSimpleName(), 0);
				Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				FileUtil.deleteDirectory(getFilesDir());
				FileUtil.deleteDirectory(getCacheDir());
				FileUtil.deleteDirectory(getExternalCacheDir());
				Intent intent = new Intent(
						CommonProvider.BROADCAST_CLOSEAPP_ACTION);
				sendBroadcast(intent);
				finish();

			}
		});
	};
}
