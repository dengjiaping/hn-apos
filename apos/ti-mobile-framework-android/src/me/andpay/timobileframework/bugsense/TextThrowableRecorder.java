package me.andpay.timobileframework.bugsense;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import me.andpay.timobileframework.bugsense.ThrowableRecorder;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import android.os.Environment;
import android.util.Log;

public class TextThrowableRecorder implements ThrowableRecorder {

	private String TAG = getClass().getName();

	public void recordThrowable(Throwable ex) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);

		ex.printStackTrace(printWriter);
		try {
			// Random number to avoid duplicate files
			Random generator = new Random();
			int random = generator.nextInt(99999);
			// Embed version in stacktrace filename
			String filename = TiAndroidRuntimeInfo.getAppVersion() + "-"
					+ Integer.toString(random);
			Log.d(TAG,
					"Writing unhandled exception to: "
							+ TiAndroidRuntimeInfo
									.getCurrentActivity()
									.getApplicationContext()
									.getExternalFilesDir(
											Environment.DIRECTORY_NOTIFICATIONS)
							+ "/" + filename + ".stacktrace");
			// Write the stacktrace to disk
			BufferedWriter bos = new BufferedWriter(new FileWriter(
					TiAndroidRuntimeInfo
							.getCurrentActivity()
							.getApplicationContext()
							.getExternalFilesDir(
									Environment.DIRECTORY_NOTIFICATIONS)
							+ "/" + filename + ".stacktrace"));

			bos.write(TiAndroidRuntimeInfo.getSdkVersion() + "\n");
			bos.write(TiAndroidRuntimeInfo.getMobileModel() + "\n");
			bos.write(new Date() + "\n");
			bos.write(result.toString());
			bos.flush();
			// Close up everything
			bos.close();
		} catch (Exception ebos2) {
			// Nothing much we can do about this - the game is over
			Log.e(TAG, "Error saving exception stacktrace", ex);
		}

	}

	public List<String> readThrowables() {
		Log.d(TAG,
				"Looking for exceptions in: "
						+ TiAndroidRuntimeInfo.getAppFilePath());

		// Find list of .stacktrace files
		File dir = new File(TiAndroidRuntimeInfo.getAppFilePath() + "/");
		// Try to create the files folder if it doesn't exist
		if (!dir.exists())
			dir.mkdir();
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".stacktrace");
			}
		};
		String[] list = dir.list(filter);

		Log.d(TAG, "Found " + list.length + " stacktrace(s)");

		try {
			final int MAX_TRACES = 5;
			List<String> sStackTraces = new ArrayList<String>();
			for (int i = 0; i < list.length; i++) {
				// Limit to a certain number of SUCCESSFULLY read traces
				if (sStackTraces.size() >= MAX_TRACES)
					break;

				String filePath = TiAndroidRuntimeInfo.getAppFilePath() + "/"
						+ list[i];

				try {

					// Extract the version from the filename:
					// "packagename-version-...."
					String version = list[i].split("-")[0];
					Log.d(TAG, "Stacktrace in file '" + filePath
							+ "' belongs to version " + version);
					// Read contents of stacktrace
					StringBuilder contents = new StringBuilder();
					BufferedReader input = new BufferedReader(new FileReader(
							filePath));
					try {
						String line = null;
						while ((line = input.readLine()) != null) {
							contents.append(line);
							contents.append(System
									.getProperty("line.separator"));
						}
					} finally {
						input.close();
					}
					String stacktrace = contents.toString();

					sStackTraces.add(stacktrace);
				} catch (FileNotFoundException e) {
					Log.e(TAG, "Failed to load stack trace", e);
				} catch (IOException e) {
					Log.e(TAG, "Failed to load stack trace", e);
				}
			}

			return sStackTraces;
		} finally {
			// Delete ALL the stack traces, even those not read (if
			// there were too many), and do this within a finally
			// clause so that even if something very unexpected went
			// wrong above, it hopefully won't happen again the next
			// time around (because the offending files are gone).
			for (int i = 0; i < list.length; i++) {
				try {
					File file = new File(TiAndroidRuntimeInfo.getAppFilePath()
							+ "/" + list[i]);
					file.delete();
				} catch (Exception e) {
					Log.e(TAG, "Error deleting trace file: " + list[i], e);
				}
			}
		}
	}

	public void deleteAllThrowables() {
	}

}
