package me.andpay.timobileframework.util.calculate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TiCalulate {

	private String expression = "";
	private String result = "0";
	private String diplayExpression = "";
	private String displayResult = "0";
	private Stack<TiCalculateResult> calculateResultStack = new Stack<TiCalculateResult>();

	// private boolean isNoHistroy;

	public TiCalculateResult getCalulateResult() {
		if (calculateResultStack.size() == 0) {
			clear();
			return genCalulateResult();
		}

		TiCalculateResult result = calculateResultStack.peek();

		return result;
	}

	private void posHistory() {
		if (calculateResultStack.size() > 1) {
			calculateResultStack.pop();
		}
	}

	private void calculate(String value) {
		// 反复输入0
		if (result.equals("0") && value.equals("0")) {
			displayResult = "0";
			posHistory();
			return;
		}

		if (value.equals("=")) {
			if (expression.equals("")) {
				posHistory();
				return;
			}
			result = calExpression(expression + result);
			expression = "";
			displayResult = getShowResult(result);
			result = convertToString2(result);
			return;
		}
		if (isSymbol(value)) {
			if (expression.length() == 0) {
				expression += result + value;
				displayResult = getShowResult(result);
				result = "0";
				return;
			}

			if (isEndSymbol(expression) && result.equals("0")) {
				expression = expression.substring(0, expression.length() - 1)
						+ value;
				result = calExpression(expression);
				posHistory();

			} else {
				expression += result;
				result = calExpression(expression);
				expression += value;
			}

			displayResult = getShowResult(result);
			result = "0";

		} else {
			// 已经包含小数点，输入小数点无效
			if (result.indexOf(".") > 0 && value.equals(".")) {
				posHistory();
				return;
			}

			result += value;
			if (result.indexOf(".") > 0&&result.endsWith("0")) {
				
				displayResult = displayResult+ value;
			} else {
				displayResult = getShowResult(result);
			}
		}

	}

	private String numFormat(Number number) throws RuntimeException {
		DecimalFormat format = new DecimalFormat("###,##0.#########");
		String data = format.format(number);
		return data;
	}

	private String numFormat2(Number number) throws RuntimeException {
		DecimalFormat format = new DecimalFormat("##0.#########");
		String data = format.format(number);
		return data;
	}

	private void clear() {
		expression = "";
		result = "0";
		diplayExpression = "";
		displayResult = "0";
		calculateResultStack.removeAllElements();
	}

	public void inPutData(String value) {

		// 清楚按键
		if (value.equals("C")) {
			clear();
			return;
		}
		// 回退建
		if (value.equals("B")) {
			if (calculateResultStack.size() > 1) {
				TiCalculateResult calculateResult = calculateResultStack.pop();
				calculateResult = calculateResultStack.peek();
				diplayExpression = calculateResult.getDiplayExpression();
				displayResult = calculateResult.getDisplayResult();
				expression = calculateResult.getExpression();
				result = calculateResult.getResult();
			} else {
				clear();
			}
			return;
		}
		calculate(value);
		calculateResultStack.push(genCalulateResult());

	}

	public TiCalculateResult genCalulateResult() {
		TiCalculateResult re = new TiCalculateResult();
		diplayExpression = getShowExpression(expression);
		re.setDiplayExpression(diplayExpression);
		re.setExpression(expression);
		re.setDisplayResult(displayResult);
		re.setResult(result);
		return re;
	}

	private String calExpression(String input) {
		if (input.equals("")) {
			input = "0";
		}
		if (isEndSymbol(input)) {
			input = expression.substring(0, expression.length() - 1);
		}
		input = input.replace("×", "*");
		input = input.replace("÷", "/");
		input = input.replace("－", "-");
		Expression boya = new Expression(input);
		return boya.getResult();
	}

	private String getShowResult(String str) {
		if (str.equals("")) {
			return "0";
		}
		if (str.equals("Infinity")) {
			return "计算错误";
		}

		String formatStr = convertToString(str);

		if (str.endsWith(".")) {
			formatStr = formatStr + ".";
		}

		return formatStr;
	}

	private String convertToString(String str) {
		Double double1 = new Double(str);
		String formatStr = numFormat(double1);
		return formatStr;
	}

	private String convertToString2(String str) {
		Double double1 = new Double(str);
		if (str.equals("Infinity")) {
			return "0";
		}
		String formatStr = numFormat2(double1);

		return formatStr;
	}

	private boolean isSymbol(String value) {

		Pattern pat = Pattern.compile("[//+|×|÷|=|－]");
		Matcher mat = pat.matcher(value);
		if (mat.find()) {
			return true;
		}
		return false;
	}

	// 是否是符号结尾
	private boolean isEndSymbol(String value) {
		Pattern pat = Pattern.compile("[//+|－|×|÷|=]$");
		Matcher mat = pat.matcher(value);
		if (mat.find()) {
			return true;
		}
		return false;
	}

	private String getShowExpression(String ex) {
		List<String> exList = new ArrayList<String>();// 存储中序表达式
		StringBuffer replaceEx = new StringBuffer();
		StringTokenizer st = new StringTokenizer(ex, "+－×÷=", true);
		while (st.hasMoreElements()) {
			String s = st.nextToken();
			if (isSymbol(s)) {
				replaceEx.append(s);
			} else {
				replaceEx.append(getShowResult(s));
			}
		}

		return replaceEx.toString();
	}

}
