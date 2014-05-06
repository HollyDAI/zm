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

public class PublishActivity extends Activity {
	private String cnan, cnv, fanhui, crenshu, cjiezhi, cbiaoti, cshijian,
			cmiaoshu, yonghu;
	private int chenggong;
	private EditText renshu, jiezhi, biaoti, shijian, miaoshu, nan, nv;
	private Button fabu;
	private static final String[] m = { "ѧ��", "��ʳ", "����", "����", "����",
			"����" };
	private Spinner spinner;
	private ArrayAdapter<String> adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.faqi);
		// !!!!!!!!!!!!!!!!!!
		// yonghu = getIntent().getExtras().getString("userToken");
		yonghu = "PublishAcitivity";
		fabu = (Button) findViewById(R.id.fqfabu);
		fabu.setOnClickListener(new fabuListener());

		//spinner = (Spinner) findViewById(R.id.infoTypeSpinner);
		//����ѡ������ArrayAdapter��������
		//adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
		
		//���������б�ķ��
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//��adapter ��ӵ�spinner��
		//spinner.setAdapter(adapter);
		
		//����¼�Spinner�¼�����  
//		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		
		//����Ĭ��ֵ
		//spinner.setVisibility(View.VISIBLE);
	}

	class fabuListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			renshu = (EditText) findViewById(R.id.fqrenshu);
			jiezhi = (EditText) findViewById(R.id.fqjiezhi);
			biaoti = (EditText) findViewById(R.id.fqbiaoti);
			shijian = (EditText) findViewById(R.id.fqshijian);
			miaoshu = (EditText) findViewById(R.id.fqmiaoshu);
			crenshu = renshu.getText().toString();
			cjiezhi = jiezhi.getText().toString();
			cbiaoti = biaoti.getText().toString();
			cshijian = shijian.getText().toString();
			cmiaoshu = miaoshu.getText().toString();
			cnan = nan.getText().toString();
			cnv = nv.getText().toString();
			try {
				
				// shuju();
				chenggong = 1;
				
				if (chenggong == 1) {
					Toast.makeText(PublishActivity.this, "��Ϣ�����ɹ�����", Toast.LENGTH_LONG);
					Intent mainIntent = new Intent(PublishActivity.this,
							MessageListActivity.class);
					Bundle b = new Bundle();
					b.putString("userToken", yonghu);
					// �˴�ʹ��putExtras�����ܷ�����Ӧ��ʹ��getExtra
					mainIntent.putExtras(b);
					PublishActivity.this.startActivity(mainIntent);
				} else {
					Toast.makeText(getApplicationContext(),
							"��֤�������ֻ��Ų����ã���˶Ժ��������룡", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "�������",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	public void shuju() {
		String uriAPI = "http://192.168.32.5:8000/activity/new?userToken="
				+ yonghu + "&limit=" + crenshu + "&limitMale=" + cnan
				+ "&limitFemale=" + cnv + "&deadline=" + cjiezhi + "&title="
				+ cbiaoti + "&time=" + cshijian + "&description=" + cmiaoshu;
		/* ����HTTP Post���� */
		HttpPost httpRequest = new HttpPost(uriAPI);
		/*
		 * Post�������ͱ���������NameValuePair[]���鴢��
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userToken", yonghu));
		params.add(new BasicNameValuePair("limit", crenshu));
		params.add(new BasicNameValuePair("limitMale", cnan));
		params.add(new BasicNameValuePair("limitFemale", cnv));
		params.add(new BasicNameValuePair("deadline", cjiezhi));
		params.add(new BasicNameValuePair("title", cbiaoti));
		params.add(new BasicNameValuePair("time", cshijian));
		params.add(new BasicNameValuePair("description", cmiaoshu));
		try {
			/* ����HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* ȡ��HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* ��״̬��Ϊ200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* ȡ����Ӧ�ַ��� */
				fanhui = EntityUtils.toString(httpResponse.getEntity());
			} else {
				Toast.makeText(getApplicationContext(), "�������",
						Toast.LENGTH_SHORT).show();
			}
		} catch (ClientProtocolException e) {
			Toast.makeText(getApplicationContext(), "�������", Toast.LENGTH_SHORT)
					.show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "�������", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "�������", Toast.LENGTH_SHORT)
					.show();
		}
		try {

			JSONTokener jsonParser = new JSONTokener(fanhui);
			JSONObject js = (JSONObject) jsonParser.nextValue();
			// �������ľ���JSON����Ĳ�����
			chenggong = js.getInt("success");
		} catch (JSONException ex) {
			// �쳣�������
		}
	}
	
	//ʹ��������ʽ����
		class SpinnerSelectedListener implements OnItemSelectedListener{

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println(m[arg2]);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		}
}
