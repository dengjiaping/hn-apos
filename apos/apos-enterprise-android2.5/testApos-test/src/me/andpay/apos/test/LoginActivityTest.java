package me.andpay.apos.test;

import me.andpay.apos.lam.activity.LoginActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	public LoginActivityTest(Class<LoginActivity> activityClass) {
		super(activityClass);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		LoginActivity loginActivity = getActivity();
		Button logButton = loginActivity.loginBtn;
		if(logButton == null) {
			System.out.println("login is null!");
		}
		 
	}

}
