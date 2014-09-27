package me.andpay.apos.common.service;

import javax.inject.Inject;

import android.app.Application;

import com.dolby.dap.DolbyAudioProcessing;
import com.dolby.dap.DolbyAudioProcessing.PROFILE;
import com.dolby.dap.OnDolbyAudioProcessingEventListener;

public class DolbyService {

	private DolbyAudioProcessing mDolbyAudioProcessing;

	@Inject
	private Application application;

	public boolean createDolbyAudioProcessing() {
		try {
			mDolbyAudioProcessing = DolbyAudioProcessing
					.getDolbyAudioProcessing(application, PROFILE.VOICE,
							new OnDolbyAudioProcessingEventListener() {

								public void onDolbyAudioProcessingProfileSelected(
										PROFILE arg0) {

								}

								public void onDolbyAudioProcessingEnabled(
										boolean arg0) {

								}

								public void onDolbyAudioProcessingClientDisconnected() {
								}

								public void onDolbyAudioProcessingClientConnected() {
									closeDolby();
								}
							});
			mDolbyAudioProcessing.setEnabled(false);

		} catch (IllegalStateException ex) {

		} catch (IllegalArgumentException ex) {

		} catch (RuntimeException ex) {

		}
		return true;
	}

	public void openDolby() {
		if (mDolbyAudioProcessing != null) {
			mDolbyAudioProcessing.setEnabled(true);

		}
	}

	public void closeDolby() {
		if (mDolbyAudioProcessing != null) {
			mDolbyAudioProcessing.setEnabled(false);
		}
	}
}
