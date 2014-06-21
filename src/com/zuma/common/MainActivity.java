package com.zuma.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
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
	private ProgressDialog pd = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zuma);
		
		pd = new ProgressDialog(MainActivity.this);
		pd.setTitle("请稍等");
		pd.setMessage("正在登录……");
		
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
			(new DologinTask()).execute(editAccount.getText(),editPwd.getText());
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

	/****异步操作****/
	public class DologinTask extends AsyncTask<Object, Object, Boolean> {

		private Boolean isrunning = true;
		private Editable username;
		private Editable password;

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
				username = (Editable) arg0[0];
				password = (Editable) arg0[1];
				return LoginAction.isCorrectedUserAndPass(username, password);
			}
			return isrunning;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			pd.dismiss();
			if (result) {
				Intent intent = new Intent(MainActivity.this, HomeActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", LoginAction.getuserToken());

				intent.putExtra("idValue", b);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				finish();
				Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
				isrunning = false;
			} else
				Toast.makeText(MainActivity.this, "登录失败,请输入正确的用户名、密码", Toast.LENGTH_LONG).show();
		}

	}
}
