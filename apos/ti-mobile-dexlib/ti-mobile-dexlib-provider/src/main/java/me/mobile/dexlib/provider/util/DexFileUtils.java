package me.mobile.dexlib.provider.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import dalvik.system.DexClassLoader;
 
import android.content.Context;

public class DexFileUtils {

	private static final int BUF_SIZE = 8 * 1024;
	


	public static boolean prepareDex(String dexFileName, Context context) {

		final File dexInternalStoragePath =getDexFile(context, dexFileName,
				"dex");
		if (dexInternalStoragePath.exists()) {
			return true;
		}

		BufferedInputStream bis = null;
		OutputStream dexWriter = null;

		try {
			bis = new BufferedInputStream(context.getAssets().open(dexFileName));
			dexWriter = new BufferedOutputStream(new FileOutputStream(
					dexInternalStoragePath));
			byte[] buf = new byte[BUF_SIZE];
			int len;
			while ((len = bis.read(buf, 0, BUF_SIZE)) > 0) {
				dexWriter.write(buf, 0, len);
			}
			dexWriter.close();
			bis.close();
			return true;
		} catch (IOException e) {
			if (dexWriter != null) {
				try {
					dexWriter.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			return true;
		}
	}

	public static String getDexFilePath(Context context, String dexFileName) {

		return getDexPath(context, dexFileName, "dex");
	}

	public static String getDexoutFilePath(Context context, String dexFileName) {

		return getDexPath(context, dexFileName, "outdex");
	}

	public static String getDexPath(Context context, String dexFileName,
			String dexName) {
		final File dexInternalStoragePath = getDexFile(context, dexFileName,
				dexName);

		return dexInternalStoragePath.getAbsolutePath();
	}

	public static File getDexFile(Context context, String dexFileName,
			String dexName) {
		return new File(context.getDir(dexName, Context.MODE_PRIVATE),
				dexFileName);
	}
}
