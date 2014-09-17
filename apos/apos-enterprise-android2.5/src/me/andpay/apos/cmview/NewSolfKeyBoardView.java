package me.andpay.apos.cmview;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;

import com.google.common.eventbus.EventBus;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NewSolfKeyBoardView {
	private Context mContext;

	private View softInputView;

	private EditText currentView;

	private RelativeLayout btn_delete;

	private Button sure_btn;

	// private boolean sureButtonFlag = true;

	/**
	 * 是否带计算器
	 */
	private boolean withCalculator = true;

	private View footView;

	private ImageView clearImageView;
	private TextView clearTextView;

	public void setFootView(View footView) {
		this.footView = footView;
	}

	/**
	 * 隐藏键盘按钮
	 */
	private ImageButton hintImgeBtn;

	private static Map<Integer, Integer> idmap = new HashMap<Integer, Integer>();

	private static Map<Integer, Integer> numKeyMap = new HashMap<Integer, Integer>();

	static {
		idmap.put(R.id.com_one_lay, KeyEvent.KEYCODE_1);
		idmap.put(R.id.com_two_lay, KeyEvent.KEYCODE_2);
		idmap.put(R.id.com_three_lay, KeyEvent.KEYCODE_3);
		idmap.put(R.id.com_four_lay, KeyEvent.KEYCODE_4);
		idmap.put(R.id.com_five_lay, KeyEvent.KEYCODE_5);
		idmap.put(R.id.com_six_lay, KeyEvent.KEYCODE_6);
		idmap.put(R.id.com_seven_lay, KeyEvent.KEYCODE_7);
		idmap.put(R.id.com_eight_lay, KeyEvent.KEYCODE_8);
		idmap.put(R.id.com_nine_lay, KeyEvent.KEYCODE_9);
		idmap.put(R.id.com_zero_lay, KeyEvent.KEYCODE_0);
		idmap.put(R.id.com_dzero_lay, KeyEvent.KEYCODE_DPAD_LEFT);
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

	public static NewSolfKeyBoardView instance(Context context,
			RelativeLayout foot, OnKeyboardListener listener) {
		View softKeyBoardView = View.inflate(context,
				R.layout.com_new_softkeyborad, null);
		NewSolfKeyBoardView solfKeyBoard = new NewSolfKeyBoardView(context,
				softKeyBoardView);
		solfKeyBoard.setOnKeyboardListener(listener);
		LayoutParams softLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
//		softKeyBoardView.setVisibility(View.GONE);

		foot.addView(softKeyBoardView, softLayoutParams);
		// foot.setVisibility(View.GONE);
		solfKeyBoard.setFootView(foot);
		solfKeyBoard.hideKeyboard();

		// 捕捉冒泡事件
		foot.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				return;
			}
		});
		return solfKeyBoard;
	}

	public NewSolfKeyBoardView(Context context, View _view) {
		mContext = context;
		softInputView = _view;
		initView();
		initListener();

		setupKeypad(_view);
	}

	private void initView() {
	}

	private void initListener() {
	}

	private void setupKeypad(View _view) {
		softInputView.findViewById(R.id.com_one_lay).setOnTouchListener(
				onTouchListener);
		softInputView.findViewById(R.id.com_two_lay).setOnTouchListener(
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

		softInputView.findViewById(R.id.com_dzero_lay).setOnTouchListener(
				onTouchListener);

		sure_btn = (Button) softInputView.findViewById(R.id.com_sure_lay);
		sure_btn.setOnTouchListener(onTouchListener);

		// softInputView.findViewById(R.id.com_keyboard).setOnClickListener(this);

		btn_delete = (RelativeLayout) softInputView
				.findViewById(R.id.com_delete_imgbt);
		btn_delete.setOnTouchListener(onTouchListener);

		clearImageView = (ImageView) softInputView
				.findViewById(R.id.com_dzero_img);
		clearTextView = (TextView) softInputView
				.findViewById(R.id.com_dzero_text);
		if (withCalculator) {
			clearTextView.setVisibility(View.GONE);
			clearImageView.setVisibility(View.VISIBLE);
		} else {
			clearTextView.setVisibility(View.VISIBLE);
			clearImageView.setVisibility(View.GONE);
		}

		softInputView.findViewById(R.id.com_sure_lay).setOnTouchListener(
				onTouchListener);

		/*
		 * hintImgeBtn = (ImageButton)
		 * _view.findViewById(R.id.com_board_hint_btn);
		 * hintImgeBtn.setOnClickListener(new View.OnClickListener() {
		 * 
		 * public void onClick(View v) { hideKeyboard(); } });
		 */

	}

	OnTouchListener onTouchListener = new OnTouchListener() {

		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (v.getId() == R.id.com_delete_imgbt
						|| v.getId() == R.id.com_dzero_lay) {
					v.setBackgroundDrawable(mContext
							.getResources()
							.getDrawable(
									R.drawable.com_new_keyboard_button_delete_sel_img));
				}
				if (v.getId() == R.id.com_sure_lay) {
					v.setBackgroundDrawable(mContext
							.getResources()
							.getDrawable(
									R.drawable.com_new_keyboard_button_blue_sel_img));
				} else {
					v.setBackgroundDrawable(mContext.getResources()
							.getDrawable(
									R.drawable.com_new_keyboard_button_sel_img));
				}
				keyPressed(idmap.get(v.getId()));
			} else {
				if (v.getId() == R.id.com_delete_imgbt
						|| v.getId() == R.id.com_dzero_lay) {
					v.setBackgroundDrawable(mContext
							.getResources()
							.getDrawable(
									R.drawable.com_new_keyboard_button_delete_img));
				} else if (v.getId() != R.id.com_sure_lay) {
					v.setBackgroundDrawable(mContext
							.getResources()
							.getDrawable(R.drawable.com_new_keyboard_button_img));
				}
				return false;
			}
			return true;
		}

	};

	private void keyPressed(int keyCode) {
		Editable etext = null;
		if (keyCode == KeyEvent.KEYCODE_DEL) {
			if (currentView.length() == 0){
				return;
			}
			String text = currentView.getText().toString();
			// currentView.setText(text);
			currentView.setText(text.subSequence(0, currentView.length() - 1));
			etext = currentView.getText();
		} else if (keyCode == KeyEvent.KEYCODE_ENTER) {

			if (mListener != null)
				mListener.sureClick();
			return;
		} else {
			String text = currentView.getText().toString();
			currentView.setText(text);
			if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
				if (withCalculator) {
					if (mListener != null)
						mListener.showCalculator();
				} else {
					currentView.setText(mContext.getResources().getString(
							R.string.com_amt_str));
				}
				return;
			} else {
				currentView.setText(new StringBuffer(text).append(numKeyMap
						.get(keyCode).toString()));
			}
			etext = currentView.getText();
			

		}



		Selection.setSelection(etext, currentView.length());

	}

	public void changeSureButton(boolean enabled, String buttonText) {

		if (enabled) {
			sure_btn.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.com_new_keyboard_button_blue_img));
			sure_btn.setTextColor(mContext.getResources().getColor(
					R.color.lam_button_entrue_col));

			sure_btn.setEnabled(enabled);
			sure_btn.setText(buttonText);
		} else {
			sure_btn.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.com_new_keyboard_button_blue_disabled_img));
			sure_btn.setTextColor(mContext.getResources().getColor(
					R.color.tam_pur_button_disable_col));

			sure_btn.setEnabled(enabled);
			sure_btn.setText(buttonText);

		}
	}

	public void showKeyboard(EditText view) {
		currentView = view;
		footView.setVisibility(View.VISIBLE);
		// softInputView.setVisibility(View.VISIBLE);

		// String etext = currentView.getText().toString();
		// if (etext.length() > 0
		// && !etext.toString().equals(
		// mContext.getResources().getString(R.string.com_amt_str))) {
		//
		// changeSureButton(true, "请刷卡");
		//
		// } else {
		// sure_btn.setBackgroundDrawable(mContext.getResources().getDrawable(
		// R.drawable.com_new_keyboard_button_blue_disabled_img));
		// sure_btn.setTextColor(view.getResources().getColor(
		// R.color.tam_pur_button_disable_col));
		//
		// changeSureButton(true, "请刷卡");
		//
		// }
	}

	public boolean isShown() {
		return softInputView.isShown();
	}

	public void hideKeyboard() {
		// footView.setVisibility(View.GONE);
		// softInputView.setVisibility(View.GONE);
	}

	public interface OnKeyboardListener {
		// 确认按钮事件
		void sureClick();

		void showCalculator();

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

	public Button getSure_btn() {
		return sure_btn;
	}

	// public void setSureButtonFlag(boolean sureButtonFlag) {
	// this.sureButtonFlag = sureButtonFlag;
	// }

	public void setWithCalculator(boolean withCalculator) {
		this.withCalculator = withCalculator;

		if (withCalculator) {
			clearTextView.setVisibility(View.GONE);
			clearImageView.setVisibility(View.VISIBLE);
		} else {
			clearTextView.setVisibility(View.VISIBLE);
			clearImageView.setVisibility(View.GONE);
		}
	}

}
