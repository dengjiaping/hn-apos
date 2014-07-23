package me.andpay.apos.tcm.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tcm.activity.ShowPictureActivity;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class UsePhotoEventController extends AbstractEventController {
	public void onClick(Activity activity, FormBean formBean, View view) {
		ShowPictureActivity picActivity = (ShowPictureActivity) activity;
		
		Map<String, String> intentData = new HashMap<String, String>();
		intentData.put("file_path", picActivity.filePath);
		TiFlowControlImpl.instanceControl().nextSetup(picActivity,
				FlowConstants.SUCCESS, intentData);
		picActivity.finish();
		// Intent intent = new Intent();
		// intent.setClass(picActivity, PhotoChallengeActivity.class);
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// picActivity.cutBitmap.compress(Bitmap.CompressFormat.JPEG, 95, baos);
		// byte[] bitmapByte = baos.toByteArray();
		// // try {
		// // File file = new File("/sdcard/test.jpg");
		// // if (file.exists())
		// // file.delete();
		// // FileOutputStream out = new FileOutputStream(file);
		// // out.write(bitmapByte);
		// // out.flush();
		// // out.close();
		// // } catch (IOException e) {
		// // e.printStackTrace();
		// // }
		// intent.putExtra("bitmap", bitmapByte);
		// picActivity.startActivity(intent);
	}
}
