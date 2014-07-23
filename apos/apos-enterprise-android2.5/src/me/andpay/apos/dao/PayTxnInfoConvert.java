package me.andpay.apos.dao;

import java.math.BigDecimal;
import java.util.Date;

import me.andpay.ac.consts.AttachmentTypes;
import me.andpay.ac.consts.TxnFlags;
import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.QueryTxnCond;
import me.andpay.ac.term.api.txn.TxnRecord;
import me.andpay.apos.common.util.ItemConvertUtil;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.dao.model.QueryPayTxnInfoCond;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.ti.util.AttachmentItem;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.StringUtil;

public class PayTxnInfoConvert {

	public static PayTxnInfo convert(TxnRecord record) {
		PayTxnInfo info = new PayTxnInfo();
		info.setDeviceId(record.getDeviceId());
		info.setRefNo(record.getRefNo());
		info.setBankAuthNo(record.getAuthCode());
		info.setMemo(record.getMemo());
		info.setRespCode(record.getRespCode());
		info.setExTraceNO(record.getExtTraceNo());
		info.setTxnId(record.getTxnId());
		info.setLatitude(record.getLatitude());
		info.setLongitude(record.getLongitude());
		info.setTxnAddress(record.getLocation());
		info.setSpecCoordType(record.getSpecCoordType());
		info.setSpecLatitude(record.getSpecLatitude());
		info.setSpecLongitude(record.getSpecLongitude());
		
		if (record.getTermTxnTime() != null) {
			info.setTermTxnTime(DateUtil.format(
					TqmProvider.TQM_PARTTERN_COMMOM_DATE,
					record.getTermTxnTime()));
		}
//		info.setMemo(record.getAttribute1());
		if (record.getSalesAmt() != null)
			info.setSalesAmt(record.getSalesAmt().abs());
		info.setShortPan(TxnUtil.hidePan(record.getShortCardNo()));
		info.setCardOrg(record.getCardAssoc());
		info.setCardType(record.getCardTypeMessage());
		info.setIssuerId(record.getIssuerId());
		info.setIssuerName(record.getIssuerName());
		info.setTxnType(record.getTxnType());
		info.setTxnTypeDesc(record.getTxnTypeMessage());
		info.setTxnStatus(TxnFlagMapping.getStatusByFlag(record.getTxnFlag()));
		info.setTxnStatusDesc(record.getTxnFlagMessage());
		info.setTxnFlag(record.getTxnFlag());
		info.setTxnFlagMessage(record.getTxnFlagMessage());
		info.setOperNo(record.getUserName());
		info.setOperName(record.getPersonName());
		info.setTermTraceNo(record.getTermTraceNo());
		info.setMerchantId(record.getMerchantId());
		info.setMerchantName(record.getMerchantName());
		info.setTxnPartyId(record.getTxnPartyId());
		info.setTxnPartyName(record.getTxnPartyName());
		if (record.getRefundedAmt() != null) {
			info.setRefundAmt(record.getRefundedAmt().abs());
		}
		if ((record.getRefundedAmt() != null && record.getRefundedAmt().abs()
				.doubleValue() >= record.getSalesAmt().abs().doubleValue())
				|| TxnFlags.VOIDED.equalsIgnoreCase(record.getTxnFlag())
				|| !TxnTypes.PURCHASE.equals(record.getTxnType())) {
			info.setIsRefundEnable(false);
		} else {
			info.setIsRefundEnable(true);
		}
		if (record.getTxnTime() != null) {
			info.setTxnTime(StringUtil.format("yyyyMMddHHmmss",
					record.getTxnTime()));
		}
		if (record.getAttachmentItems() != null) {
			for (AttachmentItem item : record.getAttachmentItems()) {
				if (AttachmentTypes.PRODUCT_PICTURE.equals(item
						.getAttachmentType())) {
					info.setTranPic(ItemConvertUtil.appendItemId(
							info.getTranPic(), item.getIdUnderType()));
				}
				if (AttachmentTypes.SIGNATURE_PICTURE.equals(item
						.getAttachmentType())) {
					info.setSignPic(ItemConvertUtil.appendItemId(
							info.getSignPic(), item.getIdUnderType()));
				}
			}
		}
		info.setTermBatchNo(record.getTermBatchNo() != null ? String
				.valueOf(record.getTermBatchNo()) : null);
		info.setUpdateTime(StringUtil.format("yyyyMMddHHmmss",
				new Date()));
		return info;
	}

