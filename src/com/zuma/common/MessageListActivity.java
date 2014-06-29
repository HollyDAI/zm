package com.zuma.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

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
import com.zuma.sql.Communicate_with_sql;
import com.zuma.util.MySimpleAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MessageListActivity extends Activity {
	private String userToken = null;
	private Bundle b = null;
	String fanhui, ghuodongming[], cid, chuodongid;
	int i, j, gxiaoxishu[], gid[], id, chenggong, ghuodongid[];
	ArrayList<HashMap<String, Object>> mylist = null;
	ListView list = null;
	MySimpleAdapter mSchedule = null;
	private ProgressDialog pd = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xiaoxi1);
		list = (ListView) findViewById(R.id.listView1);
		
		b = getIntent().getBundleExtra("idValue");
		userToken = b.getString("userToken");
		
		pd = new ProgressDialog(MessageListActivity.this);
		pd.setTitle("���Ե�");
		pd.setMessage("���ڶ�ȡ...");
		
		pd.show();
		new Thread(run).start();
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				cid = gid[arg2] + "";
				chuodongid = ghuodongid[arg2] + "";
				Intent mainIntent = new Intent(MessageListActivity.this,
						MessageDetailActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", userToken);
				b.putString("id", cid);
				b.putString("huodongid", chuodongid);
				// �˴�ʹ��putExtras�����ܷ�����Ӧ��ʹ��getExtra
				mainIntent.putExtras(b);
				MessageListActivity.this.startActivity(mainIntent);
			}
		});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() { // �ʹ�������
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(
						MessageListActivity.this);
				builder.setMessage("ȷ��ɾ������Ŀ��");
				builder.setPositiveButton("ȷ��", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mylist.remove(arg2);
						mSchedule.notifyDataSetChanged();
						list.invalidate();

					}
				});
				builder.setNegativeButton("ȡ��", new OnClickListener() {
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
		// ���list����
		for (j = 1; j < i; j++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("huodongming", ghuodongming[j]);
			map.put("xiaoxishu", gxiaoxishu[j]);
			mylist.add(map);
		}
		return mylist;

	}

	Runnable run = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			System.out.println("===========runnable===========");
			String uriAPI = api.getNews;
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userToken", userToken));
			Communicate_with_sql sql = new Communicate_with_sql();
			try {
				JSONTokener jsonParser = new JSONTokener(
						sql.request(uriAPI, params));
				JSONObject js = (JSONObject) jsonParser.nextValue();
				JSONArray newsList = js.getJSONArray("newsList");
				if (newsList != null && newsList.length() > 0) {
					for (i = 0; i < newsList.length(); i++) {
						
						//��������
						gid[i] = newsList.getJSONObject(i).getInt("id");
						ghuodongming[i] = newsList.getJSONObject(i)
								.getString("fromId");
						gxiaoxishu[i] = newsList.getJSONObject(i).getInt("creatime");
						ghuodongid[i] = newsList.getJSONObject(i)
								.getInt("newsId");
					}
					chenggong = js.getInt("success");
				}else{
					chenggong = -1;
				}
			} catch (JSONException ex) {
				// �쳣�������
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bundle b = new Bundle();
			b.putInt("success", chenggong);
			msg.setData(b);
			handler.sendMessage(msg);
		}
	}; 
	
	Handler handler = new Handler(){

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			System.out.println("========handler=======");
			Bundle data = msg.getData();
			if(data.getInt("success")==1){
				mylist = buildList();
				mSchedule =  new MySimpleAdapter(getApplicationContext(),
						mylist,// ������Դ
						R.layout.list_item2,// ListItem��XMLʵ��
						// ��̬������ListItem��Ӧ������
						new String[] { "huodongming", "xiaoxishu" },
						// ListItem��XML�ļ����������TextView ID
						new int[] { R.id.xxhuodongming, R.id.xxxiaoxishu });

				list.setAdapter(mSchedule);
				pd.dismiss();
			}else if(data.getInt("success")==-1){
				Toast.makeText(getApplicationContext(), "��ǰ��Ϣ�б�Ϊ�գ�", Toast.LENGTH_LONG).show();
				pd.dismiss();
			}else if(data.getInt("success")==0){
				Toast.makeText(getApplicationContext(), "��ȡʧ�ܣ�", Toast.LENGTH_LONG).show();
				pd.dismiss();
			}
		}
		
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), HomeActivity.class);
			Bundle b = new Bundle();
			b.putString("userToken", userToken);
			intent.putExtra("idValue", b);
			startActivity(intent);
			finish();
		}
		return true;
	}
}
