package com.zuma.common;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.common.zuma.R;
import com.zuma.base.C.api;
import com.zuma.sql.Communicate_with_sql;

public class PublishActivity extends Activity {

	private Bundle b;
	private String userToken;

	private String climit, fanhui, crenshu, cjiezhi, cbiaoti, cshijian,
			cmiaoshu;
	private int success;
	private EditText renshu, jiezhi, biaoti, shijian, miaoshu, limit;
	private Button fabu;
	// private static final String[] m = { "学术", "美食", "体育", "旅行", "娱乐",
	// "其他" };
	private Spinner spinner;
	private ArrayAdapter<String> adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.faqi);
		fabu = (Button) findViewById(R.id.fqfabu);
		fabu.setOnClickListener(new fabuListener());
		renshu = (EditText) findViewById(R.id.fqrenshu);
		jiezhi = (EditText) findViewById(R.id.fqjiezhi);
		biaoti = (EditText) findViewById(R.id.fqbiaoti);
		shijian = (EditText) findViewById(R.id.fqshijian);
		miaoshu = (EditText) findViewById(R.id.fqmiaoshu);
		limit = (EditText) findViewById(R.id.fqyaoqiu);

		// spinner = (Spinner) findViewById(R.id.infoTypeSpinner);
		// 将可选内容与ArrayAdapter连接起来
		// adapter = new
		// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

		// 设置下拉列表的风格
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		// spinner.setAdapter(adapter);

		// 添加事件Spinner事件监听
		// spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		// spinner.setVisibility(View.VISIBLE);

		b = getIntent().getBundleExtra("idValue");
		userToken = b.getString("userToken");

	}

	class fabuListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			crenshu = renshu.getText().toString();
			cjiezhi = jiezhi.getText().toString();
			cbiaoti = biaoti.getText().toString();
			cshijian = shijian.getText().toString();
			cmiaoshu = miaoshu.getText().toString();
			climit = limit.getText().toString();

			new Thread(runnable).start();
		}

		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				int success = data.getInt("success");
				Log.i("publish handler", "success=" + success);
				if (success == 1) {
					Toast.makeText(PublishActivity.this, "消息发布成功啦！",
							Toast.LENGTH_LONG).show();
					Intent mainIntent = new Intent(PublishActivity.this,
							MessageListActivity.class);
					Bundle b = new Bundle();
					b.putString("userToken", userToken);
					// 此处使用putExtras，接受方就响应的使用getExtra
					mainIntent.putExtras(b);
					PublishActivity.this.startActivity(mainIntent);
				} else {
					Toast.makeText(getApplicationContext(), "消息发布失败(┬＿┬)请重新发布",
							Toast.LENGTH_SHORT).show();
				}
			}
		};

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				//
				// TODO: http request.
				//
				Message msg = new Message();
				String uriAPI = api.postAct;

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userToken", userToken));
				params.add(new BasicNameValuePair("limit", crenshu));
				params.add(new BasicNameValuePair("limitMale", "0"));
				params.add(new BasicNameValuePair("limitFemale", "0"));
				params.add(new BasicNameValuePair("deadline", cjiezhi));
				params.add(new BasicNameValuePair("title", cbiaoti));
				params.add(new BasicNameValuePair("time", (new Timestamp(System
						.currentTimeMillis())) + ""));
				params.add(new BasicNameValuePair("desc", cmiaoshu));

				// params.add(new BasicNameValuePair("limit", yaoqiu));

				Communicate_with_sql sql = null;
				try {
					sql = new Communicate_with_sql();
					JSONTokener jsonParser = new JSONTokener(sql.request(
							uriAPI, params));
					JSONObject js = (JSONObject) jsonParser.nextValue();
					// 接下来的就是JSON对象的操作了
					success = js.getInt("success");
					Log.i("publish Message", "success=" + success);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bundle b = new Bundle();
				b.putInt("success", success);
				msg.setData(b);
				handler.sendMessage(msg);
			}
		};
	}
}

// 使用数组形式操作
// class SpinnerSelectedListener implements OnItemSelectedListener {
//
// public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
// long arg3) {
// //
// }
//
// public void onNothingSelected(AdapterView<?> arg0) {
// }
// }
