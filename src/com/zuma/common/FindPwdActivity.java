package com.zuma.common;

import com.common.zuma.R;
import com.zuma.util.CommonFunction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

public class FindPwdActivity extends Activity {

	private String userToken = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhaohui);
		
		if(getIntent().getBundleExtra("idValue").getString("userToken")!=null){
			userToken = getIntent().getBundleExtra("idValue").getString("userToken");
		}else{
			CommonFunction.alert_NoUsertoken(getApplicationContext());
		}
		
		//应根据用户名（或手机号）找回
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), SettingsActivity.class);
			Bundle b = new Bundle();
			b.putString("userToken", userToken);
			intent.putExtra("idValue", b);
			startActivity(intent);
			finish();
		}
		return true;
	}
}
