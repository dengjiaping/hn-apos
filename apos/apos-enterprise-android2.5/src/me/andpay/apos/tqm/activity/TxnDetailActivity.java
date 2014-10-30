package me.andpay.apos.tqm.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;

import me.andpay.ac.consts.AttachmentTypes;
import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.base.FlexFieldDefine;
import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.TiNetImageView;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.common.util.ItemConvertUtil;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.dao.TxnFlagMapping;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.dao.model.QueryPayTxnInfoCond;
import me.andpay.apos.tam.CardOrgImageMap;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.event.FillSignController;
import me.andpay.apos.tqm.event.MapLayoutOnclickController;
import me.andpay.apos.tqm.event.RePostVoucherOnclickController;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.apos.trm.event.RefundButtonClickController;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.cache.TiImageCacheManager.TiNetImageHttpStatusListener;
import me.andpay.timobileframework.flow.TiFlowSubFinishAware;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.util.DialogUtil;
import me.andpay.timobileframework.util.StringConvertor;

import org.apache.http.HttpStatus;

import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 交易详细查询
 * 
 * @author tinyliu
 * 
 */
@ContentView(R.layout.tqm_txn_detail_layout)
public class TxnDetailActivity extends AposBaseActivity implements
		TiFlowSubFinishAware {

	@InjectView(R.id.com_net_error_layout)
	View netErrorLayout;

	@InjectView(R.id.com_txn_detail_layout)
	View txnDetailLayout;

	String REFNO_KEY = "txnId";

	String INFO_KEY = "payTxnInfo";
	/**
	 * 动态字段******************************************
	 */
	@InjectView(R.id.tqm_txn_detail_orderid_title_tv)
	TextView orderidTitle;

	@InjectView(R.id.tqm_txn_detail_memo_title_tv)
	TextView memoTitle;

	@InjectView(R.id.tqm_txn_detail_amount_title_tv)
	TextView amountTitle;
	/**
	 * ************************************************
	 */
	@InjectView(R.id.tqm_txn_detail_txnflag_tv)
	TextView txnFlag;

	@InjectView(R.id.tqm_txn_detail_orderid_tv)
	TextView orderid;

	// @InjectView(R.id.tqm_txn_detail_merchantName_tv)
	// TextView merchantName;

	@InjectView(R.id.tqm_txn_detail_merchantNo_tv)
	TextView merchantNo;

	@InjectView(R.id.tqm_txn_detail_deviceid_tv)
	TextView deviceid;

	@InjectView(R.id.tqm_txn_detail_cardNo_tv)
	TextView cardno;

	@InjectView(R.id.tqm_txn_detail_cardOrg_img)
	ImageView cardOrg;

	@InjectView(R.id.tqm_txn_detail_cardtype_tv)
	TextView cardType;;

	@InjectView(R.id.tqm_txn_detail_txnType_tv)
	TextView txnType;

	@InjectView(R.id.tqm_txn_detail_txnTime_tv)
	TextView txnTime;

	@InjectView(R.id.tqm_txn_detail_authno_tv)
	TextView authNo;

	@InjectView(R.id.tqm_txn_detail_refno_tv)
	TextView refNo;

	@InjectView(R.id.tqm_txn_detail_traceno_tv)
	TextView traceNo;

	@InjectView(R.id.tqm_txn_detail_batchno_tv)
	TextView batchNo;

	@InjectView(R.id.tqm_txn_detail_amount_tv)
	TextView amount;

	@InjectView(R.id.tqm_txn_detail_refund_amount_tv)
	TextView refundAmount;

	@InjectView(R.id.tqm_txn_detail_location_tv)
	TextView location;

	@InjectView(R.id.tqm_txn_detail_memo_tv)
	TextView memo;

	@InjectView(R.id.tqm_txn_detail_transpic_img)
	ImageView transImg;

	@InjectView(R.id.tqm_txn_detail_signpic_img)
	ImageView signImg;

	@InjectView(R.id.tqm_txn_detail_location_img)
	ImageView locationImg;

	@InjectView(R.id.tqm_txn_detail_signpic_layout)
	View signPic_layout;

	@InjectView(R.id.tqm_txn_detail_signpic_dotted_layout)
	View signPic_dotted_layout;

	@InjectView(R.id.tqm_txn_detail_location_layout)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = MapLayoutOnclickController.class)
	RelativeLayout location_layout;

	@InjectView(R.id.tqm_txn_detail_location_dotted_layout)
	View location_dotted_layout;

	@InjectView(R.id.tqm_txn_detail_transpic_layout)
	View transPic_layout;

	@InjectView(R.id.tqm_txn_detail_transpic_dotted_layout)
	View transPic_dotted_layout;

	@InjectView(R.id.tqm_txn_refund_amount_layout)
	View refund_amount_layout;

	@InjectView(R.id.tqm_txn_refund_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = RefundButtonClickController.class)
	Button refund_button;

	@InjectView(R.id.tqm_txn_list_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	ImageView backBtn;

	@InjectView(R.id.tqm_repostvoucher_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = RePostVoucherOnclickController.class)
	private Button postVoucherBtn;

	@InjectView(R.id.tqm_fill_sign_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = FillSignController.class)
	private Button fillSignBtn;

	@InjectResource(R.string.com_time_str)
	private String dateStr;

	@InjectResource(R.string.com_progress_prompt_str)
	private String operationStr;

	public PayTxnInfo txnInfo;

	@Inject
	DynamicFieldHelper fieldHelper;

	TiNetImageView signNetImg;

	TiNetImageView transNetImg;
	@Inject
	private PayTxnInfoDao payTxnInfoDao;

	@Inject
	public TxnControl txnControl;

	@Inject
	public TiRpcClient tiRpcClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showDetailView();
		byte[] infoBytes = this.getIntent().getByteArrayExtra(INFO_KEY);
		boolean refundFlag = this.getIntent().getExtras()
				.getBoolean("isRefundQuery");
		if (infoBytes != null) {
			txnInfo = JacksonSerializer.newPrettySerializer().deserialize(
					PayTxnInfo.class, infoBytes);
			/**
			 * 云订单需要跟服务端进行数据同步
			 */
			if (txnInfo.getIsCloudOrder() == null || !txnInfo.getIsCloudOrder()) {
				initDetailView(refundFlag);
			} else {
				queryTxnDetail(txnInfo.getTxnId(), false);
			}
			return;
		}

		String txnId = this.getIntent().getExtras().getString(REFNO_KEY);
		queryTxnDetail(txnId, refundFlag);
	}

	protected void queryTxnDetail(String txnId, boolean refundFlag) {
		if (StringUtil.isEmpty(txnId)) {
			return;
		}
		CommonDialog dialog = new CommonDialog(this, operationStr);
		DialogUtil.setDialogAllowClose(dialog.getDialog(), false);
		EventRequest request = this.generateSubmitRequest(this);
		QueryConditionForm cond = new QueryConditionForm();
		cond.setTxnId(txnId);
		request.getSubmitData().put("queryConditionForm", cond);
		request.open(TqmProvider.TQM_DOMAIN_QUERY_REMOTE,
				TqmProvider.TQM_ACTION_QUERY_REMOTE_SINGETXN_REMOTE,
				Pattern.async);
		request.callBack(new QueryPayTxnInfoAfterCallback(this, refundFlag,
				txnId, dialog));
		request.submit();
		dialog.show();

	}

	/**
	 * 子流程返回重新刷新签名
	 */
	public void subFlowFinished(Map<String, Serializable> subFlowContext) {
		showSignPic();
	}

	protected void showNetError() {
		this.txnDetailLayout.setVisibility(View.GONE);
		this.netErrorLayout.setVisibility(View.VISIBLE);
	}

	protected void showDetailView() {
		this.txnDetailLayout.setVisibility(View.VISIBLE);
		this.netErrorLayout.setVisibility(View.GONE);
	}

	private void initDetailView(boolean refundFlag) {
		showDetailView();
		setDynamicFieldTitle();

		if (refundFlag) {
			refund_button.setVisibility(View.GONE);
			if (txnInfo.getRefundAmt() != null
					&& txnInfo.getSalesAmt().subtract(txnInfo.getRefundAmt())
							.compareTo(BigDecimal.ZERO) > 0
					&& TxnFlagMapping.getSuccessFlags().contains(
							txnInfo.getTxnFlag())) {
				refund_button.setVisibility(View.VISIBLE);
				postVoucherBtn.setVisibility(View.GONE);
			}
		} else {
			refund_button.setVisibility(View.GONE);

			if (PayTxnInfoStatus.STATUS_SUCCESS.equals(txnInfo.getTxnStatus())
					&& TxnTypes.PURCHASE.equals(txnInfo.getTxnType())) {
				postVoucherBtn.setVisibility(View.VISIBLE);
			} else {
				postVoucherBtn.setVisibility(View.GONE);
			}

		}
		if (TxnTypes.PURCHASE.equals(txnInfo.getTxnType())
				&& txnInfo.getRefundAmt() != null
				&& txnInfo.getRefundAmt().compareTo(BigDecimal.ZERO) > 0) {
			refund_amount_layout.setVisibility(View.VISIBLE);
			refundAmount.setText(StringConvertor.convert2Currency(txnInfo
					.getRefundAmt()));

		} else {

			refund_amount_layout.setVisibility(View.GONE);
		}
		// this.merchantName.setText(StringUtil.nullAsEmpty(txnInfo.getMerchantName()));
		this.txnFlag
				.setText(StringUtil.nullAsEmpty(txnInfo.getTxnFlagMessage()));
		this.merchantNo
				.setText(StringUtil.nullAsEmpty(txnInfo.getMerchantId()));
		this.orderid.setText(StringUtil.nullAsEmpty(txnInfo.getExTraceNO()));
		this.amount.setText(StringConvertor.convert2Currency(txnInfo
				.getSalesAmt()));
		if (TxnTypes.PURCHASE.equals(txnInfo.getTxnType())) {
			amount.setTextColor(getResources().getColor(
					R.color.tqm_list_item_amount_col));
		} else {
			amount.setTextColor(getResources().getColor(
					R.color.com_title_red_color));

		}
		this.authNo.setText(StringUtil.nullAsEmpty(txnInfo.getBankAuthNo()));
		this.batchNo.setText(StringUtil.nullAsEmpty(txnInfo.getTermBatchNo()));
		this.cardType.setText(StringUtil.nullAsEmpty(txnInfo.getIssuerName()));
		this.cardno.setText(StringUtil.nullAsEmpty(txnInfo.getShortPan()));

		this.location.setText(txnInfo.getTxnAddress());
		if (StringUtil.isBlank(txnInfo.getSpecCoordType())) {
			location_layout.setVisibility(View.GONE);
			location_dotted_layout.setVisibility(View.GONE);
		}
		this.traceNo.setText(StringUtil.nullAsEmpty(txnInfo.getTermTraceNo()));
		this.refNo.setText(StringUtil.nullAsEmpty(txnInfo.getTxnId()));
		this.txnType.setText(StringUtil.nullAsEmpty(txnInfo.getTxnTypeDesc()));
		if (txnInfo.getTermTxnTime() != null) {
			this.txnTime
					.setText(String.format(
							dateStr,
							DateUtil.parse("yyyyMMddHHmmss",
									txnInfo.getTermTxnTime())));
		}
		this.memo.setText(txnInfo.getMemo());
		this.deviceid.setText(txnInfo.getDeviceId());
		this.cardOrg.setImageResource(CardOrgImageMap.getId(txnInfo
				.getCardOrg()));
		if (StringUtil.isEmpty(txnInfo.getTranPic())) {
			transPic_layout.setVisibility(View.GONE);
			transPic_dotted_layout.setVisibility(View.GONE);
			transNetImg = null;
		} else {
			transPic_layout.setVisibility(View.VISIBLE);
			transPic_dotted_layout.setVisibility(View.VISIBLE);

			transNetImg = new TiNetImageView(this, transImg);
			transNetImg.setNetUrl(ItemConvertUtil.getFileItemsUrl(
					tiRpcClient.getUploadUrl(), txnInfo.getTranPic(),
					AttachmentTypes.PRODUCT_PICTURE).get(0));

		}
		/**
		 * 增加判断，只要是退货交易都没有签名图片
		 */
		showSignPic();

		setflashListFlag(txnInfo);

	}

	private void setflashListFlag(PayTxnInfo resultInfo) {
		QueryPayTxnInfoCond cond = new QueryPayTxnInfoCond();
		cond.setTxnId(resultInfo.getTxnId());
		List<PayTxnInfo> paInfos = payTxnInfoDao.query(cond, 0, 1);
		if (paInfos.isEmpty()) {
			return;
		}
		PayTxnInfo payTxnInfo = paInfos.get(0);
		if (payTxnInfo.getRefundAmt() == null) {
			payTxnInfo.setRefundAmt(BigDecimal.ZERO);
		}
		if (resultInfo.getRefundAmt() == null) {
			resultInfo.setRefundAmt(BigDecimal.ZERO);
		}

		if (payTxnInfo.getRefundAmt().compareTo(resultInfo.getRefundAmt()) != 0
				|| !payTxnInfo.getTxnFlag().equals(resultInfo.getTxnFlag())
				|| payTxnInfo.getIsRefundEnable() != resultInfo
						.getIsRefundEnable()) {

			payTxnInfo.setRefundAmt(resultInfo.getRefundAmt());
			payTxnInfo.setTxnFlag(resultInfo.getTxnFlag());
			payTxnInfo.setIsRefundEnable(resultInfo.getIsRefundEnable());
			payTxnInfoDao.update(payTxnInfo);
			getAppContext().setAttribute(RuntimeAttrNames.FRESH_REFUND_FLAG,
					RuntimeAttrNames.FRESH_REFUND_FLAG);
		}

	}

	/**
	 * 设置签名图片显示
	 */
	protected void showSignPic() {
		if (StringUtil.isEmpty(txnInfo.getSignPic())
				|| TxnTypes.REFUND.equalsIgnoreCase(txnInfo.getTxnType())
				|| PayTxnInfoStatus.STATUS_FAIL.equals(txnInfo.getTxnStatus())) {
			this.signPic_layout.setVisibility(View.GONE);
			this.signPic_dotted_layout.setVisibility(View.GONE);
			signNetImg = null;
		} else {
			this.signPic_layout.setVisibility(View.VISIBLE);
			this.signPic_dotted_layout.setVisibility(View.VISIBLE);
			signNetImg = new TiNetImageView(this, signImg);
			final Activity activity = this;
			signNetImg
					.setHttpStatusListener(new TiNetImageHttpStatusListener() {

						public void responseStatus(URL url, final int statusCode) {

							activity.runOnUiThread(new Runnable() {
								public void run() {
									if (statusCode == HttpStatus.SC_NOT_FOUND) {
										fillSignBtn.setVisibility(View.VISIBLE);
										signImg.setVisibility(View.GONE);
									} else {
										fillSignBtn.setVisibility(View.GONE);
										signImg.setVisibility(View.VISIBLE);
									}
								}
							});

						}
					});
			signNetImg.setNetUrl(ItemConvertUtil.getFileItemsUrl(
					tiRpcClient.getUploadUrl(), txnInfo.getSignPic(),
					AttachmentTypes.SIGNATURE_PICTURE).get(0));

		}
	}

	/**
	 * 设置动态字段显示名称
	 */
	private void setDynamicFieldTitle() {
		this.fieldHelper.setTextView(orderidTitle,
				FlexFieldDefine.FIELD_NAME_EXT_TRACE_NO,
				this.txnInfo.getTxnType());
		this.fieldHelper.setTextView(memoTitle,
				FlexFieldDefine.FIELD_NAME_MEMO, this.txnInfo.getTxnType());
		this.fieldHelper.setTextView(amountTitle,
				FlexFieldDefine.FIELD_NAME_AMT, this.txnInfo.getTxnType());
	}

	class QueryPayTxnInfoAfterCallback extends AfterProcessWithErrorHandler {

		private boolean refundFlag;

		private String txnId;

		private CommonDialog dialog;

		public QueryPayTxnInfoAfterCallback(Activity activity,
				boolean refundFlag, String txnId, CommonDialog dialog) {
			super(activity);
			this.refundFlag = refundFlag;
			this.txnId = txnId;
			this.dialog = dialog;
		}

		@Override
		public void afterRequest(ModelAndView mv) {
			DialogUtil.setDialogAllowClose(dialog.getDialog(), true);
			dialog.cancel();
			List<PayTxnInfo> infos = (List<PayTxnInfo>) mv.getValue("txnList");
			if (infos.size() > 0) {
				txnInfo = infos.get(0);
				initDetailView(refundFlag);
			}

		}

		@Override
		public void processNetworkError() {
			DialogUtil.setDialogAllowClose(dialog.getDialog(), true);
			dialog.cancel();
			super.processNetworkError();
		}

		@Override
		protected void refreshAfterNetworkError() {
			queryTxnDetail(txnId, refundFlag);
		}

	}

	public PayTxnInfo getTxnInfo() {
		return txnInfo;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (signNetImg != null) {
			signNetImg.destory();
		}
		if (transNetImg != null) {
			transNetImg.destory();
		}

	}

}
