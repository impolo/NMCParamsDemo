package com.appzorro.nmcparamsdemo.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.appzorro.nmcparamsdemo.dl.DtoDefaultValues;

@SuppressLint("NewApi") 
public class Operations {
	private static Map<String, Object> map;
	private static Map<String, Object> map2;
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String OperationCancel() { //cancel transaction
		String result = "";
		if (DtoDefaultValues.getTransactionId().length() > 0) {
			map = new HashMap();
			String dataPlana = "{\"101\":\"020400191\",\"PARAM\":{\"114.145\":\""
					+ DtoDefaultValues.getTransactionId() + "\"}}";
			String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
					+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\"" 
					+ DtoDefaultValues.getLanguage() + "\",\"53\":\"" 
					+ DtoDefaultValues.getMerchantId() + "\",\"57\":\""
					+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
					+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
					+ DtoDefaultValues.getLongitude() + "\",\"OPTLST\":["
					+ dataPlana + "]}";	
			String response = Client.Caller(parametersToCall);	
			try {
				String[] responseList = response.split("RESULT");
				String json1 = responseList[0].substring(0,
					responseList[0].length()-2);
				map2 = Client.ProcessResult(json1+"}");
				result = "Approved";
				DtoDefaultValues.setTransactionCanceled(true);
			} catch (Exception ex) {
				result = "Error";
			}
		}
		return(result);
	}
	

	public static boolean OperationInitial () {
		boolean correct = false;
	   	String dataPlana = "{\"101\":\"010100027\",\"PARAM\":{\"127.14\":\"MOBILE\"}},{\"101\":\"010300215\",\"PARAM\":{\"53\":\""
	   			+ DtoDefaultValues.getMerchantCRC() + "\",\"57\":\"" + /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() + "\"}}";
	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"57\":\"" 
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\"" 
	   			+ DtoDefaultValues.getLongitude()  + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";
	   	String response = Client.Caller(parametersToCall);	
	   	try {
	   		Log.i("param",response);
	   		JsonParser parser = new JsonParser();
	   		JsonObject objJson = parser.parse(response).getAsJsonObject();
	   		if(!objJson.get("_122_17").getAsBoolean()) {
	   			correct =true;
	   			
	   			JsonArray arrResult = objJson.get("RESULT").getAsJsonArray();
	   			
	   			Iterator<JsonElement> itr = arrResult.iterator();
	   			while(itr.hasNext()) {
	   				JsonElement item = (JsonElement)itr.next();
	   				if(item.isJsonObject()) {
	   					JsonObject tmp = item.getAsJsonObject();
	   					if(tmp.get("_39").getAsString().equals("0000")) {
	   						if(tmp.get("_101").getAsString().equals("010100027")) {
	   							JsonArray arrInnerResult = tmp.get("RESULT").getAsJsonArray();
		   						Iterator<JsonElement> innerItr = arrInnerResult.iterator();
		   						while(innerItr.hasNext()) {
		   							JsonElement innerItem = (JsonElement)innerItr.next();
		   							if(innerItem.isJsonObject()) {
		   								JsonObject appParam = innerItem.getAsJsonObject();
		   								if(appParam.get("_127_11").getAsString().equals("IMAGE_URL")) {
		   									DtoDefaultValues.setServerImage(appParam.get("127.12").getAsString());
		   								} else if (appParam.get("_127_11").getAsString().equals("VIDEO_URL")) {
		   									DtoDefaultValues.setServerVideo(appParam.get("127.12").getAsString());
		   								}else if(appParam.get("_127_11").getAsString().equals("SENDER_ID")){
		   									DtoDefaultValues.setSenderId(appParam.get("127.12").getAsString());
		   								}
		   							}
		   						}
	   						} else if(tmp.get("101").getAsString().equals("010300215")) {
	   							JsonArray arrInnerResult = tmp.get("RESULT").getAsJsonArray();
	   							Iterator<JsonElement> innerItr = arrInnerResult.iterator();
	   							while(innerItr.hasNext()) {
	   								JsonElement innerItem = (JsonElement)innerItr.next();
	   								if(innerItem.isJsonObject()) {
	   									JsonObject merchantData = innerItem.getAsJsonObject();
	   									DtoDefaultValues.setMerchantId(
	   										merchantData.get("_53").getAsString());
	   									DtoDefaultValues.setMerchantLogo(
	   										merchantData.get("_121_170").getAsString());
	   									DtoDefaultValues.setMerchantName(
	   										merchantData.get("_114_170").getAsString());
	   									DtoDefaultValues.setLocationId(
	   										merchantData.get("_114_47").getAsString());
	   									DtoDefaultValues.setTerminalId(
	   										merchantData.get("_120_69").getAsString());
	   									DtoDefaultValues.setNickName(
	   										merchantData.get("_122_126").getAsString());
	   									DtoDefaultValues.setRegistrationId(
	   										merchantData.get("_122_12").getAsString());
	   								}
	   							}
	   						}
	   					} else
	   						correct = false;
	   				}
	   			}
	   		} else
	   			correct = false;

	   	} catch (Exception ex) {
	   		correct = false;
	   	}
	
		return (correct);
	}
	
