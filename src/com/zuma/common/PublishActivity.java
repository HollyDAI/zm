package com.zuma.common;

import java.io.IOException;
import java.util.ArrayList;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.common.zuma.R;
import com.zuma.base.C;
import com.zuma.base.C.api;

public class PublishActivity extends Activity {
	
	private Bundle b;
	private String userToken;
	
	private String climit, fanhui, crenshu, cjiezhi, cbiaoti, cshijian,
			cmiaoshu, yonghu;
	private int success;
	private EditText renshu, jiezhi, biaoti, shijian, miaoshu, limit;
	private Button fabu;
//	private static final String[] m = { "学术", "美食", "体育", "旅行", "娱乐",
//			"其他" };
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
		limit = (EditText)findViewById(R.id.fqyaoqiu);
		
		//spinner = (Spinner) findViewById(R.id.infoTypeSpinner);
		//将可选内容与ArrayAdapter连接起来
		//adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
		
		//设置下拉列表的风格
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//将adapter 添加到spinner中
		//spinner.setAdapter(adapter);
		
		//添加事件Spinner事件监听  
//		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		
		//设置默认值
		//spinner.setVisibility(View.VISIBLE);
		

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
			try {
				
				shuju();
				
				if (success == 1) {
					Toast.makeText(PublishActivity.this, "消息发布成功啦！", Toast.LENGTH_LONG);
					Intent mainIntent = new Intent(PublishActivity.this,
							MessageListActivity.class);
					Bundle b = new Bundle();
					b.putString("userToken", yonghu);
					// 此处使用putExtras，接受方就响应的使用getExtra
					mainIntent.putExtras(b);
					PublishActivity.this.startActivity(mainIntent);
				} else {
					Toast.makeText(getApplicationContext(),
							"消息发布失败(┬＿┬)请重新发布", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "网络错误！",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	public void shuju() {
		String uriAPI = api.postAct;

		HttpPost httpRequest = new HttpPost(uriAPI);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userToken", yonghu));
		params.add(new BasicNameValuePair("limit", crenshu));
		params.add(new BasicNameValuePair("deadline", cjiezhi));
		params.add(new BasicNameValuePair("title", cbiaoti));
		params.add(new BasicNameValuePair("time", cshijian));
		params.add(new BasicNameValuePair("description", cmiaoshu));
		
//		params.add(new BasicNameValuePair("limit", yaoqiu));
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
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
			success = js.getInt("success");
		} catch (JSONException ex) {
			// 异常处理代码
		}
	}
	
	//使用数组形式操作
		class SpinnerSelectedListener implements OnItemSelectedListener{

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		}
}
