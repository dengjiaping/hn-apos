package me.andpay.apos.tqm.activity;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;

import me.andpay.ac.consts.TxnFlags;
import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.R;
import me.andpay.apos.cmview.TiSectionListAdapter;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 交易查询列表适配器
 * 
 * @author tinyliu
 * 
 */
public class TxnListAdapter extends TiSectionListAdapter<PayTxnInfo> {

	private String dateStr;

	private Context applicationContext;

	private QueryConditionForm form;

	// 发卡公司名超出特定的长度的部分用省略号代替
	private static final String ellipsis = "..";

	// 留白和卡号占用的像素
	private static final int unAvailableWidthPixels = 200;

	// 显示一个中文字符所需的像素
	private static final int chineseCharacterPixels = 15;

	// 小屏幕手机允许显示的中文字符数
	private int availableCharactersNumber;

	public TxnListAdapter(LinkedList<PayTxnInfo> infos, Activity activity,
			String dateStr, QueryConditionForm form) {
		this.all = new LinkedList<Pair<String, LinkedList<PayTxnInfo>>>();
		this.addValues(infos);
		this.dateStr = dateStr;
		this.applicationContext = activity.getApplicationContext();
		this.form = form;
		this.availableCharactersNumber = getAvailableCharactersNumber(activity);
	}

	/**
	 * TODO：输入信息
	 */
	@Override
	public View getSectionItemView(int sectionIndex, int itemIndex,
			View convertView, ViewGroup parent) {
		PayTxnInfo info = (PayTxnInfo) getSectionItem(sectionIndex, itemIndex);
		TxnItemViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(applicationContext).inflate(
					R.layout.tqm_txn_list_item_layout, null);
			convertView.setDrawingCacheEnabled(false);
			holder = new TxnItemViewHolder();
			holder.amount = (TextView) convertView
					.findViewById(R.id.tqm_txn_list_item_amount_tv);
			holder.time = (TextView) convertView
					.findViewById(R.id.tqm_txn_list_item_time_tv);
			holder.cardno = (TextView) convertView
					.findViewById(R.id.tqm_txn_list_item_cardno_tv);
			holder.txntype = (TextView) convertView
					.findViewById(R.id.tqm_txn_list_item_type_tv);
			holder.refund = (TextView) convertView
					.findViewById(R.id.tqm_txn_list_item_refund_tv);
			holder.cardIssueName = (TextView) convertView
					.findViewById(R.id.tqm_txn_list_item_bank_tv);
			convertView.setTag(holder);
		} else {
			holder = (TxnItemViewHolder) convertView.getTag();
		}
		// 设置交易状态图片
		setStatusImg(convertView, info.getTxnStatus());
		// 设置退款，撤销信息
		setRefundInfo(holder, info);
		// 设置动态字段
		setDynamicField(holder, info);
		holder.amount.setText(StringConvertor.convert2Currency(info
				.getSalesAmt()));

		ColorStateList blackList = (ColorStateList) applicationContext
				.getResources().getColorStateList(
						R.color.com_list_item_text_amount_selector);
		ColorStateList redList = (ColorStateList) applicationContext
				.getResources().getColorStateList(
						R.color.com_list_item_text_red_selector);

		holder.amount.setTextColor(TxnTypes.PURCHASE.equalsIgnoreCase(info
				.getTxnType()) ? blackList : redList);
		//holder.cardIssueName.setText(info.getIssuerName());
		holder.txntype.setText(info.getTxnTypeDesc());
		holder.cardno.setText(info.getShortPan());
		if (!StringUtil.isEmpty(info.getTermTxnTime())) {
			holder.time.setText(String.format(
					getDateStr(),
					DateUtil.parse(TqmProvider.TQM_PARTTERN_COMMOM_DATE,
							info.getTermTxnTime())));
		}

