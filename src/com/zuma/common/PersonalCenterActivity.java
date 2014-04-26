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

import com.common.zuma.R;
import com.zuma.syn.UploadFileTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PersonalCenterActivity extends Activity {
	private Button queding;
	private EditText nicheng, xueyuan, zhuanye;
	private String cnicheng, cxueyuan, czhuanye, fanhui, yonghu, cxingbie;
	private int chenggong;
	private ImageButton imb, nan, nv;
	private String picPath = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wanshan);
		yonghu = getIntent().getExtras().getString("userToken");
		nan = (ImageButton) findViewById(R.id.wsnan);
		nan.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nan.setClickable(false);
				nv.setClickable(true);
				cxingbie = "0";
			}
		});
		nv = (ImageButton) findViewById(R.id.wsnv);
		nv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nv.setClickable(false);
				nan.setClickable(true);
				cxingbie = "1";
			}
		});
		imb = (ImageButton) findViewById(R.id.imb);
		imb.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				// 回调图片类使用的
				startActivityForResult(intent, RESULT_CANCELED);
			}
		});
		queding = (Button) findViewById(R.id.wsqueding);
		queding.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nicheng = (EditText) findViewById(R.id.wsnicheng);
				xueyuan = (EditText) findViewById(R.id.wsxueyuan);
				zhuanye = (EditText) findViewById(R.id.wszhuanye);
				cnicheng = nicheng.getText().toString();
				cxueyuan = xueyuan.getText().toString();
				czhuanye = zhuanye.getText().toString();
				try {
					shuju();
					if (picPath != null && picPath.length() > 0) {
						UploadFileTask uploadFileTask = new UploadFileTask(
								PersonalCenterActivity.this);
						uploadFileTask.execute(picPath);
					}
					if (chenggong == 1) {
						Intent mainIntent = new Intent(
								PersonalCenterActivity.this,
								ResetPwdActivity.class);
						Bundle b = new Bundle();
						b.putString("userToken", yonghu);
						// 此处使用putExtras，接受方就响应的使用getExtra
						mainIntent.putExtras(b);
						PersonalCenterActivity.this.startActivity(mainIntent);
					} else {
						Toast.makeText(getApplicationContext(), "修改个人信息失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "网络错误！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void shuju() {
		String uriAPI = "http://192.168.32.5:8000/user/setProfile?userToken="
				+ yonghu + "&nickname=" + cnicheng + "&school=" + cxueyuan
				+ "&major=" + czhuanye + "&gender=" + cxingbie;
		/* 建立HTTP Post联机 */
		HttpPost httpRequest = new HttpPost(uriAPI);
		/*
		 * Post运作传送变量必须用NameValuePair[]数组储存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userToken", yonghu));
		params.add(new BasicNameValuePair("nickname", cnicheng));
		params.add(new BasicNameValuePair("school", cxueyuan));
		params.add(new BasicNameValuePair("major", czhuanye));
		params.add(new BasicNameValuePair("gender", cxingbie));
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			JSONTokener jsonParser = new JSONTokener(fanhui);
			JSONObject js = (JSONObject) jsonParser.nextValue();
			// 接下来的就是JSON对象的操作了
			chenggong = js.getInt("success");
		} catch (JSONException ex) {
			// 异常处理代码
		}
	}

	@SuppressWarnings("deprecation")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			/**
			 * 当选择的图片不为空的话，在获取到图片的途径
			 */
			Uri uri = data.getData();
			try {
				String[] pojo = { MediaStore.Images.Media.DATA };

				Cursor cursor = managedQuery(uri, pojo, null, null, null);
				if (cursor != null) {
					ContentResolver cr = this.getContentResolver();
					int colunm_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					String path = cursor.getString(colunm_index);
					/***
					 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
					 * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
					 */
					if (path.endsWith("jpg") || path.endsWith("png")) {
						picPath = path;
						Bitmap bitmap = BitmapFactory.decodeStream(cr
								.openInputStream(uri));
						imb.setImageBitmap(bitmap);
					} else {
						alert();
					}
				} else {
					alert();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 回调使用
		 */
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void alert() {
		Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
				.setMessage("您选择的不是有效的图片")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						picPath = null;
					}
				}).create();
		dialog.show();
	}
}
