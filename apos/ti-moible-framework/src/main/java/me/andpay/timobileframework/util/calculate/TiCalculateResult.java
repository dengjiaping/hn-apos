package me.andpay.timobileframework.util.calculate;

public class TiCalculateResult {

	private String diplayExpression;
	private String displayResult;
	private String expression;
	private String result;

	public String getDiplayExpression() {
		return diplayExpression;
	}

	public void setDiplayExpression(String diplayExpression) {
		this.diplayExpression = diplayExpression;
	}

	public String getDisplayResult() {
		return displayResult;
	}

	public void setDisplayResult(String displayResult) {
		this.displayResult = displayResult;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		String ss = "result=" + result + "   expression =" + expression
				+ "    showresult:" + displayResult + "   showexcetion :"
				+ diplayExpression;
		return ss;
	}
}
