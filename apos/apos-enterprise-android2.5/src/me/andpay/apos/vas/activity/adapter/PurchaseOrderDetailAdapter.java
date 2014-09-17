package me.andpay.apos.vas.activity.adapter;

import java.util.LinkedList;
import java.util.Map;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.ac.consts.ShopProductTypes;
import me.andpay.ac.term.api.shop.PurchaseOrderItem;
import me.andpay.apos.R;
import me.andpay.apos.cmview.TiSectionListAdapter;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.common.util.TxnUtil;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.vas.VasImageResourceUtil;
import me.andpay.apos.vas.activity.adapter.PurchaseOrderDetailAdapter.DetailItem;
import me.andpay.apos.vas.flow.model.SvcDepositeContext;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.util.StringConvertor;
import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 销售详细页面数据适配器
 * 
 * @author tinyliu
 * 
 */
public class PurchaseOrderDetailAdapter extends TiSectionListAdapter<DetailItem> {

	static final Integer MAX_DESC_SIZE = 6;

	Context applicationContext;

	public PurchaseOrderDetailAdapter(PurchaseOrderInfo info, Activity activity) {

		generateProductSection(info, activity);
		generateInfoSection(info, activity);
		applicationContext = activity.getApplicationContext();
	}

	/**
	 * 生成产品条款信息
	 * 
	 * @param info
	 * @param activity
	 */
	protected void generateProductSection(PurchaseOrderInfo info, Activity activity) {
		LinkedList<DetailItem> items = new LinkedList<DetailItem>();
		Pair<String, LinkedList<DetailItem>> itemList = new Pair<String, LinkedList<DetailItem>>(
				activity.getResources()
						.getString(R.string.vas_po_detail_item_section_str), items);
		for (PurchaseOrderItem item : info.getItems()) {
			DetailItem dItem = new DetailItem();
			dItem.setImgId(VasImageResourceUtil.getImageFromProductImage(item
					.getProductType()));
			if(StringUtil.isNotBlank(item.getProductName())) {
				dItem.setDesc(item.getProductName().length() > MAX_DESC_SIZE ? item
						.getProductName().substring(0, MAX_DESC_SIZE - 1) + "..." : item
						.getProductName());
			}
			dItem.setCount(item.getUnit());
			dItem.setAmount(item.getPrice().toString() + "元");
			dItem.setTotalAmount(StringConvertor.format(item.getSalesAmt()));
			items.add(dItem);
			if (item.getProductType().equals(ShopProductTypes.SVC_DEPOSIT)) {
				DetailItem cardItem = new DetailItem();
				cardItem.setImgId(R.drawable.com_icon_swipe_img);
				Map<String, String> attr = JacksonSerializer.newPrettySerializer()
						.deserialize(Map.class, item.getAttr().getBytes());
				cardItem.setDesc(TxnUtil.formatCardNo(attr
						.get(SvcDepositeContext.ATTR_KEY_CARDNO)));
				items.add(cardItem);
				
				cardItem = new DetailItem();
				cardItem.setImgId(R.drawable.com_icon_swipe_img); 
				cardItem.setDesc(attr.get(SvcDepositeContext.ATTR_KEY_CARDNAME));
				items.add(cardItem);
			}
		}
		DetailItem dItem = new DetailItem();
		dItem.setImgId(R.drawable.com_icon_total_img);
		dItem.setDesc(ResourceUtil.getString(activity,
				R.string.vas_po_detail_item_section_total_str));
		dItem.setTotalAmount(StringConvertor.format(info.getSalesAmt()));
		items.add(dItem);
		this.all.add(itemList);
	}

