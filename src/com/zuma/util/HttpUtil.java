package com.zuma.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtil
{
	// ����HttpClient����
	public static HttpClient httpClient = new DefaultHttpClient();
	public static final String BASE_URL =
//			"http://10.2.222.72:8080/link_news/";
		"http://wap.news.nankai.edu.cn/link_news/";
	/**
	 *
	 * @param url ���������URL
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */
	public static String getRequest(final String url)
		throws Exception
	{
		FutureTask<String> task = new FutureTask<String>(
		new Callable<String>()
		{
			@Override
			public String call() throws Exception
			{
				// ����HttpGet����
				HttpGet get = new HttpGet(url);
				// ����GET����
				HttpResponse httpResponse = httpClient.execute(get);
				// ����������ɹ��ط�����Ӧ
                 Log.e("kkkk", httpResponse.getStatusLine()
					.getStatusCode() +"");
				if (httpResponse.getStatusLine()
					.getStatusCode() == 200)
				{
					// ��ȡ��������Ӧ�ַ���
					InputStream is=httpResponse.getEntity().getContent();
					BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));
					String line=null;
					StringBuilder sb=new StringBuilder();
					while((line=br.readLine() )!= null)
						sb.append(line);
					String result = sb.toString();
//					Log.e("hhhhh", result);
					return result;
				}
				return null;
			}
		});
		new Thread(task).start();
		return task.get();
	}

	/**
	 * @param url ���������URL
	 * @param params �������
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */
	public static String postRequest(final String url
		, final Map<String ,String> rawParams)throws Exception
	{
		FutureTask<String> task = new FutureTask<String>(
		new Callable<String>()
		{
			@Override
			public String call() throws Exception
			{
				// ����HttpPost����
				HttpPost post = new HttpPost(url);
				// ������ݲ��������Ƚ϶�Ļ����ԶԴ��ݵĲ������з�װ
				List<NameValuePair> params = 
					new ArrayList<NameValuePair>();
				for(String key : rawParams.keySet())
				{
					//��װ�������
					params.add(new BasicNameValuePair(key 
						, rawParams.get(key)));
				}
				// �����������
				post.setEntity(new UrlEncodedFormEntity(
					params, "gbk"));
				// ����POST����
				HttpResponse httpResponse = httpClient.execute(post);
				// ����������ɹ��ط�����Ӧ
				if (httpResponse.getStatusLine()
					.getStatusCode() == 200)
				{
					// ��ȡ��������Ӧ�ַ���
					String result = EntityUtils
						.toString(httpResponse.getEntity());
					return result;
				}
				return null;
			}
		});
		new Thread(task).start();
		return task.get();
	}
}
