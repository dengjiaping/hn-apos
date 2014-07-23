package me.andpay.apos.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import me.andpay.ac.term.api.auth.Privileges;
import me.andpay.ac.term.api.shop.Product;
import me.andpay.ac.term.api.shop.ProductService;
import me.andpay.ac.term.api.shop.SyncProductRequest;
import me.andpay.ac.term.api.shop.SyncProductResponse;
import me.andpay.apos.common.CommonProvider;
import me.andpay.apos.common.bug.ThrowableReporter;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.dao.ProductInfoDao;
import me.andpay.apos.dao.ProductInfoDao.ProductStatisticsInfo;
import me.andpay.apos.dao.model.ProductInfo;
import me.andpay.apos.dao.model.QueryProductInfoCond;
import me.andpay.ti.lnk.transport.websock.common.NetworkErrorException;
import me.andpay.ti.lnk.transport.websock.common.WebSockTimeoutException;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.context.TiContextProvider;
import me.andpay.timobileframework.mvc.support.TiApplication;
import me.andpay.timobileframework.util.DateUtil;
import android.app.Application;

import com.google.inject.Inject;

/**
 * 用于进行product数据同步
 * 
 * 每天同步一次
 * 
 * @author tinyliu
 * 
 */
public class ProductSynchroner {

	public static final String LASTSYNCTIMEKEY = "_product_last_sync";

	public static final String LASTUPDATETIMEKEY = "_product_last_update";

	@Inject
	private ProductInfoDao productInfoDao;

	@Inject
	private ThrowableReporter throwReporter;

	private ProductService productService;

	@Inject
	private TiRpcClient tiRpcClient;

	@Inject
	private Application application;

	public void sync(Application application, boolean isImmediately) {
		// 判断是否登陆，只有在登陆情况下才能同步数据
		if (!tiRpcClient.isConfigSSL()) {
			return;
		}


		try {
			
			TiContext tiContext = ((TiApplication) application)
					.getContextProvider().provider(
							TiContext.CONTEXT_SCOPE_APPLICATION);
			TiContext tiConfig = ((TiApplication) application).getContextProvider()
					.provider(TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);
			PartyInfo party = (PartyInfo) tiContext
					.getAttribute(RuntimeAttrNames.PARTY_INFO);
			if (party == null) {
				return;
			}
			Map<String, String> privileges = party.getPrivileges();
			if (!privileges.containsKey(Privileges.ORDER_PURCHASE)
					&& !privileges.containsKey(Privileges.PURCHASE)) {
				return;
			}

			String lastUpdateStr = (String) tiConfig.getAttribute(generateKey(
					application, LASTUPDATETIMEKEY));
			Date lastUpdateTime = null;
			boolean isFirstLogin = true;
			if (!StringUtil.isEmpty(lastUpdateStr)) {
				lastUpdateTime = me.andpay.ti.util.DateUtil.parse(
						CommonProvider.DATE_PARTTERN, lastUpdateStr);
				isFirstLogin = false;
			}
			// 判断是否满足同步策略 ： 一天同步一次
			if (!isSatisfySyncPolicy(application, tiConfig) && !isImmediately) {
				return;
			}
			SyncProductRequest request = new SyncProductRequest();
			if (lastUpdateTime != null) {
				request.setMaxUpdTime(lastUpdateTime);
			}

			SyncProductResponse response = productService.syncProducts(request);

			if (isFirstLogin) {
				incrementProcess(application, response);
			} else {
				processProducts(application, response, party.getPartyId());
			}

		} catch (Throwable e) {
			if (e instanceof WebSockTimeoutException
					|| e instanceof NetworkErrorException) {
				return;
			}
//			throwReporter.submitError(e);
		}
	}

	protected String generateKey(Application application, String prefix) {
		TiContextProvider provider = ((TiApplication) application)
				.getContextProvider();
		TiContext tiContext = provider
				.provider(TiContext.CONTEXT_SCOPE_APPLICATION);
		PartyInfo party = (PartyInfo) tiContext
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		return party.getPartyId() + prefix;
	}

