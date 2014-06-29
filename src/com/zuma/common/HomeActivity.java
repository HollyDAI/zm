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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.common.zuma.R;
import com.zuma.base.C.api;
import com.zuma.sql.Communicate_with_sql;
import com.zuma.util.CommonFunction;
import com.zuma.util.SerializableMap;

@SuppressWarnings("unused")
public class HomeActivity extends Activity {

	private final static int ITEM_INDEX = 10;
	private Bundle b;
	private String userToken;
	private int id, numLimit, state, ownerId, maleLimit, femaleLimit;
	private Button faqi, xiaoxi, shezhi;

	private String title, desc, proposeTime, deadLine;
	private Communicate_with_sql sql = new Communicate_with_sql();

	private int success = 0;
	private boolean isExit = false;

	private HashMap<String, Object> map;

	private String strs[] = { "avatar", "title", "state", "limit", "time" };
	private int ids[] = { R.id.zyimg, R.id.zyhuodongming, R.id.zystate,
			R.id.zyrenshuxianzhi, R.id.zyshijian };

	private ProgressBar progressBar = null;
	private SimpleAdapter sa;
	private ListView lv;
	private List<GetAcitivityTask> ltask = new ArrayList<GetAcitivityTask>();
	private List<Map<String, Object>> ldata = new ArrayList<Map<String, Object>>();
	protected int eventMaxCount = 0;

	public int getEventMaxCount() {
		return eventMaxCount;
	}

	public void setEventMaxCount() {
		/* ！！！更改为获取到的活动总数！！！ */
		this.eventMaxCount = 25;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhuye);

		b = getIntent().getBundleExtra("idValue");
		if (b.getString("userToken") != null) {
			userToken = b.getString("userToken");
		} else {
			CommonFunction.alert_NoUsertoken(getApplicationContext());
		}

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
				mainIntent.putExtra("idValue", b);
				mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				HomeActivity.this.startActivity(mainIntent);
			}
		});

		setupListView();
	}

	public List<Map<String, Object>> getData() {
		return ldata;
	}

	public void setData() {
		Map<String, Object> map;
		for (int i = 0; i < ITEM_INDEX; i++) {
			map = new HashMap<String, Object>();
			map.put(strs[0], R.drawable.avatar);
			map.put(strs[1], "数据加载中...");
			ldata.add(map);
		}
	}

	public void setupFooterView() {

		progressBar = new ProgressBar(this);
		progressBar.setPadding(0, 0, 15, 0);
		lv.addFooterView(progressBar);
	}

	public void setupListView() {

		lv = (ListView) findViewById(R.id.listView2);
		setEventMaxCount();
		setupFooterView();
		setData();
		setSimpleAdapter();
		lv.setAdapter(sa);

		for (int i = 0; i < ITEM_INDEX; i++) {
			ltask.add(new GetAcitivityTask());
			ltask.get(i).execute(i, ldata.get(i));
		}

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						MessageDetailActivity.class);
				Bundle b = new Bundle();
				SerializableMap smap = new SerializableMap();
				smap.setMap(ldata.get(position));
				b.putSerializable("smap", smap);
				b.putString("userToken", userToken);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
		lv.setOnScrollListener(new OnScrollListener() {

			private int lastItem;

			boolean isLastItem = false;

			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == SCROLL_STATE_TOUCH_SCROLL && isLastItem) {
					Map<String, Object> map;

//					System.out.println("====stateChanged====" + "in" + lastItem  + "+3<<<<<" + eventMaxCount);
					
					if (lastItem + 3 < eventMaxCount) {
						
						for (int i = lastItem; i < lastItem + 3; i++) {
							
							map = new HashMap<String, Object>();
							map.put(strs[0], R.drawable.avatar);
							map.put(strs[1], "数据加载中");
							ldata.add(map);
							sa.notifyDataSetChanged();
							ltask.add(new GetAcitivityTask());
							ltask.get(ltask.size()-1).execute(i, map);
//							System.out.println("====No last ltask.size====" + (ltask.size()-1+""));
						}

					} else {
						for (int i = lastItem; i < eventMaxCount; i++) {
							map = new HashMap<String, Object>();
							map.put(strs[0], R.drawable.avatar);
							map.put(strs[1], "数据加载中");
							ldata.add(map);
							sa.notifyDataSetChanged();
							ltask.add(new GetAcitivityTask());
							ltask.get(ltask.size()-1).execute(i, map);
//							System.out.println("====last ltask.size====" + (ltask.size()-1+""));
							lv.removeFooterView(progressBar);
						}
						sa.notifyDataSetChanged();
					}
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (firstVisibleItem + visibleItemCount >= totalItemCount) {
					isLastItem = true;
					lastItem = totalItemCount;
					
//					System.out.println("====lastItem====" + lastItem);
				} else
					isLastItem = false;
			}
		});

	}

	public SimpleAdapter getSimpleAdapter() {
		return sa;
	}

	private void setSimpleAdapter() {
		// TODO Auto-generated method stub
		sa = new SimpleAdapter(HomeActivity.this, ldata, R.layout.list_item,
				strs, ids);

		sa.setViewBinder(new SimpleAdapter.ViewBinder() {

			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
					return true;
				} else
					return false;
			}
		});
	}

	public class GetAcitivityTask extends AsyncTask<Object, Object, Integer> {

		private boolean isRunning;

		@SuppressWarnings("unchecked")
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
			if (success == 1) {

				map.remove(strs[1]);

				map.put("id", id);
				map.put("title", title);

				/*
				 * map.put("nunlmLimit", numLimit); map.put("state", state);
				 * map.put("ownerId", ownerId); map.put("desc", desc);
				 * map.put("proposeTime", proposeTime); map.put("deadLine",
				 * deadLine); map.put("maleLimit", maleLimit);
				 * map.put("femaleLimit", femaleLimit);
				 */

				// if(state == ) //对state进行转换显示
				map.put("state", "状态:" + state);

				String limitstr = null;
				if (maleLimit == 0 && femaleLimit == 0) {
					limitstr = "无要求";
					map.put("limit", "人数:" + numLimit + "(" + limitstr + ")");
				} else {
					map.put("limit", "人数:" + numLimit + "(M:" + maleLimit
							+ "/F:" + femaleLimit + ")");
				}

				map.put("time", "时间:" + proposeTime.substring(0, 10) + " 至 "
						+ deadLine.substring(0, 10));

//				System.out.println("====ASYNC====map put =======" + ldata.size());

				HomeActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						sa.notifyDataSetChanged();
					}
				});

				isRunning = false;
			}
		}
	}

	@SuppressLint("HandlerLeak")
	Handler exitHandler = new Handler() {

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			isExit = false;
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit) {
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			} else {
				isExit = true;
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitHandler.sendEmptyMessageDelayed(0, 2000);
			}
		}
		return isExit;
	}

}
