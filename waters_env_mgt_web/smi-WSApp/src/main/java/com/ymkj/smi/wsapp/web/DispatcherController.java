package com.ymkj.smi.wsapp.web;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.smi.wsapp.network.HttpClientUtil;

/**
 * APP统一接口
 * 
 * @author longjw@yuminsoft.com
 */
@Slf4j
@Controller
public class DispatcherController {
	
    /**
     * 功能描述：app请求转发
     * 输入参数：
     * @param httpRequest
     * @param httpResponse
     * @param params
     * @return
     * 返回类型：String
     * 创建人：tianx
     * 日期：2017年8月16日
     */
/*    @RequestMapping(value = "/requestDispatcher", method=RequestMethod.GET)
    @ResponseBody
	public String appRequestDispatcher(HttpServletRequest httpRequest,HttpServletResponse httpResponse,
			@RequestParam("functionId") String functionId,
			@RequestParam("param") String param) {
    	
    	log.debug("app DispatcherController.appRequestDispatcher()请求入参：arg0="+functionId+", arg1="+param);
    	
    	JSONObject paramJson = JSONObject.fromObject(param);
    	String params = Param.getParam(functionId, paramJson);
    	
    	String result = HttpClientUtil.sendHttpPost(params);
    	log.debug("app DispatcherController.appRequestDispatcher()请求出参："+result);
    	return result;
    }*/
    @RequestMapping(value = "/Api/requestDeal")
    @ResponseBody
	public String requestDeal(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse,
			@RequestParam("arg0") String functionId,
			@RequestParam("arg1") String param, 
			@RequestParam("arg2") String sign) {
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("arg0=").append(functionId).append("&arg1=").append(param)
		.append("&arg2=").append(sign);
    	String params = sb.toString();
    	log.info(MessageFormat.format("功能号: {0}；入参: {1}", functionId, params));
    	String result = HttpClientUtil.sendHttpPost(params);
    	log.debug("app DispatcherController.appRequestDispatcher()请求出参："+result);
    	return result;
    }
}
