package me.andpay.apos.cmview;

import java.util.Timer;
import java.util.TimerTask;

import me.andpay.ti.util.StringUtil;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

public class TiTimeButton extends Button {


	private OnTimeoutListener onTimeoutListener;

	private int time;

	public String PLACEHOLDER = "$TIME$";

	public String text;
	
	private Timer timer;
	
	public TiTimeButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TiTimeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TiTimeButton(Context context) {
		super(context);
	}


	public void setOnTimeoutListener(final OnTimeoutListener onTimeoutListener) {
		this.onTimeoutListener = onTimeoutListener;
	}

	public void startTimer(int timeout) {
		if(StringUtil.isBlank(text)) {
			text = getText().toString();
		}
		this.time = timeout;
		setText(text.replace(PLACEHOLDER, Integer.toString(timeout)));
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (time < 0) {
						if (onTimeoutListener != null) {
							onTimeoutListener.onTimeout();
						}
						return;
					}
					setText(text.replace(PLACEHOLDER, Integer.toString(time)));

				}
			}
		};

		Timer timer = new Timer();
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
	
	public void stopTimer() {
		timer.cancel();
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

}
