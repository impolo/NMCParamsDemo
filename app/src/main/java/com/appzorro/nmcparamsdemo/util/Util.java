package com.appzorro.nmcparamsdemo.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.Charsets;

import com.appzorro.nmcparamsdemo.dl.DtoDefaultValues;

public class Util {
	public static Bitmap getBitmapFromUrl(String imageName) throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		Bitmap bmp;
 		URL url = new URL(DtoDefaultValues.getServerImage() + imageName);
	    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		return(bmp);
	}
	
	public static String getDeviceType() {
		String deviceModel = "";
		String deviceCode = "2206";
		deviceModel = Build.MODEL;
		if(deviceModel.toUpperCase().equals("Q8H"))
			deviceCode = "2204";
		if(deviceModel.toUpperCase().equals("IBT1000"))
			deviceCode = "2205";
		if(deviceModel.toUpperCase().equals("RK30SDK") || deviceModel.toUpperCase().equals("RK312X"))
			deviceCode = "2206";
		if(deviceModel.toUpperCase().equals("RK3188")){
			deviceCode = "2205";
		}
//		return "2206";
		return deviceCode;
	}
	
	public static void ShowMessage(Context context, String message, int duration) {
	        Toast toast = Toast.makeText(context, message, duration);
	        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 20);
	        toast.show();                
	}
	/**
	 * 
	 * @param lengtT
	 * @param data
	 * @return
	 */
	public static String lengtT(int lengtT, String data) {
        String d = "";
        if (data != null) {
            if (data.length() < lengtT) {
                for (int i = data.length(); i < lengtT; i++) {
                    d += "0";
                }
            }
            data = d + data;
        } else {
            data = "00000000000";
        }
        return data;
    }
	
    public static String ConvertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }
    
    public static String ConvertHexToString(String hex) {
    	String unhex = "";
    	if(hex.length() > 0){
    	ByteBuffer bytBuffer = ByteBuffer.allocate(hex.length() / 2);
    	for( int i = 0; i < hex.length(); i+=2){
    		bytBuffer.put((byte)Long.parseLong(hex.substring(i, i+2), 16));
    	}
    	bytBuffer.rewind();
    	Charset charSet = Charset.forName(Charsets.UTF_8.name());
    	CharBuffer chaBuffer = charSet.decode(bytBuffer);
    	unhex = chaBuffer.toString();
    	}
    	return unhex;
    }


   public static String GetDeviceId() {
	   return ConvertStringToHex(GetMacAddress());
   }
   
   public static String GetDeviceId(Activity activity){
	   return ConvertStringToHex(GetMacAddress(activity));
   }
      
	@SuppressLint("NewApi") private static String GetMacAddress() {
	    try {
	        List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
	        for (NetworkInterface intf : interfaces) {
	        	String test = intf.getName().toLowerCase();
	            if ((test.equals("wlan0")) || (test.equals("eth0"))) {
	            	byte[] mac = intf.getHardwareAddress();
	            	if (mac == null) {
	            		return "NoMac";
	            	}
	            	StringBuilder buf = new StringBuilder();
	            	for (int idx=0; idx < mac.length; idx++){
	            		buf.append(String.format("%02X:", mac[idx]));
	            	}
	            	if (buf.length() > 0) {
	            		buf.deleteCharAt(buf.length()-1);
	            	}
	            		return buf.toString();
	            }
	        }
	    } catch (Exception ex) {
	    	Log.e("Util", ex.getMessage());
	    } 
	    return "NoMac";
	}
		
	private static String GetMacAddress(Activity activity){
		WifiManager manager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		String address = info.getMacAddress();
		return address;
	}
	
	public static int GetRandomNumber(int min, int max) {
        int numPosibilidades = max - min;
        double aleat = Math.random() * numPosibilidades;
        aleat = Math.floor(aleat);
        int result = min + (int)aleat;
        return result;
    }
	
	public static void WriteLog(String message, URI fileUri){
		Calendar calendar = Calendar.getInstance();
		File logFile = new File(fileUri);
		if(!logFile.exists()){
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				Log.e( "Util.WriteLog", e.getMessage() );
			}
		}
		try {
			BufferedWriter writerBuffer = new BufferedWriter(new FileWriter(logFile, true));
			writerBuffer.append(calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH)  + "/" + calendar.get(Calendar.YEAR)  + "  " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)  + ":" + calendar.get(Calendar.SECOND)  + ":" + calendar.get(Calendar.MILLISECOND) +" -- "+ message);
			writerBuffer.newLine();
			writerBuffer.flush();
			writerBuffer.close();
		} catch (IOException e) {
			Log.e( "Util.WriteLog", e.getMessage() );
			// TODO throw or handle exception
		}
	}
}