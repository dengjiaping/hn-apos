package me.andpay.apos.vas.action;

import java.util.List;

import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.dao.ProductInfoDao;
import me.andpay.apos.dao.model.ProductInfo;
import me.andpay.apos.dao.model.QueryProductInfoCond;
import me.andpay.apos.vas.callback.ProductLocalQueryCallback;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;

import com.google.inject.Inject;


/**
 * 商品服务
 * @author cpz
 *
 */
@ActionMapping(domain = ProductAction.DOMAIN_NAME )
public class ProductAction extends SessionKeepAction{
	
	public static final String DOMAIN_NAME ="/vas/product.action";
	
	public static final String QUERY_LOCAL = "queryLocalProduct";
	
	@Inject
	private ProductInfoDao productInfoDao;
	
	/**
	 * 本地商品查询
	 * @param request
	 */
	public ModelAndView queryLocalProduct(ActionRequest request) {
		
		QueryProductInfoCond cond = (QueryProductInfoCond)request.getParameterValue("QueryProductInfoCond");
		ProductLocalQueryCallback callback = (ProductLocalQueryCallback)request.getHandler();
		List<ProductInfo> productInfos =  productInfoDao.query(cond, 0, -1);
		callback.querySuccess(productInfos);
		return null;
	}
}
