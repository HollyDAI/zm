package com.zuma.common;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.common.zuma.R;
import com.zuma.util.SerializableMap;

public class MessageDetailActivity extends Activity {
	private String fanhui, userToken, actid;
	private TextView ttitle, tnumLimit , tTime, tdesc, tlimit;
	private Button queding;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_detail);

		userToken = getIntent().getExtras().getString("userToken");
		SerializableMap smap = (SerializableMap) getIntent().getExtras()
				.getSerializable("smap");
		
		Map<String, Object> map = smap.getMap();
		
		ttitle = (TextView) findViewById(R.id.list_detail_huodongming);
		tnumLimit = (TextView) findViewById(R.id.list_detail_numLimit);
		tTime = (TextView) findViewById(R.id.list_detail_time);
		tdesc = (TextView) findViewById(R.id.list_detail_desc);
		tlimit = (TextView) findViewById(R.id.list_detail_limit);
		
		ttitle.setText((String) map.get("title"));
		
//		numLimit = (Integer)map.get("numLimit") + "";
//		maleLimit = (Integer)map.get("maleLimit") + "";
//		femaleLimit = (Integer)map.get("femaleLimit") + "";
//		tnumLimit.setText("人数:" + numLimit + "(" + maleLimit + femaleLimit + ")");
//		tTime.setText("时间:" + (String) map.get("proposeTime") + "至" + (String) map.get("deadLine"));
		tnumLimit.setText((String) map.get("limit"));
		tTime.setText((String) map.get("time"));
		tdesc.setText("活动描述:" + (String) map.get("desc"));
		tlimit.setText("人员要求:");
		
//		actid = (Integer) map.get("id") + "";
//		title = (String) map.get("title");
//		numLimit = (Integer)map.get("numLimit") + "";
//		state = map.get("state");
//		ownerId = map.get("ownerId");
//		desc = map.get("desc");
//		proposeTime = map.get("proposeTime");
//		deadLine = map.get("deadLine");
//		maleLimit = (Integer)map.get("maleLimit") + "";
//		femaleLimit = (Integer)map.get("femaleLimit") + "";
		
	}
}
