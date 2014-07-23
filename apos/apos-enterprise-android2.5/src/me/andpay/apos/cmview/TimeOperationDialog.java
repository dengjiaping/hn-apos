package me.andpay.apos.cmview;

import java.util.Timer;
import java.util.TimerTask;

import me.andpay.apos.R;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.DialogUtil;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * 公用dialog
 * 
 * @author tinyliu
 * 
 */
public class TimeOperationDialog {

	private Dialog dialog = null;

	private Button sureButton = null;

	private Button cancelButton = null;

	public String PLACEHOLDER = "$TIME$";

	public OnTimeoutListener onTimeoutListener;

	private String oldMsg;

	private int time;

	private TimerTask task = null;

	private boolean isShowing = false;

	public TimeOperationDialog(Context context, String title, String promptMsg,
			int startTime) {
		this(context, title, promptMsg, startTime, false);
	}

	public TimeOperationDialog(Context context, String title, String promptMsg,
			int startTime, boolean isSureleft) {
		this(context, title, promptMsg, null, null, startTime, isSureleft);
	}

	public TimeOperationDialog(Context context, String title, String promptMsg,
			String sureButtonStr, String cancelButtonStr, int startTime,
			boolean isSureleft) {

		this.time = startTime;
		oldMsg = promptMsg;
		String msg = oldMsg.replace(PLACEHOLDER, Integer.valueOf(time)
				.toString());

		dialog = new Dialog(context, R.style.Theme_dialog_style);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (isSureleft) {
			dialog.setContentView(R.layout.com_operation_dialog_sureleft_layout);
		} else {
			dialog.setContentView(R.layout.com_operation_dialog_layout);
		}
		sureButton = (Button) dialog.findViewById(R.id.com_sure_btn);
		cancelButton = (Button) dialog.findViewById(R.id.com_cancel_btn);
		if (!StringUtil.isEmpty(sureButtonStr)) {
			sureButton.setText(sureButtonStr);
		}
		if (!StringUtil.isEmpty(cancelButtonStr)) {
			cancelButton.setText(cancelButtonStr);
		}
		if (!StringUtil.isEmpty(title)) {
			((TextView) dialog.findViewById(R.id.com_prompt_dialog_title_tv))
					.setText(title);
		} else {
			((TextView) dialog.findViewById(R.id.com_prompt_dialog_title_tv))
					.setVisibility(View.GONE);
		}
		if (!StringUtil.isEmpty(promptMsg)) {
			((TextView) dialog.findViewById(R.id.com_prompt_dialog_tv))
					.setText(msg);
		} else {
			((TextView) dialog.findViewById(R.id.com_prompt_dialog_tv))
					.setVisibility(View.GONE);
		}
		this.setCancelButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();

			}
		});

		Timer timer = new Timer();
		final Dialog dialogMe = dialog;
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (time < 0) {
						if (!dialogMe.isShowing()) {
							return;
						}
						dialog.dismiss();
						if (onTimeoutListener != null) {
							onTimeoutListener.onTimeout();
						}
						return;
					}
					String msg1 = oldMsg.replace(PLACEHOLDER,
							Integer.valueOf(time).toString());
					((TextView) dialog.findViewById(R.id.com_prompt_dialog_tv))
							.setText(msg1);
				}
			}
		};

		task = new TimerTask() {
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

	public void setSureButtonOnclickListener(OnClickListener onclickListener) {
		sureButton.setOnClickListener(onclickListener);
	}

	public void setCancelButtonOnclickListener(OnClickListener onclickListener) {
		cancelButton.setOnClickListener(onclickListener);
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
		DialogUtil.setDialogAllowClose(this.dialog, false);
		this.isShowing = true;
		dialog.show();
	}

	public void dismiss() {
		this.isShowing = false;
		DialogUtil.setDialogAllowClose(this.dialog, true);
		task.cancel();
		dialog.dismiss();
	}

	public boolean isShowing() {
		return isShowing;
	}

	public Dialog getDialog() {
		return this.dialog;
	}
	
	
	public void setCancelable(boolean flag)  {
		dialog.setCancelable(flag);
	}
	

}
