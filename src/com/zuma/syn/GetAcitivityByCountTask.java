package com.zuma.syn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.zuma.base.C.api;
import com.zuma.sql.Communicate_with_sql;

public class GetAcitivityByCountTask extends AsyncTask<Object, Object, Integer> {

	private SimpleAdapter sa;
	
	private boolean isRunning;
	private int success = 0;
	private String userToken;
	private ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
	
	private int id, numLimit, state, ownerId, maleLimit, femaleLimit;
	private String title, desc, proposeTime, deadLine;
	
	private Communicate_with_sql sql = new Communicate_with_sql();
	private HashMap<String, Object> map;

	@Override
	protected Integer doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		isRunning=true;
		
		String uriAPI = api.actList;
		userToken = (String) arg0[0];
		sa = (SimpleAdapter) arg0[1];
		
		try {
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userToken", userToken.toString()));
			params.add(new BasicNameValuePair("activityId", "0"));
			params.add(new BasicNameValuePair("count", "1"));
			
			JSONTokener jsonParser = new JSONTokener(
					sql.request(uriAPI, params));
			JSONObject js = (JSONObject) jsonParser.nextValue();
			JSONArray actList = js.getJSONArray("actList");

			if (actList != null && actList.length() > 0) {
				for (int i = 0; i < actList.length(); i++) {
					JSONObject jo = actList.getJSONObject(i);

					id = jo.getInt("id");
					title = jo.getString("title");
					numLimit = jo.getInt("numLimit");
					state = jo.getInt("state");
					ownerId = jo.getInt("ownerId");
					desc = jo.getString("desc");
					proposeTime = jo.getString("proposeTime");
					deadLine = jo.getString("deadLine");
					maleLimit = jo.getInt("maleLimit");
					femaleLimit = jo.getInt("femaleLimit");
				}
				success = js.getInt("success");
			}

			Log.i("Getactivity by count", "success=" + success);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// if (e.getMessage()
			// .equals("Value Notice of type java.lang.String cannot be converted to JSONArray"))
			// title = "无更多信息";
			// else
			// title = "TAT数据库读取出错啦";

			Log.e("JSON数据错误", e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return success;
	}

	protected void onPostExecute(int success) {
		// TODO Auto-generated method stub
		map.put("id",id);
		map.put("title",title);
		map.put("numLimit",numLimit);
		map.put("state",state);
		map.put("ownerId",ownerId);
		map.put("desc",desc);
		map.put("proposeTime", proposeTime);
		map.put("deadLine",deadLine);
		map.put("maleLimit",maleLimit);
		map.put("femaleLimit",femaleLimit);
		
		sa.notifyDataSetChanged();
		isRunning=false;
	}
}
