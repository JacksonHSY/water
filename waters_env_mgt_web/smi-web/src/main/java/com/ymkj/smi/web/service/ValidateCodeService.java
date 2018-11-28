package com.ymkj.smi.web.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymkj.smi.web.config.ApplicationBean;
import com.ymkj.smi.web.network.HttpClientUtil;
import com.ymkj.smi.web.network.Param;
import com.ymkj.smi.web.utils.MD5;
import com.ymkj.smi.web.vo.CustomerShipVo;

/**
 * 
 * @author Cherish
 *
 */
@Slf4j
@Service
public class ValidateCodeService {

	@Autowired
	private ApplicationBean applicationBean;
	
	/**
	 * 发送短信
	 * 
	 * @param mobile 手机号
	 * @param content 内容
	 */
	public String sendValidateCode(String mobile, String content){
		JSONObject model = new JSONObject();
		model.put("mobile", mobile);
		model.put("type", content);
		String params = Param.getParam("006006", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
		
		
//		try {
//			StringBuilder params = new StringBuilder();
//			params.append("channelCode=").append(applicationBean.getChannelCode()).append("&mobile=").append(mobile);
//	
//	    	//按UTF-8进行URL编码,参数串首、尾部加上通讯密钥进行加密，生成签名。
//	    	String sign = MD5.MD5Encode(applicationBean.getSecKey() + URLEncoder.encode(params.toString(), "utf-8") + applicationBean.getSecKey());
//	    	params.append("&content=").append(URLEncoder.encode(URLEncoder.encode(content,"utf-8"), "utf-8")).append("&sign=").append(sign);
//	    	
//	    	log.info(MessageFormat.format("手机号：{0};短信发送入参:{1}", mobile, params.toString()));
//	    	String response = HttpClientUtil.sendHttpGet(applicationBean.getSmsUrl(), params.toString());
//	    	log.info(MessageFormat.format("手机号：{0};短信发送结果:{1}", mobile, response));
//	    	
//	    	JSONObject json = JSONObject.fromObject(response);
//	    	String code = json.getString("code");
//	    	if(!"1".equals(code)){
//	    		return false;
//	    	}
//	    	return true;
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			return false;
//		}
	}
	
	/**
	 * 保存验证码
	 */
	public String saveCode(String mobile,String type,String validateCode){
		JSONObject model = new JSONObject();
		model.put("mobile", mobile);
		model.put("type", type);
		model.put("validateCode", validateCode);
		String params = Param.getParam("006001", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
	/**
	 * 查询校验验证码
	 */
	public String validateSelect(String mobile,String type,String vaildateCode){
		JSONObject model = new JSONObject();
		model.put("mobile", mobile);
		model.put("type", type);
		model.put("vaildateCode", vaildateCode);
		String params = Param.getParam("006002", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
}
