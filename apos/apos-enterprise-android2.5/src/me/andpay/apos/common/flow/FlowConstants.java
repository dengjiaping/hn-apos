package me.andpay.apos.common.flow;


/**
 * 流程节点
 * @author cpz
 *
 */
public class FlowConstants {
	/**
	 * 转账刷卡流程
	 */
	public static final String LFT_TRANSFER_TXN="lft_transtf_txn";
	/**
	 * 收款人确认
	 */
	public static final String OFTEN_DEATAIL="go_person_sure";
	/**
	 * 常用联系人
	 */
	public static final String OFTEN="go_often_person";
	/**
	 * 刷卡联系人
	 */
	public static final String CARD_PERSON="person_information";
	
	/**
	 * 成功
	 */
	public static final String SUCCESS =  "success";
	/**
	 * 失败
	 */
	public static final String FAILED =  "faild";
	
	/**
	 * 失败
	 */
	public static final String FAILED_SEPT1 =  "faild_step1";
	
	
	/**
	 * 失败
	 */
	public static final String FAILED_SEPT2 =  "faild_step2";
	/**
	 * 返回首页
	 */
	public static final String GOHOME = "go_home";
	
	
	/**
	 * 成功分支1
	 */
	public static final String SUCCESS_STEP1 =  "success";
	/**
	 * 成功分支2
	 */
	public static final String SUCCESS_STEP2 =  "success2";
	/**
	 * 成功分支3
	 */
	public static final String SUCCESS_STEP3 =  "success3";
	/**
	 * 成功分支4
	 */
	public static final String SUCCESS_STEP4 =  "success4";
	/**
	 * 成功分支5
	 */
	public static final String SUCCESS_STEP5 =  "success5";
	
	/**
	 * 分支完成
	 */
	public static final String FINISH =  "finish";
	
	public static final String FINISH1 =  "finish1";

	/**
	 * 回退
	 */
	public static final String BACK =  "back";
	
	/**
	 * 启动流程，动态验证码
	 */
	public static final String START_ACTIVE = "activeCode";
	/**
	 * 启动流程，密码修改
	 */
	public static final String START_CHANGEPWD = "changePwd";
	/**
	 * 启动流程，订单检查
	 */
	public static final String START_ORDERCHECK = "orderCheck";
	/**
	 * 启动流程，读卡器设置
	 */
	public static final String START_CARDSET = "cardSet";
	
	/**
	 * 下一步流程前缀
	 */
	public static final String NEXT_STEP_PREFIX = "next-steup-";
	
	
}
