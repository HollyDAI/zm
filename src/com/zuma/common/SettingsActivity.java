package com.zuma.common;

import com.common.zuma.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	private Button geren, bangding, tuichu, genggai, tixing, yijian;
	private String userToken;
	private Bundle b = new Bundle();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shezhi);
		
		geren = (Button) findViewById(R.id.szgeren);
		bangding = (Button) findViewById(R.id.szshouji);
		tuichu = (Button) findViewById(R.id.sztuichu);
		genggai = (Button) findViewById(R.id.szgenggai);
		tixing = (Button) findViewById(R.id.sztixing);
		yijian = (Button) findViewById(R.id.szyijian);
		

		b = getIntent().getBundleExtra("idValue");
		userToken = b.getString("userToken");
		
		geren.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, PersonalCenterActivity.class);
				b.putString("userToken", userToken);
				mainIntent.putExtra("idValue",b);
				SettingsActivity.this.startActivity(mainIntent);
			}
		});
		bangding.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, BindPhoneActivity.class);
				b.putString("userToken", userToken);
				mainIntent.putExtra("idValue",b);
				SettingsActivity.this.startActivity(mainIntent);
			}
		});
		tuichu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
				b.putString("userToken", userToken);
				mainIntent.putExtra("idValue",b);
				SettingsActivity.this.startActivity(mainIntent);
				SettingsActivity.this.finish();
				Toast.makeText(getApplicationContext(), "ÍË³öµÇÂ¼£¡", Toast.LENGTH_SHORT).show();
			}
		});
		genggai.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, ResetPwdActivity.class);
				b.putString("userToken", userToken);
				mainIntent.putExtra("idValue",b);
				SettingsActivity.this.startActivity(mainIntent);
				SettingsActivity.this.finish();
			}
		});
		tixing.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, AlertSettingsActivity.class);
				b.putString("userToken", userToken);
				mainIntent.putExtra("idValue",b);
				SettingsActivity.this.startActivity(mainIntent);
			}
		});
		yijian.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mainIntent = new Intent(SettingsActivity.this, FeedbackActivity.class);
				b.putString("userToken", userToken);
				mainIntent.putExtra("idValue",b);
				SettingsActivity.this.startActivity(mainIntent);
				finish();
			}
		});
	}

}
