package me.andpay.util;

import me.andpay.timobileframework.util.calculate.TiCalulate;

import org.junit.Test;

public class CalulateTest {

	@Test
	public void numException() throws Exception {

		TiCalulate tiCalulate = new TiCalulate();
		tiCalulate.inPutData("0");
		tiCalulate.inPutData("0");
		tiCalulate.inPutData("1");
		tiCalulate.inPutData("0");
		tiCalulate.inPutData("0");
		tiCalulate.inPutData("1");
		tiCalulate.inPutData("1");
		tiCalulate.inPutData(".");
		tiCalulate.inPutData("0");
		tiCalulate.inPutData("0");
		System.out.println(tiCalulate.getCalulateResult());

		tiCalulate.inPutData("+");
		tiCalulate.inPutData("1");
		tiCalulate.inPutData("1");
		tiCalulate.inPutData("1");
		tiCalulate.inPutData("1");
		tiCalulate.inPutData("1");
		System.out.println(tiCalulate.getCalulateResult());
		tiCalulate.inPutData("+");
		System.out.println(tiCalulate.getCalulateResult());
//		tiCalulate.inPutData("1");
//		tiCalculateResult = tiCalulate.getCalulateResult();
//		System.out.println(tiCalculateResult);
//		tiCalulate.inPutData("1");
//		tiCalculateResult = tiCalulate.getCalulateResult();
//		System.out.println(tiCalculateResult);
//		tiCalulate.inPutData("+");
//		tiCalculateResult = tiCalulate.getCalulateResult();
//		System.out.println(tiCalculateResult);
//		tiCalulate.inPutData("+");
//		tiCalculateResult = tiCalulate.getCalulateResult();
//		System.out.println(tiCalculateResult);
//		tiCalulate.inPutData("－");
//		tiCalculateResult = tiCalulate.getCalulateResult();
//		System.out.println(tiCalculateResult);
//		tiCalulate.inPutData("1");
//		tiCalulate.inPutData("1");
//		tiCalulate.inPutData(".");
//		tiCalulate.inPutData("1");
//		tiCalculateResult = tiCalulate.getCalulateResult();
//		System.out.println(tiCalculateResult);
//		tiCalulate.inPutData("+");
//		tiCalculateResult = tiCalulate.getCalulateResult();
//		System.out.println(tiCalculateResult);
//		tiCalulate.inPutData("1");
//		tiCalculateResult = tiCalulate.getCalulateResult();
//		System.out.println(tiCalculateResult);
//		tiCalulate.inPutData("+");
//		System.out.println( tiCalulate.getCalulateResult());
//		tiCalulate.inPutData("+");
//		System.out.println( tiCalulate.getCalulateResult());
		
//		Expression boya = new Expression("-22+55");
//		System.out.println("======="+boya.getResult());
	}
	
//	@Test
//	public void TestNum() {
//		DecimalFormat format = new DecimalFormat("###,##0.#########");
//		Number data = null;
//		try {
//			data = format.parse("111,11.");
//		} catch (ParseException e) {
//			System.err.println(e);
//		}
//		System.err.println(data);
//	}

}
