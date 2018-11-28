package com.ymkj.smi.manager.common.untils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * NumberUtil
 * 
 * @author longjw@yuminsoft.com
 * @date 2017年5月8日 上午11:17:50
 * @version 1.0
 */
public class NumberUtil {
	
    private static Date date = new Date();  
    private static StringBuilder buf = new StringBuilder();  
    private static int seq = 0;  
    private static final int ROTATION = 99999;  
	/**
	 * 返回日期流水号
	 * @return
	 */
    public static synchronized String getNumberForPK(){  
        if (seq > ROTATION) seq = 0;  
        buf.delete(0, buf.length());  
        date.setTime(System.currentTimeMillis());  
        return String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);  
      }  
	/**
	 * 返回类型+日期流水号
	 * @return
	 */
    public static synchronized String getNumberForPK(String type){  
        StringBuffer batchNo = new StringBuffer();  
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");  
        String temp = sf.format(new Date());  
        return batchNo.append(temp).append(type).toString();
    } 

    public static void main(String args[]){
    	System.out.println(getNumberForPK("FQWHS"));
    }
}	
