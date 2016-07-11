package com.appzorro.nmcparamsdemo.util;

/*  ************************************************************************************************************************* ********
/* Autor : Romina Galavotti (autor original : desconocido por mi - se agregaron metodos )*/
/* Fecha : 13/05/2013 */
/*Numero de  programa: */
/* Versi�n del programa */
/* Lenguaje: java */
/* Descripci�n: Clase para formateo de datos
 *************************************************************************************************************************************/
import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat") public class Formatter {

	private static SimpleDateFormat DATE_EFFECTIVE = new SimpleDateFormat("yyyyMM");
	private static SimpleDateFormat DATE_FORMAT
    	= new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat TIMESTAMP_FILE_FORMAT
		= new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    private static SimpleDateFormat TIME_FORMAT
    	= new SimpleDateFormat("hh:mm:ss");
    private static SimpleDateFormat TIMESTAMP_FORMAT
    	= new SimpleDateFormat("MMddhhmmss");
    private static SimpleDateFormat TIMESTAMP_LONG_FORMAT
    	= new SimpleDateFormat("yyyyMMddhhmmss");
    private static SimpleDateFormat TIMESTAMP_NEW_FORMAT
    	= new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS Z");

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String formatDate(Calendar date) {
        return DATE_FORMAT.format(date.getTime());
    }

    public static String formatTime(Date date) {
        return TIME_FORMAT.format(date);
    }

    public static String formatTime(Calendar date) {
        return TIME_FORMAT.format(date.getTime());
    }

    public static String formatTimeStamp(Date date) {
        return TIMESTAMP_FORMAT.format(date);
    }

    public static String formatTimeStamp(Calendar date) {
        return TIMESTAMP_FORMAT.format(date.getTime());
    }

    public static String formatTimeStampLong(Date date) {
        return TIMESTAMP_LONG_FORMAT.format(date);
    }

    public static String formatTimeStampLong(Calendar date) {
        return TIMESTAMP_LONG_FORMAT.format(date.getTime());
    }

    public static String formatTimeStampFile(Date date) {
        return TIMESTAMP_FILE_FORMAT.format(date);
    }

    public static String formatTimeStampFile(Calendar date) {
        return TIMESTAMP_FILE_FORMAT.format(date.getTime());
    }

    public static Date parseDateTimeStamp(String timeStamp) {
        try {
            return TIMESTAMP_FORMAT.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String DateEffective(Date date) {
        return DATE_EFFECTIVE.format(date);
    }

    public static String DateEffective(Calendar date) {
        return DATE_EFFECTIVE.format(date.getTime());
    }
    
    public static String formatNewTimeStamp(Date date) {
        return TIMESTAMP_NEW_FORMAT.format(date);
    }

}