	/**
	 * 生成PO订单信息数据
	 * 
	 * @param info
	 * @param activity
	 */
	protected void generateInfoSection(PurchaseOrderInfo info, Activity activity) {
		LinkedList<DetailItem> items = new LinkedList<DetailItem>();
		Pair<String, LinkedList<DetailItem>> itemList = new Pair<String, LinkedList<DetailItem>>(
				activity.getResources()
						.getString(R.string.vas_po_detail_info_section_str), items);
		// 凭证信息
		DetailItem dItem = new DetailItem();
		dItem.setImgId(R.drawable.com_icon_voucher_number_img);
		dItem.setDesc(ResourceUtil.getString(activity,
				R.string.vas_po_detail_info_section_voucher_str));
		dItem.setAmount(info.getReceiptNo());
		items.add(dItem);
		// 支付方式
		dItem = new DetailItem();

		if (PaymentMethods.CASH.equalsIgnoreCase(info.getPaymentMethod())) {
			dItem.setDesc(ResourceUtil.getString(activity,
					R.string.vas_po_detail_info_section_cash_str));
			dItem.setImgId(R.drawable.com_icon_cash_img);
		} else {
			dItem.setDesc(ResourceUtil.getString(activity,
					R.string.vas_po_detail_info_section_swipe_str));
			dItem.setImgId(R.drawable.com_icon_swipe_img);
		}
		items.add(dItem);
		// 交易时间
		dItem = new DetailItem();
		dItem.setImgId(R.drawable.com_icon_time_img);
		dItem.setDesc(DateUtil.format(ResourceUtil.getString(activity,
				R.string.vas_po_detail_info_section_ordertime_str), info.getOrderTime()));
		items.add(dItem);
		if (info.getRefundTime() != null) {
			dItem = new DetailItem();
			dItem.setImgId(R.drawable.com_icon_refund_small_img);
			dItem.setDesc(ResourceUtil.getString(activity,
					R.string.vas_po_detail_info_section_refund_str));
			dItem.setAmount(DateUtil.format(ResourceUtil.getString(activity,
					R.string.vas_po_detail_info_section_refundtime_str), info
					.getRefundTime()));
			items.add(dItem);

		}
		this.all.add(itemList);
	}

	@Override
	protected void bindSectionHeader(View view, int sectionIdex,
			boolean displaySectionHeader) {
		if (displaySectionHeader) {
			view.findViewById(me.andpay.apos.R.id.section_header).setVisibility(
					View.VISIBLE);
			TextView lSectionTitle = (TextView) view
					.findViewById(R.id.vas_purchase_order_section_tv);
			lSectionTitle.setText(getSections()[sectionIdex].toString());
		} else {
			view.findViewById(R.id.section_header).setVisibility(View.GONE);
		}
	}

	@Override
	public void configureSectionView(View header, int section, int alpha) {
		TextView lSectionTitle = (TextView) header
				.findViewById(R.id.vas_purchase_order_section_tv);
		lSectionTitle.setText(getSections()[section].toString());
	}

	@Override
	public View getSectionItemView(int sectionIndex, int itemIndex, View convertView,
			ViewGroup parent) {
		DetailItem item = this.getSectionItem(sectionIndex, itemIndex);
		View view = LayoutInflater.from(applicationContext).inflate(
				R.layout.vas_purchaseorder_detail_list_item_layout, null);
		((ImageView) view.findViewById(R.id.vas_purchase_order_item_img))
				.setImageResource(item.getImgId());
		((TextView) view.findViewById(R.id.vas_purchase_order_item_descs_tv))
				.setText(item.getDesc());
		setView(view, R.id.vas_purchase_order_item_amount_tv, item.getAmount());
		if (!setView(view, R.id.vas_purchase_order_item_count_tv,
				item.getCount() > 0 ? "" + item.getCount() : null)) {
			view.findViewById(R.id.vas_purchase_order_item_count_layout).setVisibility(
					View.GONE);
		}
		setView(view, R.id.vas_purchase_order_item_total_tv, item.getTotalAmount());
		return view;
	}

	protected boolean setView(View convertView, int rid, String value) {
		TextView view = (TextView) convertView.findViewById(rid);
		if (StringUtil.isEmpty(value)) {
			view.setVisibility(View.INVISIBLE);
			return false;
		} else {
			view.setVisibility(View.VISIBLE);
			view.setText(value);
			return true;
		}
	}

	/**
	 * 页面数据对象
	 * 
	 * @author tinyliu
	 * 
	 */
	public class DetailItem {
		private int imgId = -1;

		private String desc;

		private String amount;

		private String totalAmount;

		private int count = -1;

		public int getImgId() {
			return imgId;
		}

		public void setImgId(int imgId) {
			this.imgId = imgId;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

	}

	@Override
	public long getItemId(int section, int itemIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSectionDesc(DetailItem info) {
		// TODO Auto-generated method stub
		return null;
	}

}