	public static boolean OperationInitial(File logFile){//agregar un boleano a la entrada si es verdadero usar el 192 en memoria si es falso usar el quemado feefc7e1ffc9c1f0aa853c8bac8c6c18
		boolean correct = false;
	   	String dataPlana = "{\"101\":\"010100027\",\"PARAM\":{\"127.14\":\"MOBILE\"}}";
	   	Util.WriteLog("Start operationInitial,request: " + dataPlana, logFile.toURI());//Log to file

	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"57\":\"" 
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\"" 
	   			+ DtoDefaultValues.getLongitude()  + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";
		Log.i("Start operationInitial,request: ", parametersToCall);
	   	String response = Client.Caller(parametersToCall);
	   	//Log.i("respuesta:", response);
	   	try {
	   		Util.WriteLog("operationInitial,response: " + response, logFile.toURI());//Log to file
	   		Log.i("operationInitial,response: ",response);
	   		JsonParser parser = new JsonParser();
	   		JsonObject objJson = parser.parse(response).getAsJsonObject();
	   		if(!objJson.get("_122_17").getAsBoolean()) {
	   			correct =true;
	   			
	   			JsonArray arrResult = objJson.get("RESULT").getAsJsonArray();
	   			
	   			Iterator<JsonElement> itr = arrResult.iterator();
	   			while(itr.hasNext()) {
	   				JsonElement item = (JsonElement)itr.next();
	   				if(item.isJsonObject()) {
	   					JsonObject tmp = item.getAsJsonObject();
	   					if(tmp.get("_39").getAsString().equals("0000")) {
	   						if(tmp.get("_101").getAsString().equals("010100027")) {
	   							JsonArray arrInnerResult = tmp.get("RESULT").getAsJsonArray();
		   						Iterator<JsonElement> innerItr = arrInnerResult.iterator();
		   						while(innerItr.hasNext()) {
		   							JsonElement innerItem = (JsonElement)innerItr.next();
		   							if(innerItem.isJsonObject()) {
		   								JsonObject appParam = innerItem.getAsJsonObject();
		   								if(appParam.get("_127_11").getAsString().equals("IMAGE_URL")) {
		   									DtoDefaultValues.setServerImage(appParam.get("_127_12").getAsString());
		   								} else if (appParam.get("_127_11").getAsString().equals("VIDEO_URL")) {
		   									DtoDefaultValues.setServerVideo(appParam.get("_127_12").getAsString());
		   								} else if(appParam.get("_127_11").getAsString().equals("SENDER_ID")){
		   									DtoDefaultValues.setSenderId(appParam.get("_127_12").getAsString());
		   								} else if(appParam.get("_127_11").getAsString().equals("UPDATE_URL")){
		   									DtoDefaultValues.setAppUpdateUrl(appParam.get("_127_12").getAsString());
		   								} else if(appParam.get("_127_11").getAsString().equals("PASSWORD_DONGLE")){
		   									DtoDefaultValues.setQuitPassword(appParam.get("_127_12").getAsString());
		   								}
		   							}
		   						}
	   						} else if(tmp.get("_101").getAsString().equals("010300215")) {
	   							JsonArray arrInnerResult = tmp.get("RESULT").getAsJsonArray();
	   							Iterator<JsonElement> innerItr = arrInnerResult.iterator();
	   							while(innerItr.hasNext()) {
	   								JsonElement innerItem = (JsonElement)innerItr.next();
	   								if(innerItem.isJsonObject()) {
	   									JsonObject merchantData = innerItem.getAsJsonObject();
	   									DtoDefaultValues.setMerchantId(
	   										merchantData.get("_53").getAsString());
	   									DtoDefaultValues.setMerchantLogo(
	   										merchantData.get("_121_170").getAsString());
	   									DtoDefaultValues.setMerchantName(
	   										merchantData.get("_114_170").getAsString());
	   									DtoDefaultValues.setLocationId(
	   										merchantData.get("_114_47").getAsString());
	   									DtoDefaultValues.setTerminalId(
	   										merchantData.get("_120_69").getAsString());
	   									DtoDefaultValues.setNickName(
	   										merchantData.get("_122_126").getAsString());
	   									DtoDefaultValues.setRegistrationId(
	   										merchantData.get("_122_12").getAsString());
	   									DtoDefaultValues.setIsMerchantOk(merchantData.get("_114_9").getAsBoolean());
	   								}
	   							}
	   						}
	   					} else
	   						correct = false;
	   				}
	   			}
	   		} else
	   			correct = false;

	   	} catch (Exception ex) {
	   		Util.WriteLog("operationInitial,error: " + ex.getMessage(), logFile.toURI());//Log to file
	   		correct = false;
	   	}
	
		return (correct);
	}
	
