package com.zuma.syn;

import java.io.Serializable;

import com.zuma.action.LoginAction;
import com.zuma.common.HomeActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

public class DologinTask extends AsyncTask<Object, Object, Boolean> {

	private Editable username;
	private Editable password;
	private Activity a;
	private ProgressDialog pd;

	@Override
	protected Boolean doInBackground(Object... arg0) {
		// TODO Auto-generated method stub

		username = (Editable) arg0[0];
		password = (Editable) arg0[1];
		a = (Activity) arg0[2];
		pd = (ProgressDialog) arg0[3];

		return LoginAction.isCorrectedUserAndPass(username, password);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		pd.dismiss();
		if (result) {
			Intent intent = new Intent(a, HomeActivity.class);
			Bundle b = new Bundle();
			b.putString("userToken", LoginAction.getuserToken());
			b.putString("username", username.toString());
			// b.putSerializable("contactWays", (Serializable)
			// LoginAction.getContactWays());

			intent.putExtra("idValue", b);
			// intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			a.startActivity(intent);
			a.finish();
			Toast.makeText(a, "��¼�ɹ�", Toast.LENGTH_SHORT).show();
		} else
			Toast.makeText(a, "��¼ʧ��,��������ȷ���û���������", Toast.LENGTH_LONG).show();
	}
}