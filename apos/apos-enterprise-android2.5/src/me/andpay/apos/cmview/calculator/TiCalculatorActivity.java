package me.andpay.apos.cmview.calculator;

import java.text.DecimalFormat;
import java.text.ParseException;

import me.andpay.apos.R;
import me.andpay.timobileframework.util.calculate.TiCalculateResult;
import me.andpay.timobileframework.util.calculate.TiCalulate;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 计算器
 * 
 * @author cpz
 * 
 */
public class TiCalculatorActivity extends Activity {

	private ImageView backView;

	private RelativeLayout oneLay;
	private RelativeLayout twoLay;
	private RelativeLayout threeLay;
	private RelativeLayout fourLay;
	private RelativeLayout fiveLay;
	private RelativeLayout sixLay;
	private RelativeLayout sevenLay;
	private RelativeLayout eightLay;
	private RelativeLayout nineLay;
	private RelativeLayout zeroLay;
	private RelativeLayout pointLay;

	private RelativeLayout clearLay;
	private RelativeLayout divideLay;
	private RelativeLayout multiplyLay;
	private RelativeLayout subtractLay;
	private RelativeLayout addLay;
	private RelativeLayout equalLay;
	private RelativeLayout backLay;

	private TextView resultText;
	private TextView expressionText;

	private TiCalulate tiCalulate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.com_calculator_layout);

		tiCalulate = new TiCalulate();

		backView = (ImageView) this.findViewById(R.id.com_top_back_btn);
		oneLay = (RelativeLayout) this.findViewById(R.id.com_cal_one_lay);
		oneLay.setTag("1");
		twoLay = (RelativeLayout) this.findViewById(R.id.com_cal_two_lay);
		twoLay.setTag("2");
		threeLay = (RelativeLayout) this.findViewById(R.id.com_cal_three_lay);
		threeLay.setTag("3");
		fourLay = (RelativeLayout) this.findViewById(R.id.com_cal_four_lay);
		fourLay.setTag("4");
		fiveLay = (RelativeLayout) this.findViewById(R.id.com_cal_five_lay);
		fiveLay.setTag("5");
		sixLay = (RelativeLayout) this.findViewById(R.id.com_cal_six_lay);
		sixLay.setTag("6");
		sevenLay = (RelativeLayout) this.findViewById(R.id.com_cal_seven_lay);
		sevenLay.setTag("7");
		eightLay = (RelativeLayout) this.findViewById(R.id.com_cal_eight_lay);
		eightLay.setTag("8");
		nineLay = (RelativeLayout) this.findViewById(R.id.com_cal_nine_lay);
		nineLay.setTag("9");
		zeroLay = (RelativeLayout) this.findViewById(R.id.com_cal_zero_lay);
		zeroLay.setTag("0");
		pointLay = (RelativeLayout) this.findViewById(R.id.com_cal_point_lay);
		pointLay.setTag(".");

		clearLay = (RelativeLayout) this.findViewById(R.id.com_cal_clear_lay);
		clearLay.setTag("C");
		backLay = (RelativeLayout) this.findViewById(R.id.com_cal_back_lay);
		backLay.setTag("B");

		divideLay = (RelativeLayout) this.findViewById(R.id.com_cal_divide_lay);
		divideLay.setTag("÷");
		multiplyLay = (RelativeLayout) this
				.findViewById(R.id.com_cal_multiply_lay);
		multiplyLay.setTag("×");
		subtractLay = (RelativeLayout) this
				.findViewById(R.id.com_cal_subtract_lay);
		subtractLay.setTag("－");
		addLay = (RelativeLayout) this.findViewById(R.id.com_cal_add_lay);
		addLay.setTag("+");

		equalLay = (RelativeLayout) this.findViewById(R.id.com_cal_equal_lay);
		equalLay.setTag("=");

		resultText = (TextView) this.findViewById(R.id.com_cal_result_text);
		resultText.setText("0");

		expressionText = (TextView) this
				.findViewById(R.id.com_cal_expression_text);
		expressionText.setText("");

		oneLay.setOnClickListener(new CalculatorNumberClickListener());
		twoLay.setOnClickListener(new CalculatorNumberClickListener());
		threeLay.setOnClickListener(new CalculatorNumberClickListener());
		fourLay.setOnClickListener(new CalculatorNumberClickListener());
		fiveLay.setOnClickListener(new CalculatorNumberClickListener());
		sixLay.setOnClickListener(new CalculatorNumberClickListener());
		sevenLay.setOnClickListener(new CalculatorNumberClickListener());
		eightLay.setOnClickListener(new CalculatorNumberClickListener());
		nineLay.setOnClickListener(new CalculatorNumberClickListener());
		zeroLay.setOnClickListener(new CalculatorNumberClickListener());
		pointLay.setOnClickListener(new CalculatorNumberClickListener());

		divideLay.setOnClickListener(new CalculatorNumberClickListener());
		multiplyLay.setOnClickListener(new CalculatorNumberClickListener());
		subtractLay.setOnClickListener(new CalculatorNumberClickListener());
		addLay.setOnClickListener(new CalculatorNumberClickListener());
		equalLay.setOnClickListener(new CalculatorNumberClickListener());
		clearLay.setOnClickListener(new CalculatorNumberClickListener());
		backLay.setOnClickListener(new CalculatorNumberClickListener());

		backView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				back();
			}
		});

	}

	private void back() {
		setResultData();
		this.finish();
	}

	class CalculatorNumberClickListener implements OnClickListener {

		public void onClick(View v) {

			String inputData = v.getTag().toString();
			if (inputData.equals("B")) {
				back();
				return;
			}

			tiCalulate.inPutData(inputData);
			TiCalculateResult tiCalculateResult = tiCalulate
					.getCalulateResult();

			String result = tiCalculateResult.getDisplayResult();

			if (Double.valueOf(numFormat2(result)) > 999999999) {
				tiCalulate.inPutData("C");
				resultText.setText("超出计算范围");
				expressionText.setText("");
			} else {

				resultText.setText(tiCalculateResult.getDisplayResult());
				expressionText.setText(tiCalculateResult.getDiplayExpression());
			}

		}
	}

	/**
	 * 监听返回键按钮点击事件，如果当前存在流程，则用流程控制器进行回退
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResultData();
		}

		return super.onKeyDown(keyCode, event);

	}

	private void setResultData() {
		Intent aintent = new Intent();
		String disPlayResult = tiCalulate.getCalulateResult()
				.getDisplayResult();

		if (disPlayResult == null || disPlayResult.trim().equals("")) {
			disPlayResult = "0";
		}
		disPlayResult = numFormat2(disPlayResult);
		aintent.putExtra(TiCalculatorConfigs.CALCULATOR_RESULT, disPlayResult);
		this.setResult(TiCalculatorConfigs.CALCULATOR_REQUEST_CODE, aintent);
	}

	public String numFormat2(String str) throws RuntimeException {
		DecimalFormat format = new DecimalFormat("###,##0.#########");
		Number data = null;
		String formatStr = "0";
		try {
			data = format.parse(str);
		} catch (ParseException e) {
			return formatStr;
		}
		return data.toString();
	}

}
