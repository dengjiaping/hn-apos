package me.andpay.apos.lft.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * 基础的适配器控制器
 * @author Administrator
 *
 */
public abstract class BaseAdapterController<T>{
	
	
	private BaseAdapter<T> adpter;//控制主体
	
	
	
	public BaseAdapter<T> getAdpter(){
		return adpter;
	}


	public void setAdpter(BaseAdapter<T> adpter) {
		this.adpter = adpter;
	}


	/*获得视图*/
	public View view(int arg0, View arg1, ViewGroup arg2){
		
		View view=getView(arg0, arg1, arg2);
		getEvent(view,arg0);
		return view;
		
	}
	public abstract View getView(int arg0, View arg1, ViewGroup arg2);
	
	public abstract void getEvent(View view,int position);
	
	
}
