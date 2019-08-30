package com.lucene.doc.util;

public class StringUtil {
	/***
     * 去掉字符串前后的空间，中间的空格保留
     * @param str
     * @return
     */
    public static String trimInnerSpaceStr(String str){
    	if(str==null){
    		str="";
    	}else{
    		str = str.trim();
            while(str.startsWith(" ")){
            str = str.substring(1,str.length()).trim();
            }
            while(str.endsWith(" ")){
            str = str.substring(0,str.length()-1).trim();
            }
    	}
        return str;
    }
    /***
     * 判断字符串非null 非""
     * @param str
     * @return
     */
    public static boolean isNotNull(Object str){
    	boolean bool=false;
    	if(str!=null){
    		if(str!=null&&!"".equals(trimInnerSpaceStr(String.valueOf(str)))){
        		bool= true;
        	}
    	}
    	
    	return bool;
    }
	/**
	 * 将字符串格式化
	 * 
	 * 
	 * @param str
	 *            源字符串
	 * @return
	 */
	public static String format(final Object str) {
		if(isNotNull(str)){
			return str.toString();
		}else{
			return "";
		}
	}
	/**
	 * 例子 王瑞恒  改为 王*恒
	 * @param str
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public static String formatReplaceStr(final String str,final String format,final int beginIndex,final int endIndex ) {
		try {
			if(isNotNull(str)){
		         StringBuilder sb = new StringBuilder(str);
		         sb.replace(beginIndex, endIndex, format);
		         return sb.toString();
		    }
		} catch (Exception e) {
			// TODO: handle exception
		}
	    return "";
	}
}
