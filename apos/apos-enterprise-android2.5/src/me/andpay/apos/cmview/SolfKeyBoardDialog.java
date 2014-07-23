package me.andpay.apos.cmview;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import android.app.Activity;
import android.text.Editable;
import android.text.Selection;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class SolfKeyBoardDialog {

	private Activity mActivity;

	private View softInputView;

	private EditText currentView;

	private LinearLayout btn_delete;

	private LinearLayout sure_btn;

	private boolean sureButtonFlag = true;

	PopupWindow mPopupWindow;

	private View rootView;

	private OnClickHideButtonListener onClickHideButtonListener;

	/**
	 * 隐藏键盘按钮
	 */
	private ImageButton hintImgeBtn;

	private static Map<Integer, Integer> idmap = new HashMap<Integer, Integer>();

	private static Map<Integer, Integer> numKeyMap = new HashMap<Integer, Integer>();

	static {
		idmap.put(R.id.com_one_lay, KeyEvent.KEYCODE_1);
		idmap.put(R.id.com_tow_lay, KeyEvent.KEYCODE_2);
		idmap.put(R.id.com_three_lay, KeyEvent.KEYCODE_3);
		idmap.put(R.id.com_four_lay, KeyEvent.KEYCODE_4);
		idmap.put(R.id.com_five_lay, KeyEvent.KEYCODE_5);
		idmap.put(R.id.com_six_lay, KeyEvent.KEYCODE_6);
		idmap.put(R.id.com_seven_lay, KeyEvent.KEYCODE_7);
		idmap.put(R.id.com_eight_lay, KeyEvent.KEYCODE_8);
		idmap.put(R.id.com_nine_lay, KeyEvent.KEYCODE_9);
		idmap.put(R.id.com_zero_lay, KeyEvent.KEYCODE_0);
		idmap.put(R.id.com_sure_lay, KeyEvent.KEYCODE_ENTER);
		idmap.put(R.id.com_delete_imgbt, KeyEvent.KEYCODE_DEL);

		numKeyMap.put(KeyEvent.KEYCODE_1, 1);
		numKeyMap.put(KeyEvent.KEYCODE_2, 2);
		numKeyMap.put(KeyEvent.KEYCODE_3, 3);
		numKeyMap.put(KeyEvent.KEYCODE_4, 4);
		numKeyMap.put(KeyEvent.KEYCODE_5, 5);
		numKeyMap.put(KeyEvent.KEYCODE_6, 6);
		numKeyMap.put(KeyEvent.KEYCODE_7, 7);
		numKeyMap.put(KeyEvent.KEYCODE_8, 8);
		numKeyMap.put(KeyEvent.KEYCODE_9, 9);
		numKeyMap.put(KeyEvent.KEYCODE_0, 0);
	}

	public static SolfKeyBoardDialog instance(Activity activity, View rootView,
			int height, OnKeyboardListener listener) {
		View softKeyBoardView = View.inflate(activity,
				R.layout.com_keyboard_password_layout, null);
		SolfKeyBoardDialog solfKeyBoard = new SolfKeyBoardDialog(activity,
				softKeyBoardView, rootView, height);
		solfKeyBoard.setOnKeyboardListener(listener);
		solfKeyBoard.hideKeyboard();

		return solfKeyBoard;
	}

	public SolfKeyBoardDialog(Activity activity, View _view, View rootView,
			int height) {
		mActivity = activity;
		softInputView = _view;
		this.rootView = rootView;

		mPopupWindow = new PopupWindow(_view, LayoutParams.MATCH_PARENT, height);

		setupKeypad();
	}

	private void setupKeypad() {
		softInputView.findViewById(R.id.com_one_lay).setOnTouchListener(
				onTouchListener);
		softInputView.findViewById(R.id.com_tow_lay).setOnTouchListener(
				onTouchListener);
		softInputView.findViewById(R.id.com_three_lay).setOnTouchListener(
				onTouchListener);
		softInputView.findViewById(R.id.com_four_lay).setOnTouchListener(
				onTouchListener);
		softInputView.findViewById(R.id.com_five_lay).setOnTouchListener(
				onTouchListener);
		softInputView.findViewById(R.id.com_six_lay).setOnTouchListener(
				onTouchListener);
		softInputView.findViewById(R.id.com_seven_lay).setOnTouchListener(
				onTouchListener);
		softInputView.findViewById(R.id.com_eight_lay).setOnTouchListener(
				onTouchListener);
		softInputView.findViewById(R.id.com_nine_lay).setOnTouchListener(
				onTouchListener);

		softInputView.findViewById(R.id.com_zero_lay).setOnTouchListener(
				onTouchListener);

		sure_btn = (LinearLayout) softInputView.findViewById(R.id.com_sure_lay);
		sure_btn.setOnTouchListener(onTouchListener);

		// softInputView.findViewById(R.id.com_keyboard).setOnClickListener(this);

		btn_delete = (LinearLayout) softInputView
				.findViewById(R.id.com_delete_imgbt);
		btn_delete.setOnTouchListener(onTouchListener);

		softInputView.findViewById(R.id.com_sure_lay).setOnTouchListener(
				onTouchListener);

		hintImgeBtn = (ImageButton) softInputView
				.findViewById(R.id.com_board_hint_btn);
		hintImgeBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				hideKeyboard();
				if (onClickHideButtonListener != null) {
					onClickHideButtonListener.onKeyboardHide();
				}
			}
		});

	}

	OnTouchListener onTouchListener = new OnTouchListener() {

		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.setBackgroundDrawable(mActivity.getResources().getDrawable(
						R.drawable.com_keyboard_button_blue_img));
				keyPressed(idmap.get(v.getId()));
			} else {
				v.setBackgroundDrawable(mActivity.getResources().getDrawable(
						R.drawable.com_keyboard_button_img));
				return false;
			}
			return true;
		}

	};

	private void keyPressed(int keyCode) {
		Editable etext = null;
		if (keyCode == KeyEvent.KEYCODE_DEL) {
			if (currentView.length() == 0) {
				return;
			}
			String text = currentView.getText().toString();
			currentView.setText(text.subSequence(0, currentView.length() - 1));
			etext = currentView.getText();
		} else if (keyCode == KeyEvent.KEYCODE_ENTER) {

			if (mListener != null)
				mListener.sureClick();
			return;
		} else {
			String text = currentView.getText().toString();
			// currentView.setText(text);
			currentView.setText(new StringBuffer(text).append(numKeyMap.get(
					keyCode).toString()));
			etext = currentView.getText();
		}

		if (sureButtonFlag) {
			if (etext.length() > 0
					&& !etext.toString().equals(
							mActivity.getResources().getString(
									R.string.com_amt_str))) {
				sure_btn.setBackgroundDrawable(mActivity.getResources()
						.getDrawable(R.drawable.com_keyboard_button_blue_img));
				sure_btn.setEnabled(true);
			} else {
				sure_btn.setBackgroundDrawable(mActivity.getResources()
						.getDrawable(R.drawable.com_keyboard_button_img));
				sure_btn.setEnabled(false);

			}
		}

		Selection.setSelection(etext, currentView.length());

	}

	public void showKeyboard(EditText view) {
		currentView = view;

		rootView.post(new Runnable() {
			public void run() {
				mPopupWindow.showAtLocation(rootView, Gravity.RIGHT
						| Gravity.BOTTOM, 0, 0);
			}
		});

		String etext = currentView.getText().toString();
		if (etext.length() > 0
				&& !etext.toString().equals(
						mActivity.getResources()
								.getString(R.string.com_amt_str))) {
			sure_btn.setBackgroundDrawable(mActivity.getResources()
					.getDrawable(R.drawable.com_keyboard_button_blue_img));
			sure_btn.setEnabled(true);
		} else {
			sure_btn.setBackgroundDrawable(mActivity.getResources()
					.getDrawable(R.drawable.com_keyboard_button_img));
			sure_btn.setEnabled(false);

		}
	}

	public boolean isShown() {
		return softInputView.isShown();
	}

	public void hideKeyboard() {

		mPopupWindow.dismiss();
	}

	public interface OnKeyboardListener {
		// 确认按钮事件
		void sureClick();
	}

	private OnKeyboardListener mListener;

	public void setOnKeyboardListener(OnKeyboardListener listener) {
		mListener = listener;
	}

	public View getCurrentView() {
		return currentView;
	}

	public ImageButton getHintImgeBtn() {
		return hintImgeBtn;
	}

	public void showPasswordEdit(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

	}

	public LinearLayout getSure_btn() {
		return sure_btn;
	}

	public void setSureButtonFlag(boolean sureButtonFlag) {
		this.sureButtonFlag = sureButtonFlag;
	}

	public interface OnClickHideButtonListener {
		public void onKeyboardHide();
	}

	public void setOnClickHideButtonListener(
			OnClickHideButtonListener onClickHideButtonListener) {
		this.onClickHideButtonListener = onClickHideButtonListener;
	}
}
