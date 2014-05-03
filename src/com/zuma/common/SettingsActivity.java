package com.zuma.common;

import com.common.zuma.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SettingsActivity extends Activity {
	private Button geren, bangding, tuichu, genggai, tixing, yijian;
	private String yonghu;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shezhi);
		yonghu = getIntent().getExtras().getString("userToken");
		geren = (Button) findViewById(R.id.szgeren);
		bangding = (Button) findViewById(R.id.szshouji);
		tuichu = (Button) findViewById(R.id.sztuichu);
		genggai = (Button) findViewById(R.id.szgenggai);
		tixing = (Button) findViewById(R.id.sztixing);
		yijian = (Button) findViewById(R.id.szyijian);
		geren.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, PersonalCenterActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", yonghu);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtras(b);
				SettingsActivity.this.startActivity(mainIntent);
			}
		});
		bangding.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, BindPhoneActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", yonghu);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtras(b);
				SettingsActivity.this.startActivity(mainIntent);
			}
		});
		tuichu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, HomeActivity.class);
				SettingsActivity.this.startActivity(mainIntent);
				SettingsActivity.this.finish();
			}
		});
		genggai.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, ResetPwdActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", yonghu);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtras(b);
				SettingsActivity.this.startActivity(mainIntent);
				SettingsActivity.this.finish();
			}
		});
		tixing.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, AlertSettingsActivity.class);
				SettingsActivity.this.startActivity(mainIntent);
			}
		});
		yijian.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, FeedbackActivity.class);
				Bundle b = new Bundle();
				b.putString("userToken", yonghu);
				// 此处使用putExtras，接受方就响应的使用getExtra
				mainIntent.putExtras(b);
				SettingsActivity.this.startActivity(mainIntent);
				finish();
			}
		});
	}

}
