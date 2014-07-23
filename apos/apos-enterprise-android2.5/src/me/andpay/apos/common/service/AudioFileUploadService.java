package me.andpay.apos.common.service;

import java.io.File;
import java.io.FileFilter;

import me.andpay.ac.term.api.ga.TermStatsDataTypes;
import me.andpay.apos.common.constant.AposContext;
import android.app.Application;
import android.os.Environment;

import com.google.inject.Inject;

public class AudioFileUploadService {

	@Inject
	private Application application;

	@Inject
	private AposContext aposContext;
	@Inject
	private UploadLogFileHandler uploadLogFileHandler;

	public boolean synUploadFile(String traceNo) {

		File file = findFile(traceNo);
		if (file != null) {
			return uploadLogFileHandler.synUpload(new File[] { file },
					TermStatsDataTypes.MSR_AUDIO_DATA,traceNo);
		}
		return false;
	}

	public void asynUploadFile(String traceNo) {
		File file = findFile(traceNo);
		if (file != null) {
			uploadLogFileHandler.asynUpload(new File[] { file },
					TermStatsDataTypes.MSR_AUDIO_DATA,traceNo);
		}
	}

	public File findFile(String traceNo) {

		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/Android/data/"
				+ application.getPackageName() + "/files/audio";

		File audioDir = new File(path);

		final String tempTraceNO = traceNo;
		FileFilter filter = new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.getName().indexOf(tempTraceNO) > 0) {
					return true;
				}
				return false;
			}
		};
		File[] files = audioDir.listFiles(filter);

		if (files.length > 0) {
			return files[0];
		}
		return null;
	}

	
}
