package com.zuma.common;

import com.common.zuma.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class FindPwdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhaohui);
		
		//应根据用户名（或手机号）找回
		
	}
	
}
