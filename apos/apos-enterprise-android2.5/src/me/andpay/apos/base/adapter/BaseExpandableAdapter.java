package me.andpay.apos.base.adapter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

/*基础expandableAdapter*/
public class BaseExpandableAdapter<T> extends BaseExpandableListAdapter {
	/* 数据 */
	private ArrayList<ArrayList<T>> list = new ArrayList<ArrayList<T>>();
	/* 控制器 */
	private BaseExpandableAdapterController<T> controller;
	/* 事件监听器 */
	private AdpterEventListener listener;

	public ArrayList<ArrayList<T>> getList() {
		return list;
	}

	public void setList(ArrayList<ArrayList<T>> list) {
		this.list = list;
	}

	public BaseExpandableAdapterController<T> getController() {
		return controller;
	}

	public void setController(BaseExpandableAdapterController<T> controller) {
		this.controller = controller;
		controller.setAdapter(this);

	}

	public AdpterEventListener getListener() {
		return listener;
	}

	public void setListener(AdpterEventListener listener) {
		this.listener = listener;
	}

	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return list.get(arg0).get(arg1);
	}

	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		// TODO Auto-generated method stub
		return controller.getChildView(arg0, arg1, arg2, arg3, arg4);
	}

	public int getChildrenCount(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0).size();
	}

	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		// TODO Auto-generated method stub
		return controller.getGroupView(arg0, arg1, arg2, arg3);
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