	public static boolean OperationSignature (String dataURL) { 
		boolean result = false;
	   	String dataPlana = "{\"101\":\"030300275\",\"PARAM\":{\"121.75\":\""
	   			+ Util.lengtT(11, DtoDefaultValues.getInvoiceId())
	   			+ "\", \"127.60\":\"" + dataURL + "\"}}";
	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"53\":\"" 
	   			+ DtoDefaultValues.getMerchantId() + "\",\"57\":\""
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\"" 
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
	   			+ DtoDefaultValues.getLongitude() + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";	
	   	String response = Client.Caller(parametersToCall);	
	   	try {
	   		String[] responseList = response.split("RESULT");
	   		
	   		String json1 = responseList[0].substring(0,
	   			responseList[0].length()-2);
	   		map2 = Client.ProcessResult(json1+"}");
   			if (map2.get("_122_17").toString().equals("false")) {
   				result = true; 
   			}

	   	} catch (Exception ex) {
	   		Log.e("Operations", ex.getMessage());
	   	}
	   	return(result);
	}
	
	public static boolean OperationLocations () {
		boolean correct = false;
		String strLocation="";
		String strPhoneNumber = "";
		DtoDefaultValues.clearLocations();
	   	String dataPlana = "{\"101\":\"010100368\",\"PARAM\":{\"53\":\"" 
	   			+ DtoDefaultValues.getMerchantId() + "\",\"122.126\":\""
	   			+ DtoDefaultValues.getNickName() + "\"}}";
	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"57\":\""
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\"" 
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
	   			+ DtoDefaultValues.getLongitude()  + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";
	    String response = Client.Caller(parametersToCall);	
	   	try {
			JsonParser parser = new JsonParser();
			JsonObject objJson = parser.parse(response).getAsJsonObject();
			if(!objJson.get("_122_17").getAsBoolean()) {
				correct = true;
				
				JsonArray arrResult = objJson.get("RESULT").getAsJsonArray();
				
				Iterator<JsonElement> itr = arrResult.iterator();
				while(itr.hasNext()) {
					JsonElement item = (JsonElement)itr.next();
					if(item.isJsonObject()) {
						JsonObject tmp = item.getAsJsonObject();
						if(tmp.get("_39").getAsString().equals("0000")) {
							JsonArray arrInnerResult = tmp.get("RESULT").getAsJsonArray();
							Iterator<JsonElement> innerItr = arrInnerResult.iterator();
							while(innerItr.hasNext()) {
								JsonElement innerItem = (JsonElement)innerItr.next();
								if(innerItem.isJsonObject()){
									JsonObject objLocation = innerItem.getAsJsonObject();
									strLocation = objLocation.get("_114_47").getAsString() + "-" + objLocation.get("_114_70").getAsString();
									strLocation += "-" + objLocation.get("AD").getAsJsonObject().get("_114_12").getAsString();
									strLocation += "-" + objLocation.get("AD").getAsJsonObject().get("CI").getAsJsonObject().get("_47_15").getAsString();
									strLocation += "-" + objLocation.get("AD").getAsJsonObject().get("ST").getAsJsonObject().get("_47_16").getAsString();//usar un string builder!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
									if(objLocation.get("AD").getAsJsonObject().get("ST").getAsJsonObject().has("_48_28")){
										strPhoneNumber = objLocation.get("AD").getAsJsonObject().get("ST").getAsJsonObject().get("_48_28").getAsString();
									} else {
										strPhoneNumber = "-no Phone";
									}
									strLocation += strPhoneNumber;
									DtoDefaultValues.setLocations(strLocation);
								}
							}
						}
					}
				}
			}
	
	   	} catch (Exception ex) {
	   		correct = false;
	   	}
	
		return (correct);
	}
	
