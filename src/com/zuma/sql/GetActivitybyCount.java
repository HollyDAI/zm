package com.zuma.sql;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.text.Editable;
import android.util.Log;

import com.zuma.base.C.api;

public class GetActivitybyCount {

	public static ArrayList<HashMap<String, String>> getActivityByCount(
			CharSequence userToken, CharSequence activityid, CharSequence count) {
		int success = 0;
		String uriAPI = api.actList;
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		Log.i("HomeActivity getDATAurl", uriAPI);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userToken", userToken.toString()));
		params.add(new BasicNameValuePair("activityId", activityid.toString()));
		params.add(new BasicNameValuePair("count", count.toString()));

		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(uriAPI);
			httppost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse httpresponse = httpclient.execute(httppost);
			HttpEntity httpentity = httpresponse.getEntity();

			InputStream is = httpentity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line = br.readLine();

			if (line != null && !line.equals(br)) {
				JSONTokener jsonParser = new JSONTokener(line);
				JSONObject js = (JSONObject) jsonParser.nextValue();
				// 接下来的就是JSON对象的操作了
				JSONArray numberList = js.getJSONArray("actList");
				for (int i = 0; i < numberList.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();

					map.put("id", numberList.getJSONObject(i).getInt("id") + "");
					map.put("title",
							numberList.getJSONObject(i).getString("title"));
					map.put("numLimit",
							numberList.getJSONObject(i).getInt("numLimit") + "");
					map.put("state",
							numberList.getJSONObject(i).getString("state"));
					map.put("ownerId",
							numberList.getJSONObject(i).getString("ownerId"));
					map.put("desc",
							numberList.getJSONObject(i).getString("desc"));
					map.put("proposeTime", numberList.getJSONObject(i)
							.getString("proposeTime"));
					map.put("deadLine",
							numberList.getJSONObject(i).getString("deadLine"));
					map.put("maleLimit",
							numberList.getJSONObject(i).getString("maleLimit"));
					map.put("femaleLimit", numberList.getJSONObject(i)
							.getString("femaleLimit"));

					result.add(map);
				}
				success = js.getInt("success");

				if (success != 1) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("success", "0");
					result.add(map);
				}
				Log.i("看取回的result和success", result + "\n" + success);
			}
		} catch (Exception e) {
			Log.e("GetAcitivitybyCountError", e.getMessage());
		}

		return result;
	}
}
