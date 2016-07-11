package com.appzorro.nmcparamsdemo.dl;

import java.util.ArrayList;

/// <summary>
/// Class that defines default values and static var used in app.
/// </summary>
/// <remarks>
/// Defines the properties, enumerations, constants, structures, etc with default values used in app.
/// </remarks>
public class DtoDefaultValues {
    private static int adDisplayTime = 10000;
	private static String amount = "";
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ArrayList<String> answers = new ArrayList();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static ArrayList<String> answers2 = new ArrayList();
	private static String approvalNumber = "";
	private static String btAddress = "";
	private static String cardNickname = "";
	private static String cardType = "";
	private static String cardsPay = "";
	private static String correctAnswer = "";        
    private static String correctAnswer2 = "";
	private static String customerName = ""; 
	private static String customVideoTitle = "";
	private static String currency = "$";
	private static String decimal = "2";
	private static String deviceId = "";
	private static String invoiceId = "";
    private static String ip = "100.43.205.74";// nmc-vmsrvr01.inetuhosted.net <-produccion || 100.43.205.74 <-desarrollo
	private static String isTransitionEnabled = "";
    private static String language = "en";
    private static String lastDigits = "";
    private static String lastItemId = "";//se usa?
    private static String latitude = "";
    private static int likeTimeout = 2000;
    private static String locationId = "";
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static ArrayList<String> locations = new ArrayList();
    private static String longitude = "";
    private static String merchantAddress = "";
    private static String merchantCRC = "";
    private static String merchantHours = "";
    private static String merchantLogo = "";
    private static String merchantId = "";
    private static String merchantName = "";
    private static String merchantPhone = "";
    private static String nickName = "";
    private static String param192 = "1f09eaddb545ff7c94b3c7106eede716";
    private static String payNmcId = "";
    private static String pin = "";
    private static String port = "";//"8081"<-desarrollo|443<-produccion|8095<-QA
    private static String pwdSettings = "1234";
    private static boolean registerOnGCM = false;
    private static String registrationId = "";
    private static String rrn = "";
    private static String securityQuestion = "";
    private static String securityQuestion2 = "";
    private static String tax = "0";
    private static String terminalId = "";
    private static int ticketNumber = 1;
    private static int timeout = 100000;
    private static boolean transactionCanceled = false;
    private static String transactionId = "";
    private static int tVDisplayMode = 2;//1 landscape | 0 portrait 
    private static String type = "HTTPS";
    private static Boolean isUpToDate = false;
    private static String urlServerImage = "";
    private static String urlServerVideo = "";
    private static String dayActual = "";//renombrar a serverDay
    private static String dayActual1 = "";//renombrar a localDay
    private static String senderId ="";
    private static boolean start = true;
    private static Boolean isMerchantOk = false;
    private static long serverVersionCode = 0;
    private static String appUpdateUrl = "";
    private static String quitPassword = "";

    public static int getAdDisplayTime() {
    	return adDisplayTime;
    }
    
    public static void setAdDisplayTime(int value) {
    	adDisplayTime = value;
    }
    
    public static String getAmount() {
		return amount;
	}

	public static void setAmount(String value) {
		amount = value;
	}
    
    public static void clearAnswers() {
		answers.clear();
		answers2.clear();
    }
    
    public static String[] getAnswers(int number) {
		String[] result = new String[3];
		ArrayList<Integer> index = new ArrayList<Integer>();
		index = getRandom();
		if (index.size() < 3) {
			if (number == 1) {
				result[0] = answers.get(0);
				result[1] = answers.get(1);
				result[2] = answers.get(2);
			} else if (number == 2) {
				result[0] = answers2.get(0);
				result[1] = answers2.get(1);
				result[2] = answers2.get(2);
			}
		} else {
			if (number == 1) {
				result[index.get(0)] = answers.get(0);
				result[index.get(1)] = answers.get(1);
				result[index.get(2)] = answers.get(2);
			} else if (number == 2) {
				result[index.get(0)] = answers2.get(0);
				result[index.get(1)] = answers2.get(1);
				result[index.get(2)] = answers2.get(2);
			}
		}	
		return (result);
	}

	public static void setAnswers(String value, int number) {
		if (number == 1){
			answers.add(value);
		} else if (number == 2) {
			answers2.add(value);
		}
	}
    
    public static String getApprovalNumber() {
    	return approvalNumber;
    }
    
    public static void setApprovalNumber(String value) {
    	approvalNumber = value;
    }
    
    public static String getBTAddress() {
    	return btAddress;
    }
    
    public static void setBTAddress(String value) {
    	btAddress = value;
    }
    
