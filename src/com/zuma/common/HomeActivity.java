package com.zuma.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.common.zuma.R;
import com.zuma.base.C.api;
import com.zuma.sql.Communicate_with_sql;

public class HomeActivity extends Activity {

	private final static int ITEM_INDEX = 10;
	private Bundle b;
	private String userToken;
	private int id, numLimit, state, ownerId, maleLimit, femaleLimit;
	private Button faqi, xiaoxi, shezhi;
	// , canjia, btn[];
	private String title, desc, proposeTime, deadLine;
	private Communicate_with_sql sql = new Communicate_with_sql();

	private int success = 0;
	private int alerthome = 0;

	private HashMap<String, Object> map;

	private String strs[] = { "title", "deadline", "numLimit" };
	private int ids[] = { R.id.zyhuodongming, R.id.zyjiezhishijian,
			R.id.zyrenshuxianzhi };

	private SimpleAdapter sa;
	private ListView lv;
	private List<GetAcitivityTask> ltask = new ArrayList<GetAcitivityTask>();
	private List<Map<String, Object>> ldata = new ArrayList<Map<String, Object>>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhuye);

		b = getIntent().getBundleExtra("idValue");
		userToken = b.getString("userToken");

		faqi = (Button) findViewById(R.id.zyfaqi);
		faqi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(HomeActivity.this,
						PublishActivity.class);
				b.putString("userToken", userToken);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtra("idValue", b);
				HomeActivity.this.startActivity(mainIntent);
			}
		});
		xiaoxi = (Button) findViewById(R.id.zyxiaoxi);
		xiaoxi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(HomeActivity.this,
						MessageListActivity.class);
				b.putString("userToken", userToken);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtra("idValue", b);
				HomeActivity.this.startActivity(mainIntent);
			}
		});
		shezhi = (Button) findViewById(R.id.zyshezhi);
		shezhi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(HomeActivity.this,
						SettingsActivity.class);
				b.putString("userToken", userToken);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtra("idValue", b);
				HomeActivity.this.startActivity(mainIntent);
			}
		});

		//
		setupListView();

		System.out.println("=============HomeActivity=============");

	}

	public List<Map<String, Object>> getData() {
		return ldata;
	}

	public void setData() {
		Map<String, Object> map;
		for (int i = 0; i < ITEM_INDEX; i++) {
			map = new HashMap<String, Object>();
			map.put(strs[0], "数据加载中...");
			map.put(strs[1], "数据加载中...");
			map.put(strs[2], "数据加载中...");
			ldata.add(map);
		}
	}

	public void setupListView() {

		lv = (ListView) findViewById(R.id.listView2);
		setData();
		setSimpleAdapter();
		lv.setAdapter(sa);

		for (int i = 0; i < ITEM_INDEX; i++) {
			ltask.add(new GetAcitivityTask());
			ltask.get(i).execute(i, ldata.get(i));
		}

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MessageDetailActivity.class);
				startActivity(intent);
			}
		});
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});

	}

	public SimpleAdapter getSimpleAdapter() {
		return sa;
	}

	// public void setSaToListView() {
	// lv.setAdapter(sa);
	// }

	private void setSimpleAdapter() {
		// TODO Auto-generated method stub
		sa = new SimpleAdapter(HomeActivity.this, ldata, R.layout.list_item,
				strs, ids);

		sa.setViewBinder(new SimpleAdapter.ViewBinder() {

			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				// if (view instanceof ImageView && data instanceof Bitmap) {
				// ImageView iv = (ImageView) view;
				// iv.setImageBitmap((Bitmap) data);
				return true;
				// } else
				// return false;
			}
		});
	}

	public class GetAcitivityTask extends AsyncTask<Object, Object, Integer> {

		private boolean isRunning;

		@Override
		protected Integer doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			isRunning = true;
			String uriAPI = api.actList;
			String position = (Integer) arg0[0] + "";
			map = (HashMap<String, Object>) arg0[1];

			try {
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userToken", userToken
						.toString()));
				params.add(new BasicNameValuePair("activityId", position));
				params.add(new BasicNameValuePair("count", "1"));

				JSONTokener jsonParser = new JSONTokener(sql.request(uriAPI,
						params));
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
				Log.i("Getactivity by count", id + title + numLimit + state);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("JSON数据错误", e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return success;
		}

		protected void onPostExecute(Integer success) {
			// TODO Auto-generated method stub

			System.out.println("=======tasktask==========");
			if (success == 1) {
				map.put("id", id);
				map.put("title", title);
				map.put("numLimit", numLimit);
				map.put("state", state);
				map.put("ownerId", ownerId);
				map.put("desc", desc);
				map.put("proposeTime", proposeTime);
				map.put("deadLine", deadLine);
				map.put("maleLimit", maleLimit);
				map.put("femaleLimit", femaleLimit);
				ldata.add(map);

				sa.notifyDataSetChanged();
				isRunning = false;
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			alerthome ++;
			if(alerthome%2==1){
				Toast.makeText(getApplicationContext(), "再按返回键退出",Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "谢谢使用！", Toast.LENGTH_SHORT).show();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
