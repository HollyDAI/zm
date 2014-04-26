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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	private Button faqi, xiaoxi, shezhi, canjia, btn[];
	private String fanhui, yonghu;
	private int renshu, i, j, chenggong, gid[], grenshuxianzhi[], gshijian[],
			gjiezhishijian[];
	private String ghuodongming[], gzhushi[];

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhuye);

		// yonghu = getIntent().getExtras().getString("userToken");
		yonghu = "ABCholly";

		// shuju();
		faqi = (Button) findViewById(R.id.zyfaqi);
		faqi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(HomeActivity.this,
						PublishActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", yonghu);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtras(b);
				HomeActivity.this.startActivity(mainIntent);
			}
		});
		xiaoxi = (Button) findViewById(R.id.zyxiaoxi);
		xiaoxi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(HomeActivity.this,
						MessageListActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", yonghu);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtras(b);
				HomeActivity.this.startActivity(mainIntent);
			}
		});
		shezhi = (Button) findViewById(R.id.zyshezhi);
		shezhi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(HomeActivity.this,
						SettingsActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", yonghu);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtras(b);
				HomeActivity.this.startActivity(mainIntent);
			}
		});
		ListView list = (ListView) findViewById(R.id.listView2);
		ArrayList<HashMap<String, Object>> mylist = buildList();
		MySimpleAdapter mSchedule = new MySimpleAdapter(this, // 没什么解释
				mylist, // 数据来源
				R.layout.list_item, // ListItem的XML实现
				// 动态数组与ListItem对应的子项
				new String[] { "huodongming", "renshuxianzhi", "shijian",
						"zhushi", "jiezhishijian", "touxiang", "canjia" },
				// ListItem的XML文件里面的两个TextView ID
				new int[] { R.id.zyhuodongming, R.id.zyrenshuxianzhi,
						R.id.zyshijian, R.id.zyzhushi, R.id.zyjiezhishijian,
						R.id.img, R.id.zycanjia });

		list.setAdapter(mSchedule);
	}

	private ArrayList<HashMap<String, Object>> buildList() {

		ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
		// 添加list内容
		for (j = 0; j < i; j++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("huodongming", ghuodongming[j]);
			map.put("touxiang",
					"http: www.baidu.com/img/baidu_logo_jr_1003_315.gif");
			map.put("renshuxianzhi", grenshuxianzhi[j]);
			map.put("shijian", gshijian[j]);
			map.put("zhushi", gzhushi[j]);
			map.put("jiezhishijian", gjiezhishijian[j]);
			map.put("canjia", gid[j]);
			mylist.add(map);
		}
		return mylist;
	}

	public void shuju() {
		String uriAPI = "http: 192.168.32.5:8000/activity/dig?userToken="
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
			JSONArray numberList = js.getJSONArray("activities");
			for (i = 0; i < numberList.length(); i++) {
				gid[i] = numberList.getJSONObject(i).getInt("id");
				ghuodongming[i] = numberList.getJSONObject(i)
						.getString("title");
				grenshuxianzhi[i] = numberList.getJSONObject(i).getInt("limit");
				gshijian[i] = numberList.getJSONObject(i).getInt("proposeTime");
				gzhushi[i] = numberList.getJSONObject(i).getString(
						"description");
				gjiezhishijian[i] = numberList.getJSONObject(i).getInt(
						"deadLine");
			}
			chenggong = js.getInt("success");
		} catch (JSONException ex) {
			// 异常处理代码
		}
	}
}
