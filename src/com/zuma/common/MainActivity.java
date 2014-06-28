package com.zuma.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.common.zuma.R;
import com.zuma.sql.LoginAction;

public class MainActivity extends Activity {

	private Button confirm = null;
	private Button findPwd = null;
	private Button register = null;
	private EditText editAccount = null;
	private EditText editPwd = null;
	private String account, pwd = null;
	private ProgressDialog pd = null;

	private Bundle b = null;
	private boolean isExit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zuma);

		pd = new ProgressDialog(MainActivity.this);
		pd.setTitle("请稍等");
		pd.setMessage("正在登录……");

		if (getIntent().getBundleExtra("reg") != null) {
			b = getIntent().getBundleExtra("reg");
			String action = b.getString("action");
			if (action.equals("reg")) {
				account = b.getString("username");
				pwd = b.getString("password");
				(new DologinTask()).execute(account, pwd);
			} else {

			}

		}
		editAccount = (EditText) findViewById(R.id.dlyonghuming);
		editPwd = (EditText) findViewById(R.id.dlmima);
		confirm = (Button) findViewById(R.id.dlqueding);
		confirm.setOnClickListener(new confirmListener());
		findPwd = (Button) findViewById(R.id.dlzhaohui);
		findPwd.setOnClickListener(new findPwdListener());
		register = (Button) findViewById(R.id.dlzhuce);
		register.setOnClickListener(new registerListener());
	}

	class confirmListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			account = editAccount.getText().toString();
			pwd = editPwd.getText().toString();
			(new DologinTask()).execute(account, pwd);
		}
	}

	class findPwdListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent mainIntent = new Intent(MainActivity.this,
					FindPwdActivity.class);
			MainActivity.this.startActivity(mainIntent);
		}

	}

	class registerListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent mainIntent = new Intent(MainActivity.this,
					RegisterActivity.class);
			MainActivity.this.startActivity(mainIntent);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**** 异步操作 ****/
	public class DologinTask extends AsyncTask<Object, Object, Boolean> {

		private Boolean isrunning = true;
		private String username;
		private String password;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.show();
		}

		@Override
		protected Boolean doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			// Boolean b = false;
			while (isrunning) {
				username = (String) arg0[0];
				password = (String) arg0[1];
				return LoginAction.isCorrectedUserAndPass(username, password);
			}
			return isrunning;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			pd.dismiss();
			if (result) {
				Intent intent = new Intent(MainActivity.this,
						HomeActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", LoginAction.getuserToken());

				intent.putExtra("idValue", b);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				finish();
				Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT)
						.show();
				isrunning = false;
			} else
				Toast.makeText(MainActivity.this, "登录失败,请输入正确的用户名、密码",
						Toast.LENGTH_LONG).show();
		}

	}

	@SuppressLint("HandlerLeak")
	Handler exitHandler = new Handler(){

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			isExit = false;
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(isExit){
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			} else {
				isExit = true;
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitHandler.sendEmptyMessageDelayed(0, 2000);
			}
		}
		return isExit;
	}
	
	
}
