package me.andpay.apos.lam.callback.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.auth.LoginResponse;
import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.ExceptionPayTxnInfoDao;
import me.andpay.apos.dao.ICCardInfoDao;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.dao.model.QueryExceptionPayTxnInfoCond;
import me.andpay.apos.dao.model.QueryPayTxnInfoCond;
import me.andpay.apos.mopm.callback.impl.OrderPayUtil;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.cache.TiImageCacheManager;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.RoboGuiceUtil;
import me.andpay.timobileframework.util.StringConvertor;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginCallBackHelper {

	public static boolean passwordExpire(LoginResponse response,
			TiActivity activity) {
		
		final TiActivity activityFinal = activity;
		if (LoginResponse.NEXT_STEP_NORMAL.equals(response.getNextStep())) {
			//正常登录流程
			return false;
//		}else if(LoginResponse.NEXT_STEP_CHANGE_PWD.equals(response.getNextStep())){
//			//初始密码或过期密码，必须修改
//			String msg = ResourceUtil.getString(activity,
//					R.string.lam_your_password_must_change_str);
//			final PromptDialog promptDialog = new PromptDialog(activity,
//					ResourceUtil.getString(activity,
//							R.string.tam_show_dialog_title_str), msg);
//			promptDialog.setSureButtonOnclickListener(new OnClickListener() {
//						public void onClick(View v) {
//							promptDialog.dismiss();
//							gotToModifyPassword(activityFinal);
//						}
//					});
//			promptDialog.setCancelable(false);
//			promptDialog.show();
//			return true;
//		}else if(LoginResponse.NEXT_STEP_NOTIFY_PWD_EXPIRE_DATE.equals(response.getNextStep())){
//			long lessDate = response.getPasswordValidDays();
//			// 密码90天过期后必须修改，过期前15天开始提示修改
//			if (lessDate < 16) {
//				String msg = "";
//				if (lessDate == 0) {
//					msg = ResourceUtil.getString(activity,
//							R.string.lam_your_password_past_due_str);
//					final PromptDialog promptDialog = new PromptDialog(activity,
//							ResourceUtil.getString(activity,
//									R.string.tam_show_dialog_title_str), msg);
//					promptDialog.setSureButtonOnclickListener(new OnClickListener() {
//								public void onClick(View v) {
//									promptDialog.dismiss();
//									gotToModifyPassword(activityFinal);
//								}
//							});
//					promptDialog.setCancelable(false);
//					promptDialog.show();
//				} else {
//					msg = ResourceUtil.getString(activity,
//							R.string.lam_your_password_has_str)
//							+ lessDate
//							+ ResourceUtil.getString(activity,
//									R.string.lam_must_past_due_str);
//					final OperationDialog dialog = new OperationDialog(activity,
//							ResourceUtil.getString(activity,
//									R.string.tam_show_dialog_title_str), msg);
//					dialog.setSureButtonOnclickListener(new OnClickListener() {
//						public void onClick(View v) {
//							dialog.dismiss();
//							gotToModifyPassword(activityFinal);
//						}
//					});
//					dialog.setCancelButtonOnclickListener(new OnClickListener() {
//						public void onClick(View v) {
//							dialog.dismiss();
//							nextPage(activityFinal);
//						}
//					});
//					dialog.setCancelable(false);
//					dialog.show();
//	
//				}
//				return true;
//			}
		}
		return false;

	}

	public static void nextPage(TiActivity activity) {
		activity.getAppContext().removeAttribute(RuntimeAttrNames.OLD_PASSWORD);
		// 读卡器设置
		String cardReadType = (String) activity.getAppConfig().getAttribute(
				ConfigAttrNames.CARD_READER_TYPE);
		if (StringUtil.isBlank(cardReadType)) {
			TiFlowControlImpl.instanceControl().nextSetup(activity,
					FlowConstants.START_CARDSET);
			return;
		}
		// 直接进入主页
		goHome(activity);
	}

	/**
	 * 进入密码修改页面
	 * 
	 * @param activity
	 */
	public static void gotToModifyPassword(TiActivity activity) {

		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.START_CHANGEPWD);

	}

	/**
	 * 初始化图片缓存配置
	 * 
	 * @param activity
	 */
	public static void configImagetCache(TiActivity activity) {
		TiImageCacheManager manager = TiImageCacheManager.getImageCacheService(
				activity.getApplicationContext(),
				TiImageCacheManager.MODE_LEAST_RECENTLY_USED, activity
						.getApplicationContext().getApplicationInfo().name,
				null);
		activity.getAppContext().setAttribute(
				RuntimeAttrNames.IMAGE_CACHE_MANAGE, manager);
	}
	
	private static PayTxnInfo queryTxnInfo(ExceptionPayTxnInfo expInfo, TiActivity activity) {
		
		PayTxnInfoDao payTxnInfoDao = RoboGuiceUtil
				.getInjectObject(PayTxnInfoDao.class,
						activity.getApplicationContext());
		
		QueryPayTxnInfoCond cond = new QueryPayTxnInfoCond();
		cond.setTermTraceNo(expInfo.getTermTraceNo());
		cond.setTermTxnTime(expInfo.getTermTxnTime());
		List<PayTxnInfo> payList = payTxnInfoDao.query(cond, 0, 1);
		PayTxnInfo payTxnInfo =  null;
		if(payList.size()>0) {
			payTxnInfo = payList.get(0);
		}
		
		return payTxnInfo;
	}

	/**
	 * 恢复交易视图
	 * 
	 * @param response
	 * @param activity
	 * @return
	 */
	public static boolean recoverTxn(LoginResponse response,
			final TiActivity activity, final boolean isShowDialog) {
		ExceptionPayTxnInfoDao exceptionPayTxnInfoDao = RoboGuiceUtil
				.getInjectObject(ExceptionPayTxnInfoDao.class,
						activity.getApplicationContext());
		PayTxnInfoDao payTxnInfoDao = RoboGuiceUtil
				.getInjectObject(PayTxnInfoDao.class,
						activity.getApplicationContext());
		
		if (exceptionPayTxnInfoDao == null || payTxnInfoDao == null) {
			return false;
		}
		
		PartyInfo partyInfo = (PartyInfo) activity.getAppContext()
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		

		QueryExceptionPayTxnInfoCond cond = new QueryExceptionPayTxnInfoCond();
		Set<String> statuses = new HashSet<String>();
		statuses.add(ExceptionPayTxnInfo.EXP_STATUS_WAIT_RETRY);
		statuses.add(ExceptionPayTxnInfo.EXP_STATUS_WAIT_SIGN);
		cond.setExpcetionStatuses(statuses);
		cond.setTxnPartyId(partyInfo.getPartyId());
		
		List<ExceptionPayTxnInfo> exceptionPayTxnInfos = exceptionPayTxnInfoDao
				.query(cond, 0, -1);
		if (exceptionPayTxnInfos != null && exceptionPayTxnInfos.size() > 0) {

			final ExceptionPayTxnInfo expInfo = exceptionPayTxnInfos.get(0);
			
			//IC卡交易，非等待签名的，直接发送冲正
			if(expInfo.getIsICCardTxn()&&!expInfo.getExpcetionStatus().equals(ExceptionPayTxnInfo.EXP_STATUS_WAIT_SIGN)) {
				//IC卡交易直接冲正
				expInfo.setExpcetionStatus(ExceptionPayTxnInfo.EXP_STATUS_WAIT_REVERSE);
				exceptionPayTxnInfoDao.update(expInfo);
				
				//原交易跟新失败
				PayTxnInfo payTxnInfo = queryTxnInfo(expInfo, activity);
				if(partyInfo != null) {
					payTxnInfo.setTxnStatus(PayTxnInfoStatus.STATUS_FAIL);
					payTxnInfo.setTxnFlagMessage("失败");
					payTxnInfoDao.update(payTxnInfo);
				}
				
			}else {
				
			
				
				// 启动异常交易
				TxnContext txnContext = new TxnContext();
				txnContext.setTxnType(expInfo.getTxnType());
				txnContext.setSalesAmt(expInfo.getSalesAmt());
				txnContext.setHasNewTxnButton(true);
				TxnActionResponse txnActionResponse = new TxnActionResponse();
				txnContext.setTxnActionResponse(txnActionResponse);
				txnActionResponse.setTxnAddress(expInfo.getTxnAddress());
				txnContext.setIcCardTxn(expInfo.getIsICCardTxn());
				txnActionResponse.setExPayInfoIdTxn(expInfo.getIdTxn());
				
				String txnMsg = "";
				String outButtonName = "";
				String buttonName = "";
				if (TxnTypes.PURCHASE.equals(txnContext.getTxnType())) {
					txnActionResponse.setTermTraceNo(expInfo.getTermTraceNo());
					txnActionResponse.setTermTxnTime(expInfo.getTermTxnTime());
					txnMsg = "消费";
					outButtonName = "撤销交易";
				} else {
					txnActionResponse.setTermTraceNo(expInfo.getTermTraceNo());
					txnActionResponse.setTermTxnTime(expInfo.getTermTxnTime());
					txnContext.setOrigTxnId(expInfo.getOrigTxnId());
					txnMsg = "退款";
					outButtonName = "退出交易";
				}
				
				String statusMsg = "";
				String title = null;
				if(expInfo.getExpcetionStatus().equals(ExceptionPayTxnInfo.EXP_STATUS_WAIT_RETRY)) {
					statusMsg = "未完成";
					buttonName = "完成交易";
				}else if (expInfo.getExpcetionStatus().equals(ExceptionPayTxnInfo.EXP_STATUS_WAIT_SIGN)) {
					statusMsg = "未完成签名";
					buttonName = "完成签名";
					title = "签名补入";

				}
				
				String promptMsg = "您在"
						+ StringUtil.format(
								"yyyy-MM-dd HH:mm:ss",
								StringUtil.parseToDate("yyyyMMddHHmmss",
										expInfo.getTermTxnTime())) + "有一笔金额为"
						+ StringConvertor.convert2Currency(expInfo.getSalesAmt())
						+ "的" + txnMsg + "交易"+statusMsg+",请重新完成交易。";

				Map<String, String> intentData = new HashMap<String, String>();
				intentData.put("message", promptMsg);
				intentData.put("outButtonName", outButtonName);
				intentData.put("buttonName", buttonName);
				intentData.put("title", title);
				intentData.put("exceptionStatus", expInfo.getExpcetionStatus());
				intentData.put("isRecover", Boolean.valueOf(true).toString());

				TiFlowControlImpl.instanceControl().startFlow(activity,
						FlowNames.COM_TXN_RECOVER_FLOW, intentData);
				TiFlowControlImpl.instanceControl().setFlowContextData(txnContext);
				
				return true;
			}
		}

		return false;
	}

	public static void goHome(TiActivity activity) {

		if (OrderPayUtil.isOrderPay(activity.getAppContext())) {
			OrderPayUtil.submitTxn(activity, RoboGuiceUtil.getInjectObject(TxnControl.class, activity));
			//OrderPayUtil.goToOrderCheck(activity);
			return;
		}

		TiFlowControlImpl.instanceControl().nextSetup(activity,
				FlowConstants.GOHOME);
	}
}
