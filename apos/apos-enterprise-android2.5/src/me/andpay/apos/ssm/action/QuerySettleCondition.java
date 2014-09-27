package me.andpay.apos.ssm.action;

public class QuerySettleCondition {

	private Long batch_no;

	private String batch_status;

	private Integer max_record_size;

	private String max_ref_no;

	private String min_ref_no;

	public Long getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(Long batch_no) {
		this.batch_no = batch_no;
	}

	public String getBatch_status() {
		return batch_status;
	}

	public void setBatch_status(String batch_status) {
		this.batch_status = batch_status;
	}

	public Integer getMax_record_size() {
		return max_record_size;
	}

	public void setMax_record_size(Integer max_record_size) {
		this.max_record_size = max_record_size;
	}

	public String getMax_ref_no() {
		return max_ref_no;
	}

	public void setMax_ref_no(String max_ref_no) {
		this.max_ref_no = max_ref_no;
	}

	public String getMin_ref_no() {
		return min_ref_no;
	}

	public void setMin_ref_no(String min_ref_no) {
		this.min_ref_no = min_ref_no;
	}

}