	public static QueryPayTxnInfoCond convertCondtion2Cond(
			QueryConditionForm condition) {
		QueryPayTxnInfoCond cond = new QueryPayTxnInfoCond();
		if (!StringUtil.isEmpty(condition.getStatus()))
			cond.setTxnStatus(condition.getStatus());
		if (!StringUtil.isEmpty(condition.getTxnId())) {
			cond.setTxnId(condition.getTxnId());
		}
		if (!StringUtil.isEmpty(condition.getOrderno()))
			cond.setExTraceNO(condition.getOrderno());

		if (!StringUtil.isEmpty(condition.getAmount())) {
			cond.setSalesAmt(new BigDecimal(condition.getAmount()));
		}
		if (!StringUtil.isEmpty(condition.getMaxTxnId()))
			cond.setMaxTxnId(condition.getMaxTxnId());
		if (!StringUtil.isEmpty(condition.getMinTxnId()))
			cond.setMinTxnId(condition.getMinTxnId());
		if (!StringUtil.isEmpty(condition.getTxnType()))
			cond.setTxnType(condition.getTxnType());
		if (!StringUtil.isEmpty(condition.getCardno())) {
			cond.setShortPan(TxnUtil.hidePan(condition.getCardno()));
		}
		if (condition.isRefundEnableFlag() != null) {
			cond.setIsRefundEnable(condition.isRefundEnableFlag());
		}
		cond.setBeginTermTxnTime(getTimeStr(condition.getBeginDate(),
				condition.getBeginTime()));
		cond.setEndTermTxnTime(getTimeStr(condition.getEndDate(),
				condition.getEndTime()));
		cond.setOperNo(condition.getUserId());
		cond.setTxnPartyId(condition.getTxnPartyId());
		cond.setSorts("-txnId");
		return cond;
	}

	public static QueryTxnCond convertCondtion2RemoteCond(
			QueryConditionForm condition) {
		QueryTxnCond cond = new QueryTxnCond();
//		if (!StringUtil.isEmpty(condition.getStatus())) {
//			cond.setTxnFlags(TxnFlagMapping.getFlagsByStatus(
//					condition.getStatus(), condition.isRefundEnableFlag()));
//		}
//		
//		cond.setHasRefundableAmount(hasRefundableAmount)
		if(condition.isRefundEnableFlag()!=null && condition.isRefundEnableFlag()) {
			cond.setHasRefundableAmount(true);
			cond.setTxnFlags(TxnFlagMapping.getSuccessFlags());
		}else {
			cond.setTxnFlags(TxnFlagMapping.getFlagsByStatus(condition.getStatus()));
		}
		if (!StringUtil.isEmpty(condition.getMaxTxnId()))
			cond.setMaxTxnId(condition.getMaxTxnId());
		if (!StringUtil.isEmpty(condition.getMinTxnId()))
			cond.setMinTxnId(condition.getMinTxnId());
		if (!StringUtil.isEmpty(condition.getTxnId()))
			cond.setTxnId(condition.getTxnId());
		if (!StringUtil.isEmpty(condition.getAmount())) {
			cond.setSalesAmt(new BigDecimal(condition.getAmount()));
		}
		if (!StringUtil.isEmpty(condition.getOrderno())) {
			cond.setExtTraceNo(condition.getOrderno());
		}
	
		cond.setStartTxnTime(getTime(condition.getBeginDate(),
				condition.getBeginTime()));
		if (!StringUtil.isEmpty(condition.getTxnType())) {
			cond.setTxnType(condition.getTxnType());
		}
		if (!StringUtil.isEmpty(condition.getCardno())) {
			cond.setCardNo(condition.getCardno());
		}
		cond.setEndTxnTime(getTime(condition.getEndDate(),
				condition.getEndTime()));
		cond.setSettleOrderId(condition.getSettleOrderId());
		return cond;
	}

	private static Date getTime(String date, String time) {
		if (StringUtil.isEmpty(date)) {
			return null;
		}
		if (StringUtil.isEmpty(time)) {
			return DateUtil.parse(TqmProvider.TQM_PARTTERN_DATE, date);
		}
		return DateUtil.parse(TqmProvider.TQM_PARTTERN_DATETIME, date + " "
				+ time);
	}

	private static String getTimeStr(String dateStr, String timeStr) {
		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}
		Date date = null;
		if (StringUtil.isEmpty(timeStr)) {
			date = DateUtil.parse(TqmProvider.TQM_PARTTERN_DATE, dateStr);
		} else {
			date = DateUtil.parse(TqmProvider.TQM_PARTTERN_DATETIME, dateStr
					+ " " + timeStr);
		}
		return DateUtil.format(TqmProvider.TQM_PARTTERN_COMMOM_DATE, date);
	}

}
