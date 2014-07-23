package me.andpay.apos.ssm.callback;


import java.math.BigDecimal;


/**
 * 数字计数器
 * @author cpz
 *
 */
public class NumberCounter {
	
	/**
	 * 原数字
	 */
	public BigDecimal oldNum;
	/**
	 * 新的数字结果
	 */
	public BigDecimal newNum;
	/**
	 * 增加数字
	 */
	public BigDecimal addNum;
	
	/**
	 * 数据自增次数
	 */
	public int count = 6;
	
	
	public NumberCounter(String oldNumStr,String newNumStr) {
		
		oldNum = new BigDecimal(oldNumStr);
		newNum = new BigDecimal(newNumStr);
		addNum = newNum.subtract(oldNum).divide(new BigDecimal(count),BigDecimal.ROUND_UP);
	}
	
	public BigDecimal getAndAddNum() {
		oldNum =  oldNum.add(addNum);
		if(oldNum.compareTo(newNum) >= 0 && addNum.compareTo(BigDecimal.ZERO)>0) {
			oldNum = newNum;
		}else if(oldNum.compareTo(newNum) <= 0 && addNum.compareTo(BigDecimal.ZERO)<0) {
			oldNum = newNum;
		}
		return oldNum;
	}
	
	public boolean hasNextNum() {
		if(oldNum.compareTo(newNum) == 0) {
			return false;
		}
		return true;
	}
	
	
	
	
	public static void main(String[] args) {
		NumberCounter counter = new NumberCounter("10","16");
		while (counter.hasNextNum()) {
			System.out.println(counter.getAndAddNum());
		}
	}
}
