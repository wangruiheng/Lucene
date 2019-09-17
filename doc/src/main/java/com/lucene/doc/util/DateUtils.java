package com.lucene.doc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static int getDateTimeInt(Date date) {
		try {
			return Integer.parseInt(date.getTime()/1000+"");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		
	}
    /**
     * 格式化日期
     * @param str
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date getDateToString(String str,String format) throws ParseException{
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date=sf.parse(str);
        return date;
    }
    
    /**
     * 格式化日期
     * @param date
     * @param format
     * @return
     */
    public static String getDateToString(Date date,String format){
    	try {
    		SimpleDateFormat sf = new SimpleDateFormat(format);
            String str=sf.format(date);
            return str;
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
        
    }
    
}
