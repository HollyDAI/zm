package com.zuma.syn;

import java.io.Serializable;

import com.zuma.common.HomeActivity;
import com.zuma.sql.LoginAction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

public class DologinTask extends AsyncTask<Object, Object, Boolean> {

	private Boolean isrunning = true;
	private Editable username;
	private Editable password;
	private Activity a;
	private ProgressDialog pd;

	@Override
	protected Boolean doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		// Boolean b = false;
		while (isrunning) {
			username = (Editable) arg0[0];
			password = (Editable) arg0[1];
			a = (Activity) arg0[2];
			pd = (ProgressDialog) arg0[3];

			return LoginAction.isCorrectedUserAndPass(username, password);
		}
		return isrunning;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		pd.dismiss();
		if (result) {
			Intent intent = new Intent(a, HomeActivity.class);
			Bundle b = new Bundle();
			b.putString("userToken", LoginAction.getuserToken());
			// b.putSerializable("contactWays", (Serializable)
			// LoginAction.getContactWays());

			intent.putExtra("idValue", b);
			// intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			a.startActivity(intent);
			a.finish();
			Toast.makeText(a, "登录成功", Toast.LENGTH_SHORT).show();
			isrunning = false;
		} else
			Toast.makeText(a, "登录失败,请输入正确的用户名、密码", Toast.LENGTH_LONG).show();
	}

}