	public static boolean OperationLocations(File logFile){
		boolean correct = false;
		String strLocation="";
		String strPhoneNumber = "";
		DtoDefaultValues.clearLocations();
	   	String dataPlana = "{\"101\":\"010100368\",\"PARAM\":{\"53\":\"" 
	   			+ DtoDefaultValues.getMerchantId() + "\",\"122.126\":\""
	   			+ DtoDefaultValues.getNickName() + "\"}}";
	   	Util.WriteLog("Start operationLocations,request: " + dataPlana, logFile.toURI());//Log to file
	   	Log.i("Start operationLocations,request: ", dataPlana);
	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"57\":\""
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\"" 
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
	   			+ DtoDefaultValues.getLongitude()  + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";
	   	Log.i("Start operationLocations,request: ", parametersToCall);
	    String response = Client.Caller(parametersToCall);
	    Util.WriteLog("operationLocations,response: " + response, logFile.toURI());//Log to file
	   	try {
	   		Util.WriteLog("operationLocations,response: " + response, logFile.toURI());//Log to file
	   		Log.i("Start operationLocations,response: ", response);
			JsonParser parser = new JsonParser();
			JsonObject objJson = parser.parse(response).getAsJsonObject();
			if(!objJson.get("_122_17").getAsBoolean()) {
				correct = true;
				
				JsonArray arrResult = objJson.get("RESULT").getAsJsonArray();
				
				Iterator<JsonElement> itr = arrResult.iterator();
				while(itr.hasNext()) {
					JsonElement item = (JsonElement)itr.next();
					if(item.isJsonObject()) {
						JsonObject tmp = item.getAsJsonObject();
						if(tmp.get("_39").getAsString().equals("0000")) {
							JsonArray arrInnerResult = tmp.get("RESULT").getAsJsonArray();
							Iterator<JsonElement> innerItr = arrInnerResult.iterator();
							while(innerItr.hasNext()) {
								JsonElement innerItem = (JsonElement)innerItr.next();
								if(innerItem.isJsonObject()){
									JsonObject objLocation = innerItem.getAsJsonObject();
									if(objLocation.has("_114_47")){
										strLocation = objLocation.get("_114_47").getAsString() + "-" + objLocation.get("_114_70").getAsString();
										strLocation += "-" + objLocation.get("AD").getAsJsonObject().get("_114_12").getAsString();
										strLocation += "-" + objLocation.get("AD").getAsJsonObject().get("CI").getAsJsonObject().get("_47_15").getAsString();
										strLocation += "-" + objLocation.get("AD").getAsJsonObject().get("ST").getAsJsonObject().get("_47_16").getAsString();//usar un string builder!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
										if(objLocation.get("PH").getAsJsonObject().has("_48_28")){
											strPhoneNumber = "-" + objLocation.get("PH").getAsJsonObject().get("_48_28").getAsString();
										} else {
											strPhoneNumber = "-no Phone";
										}
										strLocation += strPhoneNumber;
										DtoDefaultValues.setLocations(strLocation);
									}
								}
							}
						}
					}
				}
			}
	
	   	} catch (Exception ex) {
	   		Util.WriteLog("operationLocations,error: " + ex.getMessage(), logFile.toURI());//Log to file
	   		Log.e("operation locations", ex.getMessage());
	   		correct = false;
	   	}
	
		return (correct);
	}
	
	public static String OperationSendLocations () {
		String correct = "0";
	   	String dataPlana = "{\"101\":\"020300301\",\"PARAM\":{\"57\":\""
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"122.126\":\""
	   			+ DtoDefaultValues.getNickName() + "\",\"114.47\":\"" 
	   			+ Util.lengtT(11, DtoDefaultValues.getLocationId())
	   			+ "\", \"122.170\":\"" + Util.getDeviceType() 
	   			+ "\", \"122.12\":\"" + DtoDefaultValues.getRegistrationId()
	   			+ "\"}}";
	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"57\":\""
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
	   			+ DtoDefaultValues.getLongitude()  + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";
	   	String response = Client.Caller(parametersToCall);	
	   	try {
	   		String[] responseList = response.split("RESULT");

	   		String json1 = responseList[0].substring(0,
	   			responseList[0].length()-2);
	   		map2 = Client.ProcessResult(json1+"}");
	   		if (map2.get("_122_17").toString().equals("false")) {
		   		String json2 = (responseList[2].replace("]}]}", "")).replace("\":[",
		   			"");
		   		map = Client.ProcessResult(json2);
	   			DtoDefaultValues.setTerminalId(map.get("_120_69").toString());
	   			correct = "1";
	   		} else {
	   			String json2 = responseList[1].substring(3, responseList[1].length()-2);
		   		map = Client.ProcessResult(json2 + "}");
		   		if (map.get("_39").toString().equals("2056")) {
			   		correct = "2056";
		   		} else {
		   			correct = "0";
		   		}
	   		}
	   	} catch (Exception ex) {
	   		correct = "0";
	   	}
	
		return (correct);
	}
	
