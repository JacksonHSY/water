package com.ymkj.smi.manager.common.untils;

import java.util.List;

/**
 * 判断对象是否为空
 */
public class NullUtil {
	
	/**
	 * 数组是否为空
	 */
	public static boolean isEmpty(Object objs){
		if(objs==null){
			return true;
		}
		return false;
	}
	
	/**
	 * 数组是否为空
	 */
	public static boolean isEmpty(Object[] objs){
		if(objs==null || objs.length==0){
			return true;
		}
		return false;
	}
	
	/**
	 * 数组是否为空
	 */
	public static boolean isEmpty(List<?> objs){
		if(objs==null || objs.size()<=0){
			return true;
		}
		return false;
	}
	
	/**
	 * byte是否为空
	 */
	public static boolean isEmpty(byte[] objs){
		if(objs==null || objs.length==0){
			return true;
		}
		return false;
	}
	
	/**
	 * 字符串是否为空
	 */
	public static boolean isEmpty(String str){
		if(str==null || str.equals("null") || str.trim().length()==0){
			return true;
		}
		return false;
	}
	
	/**
	 * long是否为空
	 */
	public static boolean isEmpty(Long l){
		if(l==null || l.longValue()==0L){
			return true;
		}
		return false;
	}
	
	/**
	 * integer是否为空
	 */
	public static boolean isEmpty(Integer i){
		if(i==null || i==0){
			return true;
		}
		return false;
	}
	
}
