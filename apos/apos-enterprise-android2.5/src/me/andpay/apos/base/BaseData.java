package me.andpay.apos.base;

import java.io.Serializable;
import java.util.Map;

/**
 * 基本数据类型接口
 * 
 * @author Administrator
 *
 */
public interface BaseData extends Serializable{
  /*解析数据*/
  public void parse(Map map);
  /*打包数据*/
  public Map page();
}