	public static boolean OperationGetScheduleLocation () {
		boolean isCorrect = false;
		DtoDefaultValues.setMerchantHours("");
	   	String dataPlana = "{\"101\":\"010100306\",\"PARAM\":{\"114.47\":\""
	   			+ Util.lengtT(11, DtoDefaultValues.getLocationId()) + "\"}}";
	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"57\":\""
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
	   			+ DtoDefaultValues.getLongitude()  + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";
	   	String response = Client.Caller(parametersToCall);	
	   	try {
	   		JsonParser parser = new JsonParser();
	   		JsonObject objJson = parser.parse(response).getAsJsonObject();
	   		if(!objJson.get("_122_17").getAsBoolean()){
	   			isCorrect = true;
	   			JsonArray arrResult = objJson.getAsJsonArray("RESULT");
	   			JsonArray arrInnerResult = arrResult.get(0).getAsJsonObject().getAsJsonArray("RESULT");
	   			JsonArray arrSchedule = arrInnerResult.get(0).getAsJsonObject().getAsJsonArray("SL");
	   			for(int i = 0; i < arrSchedule.size(); i++){
	   				ProcessSchedule(arrSchedule.get(i).getAsJsonObject());
	   			}
	   		} else {
	   			DtoDefaultValues.setMerchantHours("No data");
	   		}
	   		/*
	   		String[] responseList = response.split("RESULT");

	   		String json1 = responseList[0].substring(0, responseList[0].length()-2);
	   		map2 = Client.ProcessResult(json1+"}");
	   		if (map2.containsKey("122.18")) {
	   			DtoDefaultValues.setKey(map2.get("122.18").toString());
	   			//aux.UpdateKey();
	   		}
	   		if (map2.get("122.17").toString().equals("false")) {
	   			String json2 = responseList[2];
	   			json2 = json2.substring(json2.lastIndexOf("SL")+5, json2.length()-6);
   	 
	   			int count = countStr(json2, "127.89");
	   			while (count > 0) {
	   				String temp = "";
	   				if (count != 1) {
	   					temp = json2.substring(json2.indexOf("127.89")-2,
	   						json2.indexOf(",{"));
	   				}
	   				map = Client.ProcessResult(temp);
	   				ProcessShedule(map);
	   				json2 = json2.replace(temp+",",  "");
	   				count--;
	   			}
	   		} else {
				DtoDefaultValues.setMerchantHours("No data");
	   		}*/
	   	} catch (Exception ex) {
	   		isCorrect = false;
	   		if (!(DtoDefaultValues.getMerchantHours().length() > 0)) {
	   			DtoDefaultValues.setMerchantHours("No data");
	   		}
	   	}
	
		return (isCorrect);
	}
	
	public static boolean OperationPayNMC() { 
		boolean correct = false;
		String payNmcId = Util.ConvertStringToHex(DtoDefaultValues.getPayNmcId());
	   	String dataPlana = "{\"101\":\"010300189\",\"PARAM\":{\"114.145\":\""
	   			+ DtoDefaultValues.getTransactionId() + "\",\"127.61\":\""
	   			+ payNmcId + "\"}}";
	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"53\":\""
	   			+ DtoDefaultValues.getMerchantId() + "\",\"57\":\""
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
	   			+ DtoDefaultValues.getLongitude() + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";
	   	String response = Client.Caller(parametersToCall);	
	   	try {
	   		String[] responseList = response.split("RESULT");
	   		String json1 = responseList[0].substring(0,
	   			responseList[0].length()-2);
	   		map2 = Client.ProcessResult(json1+"}");
   			if (map2.get("_122_17").toString().equals("false")) {
   				String json = (responseList[2].replace("]}]}", "")).replace("\":[",
   					"");
   				String jsonquestion1 = json.substring(json.indexOf("_53")-2,
   					json.indexOf("},{"));
   				String jsonquestion2 = json.substring(json.lastIndexOf("_53")-2,
   					json.length());
			
   				map = Client.ProcessResult(jsonquestion1+"}");	
   				map2 = Client.ProcessResult(jsonquestion2.substring(0,
   					jsonquestion2.length()-2));

	   			DtoDefaultValues.setPayNmcId(map.get("_53").toString());
	   			DtoDefaultValues.setSecurityQuestion(map.get("_122_71").toString());   			
	   			DtoDefaultValues.setCorrectAnswer(map.get("_119_4").toString());
	   			String[] answers1 = map.get("_119_6").toString().split("~");
	   			
	   			DtoDefaultValues.setAnswers(answers1[0], 1);
	   			DtoDefaultValues.setAnswers(answers1[1], 1);
	   			DtoDefaultValues.setAnswers(DtoDefaultValues.getCorrectAnswer(), 1);

	   			DtoDefaultValues.setSecurityQuestion2(map2.get("_122_71").toString());   			
	   			DtoDefaultValues.setCorrectAnswer2(map2.get("_119_4").toString());
	   			String[] answers2 = map2.get("_119_6").toString().split("~");
	   			
	   			DtoDefaultValues.setAnswers(answers2[0], 2);
	   			DtoDefaultValues.setAnswers(answers2[1], 2);
	   			DtoDefaultValues.setAnswers(DtoDefaultValues.getCorrectAnswer2(), 2);
	   			correct = true;
   			}
	   	} catch (Exception ex) {
	   		correct = false;
		}
	   	return(correct);
	}
	
