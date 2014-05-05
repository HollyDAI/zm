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
		Set<String> mapSet = result.keySet(); // 获取所有的key值 为set的集合
		Iterator<String> itor = mapSet.iterator(); // 获取key的Iterator便利
		while (itor.hasNext()) { // 存在下一个值
			String key = itor.next(); // 当前key值
			if (result.get(key).equals("success")) { // 获取value 与 所知道的value比较
				result.values();
				Toast.makeText(a, "获取数据失败，请稍候！", Toast.LENGTH_LONG);
			}
		}
	}
}
