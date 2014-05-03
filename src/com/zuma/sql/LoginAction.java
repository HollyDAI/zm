package com.zuma.sql;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.zuma.base.C.api;

import android.util.Log;

public class LoginAction {

	private static String userToken;

	// private static String phonenumber,mail,qqnumber,userNickname;

	public static String getuserToken() {
		return userToken;
	}

	// public static Map<String ,String> getContactWays(){
	// Map<String,String> map=new HashMap<String,String>();
	// map.put("phonenumber", phonenumber);
	// map.put("mail", mail);
	// map.put("qqnumber", qqnumber);
	// map.put("nickname", userNickname);
	// return map;
	// }

	public static boolean isCorrectedUserAndPass(CharSequence username,
			CharSequence password) {

		// OpenInternetInMainThread.OIIMT();
		boolean a = false;
		if (getLoginResult(username.toString(), password.toString()) == 1) {
			a = true;
		}
		return a;
	}

	public static String get32MD5String(String str) {
		MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
			messagedigest.reset();
			messagedigest.update(str.getBytes());
		} catch (NoSuchAlgorithmException e) {
			Log.e("md5", "NoSuchAlgorithmException caught!");
		}

		byte[] byteArray = messagedigest.digest();
		StringBuilder md5sb = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xff & byteArray[i]).length() == 1)
				md5sb.append("0").append(
						Integer.toHexString(0xff & byteArray[i]));
			else
				md5sb.append(Integer.toHexString(0xff & byteArray[i]));
		}
		Log.i("看32MD5pass", str + "  " + md5sb.toString());
		return md5sb.toString();
	}

	public static int getLoginResult(String username, String password) {

		int result = 0;
		String apiURL = api.login;

		ArrayList<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		namevaluepairs.add(new BasicNameValuePair("username", username));
		namevaluepairs.add(new BasicNameValuePair("password", password));
		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(apiURL);
			httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
			HttpResponse httpresponse = httpclient.execute(httppost);
			HttpEntity httpentity = httpresponse.getEntity();

			InputStream is = httpentity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line = br.readLine();

			if (line != null && !line.equals(br)) {
				JSONObject jo = new JSONObject(line);
				result = jo.getInt("success");
				userToken = jo.getString("userToken");
				Log.i("看取回的result和userToken", result + "\n" + userToken);
			}
		} catch (Exception e) {
			Log.e("getMD5PassAndIdError", e.getMessage());
		}

		return result;
	}
}
