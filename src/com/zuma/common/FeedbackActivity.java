package com.zuma.common;

import com.common.zuma.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackActivity extends Activity {

	private String userToken = null;
	
	private Button commit = null;
	private EditText comment = null;
	private int success = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tucaozhongxin);
		comment = (EditText) findViewById(R.id.tctucao);
		commit = (Button) findViewById(R.id.tctijiao);

		Bundle b = getIntent().getBundleExtra("idValue");
		userToken = b.getString("userToken");
		
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String scomment = comment.getText().toString();
				if (scomment.equals("")) {
					Toast.makeText(getApplicationContext(), "请输入您想说的话",
							Toast.LENGTH_LONG).show();
				} else {
					// new Thread().start(); //用于上传用户反馈的意见
					success = 1;
					
					if (success == 1) {
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(),
								SettingsActivity.class);
						Bundle b = new Bundle();
						b.putString("userToken", userToken);
						intent.putExtra("idValue",b);
						startActivity(intent);
						finish();
						Toast.makeText(getApplicationContext(),
								R.string.tucao_success, Toast.LENGTH_LONG)
								.show();
					}
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), SettingsActivity.class);
			Bundle b = new Bundle();
			b.putString("userToken", userToken);
			startActivity(intent);
			finish();
		}
		return true;
	}
}
