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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.common.zuma.R;
import com.zuma.base.C.api;
import com.zuma.sql.Communicate_with_sql;

public class PublishActivity extends Activity {

	private Bundle b;
	private String userToken;

	private String climit, crenshu, cjiezhi, cbiaoti, cshijian,
			cmiaoshu, crenshumale, crenshufemale;
	private int success;
	private EditText renshu, jiezhi, biaoti, shijian, miaoshu, limit,renshumale,renshufemale;
	private Button fabu;
	// private static final String[] m = { "学术", "美食", "体育", "旅行", "娱乐",
	// "其他" };
//	private Spinner spinner;
//	private ArrayAdapter<String> adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.faqi);
		fabu = (Button) findViewById(R.id.fqfabu);
		fabu.setOnClickListener(new fabuListener());
		renshu = (EditText) findViewById(R.id.fqrenshu);
		renshumale = (EditText)findViewById(R.id.fqrenshumale);
		renshufemale = (EditText)findViewById(R.id.fqrenshufemale);
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

	@SuppressLint("HandlerLeak")
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
			crenshumale = renshumale.getText().toString();
			crenshufemale = renshufemale.getText().toString();

			if(cbiaoti.equals("")){
				Toast.makeText(getApplicationContext(), "请填入标题！", Toast.LENGTH_SHORT).show();
			}else if(crenshu.equals("")){
				Toast.makeText(getApplicationContext(), "请填入人数限制！", Toast.LENGTH_SHORT).show();
			}else if(crenshu.equals("0")){
				Toast.makeText(getApplicationContext(), "人数限制需大于0！", Toast.LENGTH_SHORT).show();
			}else if(cshijian.equals("")){
				Toast.makeText(getApplicationContext(), "请填入活动时间！", Toast.LENGTH_SHORT).show();
			}else if(cjiezhi.equals("")){
				Toast.makeText(getApplicationContext(), "请填入截止时间！", Toast.LENGTH_SHORT).show();
			}else{
				new Thread(runnable).start();
			}
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
							HomeActivity.class);
					Bundle b = new Bundle();
					b.putString("userToken", userToken);
					// 此处使用putExtras，接受方就响应的使用getExtra
					mainIntent.putExtra("idValue",b);
					startActivity(mainIntent);
					finish();
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
				params.add(new BasicNameValuePair("limitMale", crenshumale));
				params.add(new BasicNameValuePair("limitFemale", crenshufemale));
				params.add(new BasicNameValuePair("deadline", cjiezhi));
				params.add(new BasicNameValuePair("title", cbiaoti));
				params.add(new BasicNameValuePair("time", cshijian));
				params.add(new BasicNameValuePair("desc", cmiaoshu));

//				(new Timestamp(System.currentTimeMillis())) + ""
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
