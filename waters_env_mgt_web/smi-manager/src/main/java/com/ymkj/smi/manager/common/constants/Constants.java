package com.ymkj.smi.manager.common.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	/**
	 * 任务状态
	 */
	public static String WAIT_DISPATCH = "0";// 待分派
	public static String WAIT_DEAL = "1";// 待处理
	public static String DEALING = "2";// 处理中
	public static String CANCELLED = "3";// 已取消
	public static String DEAL_DONE = "4";// 已处理
	public static String EVAL_DONE = "5";// 已评价
	/**
	 * 任务类型
	 */
	public static String ORDER_TYPE_FQWJS = "0"; //废弃物接收
	public static String ORDER_TYPE_SMBJ = "1"; //水面保洁
	public static String ORDER_TYPE_HEBJ = "2";//河滩保洁
	public static String ORDER_TYPE_JSQKCL = "3";//紧急情况处理
	
	/**
	 * 垃圾统计：时间刻度
	 */
	/**
	 * 当月
	 */
	public static String STATISTIC_TIME_CURMONTH = "curMonth"; 
	/**
	 * 上一月
	 */
	public static String STATISTIC_TIME_LASTMONTH = "lastMonth"; 
	/**
	 * 今年
	 */
	public static String STATISTIC_TIME_CURYEAR = "curYear";
	/**
	 * 上一年
	 */
	public static String STATISTIC_TIME_LASTYEAR = "lastYear";
	
	/**
	 * 垃圾种类名
	 */
	public static String GARBAGE_TYPE_GARBAGE = "生活垃圾";
	public static String GARBAGE_TYPE_WATER = "生活污水";
	public static String GARBAGE_TYPE_FOOD = "食品废弃物";
	public static String GARBAGE_TYPE_PLASTIC = "塑料垃圾";
	public static String GARBAGE_TYPE_SWEEPING = "扫舱垃圾";
	public static String GARBAGE_TYPE_BURNCINDER = "焚烧炉火渣";
	public static String GARBAGE_TYPE_GUNGO = "油污";
	public final static Map ORDER_TYPE = new HashMap<String, String>(){{  
		put("0","FQWJS");  
	    put("1","SMBJ");   
	    put("2", "HTBJ");
	    put("3","JJQKL");		
	}}; 
	
	/**
	 * 船舶性质
	 */
	public final static Map WORK_SHIP_NATURE = new HashMap<String, String>(){{  
	    put("1","多功能");   
	    put("2", "油污");
	    put("3","普通");		
	}}; 
	
	/**
	 * 客户性质
	 */
	public final static Map CUSTOMER_SHIP_NATURE = new HashMap<String, String>(){{  
	    put("1","邮轮");   
	    put("2", "货轮");
	    put("3","单位");		
	}};
	
	public final static Map ORDER_TYPE_STR = new HashMap<String, String>(){{  
		put("0","废弃物接收");
	    put("1","水面保洁");   
	    put("2", "河滩保洁");
	    put("3","紧急任务");		
	}};
	
	public final static Map ORDER_STATUS = new HashMap<String, String>(){{  
	    put("0","待分派");   
	    put("1", "待处理");
	    put("2","处理中");	
	    put("3","已取消");
	    put("4","已完成");
	    put("5","已评价");
	}};
	
	public static final String CODE_SUCCESS = "0000";
	public static final String CODE_FAILURE = "9999";
	public static final String MSG_SUCCESS = "SUCCESS";

	public static final String SYS_LOGIN_USR = "loginUser";// 当前登录用户
	public static final String SYS_LOGIN_ROLE = "loginRole";// 当前用户角色
	public static final String SYS_LOGIN_ORG = "loginDepart";// 当前用户机构
	public static final String SYS_LOGIN_STAFF_DEP = "loginStaffDep";// 当前登录用户报表组织权限
	public static final String SYS_PARENT_ID = "-1";// 最高级菜单id
	public static final String SYS_DEPARMENT_ID = "-1";//最高级组织
	
	public static final String APP_FUNC_PARENT_CODE = "9999";//APP系统父菜单节点ID
	
	/**
	 * 数据有效性
	 */
	public static final String DATA_VALID = "1";
	public static final String DATA_UNVALID = "0";
	
	/*收费员
	 * */
	public static final String CASHIER = "2";
	
	/**
	 * 用户默认密码
	 */
	public static final String DEFAULT_PASSWORD = "123456";
}
