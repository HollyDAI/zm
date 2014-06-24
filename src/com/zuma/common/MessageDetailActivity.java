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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MessageDetailActivity extends Activity {
	String fanhui,yonghu,yonghuid,gnicheng,gxueyuan,gzhuanye,huodongming;
	int chenggong,chenggong2,gxingbie;
	TextView nicheng,xueyuan,zhuanye;
	Button queding;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xiaoxi2);
		huodongming=getIntent().getExtras().getString("id");
		yonghuid=getIntent().getExtras().getString("yonghuid");
		yonghu = getIntent().getExtras().getString("userToken");
		nicheng=(TextView)findViewById(R.id.cynicheng);
		xueyuan=(TextView)findViewById(R.id.cyxueyuan);
		zhuanye=(TextView)findViewById(R.id.cyzhuanye);
		shuju();
		nicheng.setText(gnicheng);
		xueyuan.setText(gxueyuan);
		zhuanye.setText(gzhuanye);
		queding=(Button)findViewById(R.id.cylajin);
		queding.setOnClickListener(new View.OnClickListener(){
	       	 public void onClick(View v) { 
	       		shuju2();
	       		if(chenggong2==1){
	       			
	       		}
	            }  
	       });
	}
    public void shuju(){
    	String uriAPI = "http://192.168.32.5:8000/user/profile?userToken="+yonghu+"&userId="+yonghuid;
        /*����HTTP Post����*/
        HttpPost httpRequest = new HttpPost(uriAPI); 
        /*
         * Post�������ͱ���������NameValuePair[]���鴢��
        */
        List <NameValuePair> params = new ArrayList <NameValuePair>(); 
        params.add(new BasicNameValuePair("userToken", yonghu));
        params.add(new BasicNameValuePair("userId", yonghuid));
        try 
        { 
          /*����HTTP request*/
          httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
          /*ȡ��HTTP response*/
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
          /*��״̬��Ϊ200 ok*/
          if(httpResponse.getStatusLine().getStatusCode() == 200)  
          { 
            /*ȡ����Ӧ�ַ���*/
            fanhui = EntityUtils.toString(httpResponse.getEntity());  
          } 
          else 
          { 
        	  Toast.makeText(getApplicationContext(), "�������",Toast.LENGTH_SHORT).show(); 
          } 
        } 
        catch (ClientProtocolException e) 
        {  
        	Toast.makeText(getApplicationContext(), "�������",Toast.LENGTH_SHORT).show(); 
        } 
        catch (IOException e) 
        {  
        	Toast.makeText(getApplicationContext(), "�������",Toast.LENGTH_SHORT).show(); 
        } 
        catch (Exception e) 
        {  
        	Toast.makeText(getApplicationContext(), "�������",Toast.LENGTH_SHORT).show(); 
        }
        try { 
          	 
      	   JSONTokener jsonParser = new JSONTokener(fanhui); 
      	   JSONObject js = (JSONObject) jsonParser.nextValue(); 
      	   // �������ľ���JSON����Ĳ����� 
      	   chenggong=js.getInt("success");
      	   gnicheng=js.getString("nickname");
      	   gxueyuan=js.getString("school");
      	   gzhuanye=js.getString("major");
      	   gxingbie=js.getInt("gender");
      	} catch (JSONException ex) { 
      	   // �쳣������� 
      	}
    }
    public void shuju2(){
    	String uriAPI = "http://192.168.32.5:8000/activity/confirm?userToken="+yonghu+"&userId="+yonghuid+"&activityId="+huodongming+"&accepted=1";
        /*����HTTP Post����*/
        HttpPost httpRequest = new HttpPost(uriAPI); 
        /*
         * Post�������ͱ���������NameValuePair[]���鴢��
        */
        List <NameValuePair> params = new ArrayList <NameValuePair>(); 
        params.add(new BasicNameValuePair("userToken", yonghu));
        params.add(new BasicNameValuePair("userId", yonghuid));
        params.add(new BasicNameValuePair("activityId", huodongming));
        params.add(new BasicNameValuePair("accepted", "1"));
        try 
        { 
          /*����HTTP request*/
          httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
          /*ȡ��HTTP response*/
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
          /*��״̬��Ϊ200 ok*/
          if(httpResponse.getStatusLine().getStatusCode() == 200)  
          { 
            /*ȡ����Ӧ�ַ���*/
            fanhui = EntityUtils.toString(httpResponse.getEntity());  
          } 
          else 
          { 
        	  Toast.makeText(getApplicationContext(), "�������",Toast.LENGTH_SHORT).show(); 
          } 
        } 
        catch (ClientProtocolException e) 
        {  
        	Toast.makeText(getApplicationContext(), "�������",Toast.LENGTH_SHORT).show(); 
        } 
        catch (IOException e) 
        {  
        	Toast.makeText(getApplicationContext(), "�������",Toast.LENGTH_SHORT).show(); 
        } 
        catch (Exception e) 
        {  
        	Toast.makeText(getApplicationContext(), "�������",Toast.LENGTH_SHORT).show(); 
        }
        try { 
          	 
      	   JSONTokener jsonParser = new JSONTokener(fanhui); 
      	   JSONObject js = (JSONObject) jsonParser.nextValue(); 
      	   // �������ľ���JSON����Ĳ����� 
      	   chenggong2=js.getInt("success");
      	} catch (JSONException ex) { 
      	   // �쳣������� 
      	}
    }

}
