package com.zuma.syn;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.zuma.sql.GetActivitybyCount;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Editable;
import android.widget.Toast;

public class GetAcitivityByCountTask extends AsyncTask<Object, Object, Integer> {

	private Activity a;
	private String userToken;

	@Override
	protected Integer doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		userToken = (String) arg0[0];
		GetActivitybyCount.getActivityByCount(userToken, "0", "3");

		return 1;
	}

	protected void onPostExecute(HashMap<String, String> result) {
		// TODO Auto-generated method stub
		Set<String> mapSet = result.keySet(); // ��ȡ���е�keyֵ Ϊset�ļ���
		Iterator<String> itor = mapSet.iterator(); // ��ȡkey��Iterator����
		while (itor.hasNext()) { // ������һ��ֵ
			String key = itor.next(); // ��ǰkeyֵ
			if (result.get(key).equals("success")) { // ��ȡvalue �� ��֪����value�Ƚ�
				result.values();
				Toast.makeText(a, "��ȡ����ʧ�ܣ����Ժ�", Toast.LENGTH_LONG);
			}
		}
	}
}
