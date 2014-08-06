package me.andpay.apos.lft.data;

public class WaterOrder implements BaseData{
	private String time;// 时间
	private String oweCost;// 欠费
	private String breachCost;// 违约
	private String shouldbeCost;// 应缴

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOweCost() {
		return oweCost;
	}

	public void setOweCost(String oweCost) {
		this.oweCost = oweCost;
	}

	public String getBreachCost() {
		return breachCost;
	}

	public void setBreachCost(String breachCost) {
		this.breachCost = breachCost;
	}

	public String getShouldbeCost() {
		return shouldbeCost;
	}

	public void setShouldbeCost(String shouldbeCost) {
		this.shouldbeCost = shouldbeCost;
	}


}
