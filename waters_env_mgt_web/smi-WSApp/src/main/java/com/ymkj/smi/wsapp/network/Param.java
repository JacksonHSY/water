package com.ymkj.smi.wsapp.network;

import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import com.ymkj.smi.wsapp.config.ApplicationBean;
import com.ymkj.smi.wsapp.utils.Constants;
import com.ymkj.smi.wsapp.utils.DateUtil;
import com.ymkj.smi.wsapp.utils.MD5;
import com.ymkj.smi.wsapp.utils.SpringContextHelper;

/**
 * webservice的传递参数
 * 
 */
@Slf4j
public class Param {
	
    private static final String SIGN_SEPARATOR = "|";

    private static ApplicationBean applicationBean;

    static{
    	applicationBean = (ApplicationBean) SpringContextHelper.getBean("applicationBean");
    }
    
	/**
	 * 获取验发转签接口请求的参数对象
	 * 
	 * @param params 参数
	 * @return
	 */
	public static String getReqMain(JSONObject params) {
		JSONObject json = new JSONObject();
		String projectNo = "smi";
		String sn = projectNo + createSn();
		json.put(Constants.PROGECT_NO, projectNo);
		json.put(Constants.REQ_RUL, "");
		json.put(Constants.REQ_PARAM, params);
		json.put(Constants.REQ_HEAD_PARAM, getReqHeadParams());
		json.put(Constants.REQ_TIMESTAMP, "123"); 
		json.put(Constants.SN, sn);
		return json.toString();
	}

	/**
	 * 报文头
	 * 
	 * @return
	 */
	public static JSONObject getReqHeadParams() {
		JSONObject json = new JSONObject();
		json.put(Constants.SESSIONTOKEN, "");
		json.put(Constants.VERSION, "1.0.0");
		json.put(Constants.MECHANISM, "");
		json.put(Constants.PLATFORM, "web");
		json.put(Constants.TOGATHERTYPE, "");
		json.put(Constants.OPENCHANNEL, "");
		json.put(Constants.TOKEN, "");
		json.put(Constants.USERAGENT, "web");
		return json;
	}

	/**
	 * 生成流水号
	 * 
	 * @return
	 */
	private static String createSn() {
		String sign = DateUtil
				.format(new Date(), DateUtil.DATE_TIME_SSS_FORMAT)
				+ UUID.randomUUID().toString().substring(0, 8);
		return sign;
	}
	
	/**
	 * 请求入参
	 * 
	 * @param functionId 功能号
	 * @param model 请求参数
	 * @return<arg0=&arg1=&arg2=>
	 */
	public static String getParam(String functionId, JSONObject model) {
		String arg0 = functionId;// 功能号
		String arg1 = getReqMain(model);// 请求参数
		String arg2 = applicationBean.getMd5SignKey();
		StringBuilder sb = null;
		if (!ApplicationBean.SWITCH_OFF.equals(applicationBean
				.getCeValidateSwitch())) {
			sb = new StringBuilder();
			sb.append(arg0).append(SIGN_SEPARATOR).append(arg1).append(SIGN_SEPARATOR).append(arg2);
			arg2 = MD5.MD5Encode(sb.toString());
		}
		sb = new StringBuilder();
		sb.append("arg0=").append(arg0).append("&arg1=").append(arg1)
				.append("&arg2=").append(arg2);
		String params = sb.toString();
		log.info(MessageFormat.format("功能号: {0}；入参: {1}", functionId, params));
		return params;
	}

	public static void main(String[] args) {
		JSONObject param = new JSONObject();
		param.put("loginName", "zhangsan");
		param.put("password", "123456");
		System.out.println(getReqMain(param));
	}
}
