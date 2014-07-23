package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.PurchaseFirstActivity;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class PurOnGestureListener implements OnGestureListener {

	PurchaseFirstActivity activity;

	public PurOnGestureListener(PurchaseFirstActivity activity) {
		this.activity = activity;
	}

	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		final int FLING_MIN_DISTANCE = 100;// X或者y轴上移动的距离(像素)
		final int FLING_MIN_VELOCITY = 200;// x或者y轴上的移动速度(像素/秒)
		if ((e1.getX() - e2.getX()) > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			if(activity.amtEditText.isBank()) {
				return false;
			}
			activity.txnSunmit();
		} else if ((e2.getX() - e1.getX()) > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			//activity.txnSunmit();
		}

		return false;
	}

}
