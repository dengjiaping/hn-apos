package me.andpay.apos.common.service;

import java.util.List;

import me.andpay.ac.term.api.txn.PurchaseRequest;
import me.andpay.ac.term.api.txn.TxnRequest;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.dao.ExceptionPayTxnInfoDao;
import me.andpay.apos.dao.ICCardInfoDao;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.dao.model.ICCardInfo;
import me.andpay.apos.dao.model.QueryExceptionPayTxnInfoCond;
import me.andpay.apos.dao.model.QueryICCardInfoCond;
import me.andpay.timobileframework.util.BeanUtils;

import com.google.inject.Inject;

public class ExceptionPayTxnInfoService {

	@Inject
	private ExceptionPayTxnInfoDao exceptionPayTxnInfoDao;

	@Inject
	private ICCardInfoDao icCardInfoDao;

	public void removeExceptionTxn(String termTraceNo, String termTxnTime) {
		QueryExceptionPayTxnInfoCond payTxnCond = new QueryExceptionPayTxnInfoCond();
		payTxnCond.setTermTraceNo(termTraceNo);
		payTxnCond.setTermTxnTime(termTxnTime);
		payTxnCond.setSorts("+idTxn");
		List<ExceptionPayTxnInfo> txns = exceptionPayTxnInfoDao.query(
				payTxnCond, 0, 1);
		if (txns.size() > 0) {
			ExceptionPayTxnInfo exceptionPayTxnInfo = txns.get(0);
			exceptionPayTxnInfoDao.delete(exceptionPayTxnInfo.getIdTxn());
			if (exceptionPayTxnInfo.getIsICCardTxn()) {

				QueryICCardInfoCond cond = new QueryICCardInfoCond();
				cond.setIdTxn(exceptionPayTxnInfo.getIdTxn());

				List<ICCardInfo> icCardInfos = icCardInfoDao.query(cond, 0, 1);
				if (icCardInfos.size() > 0) {
					icCardInfoDao.delete(icCardInfos.get(0).getIdICCardInfo());
				}
			}
		}

	}

	public ExceptionPayTxnInfo getExceptionTxn(String termTraceNo,
			String termTxnTime) {
		QueryExceptionPayTxnInfoCond payTxnCond = new QueryExceptionPayTxnInfoCond();
		payTxnCond.setTermTraceNo(termTraceNo);
		payTxnCond.setTermTxnTime(termTxnTime);
		payTxnCond.setSorts("+idTxn");
		List<ExceptionPayTxnInfo> txns = exceptionPayTxnInfoDao.query(
				payTxnCond, 0, 1);

		if (txns.size() > 0) {
			return txns.get(0);
		}
		return null;
	}

	public long payTxnInfoSnapshot(ExceptionPayTxnInfo exceptionPayTxnInfo,
			TxnRequest txnRequest) {

		exceptionPayTxnInfo.setIdTxn(null);
		if (txnRequest != null && txnRequest instanceof PurchaseRequest) {
			PurchaseRequest purchaseRequest = (PurchaseRequest) txnRequest;
			exceptionPayTxnInfo.setTrackAll(purchaseRequest.getTrackAll());
			exceptionPayTxnInfo.setTrackRandomFactor(purchaseRequest
					.getTrackRandomFactor());
		}
		exceptionPayTxnInfo
				.setExpcetionStatus(ExceptionPayTxnInfo.EXP_STATUS_WAIT_RETRY);
		return exceptionPayTxnInfoDao.insert(exceptionPayTxnInfo);

	}

	public Long payTxnInfoSnapshot(ExceptionPayTxnInfo exceptionPayTxnInfo,
			TxnRequest txnRequest, AposICCardDataInfo aposICCardDataInfo) {

		Long idTxn = this.payTxnInfoSnapshot(exceptionPayTxnInfo, txnRequest);

		if (exceptionPayTxnInfo.getIsICCardTxn()) {
			ICCardInfo icCardInfo = BeanUtils.copyProperties(ICCardInfo.class,
					aposICCardDataInfo);
			icCardInfo.setIdTxn(idTxn.intValue());
			icCardInfoDao.insert(icCardInfo);
		}

		return idTxn;

	}

	public void changeExceptionStatus(String termTraceNo, String termTxnTime,
			String status) {
		QueryExceptionPayTxnInfoCond payTxnCond = new QueryExceptionPayTxnInfoCond();
		payTxnCond.setTermTraceNo(termTraceNo);
		payTxnCond.setTermTxnTime(termTxnTime);
		payTxnCond.setSorts("+idTxn");
		List<ExceptionPayTxnInfo> txns = exceptionPayTxnInfoDao.query(
				payTxnCond, 0, 1);
		if (txns.size() > 0) {
			ExceptionPayTxnInfo exceptionPayTxnInfo = txns.get(0);
			exceptionPayTxnInfo.setExpcetionStatus(status);
			exceptionPayTxnInfoDao.update(exceptionPayTxnInfo);
		}
	}

}
