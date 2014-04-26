package com.zuma.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.common.zuma.R;
import com.zuma.util.MySimpleAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MessageListActivity extends Activity {
	String fanhui, yonghu, ghuodongming[], cid, chuodongid;
	int i, j, gxiaoxishu[], gid[], id, chenggong, ghuodongid[];
	ArrayList<HashMap<String, Object>> mylist = buildList();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xiaoxi1);
		yonghu = getIntent().getExtras().getString("userToken");
		/* shuju(); */
		final ListView list = (ListView) findViewById(R.id.listView1);
		final MySimpleAdapter mSchedule = new MySimpleAdapter(this, // 没什么解释
				mylist,// 数据来源
				R.layout.list_item2,// ListItem的XML实现
				// 动态数组与ListItem对应的子项
				new String[] { "huodongming", "xiaoxishu" },
				// ListItem的XML文件里面的两个TextView ID
				new int[] { R.id.xxhuodongming, R.id.xxxiaoxishu });

		list.setAdapter(mSchedule);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				cid = gid[arg2] + "";
				chuodongid = ghuodongid[arg2] + "";
				Intent mainIntent = new Intent(MessageListActivity.this, MessageDetailActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", yonghu);
				b.putString("id", cid);
				b.putString("huodongid", chuodongid);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtras(b);
				MessageListActivity.this.startActivity(mainIntent);
			}
		});
		list.setOnItemLongClickListener(new OnItemLongClickListener() { // 和大神商议
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(MessageListActivity.this);
				builder.setMessage("确认删除此条目吗？");
				builder.setPositiveButton("确认", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mylist.remove(arg2);
						mSchedule.notifyDataSetChanged();
						list.invalidate();

					}
				});
				builder.setNegativeButton("取消", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.create().show();
				return true;

			}

		});

	}

	private ArrayList<HashMap<String, Object>> buildList() {

		ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
		// 添加list内容
		for (j = 1; j < i; j++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("huodongming", ghuodongming[j]);
			map.put("xiaoxishu", gxiaoxishu[j]);
			mylist.add(map);
		}
		return mylist;

	}

	public void shuju() {
		String uriAPI = "http://192.168.32.5:8000/message/list?userToken="
				+ yonghu;
		/* 建立HTTP Post联机 */
		HttpPost httpRequest = new HttpPost(uriAPI);
		/*
		 * Post运作传送变量必须用NameValuePair[]数组储存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userToken", yonghu));
		try {
			/* 发出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出响应字符串 */
				fanhui = EntityUtils.toString(httpResponse.getEntity());
			} else {
				Toast.makeText(getApplicationContext(), "网络错误！",
						Toast.LENGTH_SHORT).show();
			}
		} catch (ClientProtocolException e) {
			Toast.makeText(getApplicationContext(), "网络错误！", Toast.LENGTH_SHORT)
					.show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "网络错误！", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "网络错误！", Toast.LENGTH_SHORT)
					.show();
		}
		try {

			JSONTokener jsonParser = new JSONTokener(fanhui);
			JSONObject js = (JSONObject) jsonParser.nextValue();
			// 接下来的就是JSON对象的操作了
			JSONArray numberList = js.getJSONArray("messages");
			for (i = 1; i < numberList.length(); i++) {
				gid[i] = numberList.getJSONObject(i).getInt("id");
				ghuodongming[i] = numberList.getJSONObject(i)
						.getString("title");
				gxiaoxishu[i] = numberList.getJSONObject(i).getInt("new");
				ghuodongid[i] = numberList.getJSONObject(i)
						.getInt("activityId");
			}
			chenggong = js.getInt("success");
		} catch (JSONException ex) {
			// 异常处理代码
		}
	}
}
