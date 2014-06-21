package com.zuma.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.common.zuma.R;
import com.zuma.sql.LoginAction;
import com.zuma.syn.GetAcitivityByCountTask;

public class HomeActivity extends Activity {

	private Bundle b;
	private String userToken;
	private final static int ITEM_INDEX = 10;

	private Button faqi, xiaoxi, shezhi;
	// , canjia, btn[];
	private String fanhui;

	private String strs[] = { "title", "deadline", "numLimit" };
	private int ids[] = { R.id.zyhuodongming, R.id.zyjiezhishijian,
			R.id.zyrenshuxianzhi };

	private SimpleAdapter sa;
	private ListView lv;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhuye);

		b = getIntent().getBundleExtra("idValue");
		userToken = b.getString("userToken");

		setupListView();
		(new GetAcitivityByCountTask()).execute(userToken, sa);
		
		System.out.println("=============HomeActivity=============");

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

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData() {
		Map<String, Object> map;
		for (int i = 0; i < ITEM_INDEX; i++) {
			map = new HashMap<String, Object>();
			map.put(strs[0], "数据加载中...");
			map.put(strs[1], "数据加载中...");
			map.put(strs[2], "数据加载中...");
			data.add(map);
		}
	}

	public void setupListView() {

		setData();
		setSimpleAdapter();
		lv = (ListView) findViewById(R.id.listView2);
		lv.setAdapter(sa);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});

	}

	public SimpleAdapter getSimpleAdapter() {
		return sa;
	}

	public void setSaToListView() {
		lv.setAdapter(sa);
	}

	private void setSimpleAdapter() {
		// TODO Auto-generated method stub
		sa = new SimpleAdapter(HomeActivity.this, data, R.layout.list_item,
				strs, ids);

		sa.setViewBinder(new SimpleAdapter.ViewBinder() {

			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				// if (view instanceof ImageView && data instanceof Bitmap) {
				// ImageView iv = (ImageView) view;
				// iv.setImageBitmap((Bitmap) data);
				return true;
				// } else
				// return false;
			}
		});
	}

}
