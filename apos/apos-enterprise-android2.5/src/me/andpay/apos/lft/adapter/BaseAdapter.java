package me.andpay.apos.lft.adapter;

import java.util.ArrayList;

import me.andpay.apos.lft.data.BaseData;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
/**
 * 基础适配器
 * @author Administrator
 *
 * @param <T>
 */
public class BaseAdapter<T extends BaseData> extends
		android.widget.BaseAdapter {
	private Context context;//上下文环境
	private ArrayList<T> list = new ArrayList<T>();// 适配器数据
   
	private BaseAdapterController<T> controller;// Adapter 控制器

	private AdpterEventListener adpterEventListener;//事件监听器
	
    
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ArrayList<T> getList(){
		return list;
	}

	public void setList(ArrayList<T> list) {
		this.list = list;
	}

	public BaseAdapterController<T> getController() {
		return controller;
	}

	public void setController(BaseAdapterController<T> controller) {
		this.controller = controller;
		controller.setAdpter(this);
	}
	
	

	public AdpterEventListener getAdpterEventListener() {
		return adpterEventListener;
	}

	public void setAdpterEventListener(AdpterEventListener adpterEventListener) {
		this.adpterEventListener = adpterEventListener;
	}
	
	

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		return controller.view(arg0, arg1, arg2);

	}

}
