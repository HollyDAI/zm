package com.zuma.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MySimpleAdapter extends SimpleAdapter {

	private int[] mTo;
    private String[] mFrom;
    private ViewBinder mViewBinder;

    private List<? extends Map<String, ?>> mData;

    private int mResource;
    private int mDropDownResource;
    private LayoutInflater mInflater;
    String fanhui;
    int chenggong;
	
	public MySimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		mData = data;
        mResource = mDropDownResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	 /**
     * @see android.widget.Adapter#getView(int, View, ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mResource);
    }

    private View createViewFromResource(int position, View convertView,
            ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);

            final int[] to = mTo;
            final int count = to.length;
            final View[] holder = new View[count];

            for (int i = 0; i < count; i++) {
                holder[i] = v.findViewById(to[i]);
            }

            v.setTag(holder);
        } else {
            v = convertView;
        }

        bindView(position, v);

        return v;
    }
    
    private void bindView(int position, View view) {
        final Map dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }

        final ViewBinder binder = mViewBinder;
        final View[] holder = (View[]) view.getTag();
        final String[] from = mFrom;
        final int[] to = mTo;
        final int count = to.length;

        for (int i = 0; i < count; i++) {
            final View v = holder[i];
            if (v != null) {
                final Object data = dataSet.get(from[i]);
                final String text = data == null ? "" : data.toString();
                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, data, text);
                }

                if (!bound) {
                    if (v instanceof Checkable) {
                        if (data instanceof Boolean) {
                            ((Checkable) v).setChecked((Boolean) data);
                        } else {
                            throw new IllegalStateException(v.getClass().getName() +
                                    " should be bound to a Boolean, not a " + data.getClass());
                        }
                    } else if (v instanceof TextView) {
                        // Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        setViewText((TextView) v, text);
                    } else if (v instanceof ImageView) {
                    	
                    	
//                    	Bitmap bitmap = WebImageBuilder.returnBitMap("http://timg3.ddmapimg.com/city/images/citynew/2696c2126e903cf8d-7f23.jpg");
//                    	((ImageView) v).setImageBitmap(bitmap);
//                    	setViewImage((ImageView) v,"http://timg3.ddmapimg.com/city/images/citynew/2696c2126e903cf8d-7f23.jpg");
                        if (data instanceof Integer) {
                            setViewImage((ImageView) v, (Integer) data);                            
                        } else {
                            setViewImage((ImageView) v, text);
                        }
                    } else if (v instanceof Button) {
                        // Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        ((Button)v).setOnClickListener(new View.OnClickListener(){
   	       	 public void onClick(View v) {
   	       	String uriAPI = "";
   	     /*建立HTTP Post联机*/
   	     HttpPost httpRequest = new HttpPost(uriAPI); 
   	     /*
   	      * Post运作传送变量必须用NameValuePair[]数组储存
   	     */
   	     List <NameValuePair> params = new ArrayList <NameValuePair>(); 
   	     params.add(new BasicNameValuePair("activityId", text));
   	     params.add(new BasicNameValuePair("accepted", "1"));
   	     try 
   	     { 
   	       /*发出HTTP request*/
   	       httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
   	       /*取得HTTP response*/
   	       HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
   	       /*若状态码为200 ok*/
   	       if(httpResponse.getStatusLine().getStatusCode() == 200)  
   	       {   
   	     	  fanhui = EntityUtils.toString(httpResponse.getEntity()); 
   	       } 
   	       else 
   	       { 
   	     	   
   	       } 
   	     } 
   	     catch (ClientProtocolException e) 
   	     {  
   	       e.printStackTrace(); 
   	     } 
   	     catch (IOException e) 
   	     {  
   	       e.printStackTrace(); 
   	     } 
   	     catch (Exception e) 
   	     {  
   	       e.printStackTrace();  
   	     }
   	     try { 
   	      	 
   	    	   JSONTokener jsonParser = new JSONTokener(fanhui); 
   	    	   JSONObject js = (JSONObject) jsonParser.nextValue(); 
   	    	   // 接下来的就是JSON对象的操作了 
   	    	   chenggong=js.getInt("success");  
   	    	} catch (JSONException ex) { 
   	    	   // 异常处理代码 
   	    	}
   	    	if(chenggong==1){
   	    		((Button)v).setBackgroundColor(Color.GRAY);
   	    		((Button)v).setText("已参加！");
   	    	}
   	       	 }
    		});
                    }else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                " view that can be bounds by this SimpleAdapter");
                    }
                }
            }
        }
    }
    
    /**
     * Called by bindView() to set the image for an ImageView but only if
     * there is no existing ViewBinder or if the existing ViewBinder cannot
     * handle binding to an ImageView.
     *
     * This method is called instead of {@link #setViewImage(ImageView, String)}
     * if the supplied data is an int or Integer.
     *
     * @param v ImageView to receive an image
     * @param value the value retrieved from the data set
     *
     * @see #setViewImage(ImageView, String)
     */
    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }
    public long getItemId(int position) {  
    	  // TODO Auto-generated method stub   
    	  return position;  
    	 }   

    /**
     * Called by bindView() to set the image for an ImageView but only if
     * there is no existing ViewBinder or if the existing ViewBinder cannot
     * handle binding to an ImageView.
     *
     * By default, the value will be treated as an image resource. If the
     * value cannot be used as an image resource, the value is used as an
     * image Uri.
     *
     * This method is called instead of {@link #setViewImage(ImageView, int)}
     * if the supplied data is not an int or Integer.
     *
     * @param v ImageView to receive an image
     * @param value the value retrieved from the data set
     *
     * @see #setViewImage(ImageView, int) 
     */
    public void setViewImage(ImageView v, String value) {
    	Bitmap bitmap = WebImageBuilder.returnBitMap(value);
    	((ImageView) v).setImageBitmap(bitmap);
    }
    
    

}
