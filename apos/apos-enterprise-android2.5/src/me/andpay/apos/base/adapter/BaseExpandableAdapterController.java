package me.andpay.apos.base.adapter;

import android.view.View;
import android.view.ViewGroup;

/*基础expandableAdapter控制器*/
public abstract class BaseExpandableAdapterController<T> {
	private BaseExpandableAdapter<T> adapter;

	public BaseExpandableAdapter<T> getAdapter() {
		return adapter;
	}

	public void setAdapter(BaseExpandableAdapter<T> adapter) {
		this.adapter = adapter;
	}

	/* 返回子视图 */
	public abstract View getChildView(int arg0, int arg1, boolean arg2,
			View arg3, ViewGroup arg4);

	/* 返回主视图 */
	public abstract View getGroupView(int arg0, boolean arg1, View arg2,
			ViewGroup arg3);
}
