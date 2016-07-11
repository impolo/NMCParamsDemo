package com.appzorro.nmcparamsdemo.util;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.appzorro.nmcparamsdemo.dl.DtoDefaultValues;


@SuppressLint("NewApi") public class Client {
	
	public static String Caller (String parametersToCall) {
		String result = "";

		if (DtoDefaultValues.getType().equals("HTTP")) {
			result = CallerHttp(parametersToCall);
		} else if (DtoDefaultValues.getType().equals( "HTTPS")) {
			Log.i("url", "https://"
					+ DtoDefaultValues.getIp() + ":" + DtoDefaultValues.getPort()
					+ "/NmcServerS/nmc-server/post/");

			result = ConnectionHttps.doPost("https://"
					+ DtoDefaultValues.getIp() + ":" + DtoDefaultValues.getPort()
					+ "/NmcServerS/nmc-server/post/", parametersToCall);
		}
		return(result);
	}
	
	public static String CallerHttp (String parametersToCall) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		String json = "";
        String port = DtoDefaultValues.getPort();
        String ip = DtoDefaultValues.getIp();
        try {	
        	String url = "http://" + ip + ":" + port 
        			+ "/NmcServerS/nmc-server/post";//esto no deberia estar quemado!!!!!!!!!!!!!!!!!!!
        	String encodedText = new String(Base64.encodeBase64(parametersToCall.getBytes(Charset.forName("UTF-8"))));
            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is established.
            int timeoutConnection = 300000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 300000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);           
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(url);      
            //set data to StringEntity
            StringEntity se = new StringEntity(encodedText);
            //set httpPost Entity
           	httpPost.setEntity(se);
            //Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "text/plain");
            httpPost.setHeader("Content-type", "text/plain");
            //Execute POST request to the given URL
            HttpResponse response = httpclient.execute(httpPost);
            //receive response as inputStream
            json = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {        	
        	Writer writer = new StringWriter();
        	PrintWriter printWriter = new PrintWriter(writer);
        	e.printStackTrace(printWriter);
        	json = "Error: " + writer.toString();       
        }	
		
		return json;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> ProcessResult(String json) {
	   	JSONObject obj;
	    Map<String, Object> map = new HashMap();
	   	try {
			obj = new JSONObject(json);
		    Iterator keys = obj.keys();
		    while (keys.hasNext()) {
		    	String key = (String) keys.next();
		        map.put(key, obj.get(key));
		    }
    	} catch (JSONException e) {
        	Writer writer = new StringWriter();
        	PrintWriter printWriter = new PrintWriter(writer);
        	e.printStackTrace(printWriter);
		}
    	return(map);	
   	}
	
	public static String getParam192() {
		return(java.util.UUID.randomUUID().toString().substring(0, 32));		
	}
	
	public static String getTimeStamp() {
		 Date date = new Date();
		 return (Formatter.formatNewTimeStamp(date));	
	}

	public static String getDate() {
		 Date date = new Date();
		 return (Formatter.formatDate(date));	
	}
	
	public static String getTime() {
		 Date date = new Date();
		 return (Formatter.formatTime(date));	
	}
}