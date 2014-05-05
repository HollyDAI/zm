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
import com.zuma.base.C.api;
import com.zuma.syn.GetAcitivityByCountTask;
import com.zuma.util.MySimpleAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	private Button faqi, xiaoxi, shezhi, canjia, btn[];
	private String fanhui;
	private Bundle b;
	private String userToken;
	private int renshu, i, j, chenggong, gid[], grenshuxianzhi[], gshijian[],
			gjiezhishijian[];
	private String ghuodongming[], gzhushi[];

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhuye);

		b = getIntent().getBundleExtra("idValue");
		userToken = b.getString("userToken");

		(new GetAcitivityByCountTask()).execute(userToken);
		ListView list = (ListView) findViewById(R.id.listView2);
		
		faqi = (Button) findViewById(R.id.zyfaqi);
		faqi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(HomeActivity.this,
						PublishActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", userToken);
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
				b.putString("userToken", userToken);
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
				b.putString("userToken", userToken);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtras(b);
				HomeActivity.this.startActivity(mainIntent);
			}
		});
	}
}
