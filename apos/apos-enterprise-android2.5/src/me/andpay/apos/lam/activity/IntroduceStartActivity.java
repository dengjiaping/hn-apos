package me.andpay.apos.lam.activity;

import java.util.ArrayList;

import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.lam.event.IntroduceStartPageChangeEventControl;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.support.TiActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


/**
 * 产品介绍引导
 * @author cpz
 */
@ContentView(R.layout.lam_introduce_start_layout)
public class IntroduceStartActivity extends TiActivity implements
ValueContainer{

	@InjectView(R.id.introduce_viewpager)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean=false, delegateClass = OnPageChangeListener.class, toEventController = IntroduceStartPageChangeEventControl.class)
	public ViewPager introViewpager;	
	
	@InjectView(R.id.com_page0)
   	public ImageView mPage0;
	@InjectView(R.id.com_page1)
	public ImageView mPage1;
	//@InjectView(R.id.com_page2)
	//public ImageView mPage2;
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		  
    	super.onCreate(savedInstanceState); 
    	
	    LayoutInflater mLi = LayoutInflater.from(this);
        View introduce1 = mLi.inflate(R.layout.lam_introduce1_layout, null);
        View introduce2 = mLi.inflate(R.layout.lam_introduce2_layout, null);
        //View introduce3 = mLi.inflate(R.layout.lam_login_layout, null);
       // localActivityManager = new LocalActivityManager(this, true);
        //localActivityManager.dispatchCreate(savedInstanceState);
        //Intent intent = new Intent(IntroduceStartActivity.this,LoginActivity.class);
        //View introduce3 = localActivityManager.startActivity(LamProvider.LAM_LOGIN_ACTIVITY, intent).getDecorView();
        final ArrayList<View> introduceList = new ArrayList<View>();
        introduceList.add(introduce1);
        introduceList.add(introduce2);
        //introduceList.add(introduce3);
        
        //填充ViewPager的数据适配器
        PagerAdapter introducePagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			@Override
			public int getCount() {
				return introduceList.size();
			}
			@Override 
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(introduceList.get(position));
			}	
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(introduceList.get(position));
				return introduceList.get(position);
			}
		};
		
		introViewpager.setAdapter(introducePagerAdapter);
	}
	
//	public void userLogin(View view) {
//		Intent intent = new Intent();
//		intent.setAction(LamProvider.LAM_LOGIN_ACTIVITY);
//		startActivity(intent);
//		this.finish();
//	}
//	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 返回退出
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);

	}
	
	public void showBackDialog(Activity activity) {
		final OperationDialog dialog = new OperationDialog(activity,
				getResources().getString(R.string.com_show_str), getResources()
						.getString(R.string.com_sure_logout__apos_str), true);
		final Activity inActivity = activity;
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				inActivity.finish();
				System.exit(0);
			}
		});
		dialog.show();
	}
}
