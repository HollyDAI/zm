package com.zuma.common;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.common.zuma.R;
import com.zuma.base.C.api;
import com.zuma.sql.Communicate_with_sql;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText name = null;
	private EditText pass = null;
	private EditText school = null;
	private EditText major = null;
	private ImageButton male = null;
	private ImageButton female = null;
	private Button commit = null;

	private int success = 0;
	private String ans = null;
	private String userToken = null;
	private String userName, userPass, userSchool, userMajor;
	private int userGender;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		name = (EditText) findViewById(R.id.regnicheng);
		pass = (EditText) findViewById(R.id.regmima);
		school = (EditText) findViewById(R.id.regxuexiao);
		major = (EditText) findViewById(R.id.regzhuanye);
		male = (ImageButton) findViewById(R.id.regnan);
		female = (ImageButton) findViewById(R.id.regnv);
		commit = (Button) findViewById(R.id.regzhuce);

		male.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userGender = 1;
				male.setBackgroundColor(Color.RED);
				female.setBackgroundColor(Color.GRAY);
			}
		});
		female.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userGender = 0;
				female.setBackgroundColor(Color.RED);
				male.setBackgroundColor(Color.GRAY);
			}
		});

		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userName = name.getText().toString();
				userPass = pass.getText().toString();
				userSchool = school.getText().toString();
				userMajor = major.getText().toString();

				if (userName.equals("")) {
					Toast.makeText(getApplicationContext(), "请填入用户名！",
							Toast.LENGTH_SHORT).show();
				} else if(userName.length()<4){
					Toast.makeText(getApplicationContext(), "用户名应至少4位！", Toast.LENGTH_SHORT).show();
				}else if (userPass.equals("")) {
					Toast.makeText(getApplicationContext(), "请填入密码！",
							Toast.LENGTH_SHORT).show();
				} else if (userSchool.equals("")) {
					Toast.makeText(getApplicationContext(), "请填入学校！",
							Toast.LENGTH_SHORT).show();
				} else {
					new Thread(runnable).start();
					new Thread(runnable2).start();
				}
			}

			Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					Bundle data = msg.getData();
					int success = data.getInt("success");
					String ans = data.getString("ans");
					Log.i("register handler", "success=" + success +"ans"+ans);
					if (success == 1) {
						if(ans.equals("ture")){
							Toast.makeText(getApplicationContext(), "该用户名已被注册，请更换", Toast.LENGTH_SHORT).show();
						}else{
							
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"系统发生错误！请重按“注册”键", Toast.LENGTH_SHORT).show();
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
					String uriAPI = api.usernameCheck;

					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", userName));

					Communicate_with_sql sql = null;
					try {
						sql = new Communicate_with_sql();
						JSONTokener jsonParser = new JSONTokener(sql.request(
								uriAPI, params));
						JSONObject js = (JSONObject) jsonParser.nextValue();
						// 接下来的就是JSON对象的操作了
						success = js.getInt("success");
						ans = js.getString("ans");
						Log.i("register Message", "success=" + success+"ans="+ans);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Bundle b = new Bundle();
					b.putInt("success", success);
					b.putString("ans", ans);
					msg.setData(b);
					handler.sendMessage(msg);
				}
			};

			
			Handler handler2 = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					Bundle data = msg.getData();
					int success = data.getInt("success");
					Log.i("register2 handler", "success=" + success );
					if (success == 1) {
						Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(), MainActivity.class);
						Bundle b = new Bundle();
						b.putString("action", "reg");
						b.putString("username", userName);
						b.putString("password", userPass);
						intent.putExtra("reg", b);
						startActivity(intent);
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"该用户名已被注册，请更换", Toast.LENGTH_SHORT).show();
					}
				}
			};

			Runnable runnable2 = new Runnable() {
				@Override
				public void run() {
					//
					// TODO: http request.
					//
					Message msg = new Message();
					String uriAPI = api.register;

					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", userName));
					params.add(new BasicNameValuePair("password", userPass));
					params.add(new BasicNameValuePair("school", userSchool));
					params.add(new BasicNameValuePair("major", userMajor));
					params.add(new BasicNameValuePair("gender", userGender+""));

					Communicate_with_sql sql = null;
					try {
						sql = new Communicate_with_sql();
						JSONTokener jsonParser = new JSONTokener(sql.request(
								uriAPI, params));
						JSONObject js = (JSONObject) jsonParser.nextValue();
						// 接下来的就是JSON对象的操作了
						success = js.getInt("success");
						Log.i("register2 Message", "success=" + success);
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
					handler2.sendMessage(msg);
				}
			};
		});
	}
}
