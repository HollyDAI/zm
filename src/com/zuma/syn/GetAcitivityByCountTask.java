package com.zuma.syn;

import com.zuma.sql.GetActivitybyCount;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Editable;

public class GetAcitivityByCountTask extends AsyncTask<Object, Object, Integer> {
	
	private Activity a;
	private String userToken;
	
	@Override
	protected Integer doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		userToken = (String)arg0[0];
		GetActivitybyCount.getActivityByCount(userToken,"0", "3");
		
		return 1;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
