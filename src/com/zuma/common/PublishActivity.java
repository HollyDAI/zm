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
	// private static final String[] m = { "ѧ��", "��ʳ", "����", "����", "����",
	// "����" };
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
		// ����ѡ������ArrayAdapter��������
		// adapter = new
		// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

		// ���������б�ķ��
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�spinner��
		// spinner.setAdapter(adapter);

		// ����¼�Spinner�¼�����
		// spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

		// ����Ĭ��ֵ
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
				Toast.makeText(getApplicationContext(), "��������⣡", Toast.LENGTH_SHORT).show();
			}else if(crenshu.equals("")){
				Toast.makeText(getApplicationContext(), "�������������ƣ�", Toast.LENGTH_SHORT).show();
			}else if(crenshu.equals("0")){
				Toast.makeText(getApplicationContext(), "�������������0��", Toast.LENGTH_SHORT).show();
			}else if(cshijian.equals("")){
				Toast.makeText(getApplicationContext(), "������ʱ�䣡", Toast.LENGTH_SHORT).show();
			}else if(cjiezhi.equals("")){
				Toast.makeText(getApplicationContext(), "�������ֹʱ�䣡", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(PublishActivity.this, "��Ϣ�����ɹ�����",
							Toast.LENGTH_LONG).show();
					Intent mainIntent = new Intent(PublishActivity.this,
							HomeActivity.class);
					Bundle b = new Bundle();
					b.putString("userToken", userToken);
					// �˴�ʹ��putExtras�����ܷ�����Ӧ��ʹ��getExtra
					mainIntent.putExtra("idValue",b);
					startActivity(mainIntent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "��Ϣ����ʧ��(�Уߩ�)�����·���",
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
					// �������ľ���JSON����Ĳ�����
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

// ʹ��������ʽ����
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
