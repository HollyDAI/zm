package com.zuma.syn;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zuma.util.ZoomImage;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleAdapter;

//load image and title,info;
public class LoadImageTask extends AsyncTask<Object,Object,Bitmap> {

	@Override
	protected Bitmap doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

//	private boolean isRunning;
//	private Integer position;
//	private Integer fragmentid;
//	
//	private String pictureurl;
//	private String title;
//	private String describe;
//	private String price;
//	private String mobile;
//	
//	private StringBuilder sb_lxfs=new StringBuilder();
//	private String lxfs;
//	
//	private String chenghu;
//	private String degree;
//	
//	private SimpleAdapter sa;
//	private HashMap<String,Object> hashmap;
//	private Communicate_with_mysql cwm;
//	private JSONArray ja;
//	
//	private String line;
//	private int displaywidth,displayheight;
//	
//	@Override
//	protected Bitmap doInBackground(Object... params) {
//		// TODO Auto-generated method stub
//		isRunning=true;
//		
//		position=(Integer)params[0];
//		fragmentid=(Integer)params[1];
//		
//	 	sa=(SimpleAdapter)params[2];
//		hashmap=(HashMap<String,Object>)params[3];
//	    
//		displaywidth=(Integer)params[4];
//		displayheight=(Integer)params[5];
//		
//		cwm=new Communicate_with_mysql();
//		
//		
//		try {
//			ja=new JSONArray((line=cwm.request(position,fragmentid)));
//			if(ja!=null&&ja.length()>0){
//			for(int i=0;i<ja.length();i++){
//				JSONObject jo=ja.getJSONObject(i);
//				pictureurl="http://imarket.nankai.edu.cn/"+jo.getString("picture");
//				title=jo.getString("itemname");
//				mobile = jo.getString("mobile");
//				describe=jo.getString("detail");
//				price=jo.getString("price");
//				chenghu=jo.getString("nickname");
//				int intdegree=jo.getInt("newold");
//				    if(intdegree==1)
//				    	degree="9.9成新";
//				    if(intdegree==2)
//				    	degree="9成新";
//				    if(intdegree==3)
//				    	degree="8成新";
//				    if(intdegree==4)
//				    	degree="7成新";
//				    if(intdegree==5)
//				    	degree="7成新以下";
//				    	
//				if(!jo.getString("mobile").equals(""))
//					sb_lxfs.append("电话："+jo.getString("mobile")+"\n");
//				if(!jo.getString("email").equals(""))
//					sb_lxfs.append("邮箱："+jo.getString("email")+"\n");
//				if(!jo.getString("qq").equals(""))
//					sb_lxfs.append("QQ: "+jo.getString("qq"));
//				lxfs=sb_lxfs.toString();
//				
//			}
//		  }
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			pictureurl=null;
//			title="无法连接数据库";
//			describe=e.getMessage();
//			if(e.getMessage().equals("Value <br of type java.lang.String cannot be converted to JSONArray"))
//				describe="无更多商品";
//			price="无法连接数据库";
//			mobile = "无法连接数据库";
//			lxfs="无法连接数据库";
//			chenghu="无法连接数据库";
//			degree="无法连接数据库";
//			Log.e("看JSON数据错误", e.getMessage());
//		}	
//		
//		
//		Bitmap bitmap=ZoomImage.loadWebImage(pictureurl, displaywidth, displayheight);
//	    if(bitmap!=null)
//		  return bitmap;
//	    else
//	    	return null;
//	}
//	
//	@Override
//	protected void onPostExecute(Bitmap result){
//		
//		hashmap.remove("title");
//		hashmap.remove("describe");
//		hashmap.remove("mobile");
//		hashmap.remove("price");
//		
//		if(!title.equals("无法连接数据库")){
//			hashmap.put("title",title);
//			hashmap.put("describe", describe);
//			hashmap.put("price", price);
//			hashmap.put("mobile", mobile);
//			hashmap.put("lxfs", lxfs);
//			hashmap.put("chenghu", chenghu);
//			hashmap.put("degree", degree);
//		
//			if(result!=null){
//				hashmap.remove("img");
//				hashmap.put("img", result);
//			}
//			
//		}
//		else
//			if(result==null)
//				hashmap.remove("img");
//		sa.notifyDataSetChanged();
//	    isRunning=false;
//	}

}
