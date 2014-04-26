package com.zuma.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.common.zuma.R;

public class MainActivity extends Activity {

	private Button confirm = null;
	private Button findPwd = null;
	private Button register = null;
	private EditText editAccount = null;
	private EditText editPwd = null;
	private String account, pwd, back, userName = "";
	private int result = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zuma);
		editAccount = (EditText) findViewById(R.id.dlyonghuming);
		editPwd = (EditText) findViewById(R.id.dlmima);
		confirm = (Button) findViewById(R.id.dlqueding);
		confirm.setOnClickListener(new confirmListener());
		findPwd = (Button) findViewById(R.id.dlzhaohui);
		findPwd.setOnClickListener(new findPwdListener());
		register = (Button) findViewById(R.id.dlzhuce);
		register.setOnClickListener(new registerListener());
	}

	class confirmListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			try {
				result = 1;			//测试用
//				getData();
				if (result == 1) {
					Intent mainIntent = new Intent(MainActivity.this,
							HomeActivity.class);
					Bundle b = new Bundle();
					b.putString("userToken", userName);
					// 此处使用putExtras，接受方就响应的使用getExtra
					mainIntent.putExtras(b);
					MainActivity.this.startActivity(mainIntent);
				} else {
					Toast.makeText(MainActivity.this, "用户名或密码错误，请检查以后再次输入！",
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "网络错误！",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	class findPwdListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent mainIntent = new Intent(MainActivity.this,
					FindPwdActivity.class);
			MainActivity.this.startActivity(mainIntent);
		}

	}

	class registerListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent mainIntent = new Intent(MainActivity.this,
					RegisterActivity.class);
			MainActivity.this.startActivity(mainIntent);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void getData() {
		account = editAccount.getText().toString();
		pwd = editPwd.getText().toString();
//		Log.d("input：",account+pwd);
		String uriAPI = "http://115.28.238.91:8000/user/login?password=" + pwd
				+ "&username=" + account;
		
		/* Post运作传送变量必须用NameValuePair[]数组储存   */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", account));
		params.add(new BasicNameValuePair("password", pwd));
		
		Log.d("input：",account+pwd);
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			/* 建立HTTP Post联机 */
			HttpPost httpRequest = new HttpPost(uriAPI);
			/* 发出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			int status = httpResponse.getStatusLine().getStatusCode();
			if (status == 200) {
				/* 取出响应字符串 */
				back = EntityUtils.toString(httpResponse.getEntity());
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
		} 
		catch (Exception e) {
			Toast.makeText(getApplicationContext(), "网络错误！", Toast.LENGTH_SHORT)
					.show();
		}
		try {

			JSONTokener jsonParser = new JSONTokener(back);
			JSONObject js = (JSONObject) jsonParser.nextValue();
			
			Log.d("output：",back);
			
			// 接下来的就是JSON对象的操作了
			result = js.getInt("success");
			userName = js.getString("userToken");
		} catch (JSONException ex) {
			Toast.makeText(getApplicationContext(), "网络错误！", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