	public static boolean OperationGetPayData () { 
		boolean result = false;
	   	String dataPlana = "{\"101\":\"010100312\",\"PARAM\":{\"121.75\":\""
	   			+ Util.lengtT(11, DtoDefaultValues.getInvoiceId()) + "\"}}";
	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"53\":\""
	   			+ DtoDefaultValues.getMerchantId() + "\",\"57\":\""
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
	   			+ DtoDefaultValues.getLongitude() + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";	
	   	String response = Client.Caller(parametersToCall);	
	   	try {
	   		JsonParser parser = new JsonParser();
	   		JsonObject objJson = parser.parse(response).getAsJsonObject();
	   		if(!objJson.get("_122_17").getAsBoolean()) {
	   			result = true;
	   			
	   			JsonArray arrResult = objJson.get("RESULT").getAsJsonArray();
	   			
	   			Iterator<JsonElement> itr = arrResult.iterator();
	   			while(itr.hasNext()) {
	   				JsonElement item = (JsonElement)itr.next();
	   				if(item.isJsonObject()) {
	   					JsonObject tmp = item.getAsJsonObject();
	   					if(tmp.get("_39").getAsString().equals("0000")) {
	   						JsonArray arrInnerResult = tmp.get("RESULT").getAsJsonArray();
	   						Iterator<JsonElement> innerItr = arrInnerResult.iterator();
	   						while(innerItr.hasNext()) {
	   							JsonElement innerItem = (JsonElement)innerItr.next();
	   							if(innerItem.isJsonObject()) {
	   								JsonObject payData = innerItem.getAsJsonObject();
	   								DtoDefaultValues.setCardType(payData.get("_121_72").getAsString());
	   								DtoDefaultValues.setLastDigits(payData.get("_2").getAsString());
	   								DtoDefaultValues.setApprovalNumber(payData.get("_38").getAsString());
	   								DtoDefaultValues.setRrn(payData.get("_37").getAsString());
	   								DtoDefaultValues.setCardNickname(payData.get("_48_6").getAsString());
	   								DtoDefaultValues.setCustomerName(payData.get("_114_53").getAsString());
	   								String card = payData.get("_121_72").getAsString() + " - " + DtoDefaultValues.getCurrency() + " " + payData.get("_121_110").getAsString();
	   								if (DtoDefaultValues.getCardPay().length() > 0) {
	   									DtoDefaultValues.setCardPay(DtoDefaultValues.getCardPay() + "\n" + card);
	   								} else {
	   									DtoDefaultValues.setCardPay(card);
	   								}
	   							}
	   						}
	   					}
	   				}
	   			}
	   		}
	   	} catch (Exception ex) {
	   		Log.e("Operations", ex.getMessage());
	   	}
	   	return(result);
	}
	
	private static void ProcessSchedule(JsonObject scheduleDay){
		String strScheduleDay =  "";
		switch(Integer.parseInt(scheduleDay.get("_127_89").getAsString())){
			case(0):
				strScheduleDay = "Monday: ";
				break;
			case(1):
				strScheduleDay = "Tuesday: ";
				break;
			case(2):
				strScheduleDay = "Wednesday: ";
				break;
			case(3):
				strScheduleDay = "Thursday: ";
				break;
			case(4):
				strScheduleDay = "Friday: ";
				break;
			case(5):
				strScheduleDay = "Saturday: ";
				break;
			case(6):
				strScheduleDay = "Sunday: ";
				break;
		}
		if(Boolean.parseBoolean(scheduleDay.get("_127_90").getAsString())){
			strScheduleDay += "Open All Day";
		}
		if(Boolean.parseBoolean(scheduleDay.get("_127_91").getAsString())){
			strScheduleDay += "Closed";
		}
		if(!Boolean.parseBoolean(scheduleDay.get("_127_90").getAsString()) && !Boolean.parseBoolean(scheduleDay.get("_127_91").getAsString())){
			strScheduleDay += scheduleDay.get("_127_64").getAsString() + " - " + scheduleDay.get("_127_65").getAsString();
			
		}
		
		if(DtoDefaultValues.getMerchantHours().length() > 0){
			DtoDefaultValues.setMerchantHours(DtoDefaultValues.getMerchantHours() + "\n" + strScheduleDay);
		} else {
			DtoDefaultValues.setMerchantHours(strScheduleDay);
		}
		
	}
	
