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

import com.zuma.base.C.api;
import com.zuma.sql.Communicate_with_sql;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleAdapter;

public class LoadImageTask extends AsyncTask<Object, Object, Integer> {

	@SuppressWarnings("unused")
	private boolean isRunning;

	private int id, numLimit, state, ownerId, maleLimit, femaleLimit;
	private String title, desc, proposeTime, deadLine;
	private int success = 0;
	
	private SimpleAdapter sa;
	private HashMap<String, Object> map;
	private Communicate_with_sql sql = new Communicate_with_sql();

	@SuppressWarnings("unchecked")
	@Override
	protected Integer doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		isRunning = true;

		String uriAPI = api.actList;
		String position = (Integer)arg0[0]+"";
		map = (HashMap<String, Object>) arg0[1];
		
		try {
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("activityId", position));
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
			Log.i("Getactivity by count", id+title+numLimit+state);
		} catch (JSONException e) {
			Log.e("JSONÊý¾Ý´íÎó", e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return success;
	}

	protected void onPostExecute(int success) {

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