	protected String generateLastUpdateKey(Application application) {
		TiContextProvider provider = ((TiApplication) application)
				.getContextProvider();
		TiContext tiContext = provider
				.provider(TiContext.CONTEXT_SCOPE_APPLICATION);
		PartyInfo party = (PartyInfo) tiContext
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		return party.getPartyId() + LASTSYNCTIMEKEY;
	}

	/**
	 * 判断同步策略
	 * 
	 * @param lastUpdateTime
	 * @return
	 */
	protected boolean isSatisfySyncPolicy(Application application,
			TiContext tiConfig) {
		String lastSyncDateStr = (String) tiConfig.getAttribute(generateKey(
				application, LASTSYNCTIMEKEY));
		Date currentDate = DateUtil.getCurrentZeroDate();
		if (StringUtil.isEmpty(lastSyncDateStr)) {
			return false;
		}
		Date lastSyncDate = me.andpay.ti.util.DateUtil.parse(
				CommonProvider.DATE_PARTTERN, lastSyncDateStr);
		if (lastSyncDate != null && currentDate.before(lastSyncDate)) {
			return false;
		}
		return true;
	}

	protected void processProducts(Application application,
			SyncProductResponse response, String partyId) {
		// 判断是否需要全量更新
		if (isSyncAllProduct(response, partyId)) {
			productInfoDao.deleteAllProductByPartyId(partyId);
			SyncProductRequest request = new SyncProductRequest();
			response = productService.syncProducts(request);
		}
		if (response.getProducts() == null || response.getProducts().isEmpty()) {
			return;
		}
		incrementProcess(application, response);
	}

	/**
	 * 根据返回的有效产品数量和总金额来判断是否需要全量更新
	 * 
	 * @param response
	 * @param partyId
	 * @return
	 */
	protected boolean isSyncAllProduct(SyncProductResponse response,
			String partyId) {
		Long valProductCount = response.getCount();
		BigDecimal valProductTotal = response.getTotal();
		ProductStatisticsInfo info = this.productInfoDao
				.getPartyValiProductStatisticsInfo(partyId);
		if (valProductCount.compareTo(info.getCount()) == 0
				&& valProductTotal.compareTo(info.getTotal()) == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 增量更新产品信息
	 * 
	 * @param products
	 */
	protected void incrementProcess(Application application,
			SyncProductResponse response) {
		updateSyncPolicy(application, response);
		for (Product product : response.getProducts()) {
			if (!Product.STATUS_NORMAL.equalsIgnoreCase(product.getStatus())) {
				productInfoDao.deleteProduct(product);
				continue;
			}
			QueryProductInfoCond cond = new QueryProductInfoCond();
			cond.setProductId(product.getProductId());
			product.setUpdTime(new Date());
			List<ProductInfo> productInfo = productInfoDao.query(cond, 0, -1);
			if (productInfo == null || productInfo.isEmpty()) {
				productInfoDao.addProduct(product);
			} else {
				productInfoDao.updateProduct(product, productInfo.get(0));
			}
		}
	}

	/**
	 * 更新最后同步时间
	 * 
	 * @param application
	 * @param response
	 */
	protected void updateSyncPolicy(Application application,
			SyncProductResponse response) {
		TiContextProvider provider = ((TiApplication) application)
				.getContextProvider();
		TiContext tiConfig = provider
				.provider(TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);
		tiConfig.setAttribute(generateKey(application, LASTUPDATETIMEKEY),
				me.andpay.ti.util.DateUtil.format(CommonProvider.DATE_PARTTERN,
						response.getUpdTime()));
		tiConfig.setAttribute(generateKey(application, LASTSYNCTIMEKEY),
				me.andpay.ti.util.DateUtil.format(CommonProvider.DATE_PARTTERN,
						new Date()));
	}

	// protected boolean isLogin(Application application) {
	// TiContextProvider provider = ((TiApplication)
	// application).getContextProvider();
	// TiContext tiContext =
	// provider.provider(TiContext.CONTEXT_SCOPE_APPLICATION);
	// tiContext.getAttribute(RuntimeAttrNames.LOGIN_CURRENT_USER);
	// if (tiContext.getAttribute(RuntimeAttrNames.LOGIN_CURRENT_USER) != null)
	// {
	// return true;
	// }
	// return false;
	// }

}
