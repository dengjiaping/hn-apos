package me.andpay.apos.tam.action.txn.cloud;

import me.andpay.ac.consts.ProductCodes;
import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.PurchaseRequest;
import me.andpay.ac.term.api.txn.order.CloudOrderApply;
import me.andpay.apos.cdriver.CardReaderTypes;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.tam.form.TxnForm;
import me.andpay.timobileframework.mvc.context.TiContext;
import android.app.Activity;

/**
 * 云订单工具类
 * 
 * @author tinyliu
 * 
 */
public class CloudPosUtil {
	/**
	 * 转换消费请求为云订单申请请求
	 * 
	 * @param txnForm
	 * @param txnRequest
	 * @return
	 */
	public static CloudOrderApply convert2CloudRequest(TxnForm txnForm,
			PurchaseRequest txnRequest) {
		CloudOrderApply apply = new CloudOrderApply();
		apply.setAttachmentTypes(txnRequest.getAttachmentTypes());
		apply.setTraceNo(txnRequest.getTermTraceNo());
		apply.setReceiptNo(txnRequest.getReceiptNo());
		apply.setMemo(txnRequest.getMemo());
		apply.setExtTraceNo(txnRequest.getExtTraceNo());
		apply.setSalesAmt(txnRequest.getSalesAmt());
		apply.setTxnType(txnRequest.getTxnType());
		apply.setSalesCur(txnRequest.getSalesCur());
		apply.setProductCode(ProductCodes.APOS_ACQUIRE);
		return apply;
	}

	/**
	 * 判断是否云pos是否支持该交易类型
	 * 
	 * @param activity
	 * @param cardReaderType
	 * @param txnTypes
	 * @return
	 */
	public static boolean isAllowTxn(Activity activity, int cardReaderType,
			String txnTypes) {
		if (!(CardReaderTypes.CLOUD_POS == cardReaderType)) {
			return true;
		}
		if (!TxnTypes.INQUIRY_BALANCE.equals(txnTypes)) {
			return true;
		}
		/*
		 * PromptDialog dialog = new PromptDialog(activity, null,
		 * ResourceUtil.getString( activity,
		 * R.string.tam_cloud_txntype_error_str)); dialog.show();
		 */
		return false;
	}

	/**
	 * 判断是否是云POS类型
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isCloudPosCardReader(AposBaseActivity activity) {
		return isCloudPosCardReader(activity.getAppConfig());
	}

	/**
	 * 判断是否是云POS类型
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isCloudPosCardReader(TiContext appConfig) {
		String cardReaderType = (String) appConfig
				.getAttribute(ConfigAttrNames.CARD_READER_TYPE);
		if (CardReaderTypes.CLOUD_POS == Integer.parseInt(cardReaderType)) {
			return true;
		}
		return false;
	}
}
