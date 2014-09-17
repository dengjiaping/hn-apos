package me.andpay.apos.cmview;

import me.andpay.apos.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;

public class TiNoteEditText extends EditText {

	private int color;

	private int linecount = 10;

	public TiNoteEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		String lines = attrs.getAttributeValue(context.getPackageName(),
				"lines");
		Log.v(this.getClass().getName(), " lines : " + lines);
		color = getResources().getColor(R.color.com_bule_color);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		int lineHeight = this.getLineHeight();
		Paint mPaint = getPaint();
		mPaint.setColor(color);
		int topPadding = this.getPaddingTop();
		int leftPadding = this.getPaddingLeft();
		float textSize = getTextSize();
		setGravity(Gravity.LEFT | Gravity.TOP);
		int y = (int) (topPadding + textSize);

		for (int i = 0; i < getLineCount(); i++) {
			Log.v(this.getClass().getName(), "" + y);
			canvas.drawLine(0, y + 2, getRight(), y + 2, mPaint);
			y += lineHeight;
		}
		mPaint.setColor(getResources().getColor(R.color.com_red_color));
		canvas.drawLine(leftPadding / 2, 0, leftPadding / 2, y, mPaint);
		canvas.drawLine(leftPadding * 3 / 4, 0, leftPadding * 3 / 4, y, mPaint);
		canvas.translate(0, 0);
		super.onDraw(canvas);
	}

	@Override
	public int getLineCount() {
		return this.linecount;
	}

	/**
	 * 设置记事本的编辑框背景线条颜色
	 * 
	 * @paramcolorinttype【代表颜色的整数】
	 */
	public void setBGColor(int color) {
		this.color = color;
		invalidate();
	}

	/**
	 * 设置记事本的编辑框背景线条颜色
	 * 
	 * @paramcolorIdinttype【代表颜色的资源id】
	 */
	public void setBGColorId(int colorId) {
		this.color = getResources().getColor(colorId);
		invalidate();
	}

}
