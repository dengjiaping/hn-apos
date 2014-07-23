package me.andpay.apos.cmview;

import java.util.Timer;
import java.util.TimerTask;

import me.andpay.timobileframework.util.DialogUtil;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 倒计时提交器
 * 
 * @author cpz
 * 
 */
public class TimeProgressDialog {

	private TiCustomProgressDialog dialog;

	private final static int DEFUAL_START_TIME = 60;

	private int time;

	public String PLACEHOLDER = "$TIME$";

	public OnTimeoutListener onTimeoutListener;

	private String oldMsg;

	private boolean isShowing = false;

	public TimeProgressDialog(Context context, String promptMsg) {
		this(context, promptMsg, DEFUAL_START_TIME);
	}

	public TimeProgressDialog(Context context, String promptMsg, int startTime) {

		this.time = startTime;
		oldMsg = promptMsg;

		String msg = oldMsg.replace(PLACEHOLDER, Integer.valueOf(time)
				.toString());
		dialog = TiCustomProgressDialog.createDialog(context);
		// 设置ProgressDialog 提示信息
		dialog.setMessage(msg);

		Timer timer = new Timer();
		final TiCustomProgressDialog dialogMe = dialog;
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (time < 0) {

						if (!isShowing) {
							return;
						}

						dialog.cancel();
						if (onTimeoutListener != null) {
							onTimeoutListener.onTimeout();
						}
						return;
					}
					String msg1 = oldMsg.replace(PLACEHOLDER,
							Integer.valueOf(time).toString());
					dialogMe.setMessage(msg1);
				}
			}
		};

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				time--;
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
				if (time < 0) {
					cancel();
					return;
				}
			}
		};

		timer.schedule(task, 1000, 1000);

	}

	public void setOnTimeoutListener(OnTimeoutListener onTimeoutListener) {
		this.onTimeoutListener = onTimeoutListener;
	}

	/**
	 * 超时监听
	 * 
	 * @author cpz
	 * 
	 */
	public interface OnTimeoutListener {
		public void onTimeout();
	}

	public void show() {
		this.isShowing = true;
		dialog.show();
		DialogUtil.setDialogAllowClose(this.dialog, false);
	}

	public void cancel() {
		this.isShowing = false;
		onTimeoutListener = null;
		time = -1;
		DialogUtil.setDialogAllowClose(this.dialog, true);
		dialog.cancel();
	}

	public boolean isShowing() {
		return this.isShowing;
	}

	public Dialog getDialog() {
		return dialog;
	}
	
	public void setCancelable(boolean flag)  {
		dialog.setCancelable(flag);
	}
	
}
