package com.zuma.common;

import com.common.zuma.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuce);
	}
	
}
