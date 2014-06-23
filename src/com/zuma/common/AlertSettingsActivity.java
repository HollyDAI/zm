package com.zuma.common;

import com.common.zuma.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class AlertSettingsActivity extends Activity {

	private Button commit = null;
	private String userToken = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tixingshezhi);
		
		userToken = getIntent().getBundleExtra("idValue").getString("userToken");
		
		commit = (Button) findViewById(R.id.txwancheng);
		commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), SettingsActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", userToken);
				intent.putExtra("idValue", b);
				startActivity(intent);
				finish();
				Toast.makeText(getApplicationContext(), R.string.setting_success, Toast.LENGTH_SHORT).show();
			}
		});
	}

	
}