		setTvsTextColor(info, holder);
		if (itemIndex == this.getSectionItemCount(sectionIndex) - 1) {

		}
		return convertView;
	}

	/**
	 * 设置textview color
	 * 
	 * @param info
	 */
	private void setTvsTextColor(PayTxnInfo info, TxnItemViewHolder holder) {
		if (TxnTypes.PURCHASE.equalsIgnoreCase(info.getTxnType())) {
			holder.amount.setTextColor(this.applicationContext.getResources()
					.getColor(R.color.tqm_list_item_amount_col));
			holder.time.setTextColor(this.applicationContext.getResources()
					.getColor(R.color.tqm_list_item_amount_col));
			holder.cardIssueName.setTextColor(this.applicationContext
					.getResources().getColor(R.color.tam_text_col));
			holder.cardno.setTextColor(this.applicationContext.getResources()
					.getColor(R.color.tam_text_col));
			holder.txntype.setTextColor(this.applicationContext.getResources()
					.getColor(R.color.tam_text_col));
		} else {
			holder.amount.setTextColor(this.applicationContext.getResources()
					.getColor(R.color.com_button_top_col));
			holder.time.setTextColor(this.applicationContext.getResources()
					.getColor(R.color.com_button_top_col));
			holder.cardIssueName.setTextColor(this.applicationContext
					.getResources().getColor(R.color.com_button_top_col));
			holder.cardno.setTextColor(this.applicationContext.getResources()
					.getColor(R.color.com_button_top_col));
			holder.txntype.setTextColor(this.applicationContext.getResources()
					.getColor(R.color.com_button_top_col));
		}
	}

	private int getAvailableCharactersNumber(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int availableNumber = (int) ((metric.widthPixels/metric.density - unAvailableWidthPixels)
				/ chineseCharacterPixels);
		return availableNumber;
	}

	public String getMaxTxnId() {
		return all.get(0).second.getFirst().getTxnId();
	}

	public String getMinTxnId() {
		return all.get(all.size() - 1).second.getLast().getTxnId();
	}

	private void setDynamicField(TxnItemViewHolder holder, PayTxnInfo info) {
		String issuerName = info.getIssuerName();
		if (StringUtil.isNotBlank(issuerName)&&issuerName.length() > this.availableCharactersNumber) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(issuerName.substring(0,
					this.availableCharactersNumber));
			buffer.append(ellipsis);
			holder.cardIssueName.setText(buffer.toString());
		}else{
			holder.cardIssueName.setText(issuerName);
		}
	}

	private void setRefundInfo(TxnItemViewHolder holder, PayTxnInfo info) {

		if (TxnTypes.REFUND.equalsIgnoreCase(info.getTxnType())
				|| TxnTypes.VOID_PURCHASE.equalsIgnoreCase(info.getTxnType())) {
			holder.refund.setText("");
			return;
		}
		if (info.getTxnFlag() != null
				&& TxnFlags.VOIDED.equalsIgnoreCase(info.getTxnFlag())) {
			holder.refund.setText(R.string.tqm_txn_detail_void_info_str);
			return;
		}
		/**
		 * 显示交易是否已撤销，退款描述
		 */
		if (info.getRefundAmt() != null
				&& info.getRefundAmt().compareTo(BigDecimal.ZERO) > 0) {
			holder.refund.setVisibility(View.VISIBLE);
			int resId = (info.getSalesAmt().compareTo(info.getRefundAmt()) <= 0) ? R.string.tqm_txn_detail_refund_info_str
					: R.string.tqm_txn_detail_subrefund_info_str;
			holder.refund.setText(resId);
		} else {
			holder.refund.setText("");
		}
	}

	private void setStatusImg(View convertView, String txnStatus) {
		/**
		 * 设置交易状态图片
		 */
		ImageView img = (ImageView) convertView
				.findViewById(R.id.tqm_txn_list_item_type_img);
		if (PayTxnInfoStatus.STATUS_SUCCESS.equalsIgnoreCase(txnStatus)) {
			img.setImageResource(R.drawable.com_icon_succeed_img);
		}
		if (PayTxnInfoStatus.STATUS_FAIL.equalsIgnoreCase(txnStatus)) {
			img.setImageResource(R.drawable.com_icon_delete_img);
		}
		if (StringUtil.isEmpty(txnStatus)
				|| PayTxnInfoStatus.STATUS_PENDING.equalsIgnoreCase(txnStatus)) {
			img.setImageResource(R.drawable.com_icon_undetermined_img);
		}
	}

	public void destory() {
		for (Pair<String, LinkedList<PayTxnInfo>> infos : all) {
			infos.second.clear();
		}
		all.clear();
	}

	public void setForm(QueryConditionForm form) {
		this.form = form;
	}

	public String getDateStr() {
		return dateStr;
	}

	public QueryConditionForm getCondition() {
		return form;
	}

	@Override
	protected void bindSectionHeader(View view, int sectionIndex,
			boolean displaySectionHeader) {
		if (displaySectionHeader) {
			view.findViewById(me.andpay.apos.R.id.section_header)
					.setVisibility(View.VISIBLE);
			TextView lSectionTitle = (TextView) view
					.findViewById(R.id.vas_purchase_order_section_tv);
			lSectionTitle.setText(getSections()[sectionIndex].toString());
		} else {
			view.findViewById(R.id.section_header).setVisibility(View.GONE);
		}
	}

	public void configureSectionView(View header, int section, int alpha) {
		TextView lSectionTitle = (TextView) header
				.findViewById(R.id.vas_purchase_order_section_tv);
		lSectionTitle.setText(getSections()[section].toString());
	}

	@Override
	public long getItemId(int section, int itemIndex) {
		return 0;
	}

	@Override
	public String getSectionDesc(PayTxnInfo info) {
		Date termTxnDate = DateUtil.parse(
				CommonProvider.DATABASE_DATE_PARTTERN, info.getTermTxnTime());
		String orderTimeStr = DateFormat.getDateInstance(DateFormat.FULL)
				.format(termTxnDate);
		return orderTimeStr;
	}
}
