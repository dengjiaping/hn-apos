package me.andpay.timobileframework.mvc.context;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

/**
 * 数据上下文接口
 * 
 * @author tinyliu
 *
 */
public interface TiContext {
	
	public static int CONTEXT_SCOPE_APPLICATION = 1;
	
	public static int CONTEXT_SCOPE_EVENTPROCESS = 2;
	
	public static int CONTEXT_SCOPE_APPLICATION_CONFIG = 3;
	
	/**
	 * 获取上下文数据
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name);
    
	/**
	 * 设置上下文数据
	 * @param name
	 * @param value
	 */
	public void setAttribute(String name,Object value);
	/**
	 * 设置上下文数据
	 * @param attribs
	 */
	public void setAttribute(Map<String, Object> attribs);
	
    /**
     * 从上下文中移出数据
     * @param name
     */
    public void removeAttribute(String name);
    /**
     * 获取上下文创建时间
     * @return
     */
    public Date getCreationTime();
    /**
     * 获取最后访问时间
     * @return
     */
    public Date getLastAccessTime();
	/**
	 * 获取上下文作用范围
	 * @return
	 */
    public int getScope();
    /**
     * 清空上下文信息，重建
     */
    public void inValidate();
    /**
     * 上下文中是否存在数据
     */
    public boolean isEmpty();
    /**
     * 更具类型获取数据
     * @param clazz
     * @param name
     * @return
     */
	public Object getAttribute(Type type,String name);

}
