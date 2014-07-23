package me.andpay.apos.vas.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.andpay.ac.consts.CurrencyCodes;
import me.andpay.ac.consts.GeoCooTypes;
import me.andpay.ac.consts.PurchaseOrderTypes;
import me.andpay.ac.consts.SalesChanTypes;
import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.ac.term.api.shop.PurchaseOrderItem;
import me.andpay.ac.term.api.shop.PurchaseOrderService;
import me.andpay.ac.term.api.shop.SvcLifecycleService;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.common.service.LocationService;
import me.andpay.apos.common.service.model.TiLocation;
import me.andpay.apos.dao.PurchaseOrderInfoDao;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.vas.callback.PurchaseOrderCallback;
import me.andpay.apos.vas.form.PurchaseOrderForm;
import me.andpay.apos.vas.spcart.ProductItem;
import me.andpay.apos.vas.spcart.ShoppingCart;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.util.BeanUtils;
import android.util.Log;

import com.google.inject.Inject;

@ActionMapping(domain = PurchaseOrderAction.DOMAIN_NAME)
public class PurchaseOrderAction extends SessionKeepAction {

	public static final String DOMAIN_NAME = "/vas/purchaseOrder.action";

	public static final String PLACEORDER = "placeOrder";

	public PurchaseOrderService purchaseOrderService;

	public SvcLifecycleService svcLifecycleService;

	@Inject
	public PurchaseOrderInfoDao purchaseOrderInfoDao;

	@Inject
	public LocationService locationService;

	private TiContext tiConfig;

	public ModelAndView placeOrder(ActionRequest request) {

		tiConfig = request
				.getContext(TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);

		PurchaseOrderCallback placeOrderCallback = (PurchaseOrderCallback) request
				.getHandler();
		PurchaseOrderForm foOrderForm = (PurchaseOrderForm)request.getParameterValue("PurchaseOrderForm");
		
		try {
			PurchaseOrder purchaseOrder = createPuraseOrder(foOrderForm);
			purchaseOrder = purchaseOrderService.placeOrder(purchaseOrder);
			storePurchaseOrder(purchaseOrder);
			placeOrderCallback.placeOrderSuccess(purchaseOrder);
			
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), "placeOrder error!", ex);
			placeOrderCallback.networkError();
		}

		return null;

	}

	private void storePurchaseOrder(PurchaseOrder purchaseOrder) {
		PurchaseOrderInfo purchaseOrderInfo = BeanUtils.copyProperties(
				PurchaseOrderInfo.class, purchaseOrder);
		purchaseOrderInfo.setUpdateTime(new Date());
		purchaseOrderInfoDao.insert(purchaseOrderInfo);
	}

	private PurchaseOrder createPuraseOrder(PurchaseOrderForm foOrderForm ) {

		ShoppingCart shoppingCart = foOrderForm.getShoppingCart();
		PurchaseOrder purchaseOrder = new PurchaseOrder();

		TiLocation location = locationService.getLocation();
		if (location != null) {
			purchaseOrder.setLocation(purchaseOrder.getLocation());
			purchaseOrder.setSpecCoordType(GeoCooTypes.BD_09);
			purchaseOrder.setSpecLatitude(location.getSpecLatitude());
			purchaseOrder.setSpecLongitude(location.getSpecLongitude());
			purchaseOrder.setLatitude(location.getLatitude());
			purchaseOrder.setLongitude(location.getLongitude());
			purchaseOrder.setLocation(location.getAddress());
		}

		
		List<PurchaseOrderItem> items = new ArrayList<PurchaseOrderItem>();
		for (ProductItem productItem : shoppingCart.getItemsList()) {
			PurchaseOrderItem purchaseOrderItem = BeanUtils.copyProperties(
					PurchaseOrderItem.class, productItem);
			purchaseOrderItem.setSalesAmt(productItem.getPrice());
			items.add(purchaseOrderItem);

		}
		purchaseOrder.setItems(items);

		purchaseOrder.setSalesAmt(shoppingCart.getTotalAmt());
		purchaseOrder.setReceiptNo(foOrderForm.getReceiptNo());
		purchaseOrder.setSalesChanType(SalesChanTypes.APOS);
		purchaseOrder.setSalesCur(CurrencyCodes.CNY);
		purchaseOrder.setOrderType(PurchaseOrderTypes.SALES);
		purchaseOrder.setOrderTime(foOrderForm.getOrderTime());
		purchaseOrder.setStatus(foOrderForm.getPurchaseStatus());
		purchaseOrder.setPaymentMethod(foOrderForm.getPaymeneMethed());
		purchaseOrder.setPaymentTxnId(foOrderForm.getPaymentTxnId());

		return purchaseOrder;
	}

}