    public static String getCardNickname() {
    	return cardNickname;
    }
    
    public static void setCardNickname(String value) {
    	cardNickname = value;
    }
    
    public static String getCardPay() {
		return cardsPay;
	}

	public static void setCardPay(String value) {
		cardsPay = value;
	}
    
    public static String getCardType() {
    	return cardType;
    }
    
    public static void setCardType(String value) {
    	cardType = value;
    }
    
    public static String getCorrectAnswer() {
		return correctAnswer;
	}

	public static void setCorrectAnswer(String value) {
		correctAnswer = value;
	}
    
	public static String getCorrectAnswer2() {
		return correctAnswer2;
	}

	public static void setCorrectAnswer2(String value) {
		correctAnswer2 = value;
	}
	
	public static String getCurrency() {
		return currency;
	}

	public static void setCurrency(String value) {
		currency = value;
	}
	
    public static String getCustomVideoTitle() {
    	return customVideoTitle;
    }
    
    public static void setCustomVideoTitle(String value) {
    	customVideoTitle = value;
    }
    
    public static String getCustomerName() {
    	return customerName;
    }
    
    public static void setCustomerName(String value) {
    	customerName = value;
    }
    
    public static String getDecimal() {
		return decimal;
	}

	public static void setDecimal(String value) {
		decimal = value;
	}
    
	public static String getDeviceId(){
		return deviceId;
	}
	
	public static void setDeviceId(String value){
		deviceId = value;
	}
	
    public static String getInvoiceId() {
		return invoiceId;
	}

	public static void setInvoiceId(String value) {
		invoiceId = value;
	}
    
	public static String getIp() {
		return ip;
	}

	public static void setIp(String value) {
		ip = value;
	}
	
	public static String getIsTransitionEnabled(){
		return isTransitionEnabled;
	}
	
	public static void setIsTransitonEnabled(String value){
		isTransitionEnabled = value;
	}
	
	public static String getLanguage() {
		return language;
	}

	public static void setLanguage(String value) {
		language = value;
	}
	
	public static String getLastDigits() {
		return lastDigits;
	}
	
	public static void setLastDigits(String value) {
		lastDigits = value;
	}

	public static String getLastItemId() {
		return lastItemId;
	}

	public static void setLastItemId(String value) {
		lastItemId = value;
	}
	
	public static String getLatitude() {
		return latitude;
	}

	public static void setLatitude(String value) {
		latitude = value;
	}
	
	public static int getLikeTimeout() {
		return likeTimeout;
	}
	
	public static void setLikeTimeout(int value) {
		likeTimeout = value;
	}
	
	public static String getLocationId() {
		return locationId;
	}

	public static void setLocationId(String value) {
		locationId = value;
	}
	
	public static void clearLocations() {
		locations.clear();
    }
	
	public static  ArrayList<String> getLocations() {
		return (locations);
	}

	public static void setLocations(String value) {
			locations.add(value);
	}
	
	public static String getLongitude() {
		return longitude;
	}

	public static void setLongitude(String value) {
		longitude = value;
	}
	
	public static String getMerchantAddress() {
		return merchantAddress;
	}

	public static void setMerchantAddress(String value) {
		merchantAddress = value;
	}
	
	public static String getMerchantCRC() {
		return merchantCRC;
	}
	
	public static void setMerchantCRC(String value) {
		merchantCRC = value; 
	} 
	
	public static String getMerchantHours() {
		return merchantHours;
	}

	public static void setMerchantHours(String value) {
		merchantHours = value;
	}
	
	public static String getMerchantId() {
		return merchantId;
	}

	public static void setMerchantId(String value) {
		merchantId = value;
	}

	public static String getMerchantLogo() {
		return merchantLogo;
	}

	public static void setMerchantLogo(String value) {
		merchantLogo = value;
	}
	
	public static String getMerchantName() {
		return merchantName;
	}

	public static void setMerchantName(String value) {
		merchantName = value;
	}
	
	public static String getMerchantPhone() {
		return merchantPhone;
	}

	public static void setMerchantPhone(String value) {
		merchantPhone = value;
	}
	
	public static String getNickName() {
		return nickName;
	}

	public static void setNickName(String value) {
		nickName = value;
	}
	
	public static String getParam192() {
		return param192;
	}

	public static void setParam192(String value) {
		param192 = value;
	}
	
	public static String getPayNmcId() {
		return payNmcId;
	}

	public static void setPayNmcId(String value) {
		payNmcId = value;
	}
	
	public static String getPin() {
		return pin;
	}

	public static void setPin(String value) {
		pin = value;
	}
	
	public static String getPort() {
		return port;
	}