	private static void ProcessShedule(Map<String, Object> map) {
		String Shedule = "";
		if (map.containsKey("_127_89")) {
			if (DtoDefaultValues.getLanguage().equals("es")) {
				switch(Integer.parseInt(map.get("_127_89").toString())) {
					case(0):
						Shedule = "Lunes";
						break;
					case(1):
						Shedule = "Martes";
						break;
					case(2):
						Shedule = "Miercoles";
						break;
					case(3):
						Shedule = "Jueves";
						break;
					case(4):
						Shedule = "Viernes";
						break;
					case(5):
						Shedule = "S�bado";
						break;
					case(6):
						Shedule = "Domingo";
						break;
				}	
			} else {	
				switch(Integer.parseInt(map.get("_127_89").toString())) {
					case(0):
						Shedule = "Monday";
						break;
					case(1):
						Shedule = "Tuesday";
						break;
					case(2):
						Shedule = "Wednesday";
						break;
					case(3):
						Shedule = "Thursday";
						break;
					case(4):
						Shedule = "Friday";
						break;
					case(5):
						Shedule = "Saturday";
						break;
					case(6):
						Shedule = "Sunday";
						break;
				}	
			}
		}
		String startHour = map.get("_127_64").toString();
		String closeHour = map.get("_127_65").toString();
		if (startHour.length() > 5) {
			startHour = startHour.substring(0, 5);
		}
		if (closeHour.length() > 5) {
			closeHour = closeHour.substring(0, 5);
		}
		Shedule = Shedule + ": " + startHour + "-" + closeHour;
		if (DtoDefaultValues.getMerchantHours().length() > 0) {
			DtoDefaultValues.setMerchantHours(DtoDefaultValues.getMerchantHours()
				+ "\n" + Shedule);
		} else {
			DtoDefaultValues.setMerchantHours(Shedule);
		}
	}

	private static int countStr(String json, String strFind) {
	    Pattern p = Pattern.compile(strFind);
	    Matcher m = p.matcher(json);
	    int count = 0;
	    while (m.find()) {
	    	count +=1;
	    }
	   return(count);
	}
	
	public static String OperationGetInvoices (String dataSearch) {
		String result = "";
		String dataPlana = "{\"101\":\"010300333\",\"FILTER\":[" + dataSearch
				+ "{\"53\":\"" + DtoDefaultValues.getMerchantId()
				+ "\"}],\"EXPECTED\":\"114.3,114.5,37\"}";
	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"53\":\""
	   			+ DtoDefaultValues.getMerchantId() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"57\":\""
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
	   			+ DtoDefaultValues.getLongitude()  + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";
	   	String response = Client.Caller(parametersToCall);	
	   	try {
	   		String[] responseList = response.split("RESULT");

	   		String json1 = responseList[0].substring(0,
	   			responseList[0].length()-2);
	   		map2 = Client.ProcessResult(json1+"}");
	   		result = responseList[2];
	   	} catch (Exception ex) {
	   		result = response;
	   	}
	
		return (result);
	}
	
	public static boolean OperationRefund (String invoice) {
		boolean correct = false;
	   	String dataPlana = "{\"101\":\"020300332\",\"PARAM\":{\"121.75\":\""
	   			+ Util.lengtT(11, invoice) + "\"}}";
	   	String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
	   			+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"53\":\""
	   			+ DtoDefaultValues.getMerchantId() + "\",\"122.45\":\""
	   			+ DtoDefaultValues.getLanguage() + "\",\"57\":\""
	   			+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
	   			+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
	   			+ DtoDefaultValues.getLongitude()  + "\",\"OPTLST\":["
	   			+ dataPlana + "]}";
	   	String response = Client.Caller(parametersToCall);	
	   	try {
	   		String[] responseList = response.split("RESULT");

	   		String json1 = responseList[0].substring(0,
	   			responseList[0].length()-2);
	   		map2 = Client.ProcessResult(json1+"}");
	   		String json2 = (responseList[2].replace("]}]}", "")).replace("\":[",
	   			"");
	   		map = Client.ProcessResult(json2);
	   		if (map2.get("122.17").toString().equals("false")) {
	   			correct = true;
	   		} else {
	   			correct = false;
	   		}
	   	} catch (Exception ex) {
	   		correct = false;
	   	}
	
		return (correct);
	}
	
	public static String sendAck() {
		String terminalPosition ="";
		if(DtoDefaultValues.getTVDisplayMode() == 1){
			terminalPosition = "69002";
		}else{
			terminalPosition = "69001";
		}
		
		String result = "true";
		String plainData = "{\"101\":\"020400378\", \"PARAM\":{\"120.69\":\""
				+ DtoDefaultValues.getTerminalId() + "\",\"123.67\":\""+terminalPosition+"\"}}";
		String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
				+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
				+ DtoDefaultValues.getLanguage() + "\",\"53\":\""
				+ DtoDefaultValues.getMerchantId() + "\",\"57\":\""
				+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
				+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
				+ DtoDefaultValues.getLongitude() + "\",\"OPTLST\":["
				+ plainData + "]}";
		Log.i("operation ack", parametersToCall);
  		String response = Client.Caller(parametersToCall);
		result = response;
		try {
			result = response;
			Log.i("operationAck", response);
			JsonParser parser = new JsonParser();
			JsonArray arrayJson =  new JsonArray();
			JsonObject objJson = parser.parse(response).getAsJsonObject();
			if(!objJson.get("_122_17").getAsBoolean()) {
				result = objJson.get("RESULT").toString();
				arrayJson = parser.parse(result).getAsJsonArray();
				result = arrayJson.get(0).toString();
				objJson = parser.parse(result).getAsJsonObject();
				result = objJson.get("RESULT").toString();
				arrayJson = parser.parse(result).getAsJsonArray();
				result = arrayJson.get(0).toString();
				objJson = parser.parse(result).getAsJsonObject();
				DtoDefaultValues.setDayActual(objJson.get("_123_55").getAsString());
				DtoDefaultValues.setRegisterOnGCM(objJson.get("_114_9").getAsBoolean());
				DtoDefaultValues.setMerchantName(objJson.get("_114_70").getAsString());
				DtoDefaultValues.setIsUpToDate(objJson.get("_127_72").getAsBoolean());
				DtoDefaultValues.setServerVersionCode(objJson.get("_117_28").getAsLong());
			} else {
				result = "Error 122.17 is false";
			}
		} catch (Exception ex) {
			Log.i("Exception Ack", "error " + ex.getMessage());
//			result = "Error exception thrown " + ex.getMessage();
		}
		return result;
	}
	
