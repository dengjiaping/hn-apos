package me.andpay.apos.scm.callback.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import me.andpay.ac.term.api.rcs.PartyTxnAmtQuotaInfo;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.scm.activity.ScmPartyLimitActivity;
import me.andpay.apos.scm.callback.ScmPartyLimitCallBack;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

@CallBackHandler
public class ScmPartyLimitCallBackImpl implements ScmPartyLimitCallBack {

	private ScmPartyLimitActivity aLimitActivity;

	public ScmPartyLimitCallBackImpl(ScmPartyLimitActivity aLimitActivity) {
		this.aLimitActivity = aLimitActivity;
	}

	public void querySuccess(PartyTxnAmtQuotaInfo partyLimit) {

		boolean dataFlag = false;

		boolean showFlag1 = showLimit(
				partyLimit.getMonthlyCreditCardTxnAmtTotal(),
				partyLimit.getMonthlyCreditCardTxnAmtQuota(),
				aLimitActivity.useCreditMonthLimit,
				aLimitActivity.totalCreditMonthLimit,
				aLimitActivity.remainCreditMonthLimit,
				aLimitActivity.creditMonthLimitLv);
		dataFlag = sethasDataFlag(dataFlag, showFlag1);

		boolean showFlag2 = showLimit(partyLimit.getDailyTxnTotal(),
				partyLimit.getDailyTxnAmtQuota(), aLimitActivity.useDayLimit,
				aLimitActivity.totalDayLimit, aLimitActivity.remainDayLimit,
				aLimitActivity.dayLimitLv);
		dataFlag = sethasDataFlag(dataFlag, showFlag2);

		boolean showFlag3 = showLimit(partyLimit.getMonthlyTxnTotal(),
				partyLimit.getMonthlyTxnAmtQuota(),
				aLimitActivity.useMonthLimit, aLimitActivity.totalMonthLimit,
				aLimitActivity.remainMonthLimit, aLimitActivity.monthLimitLv);
		dataFlag = sethasDataFlag(dataFlag, showFlag3);

		boolean showFlag4 = showLimit(partyLimit.getDailyTxnAmtQuotaPerCc(),
				aLimitActivity.totalDailyTxnQuota,
				aLimitActivity.totalDailyTxnQuotaLv);
		dataFlag = sethasDataFlag(dataFlag, showFlag4);

		boolean showFlag5 = showLimit(partyLimit.getMonthlyTxnAmtQuotaPerCc(),
				aLimitActivity.totalMonthTxnQuota,
				aLimitActivity.totalMonthTxnQuotaLv);
		dataFlag = sethasDataFlag(dataFlag, showFlag5);

		if (!dataFlag) {
			aLimitActivity.showNoData();
		} else {
			aLimitActivity.hideProgess();
		}
	}

	public boolean sethasDataFlag(boolean dataFlag, boolean showFlag) {
		if (dataFlag) {
			return dataFlag;
		}
		return showFlag;
	}

	public boolean showLimit(BigDecimal quotaAmt, TextView quotaText,
			LinearLayout showLy) {

		if (quotaAmt != null && quotaAmt.compareTo(new BigDecimal("0")) > 0) {

			quotaText.setText(quotaAmt.toString());
			showLy.setVisibility(View.VISIBLE);
			return true;
		} else {
			showLy.setVisibility(View.GONE);
		}

		return false;
	}

	public boolean showLimit(BigDecimal totalAmt, BigDecimal quotaAmt,
			TextView usetText, TextView talolText, TextView remainText,
			LinearLayout showLy) {

		if (quotaAmt != null && quotaAmt.compareTo(new BigDecimal("0")) > 0) {

			DisplayMetrics dm = new DisplayMetrics();
			dm = aLimitActivity.getResources().getDisplayMetrics();

			talolText.setText("总额：" + quotaAmt);
			double scale = totalAmt.divide(quotaAmt, RoundingMode.DOWN)
					.doubleValue();

			Rect rect = new Rect();
			talolText.getPaint().getTextBounds(totalAmt.toString(), 0,
					totalAmt.toString().length(), rect);
			int minWidth = rect.width()
					+ Float.valueOf(12 * dm.density).intValue();

			int scaleWidth = Double.valueOf(dm.widthPixels * scale).intValue();
			if (scaleWidth < minWidth
					&& totalAmt.compareTo(new BigDecimal("0")) != 0) {
				scaleWidth = minWidth;
			}

			BigDecimal remainAmt = quotaAmt.subtract(totalAmt);

			talolText.getPaint().getTextBounds(remainText.toString(), 0,
					remainText.toString().length(), rect);
			int rightMinWidth = rect.width()
					+ Float.valueOf(12 * dm.density).intValue();
			int rightWidth = dm.widthPixels - scaleWidth;
			if (rightWidth < rightMinWidth
					&& quotaAmt.subtract(totalAmt).compareTo(
							new BigDecimal("0")) != 0) {
				rightWidth = rightMinWidth;
				scaleWidth = dm.widthPixels - rightMinWidth;
			}

			LayoutParams linearParams = usetText.getLayoutParams();
			linearParams.width = scaleWidth;
			usetText.setLayoutParams(linearParams);

			LayoutParams linearParams1 = remainText.getLayoutParams();
			linearParams1.width = rightWidth;
			remainText.setLayoutParams(linearParams1);

			usetText.setText(totalAmt.toString());
			remainText.setText(remainAmt.toString());

			showLy.setVisibility(View.VISIBLE);
			return true;
		} else {
			showLy.setVisibility(View.GONE);
		}

		return false;
	}

	public void queryError(String errorMsg) {

		final OperationDialog dialog = new OperationDialog(aLimitActivity,
				"提示", errorMsg);
		dialog.setCancelable(false);
		dialog.setCancelButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				aLimitActivity.finish();
			}
		});

		dialog.setSureButtonOnclickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				aLimitActivity.queryLimit();
			}
		});
		dialog.setCancelButtonName("返回");
		dialog.setSureButtonName("重试");
		dialog.show();

	}

}
