package com.zuma.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.common.zuma.R;
import com.zuma.syn.DologinTask;

public class MainActivity extends Activity {

	private Button confirm = null;
	private Button findPwd = null;
	private Button register = null;
	private EditText editAccount = null;
	private EditText editPwd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zuma);
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
			ProgressDialog pd = ProgressDialog.show(MainActivity.this, "ÇëÉÔµÈ",
					"ÕýÔÚµÇÂ¼¡­¡­");
			(new DologinTask()).execute(editAccount.getText(),
					editPwd.getText(), MainActivity.this, pd);
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

}