	public static String sendAdvCount(String advJson) {
		String result = "";
		String plainData = "{\"101\":\"030400379\", \"PARAM\":{\"120.69\":\""
		+ DtoDefaultValues.getTerminalId() + "\"," + advJson + "}}";
		String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
				+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
				+ DtoDefaultValues.getLanguage() + "\",\"53\":\""
				+ DtoDefaultValues.getMerchantId() + "\",\"57\":\""
				+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
				+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
				+ DtoDefaultValues.getLongitude() + "\",\"OPTLST\":["
				+ plainData + "]}";
		String response = Client.Caller(parametersToCall);
		try {
			JsonParser parser = new JsonParser();
			JsonObject objJson = parser.parse(response).getAsJsonObject();
			if(!objJson.get("_122_17").getAsBoolean()) {
				result = objJson.get("RESULT").toString();
			} else {
				result = "Error";
			}
		} catch (Exception ex) {
			result = "Error";
		}
		return result;
	}
	
	public static String sendAdvCount(String advJson, File logFile) {
		String result = "";
		String plainData = "{\"101\":\"030400379\", \"PARAM\":{\"120.69\":\""
		+ DtoDefaultValues.getTerminalId() + "\"," + advJson + "}}";
		Util.WriteLog("sendAdvCount, plain data: " + plainData, logFile.toURI());//Log to file
		String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
				+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
				+ DtoDefaultValues.getLanguage() + "\",\"53\":\""
				+ DtoDefaultValues.getMerchantId() + "\",\"57\":\""
				+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
				+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
				+ DtoDefaultValues.getLongitude() + "\",\"OPTLST\":["
				+ plainData + "]}";
		Util.WriteLog("sendAdvCount, request: " + parametersToCall, logFile.toURI());//Log to file
		String response = Client.Caller(parametersToCall);
		Util.WriteLog("sendAdvCount, response: " + response, logFile.toURI());//Log to file
		try {
			Util.WriteLog("sendAdvCount, decrypted response: " + response, logFile.toURI());//Log to file
			JsonParser parser = new JsonParser();
			JsonObject objJson = parser.parse(response).getAsJsonObject();
			if(!objJson.get("_122_17").getAsBoolean()) {
				result = objJson.get("RESULT").toString();
			} else {
				result = "Error";
			}
		} catch (Exception ex) {
			result = "Error";
			Util.WriteLog("sendAdvCount, decrypted exception: " + ex.getMessage(), logFile.toURI());//Log to file
		}
		return result;
	}
	
	public static void sendRegistrationId(){
		String plainData = "{\"101\":\"020400417\", \"PARAM\":{\"120.69\":\""
				+ DtoDefaultValues.getTerminalId() + "\",\"122.12\":\""+ DtoDefaultValues.getRegistrationId() +"\"}}";
		String parametersToCall = "{\"192\":\"" + DtoDefaultValues.getParam192()
				+ "\",\"11\":\"" + Client.getTimeStamp() + "\",\"122.45\":\""
				+ DtoDefaultValues.getLanguage() + "\",\"53\":\""
				+ DtoDefaultValues.getMerchantId() + "\",\"57\":\""
				+ /*Util.GetDeviceId()*/DtoDefaultValues.getDeviceId() +"\",\"120.38\":\""
				+ DtoDefaultValues.getLatitude() + "\",\"120.39\":\""
				+ DtoDefaultValues.getLongitude() + "\",\"OPTLST\":["
				+ plainData + "]}";
		Log.i("sendRegistrationId, request:", parametersToCall);
  		String response = Client.Caller(parametersToCall);
  		try {
			Log.i("sendRegistrationId response:", response);
			JsonParser parser = new JsonParser();
			JsonArray arrayJson =  new JsonArray();
			JsonObject objJson = parser.parse(response).getAsJsonObject();
		} catch (Exception ex) {
			Log.i("Exception SendRegistrationId", "error" + ex.getMessage());
		}
	}

}