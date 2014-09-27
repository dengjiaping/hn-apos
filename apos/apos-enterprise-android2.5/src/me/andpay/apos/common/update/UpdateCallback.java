package me.andpay.apos.common.update;

import me.andpay.timobileframework.bugsense.ThrowableInfo;

public interface UpdateCallback {
	public void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo);

	public void downloadProgressChanged(int progress);

	public void downloadCanceled();

	public void downloadCompleted(Boolean sucess, CharSequence errorMsg);

	public void processThrowable(ThrowableInfo info);
}