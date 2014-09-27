package me.andpay.apos.common.util;

import android.os.Handler;

public class MainhandlerFactory {

	private Handler mHandler;

	public void createMainHandler() {

		if (mHandler == null) {
			mHandler = new Handler();
		}
	}

	public MainhandlerFactory() {

		// this.createMainHandler();
	}

	public Handler getMainHandler() {
		return mHandler;
	}
}
