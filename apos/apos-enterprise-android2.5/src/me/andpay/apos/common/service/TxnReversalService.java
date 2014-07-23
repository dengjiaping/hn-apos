package me.andpay.apos.common.service;

import java.math.BigDecimal;
import java.util.List;

import me.andpay.ac.consts.ProductCodes;
import me.andpay.ac.term.api.txn.ReverseTxnRequest;
import me.andpay.ac.term.api.txn.TxnService;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.dao.ExceptionPayTxnInfoDao;
import me.andpay.apos.dao.ICCardInfoDao;
import me.andpay.apos.dao.model.ExceptionPayTxnInfo;
import me.andpay.apos.dao.model.ICCardInfo;
import me.andpay.apos.dao.model.QueryExceptionPayTxnInfoCond;
import me.andpay.apos.dao.model.QueryICCardInfoCond;
import me.andpay.apos.tam.action.txn.TxnHelper;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.support.TiApplication;
import me.andpay.timobileframework.util.BeanUtils;
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

/**
 * 交易冲正处理
 * 
 * @author cpz
 * 
 */
public class TxnReversalService {

	public TxnService txnService;

	@Inject
	public ExceptionPayTxnInfoDao exceptionPayTxnInfoDao;
	@Inject
	public ICCardInfoDao icCardInfoDao;

	@Inject
	private Application application;

	private List<ExceptionPayTxnInfo> queryPayTxnInfos() {
		TiContext tiContext = ((TiApplication) application)
				.getContextProvider().provider(
						TiContext.CONTEXT_SCOPE_APPLICATION);

		PartyInfo partyInfo = (PartyInfo) tiContext
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		if (partyInfo == null) {
			return null;
		}

		// 只冲当前商户的
		QueryExceptionPayTxnInfoCond cond = new QueryExceptionPayTxnInfoCond();
		cond.setTxnPartyId(partyInfo.getPartyId());
		cond.setExpcetionStatus(ExceptionPayTxnInfo.EXP_STATUS_WAIT_REVERSE);
		List<ExceptionPayTxnInfo> payTxnInfos = exceptionPayTxnInfoDao.query(
				cond, 0, -1);
		return payTxnInfos;
	}

	public void statrtReversal() {
		Thread thread = new Thread(new ReversalRunner());
		thread.start();
	}

	public class ReversalRunner implements Runnable {

		public void run() {
			try {
				reversalTxn();
			} catch (Throwable e) {
				Log.e(this.getClass().getName(), "interrupt reversal!");

			}
		}
	}

	public void reversalTxn() {
		try {
			Log.e(this.getClass().getName(), "start reversal!");
			List<ExceptionPayTxnInfo> payTxnInfos = queryPayTxnInfos();
			if (payTxnInfos == null || payTxnInfos.isEmpty()) {
				Log.e(this.getClass().getName(), "noData reversal!");
				return;
			}

			for (ExceptionPayTxnInfo payTxnInfo : payTxnInfos) {

				ReverseTxnRequest request = new ReverseTxnRequest();
				request.setSalesAmt(new BigDecimal(payTxnInfo.getSalesAmt()
						.toString()));
				request.setSalesCur(payTxnInfo.getSalesCur());
				request.setTermTraceNo(payTxnInfo.getTermTraceNo());
				request.setProductCode(ProductCodes.APOS_ACQUIRE);
				request.setTrackAll(payTxnInfo.getTrackAll());
				request.setTrackRandomFactor(payTxnInfo.getTrackRandomFactor());
				request.setKsn(payTxnInfo.getKsn());
				if (StringUtil.isNotBlank(payTxnInfo.getTermTxnTime())) {
					request.setTermTxnTime(StringUtil.parseToDate(
							"yyyyMMddHHmmss", payTxnInfo.getTermTxnTime()));
				}

				ICCardInfo icCardInfo = null;
				if (payTxnInfo.getIsICCardTxn()) {
					QueryICCardInfoCond cond = new QueryICCardInfoCond();
					cond.setIdTxn(payTxnInfo.getIdTxn());
					List<ICCardInfo> icCardInfos = icCardInfoDao.query(cond, 0,
							1);
					if (icCardInfos.size() == 1) {
						icCardInfo = icCardInfos.get(0);
						TxnHelper.setICCardInfo(BeanUtils.copyProperties(
								AposICCardDataInfo.class, icCardInfo), request);
					}
				}

				txnService.syncReverse(request);
				exceptionPayTxnInfoDao.delete(payTxnInfo.getIdTxn());
				if (icCardInfo != null) {
					icCardInfoDao.delete(icCardInfo.getIdICCardInfo());
				}

			}

		} catch (Throwable e) {
			// 一般是网络异常
			Log.e(this.getClass().getName(), "reverse error!", e);
		}
	}
}
