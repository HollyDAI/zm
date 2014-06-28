package com.zuma.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class Communicate_with_sql {
	private String result = "";

	public String request(String uri,ArrayList<NameValuePair> params) throws IOException {
		InputStream is = null;
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader("charset", HTTP.UTF_8);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("Communicate error", "httpConnection" + e.toString());
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			result = sb.toString();
			// System.out.println("=====result===========" + result);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Communicate convert error",
					"Error converting result " + e.toString());
		} finally {
			is.close(); // 读完记得一定要关闭
		}

		return result;
	}
}
