package me.andpay.apos.base.tools;

import java.io.File;

import android.R.bool;
import android.os.Environment;

/**
 * 文件操作
 * 
 * @author Administrator
 *
 */
public class FileUtil {
	/**
	 * 获得sd卡路径
	 * 
	 * @return
	 */
	public static String getSDPath() {

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		} else {

			return "";
		}

	}

	/**
	 * 创建路径
	 * 
	 * @param path
	 *            路径地址
	 * @return 是否创建成功
	 */
	public static Boolean creatPath(String path) {

		File file = new File(path);
		if (file.exists()) {

			return false;
		}
		if (path.endsWith(File.separator)) {
			file.mkdirs();
			return true;
		}
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录

			if (!file.getParentFile().mkdirs()) {

				return false;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {

				return true;
			} else {

				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

	}

}