	public static void setPort(String value) {
		port = value;
	}
	
	public static String getPwdSettings() {
		return pwdSettings;
	}

	public static void setPwdSettings(String value) {
		pwdSettings = value;
	}
	
	public static ArrayList<Integer> getRandom() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		while (numbers.size() < 3) {
			int i = GetRandomNumber(0, 3);
			if (!(numbers.contains(i))) {
				numbers.add(i);
			}
		}
		return(numbers);
    }
	
	public static int GetRandomNumber(int min, int max) {
        int numPosibilidades = max - min;
        double aleat = Math.random() * numPosibilidades;
        aleat = Math.floor(aleat);
        int result = min + (int)aleat;
        return result;
    }
	
	public static boolean getRegisterOnGCM(){
		return registerOnGCM;
	}
	
	public static void setRegisterOnGCM(boolean value){
		registerOnGCM = value;
	}
	
	public static String getRegistrationId() {
		return registrationId;
	}
	
	public static void setRegistrationId(String value) {
		registrationId = value;
	}
	
	public static String getRrn() {
		return rrn;
	}
	
	public static void setRrn(String value) {
		rrn = value;
	}

	public static String getSecurityQuestion() {
		return securityQuestion;
	}

	public static void setSecurityQuestion(String value) {
		securityQuestion = value;
	}
	
	public static String getSecurityQuestion2() {
		return securityQuestion2;
	}

	public static void setSecurityQuestion2(String value) {
		securityQuestion2 = value;
	}
	
	public static Boolean getIsUpToDate(){
		return isUpToDate;
	}
	
	public static void setIsUpToDate(Boolean value){
		isUpToDate = value;
	}
	
	public static String getServerImage() {
		return urlServerImage;
	}

	public static void setServerImage(String value) {
		urlServerImage = value;
	}
	
	public static String getServerVideo() {
		return urlServerVideo;
	}

	public static void setServerVideo(String value) {
		urlServerVideo = value;
	}
	
	public static String getTax() {
		return tax;
	}

	public static void setTax(String value) {
		tax = value;
	}
	
	public static String getTerminalId() {
		return terminalId;
	}

	public static void setTerminalId(String value) {
		terminalId = value;
	}

	public static int getTicketNumber() {
		return ticketNumber;
	}

	public static void setTicketNumber(int value) {
		ticketNumber = value;
	}	
	
	public static int getTimeOut() {
		return timeout;
	}

	public static void setTimeOut(int value) {
		timeout = value;
	}
	
	public static boolean getTransactionCanceled() {
		return transactionCanceled;
	}

	public static void setTransactionCanceled(boolean value) {
		transactionCanceled = value;
	}
	
	public static String getTransactionId() {
		return transactionId;
	}

	public static void setTransactionId(String value) {
		transactionId = value;
	}
	
	public static int getTVDisplayMode() {
		return tVDisplayMode;
	}
	
	public static void setTVDisplayMode(int value) {
		tVDisplayMode = value;
	}

	public static String getType() {
		return type;
	}

	public static void setType(String value) {
		type = value;
	}

	/**
	 * @return the dayActual
	 */
	public static String getDayActual() {
		return dayActual;
	}

	/**
	 * @param dayActual the dayActual to set
	 */
	public static void setDayActual(String dayActual) {
		DtoDefaultValues.dayActual = dayActual;
	}

	/**
	 * @return the dayActual1
	 */
	public static String getDayActual1() {
		return dayActual1;
	}

	/**
	 * @param dayActual1 the dayActual1 to set
	 */
	public static void setDayActual1(String dayActual1) {
		DtoDefaultValues.dayActual1 = dayActual1;
	}

	/**
	 * @return the senderId
	 */
	public static String getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public static void setSenderId(String senderId) {
		DtoDefaultValues.senderId = senderId;
	}

	/**
	 * @return the start
	 */
	public static boolean isStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public static void setStart(boolean start) {
		DtoDefaultValues.start = start;
	}
	
	public static boolean getIsMerchantOk(){
		return isMerchantOk;
	}
	
	public static void setIsMerchantOk(Boolean value){
		isMerchantOk = value;
	}
	
	public static long getServerVersionCode(){
		return serverVersionCode;
	}
	
	public static void setServerVersionCode(long value){
		serverVersionCode = value;
	}
	
	public static String getAppUpdateUrl(){
		return appUpdateUrl;
	}
	
	public static void setAppUpdateUrl(String value){
		appUpdateUrl = value;
	}
	
	public static String getQuitPassword(){
		return quitPassword;
	}
	
	public static void setQuitPassword(String value){
		quitPassword = value;
	}
}