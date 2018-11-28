package com.ymkj.smi.web.service;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;



import com.ymkj.smi.web.network.HttpClientUtil;
import com.ymkj.smi.web.network.Param;
import com.ymkj.smi.web.utils.MD5;
import com.ymkj.smi.web.vo.CustomerShipVo;

/**
 * 客户service
 * 
 * @author 
 *
 */
@Service
public class CustomerService {

	/**
	 * 登录
	 * 
	 * @param loginName 登录名
	 * @param password 密码
	 * @return
	 */
	public String login(String loginName, String password){
		JSONObject model = new JSONObject();
		model.put("loginName", loginName);
		model.put("password", MD5.MD5Encode(password));
		String params = Param.getParam("001002", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	/**
	 * （便民APP修改）
	 * @param name
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * String
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月4日
	 */
	public String updateCustomerPwd(String userName,String oldPassword,String newPassword){
		JSONObject model = new JSONObject();
		model.put("userName", userName);
		model.put("oldPassword", oldPassword);
		model.put("newPassword", newPassword);
		String params = Param.getParam("001004", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	/**
	 * 客户注册
	 */
	public String regiestCustomer(String phone,String password){
		JSONObject model = new JSONObject();
		model.put("phone", phone);
		model.put("password", MD5.MD5Encode(password));
		String params = Param.getParam("005003", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	/**
	 * 客户信息查询
	 * 
	 */
	public String selectCustomer(String phone){
		JSONObject model = new JSONObject();
		model.put("phone", phone);
		String params = Param.getParam("005004", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
	/**
	 * 修改个人信息
	 */
	public String updateCustomer(Long cid,String cusName,Long id,CustomerShipVo shipList){
		JSONObject model = new JSONObject();
		model.put("id", id);
		model.put("cId", cid);
		model.put("cusName", cusName);
		model.put("shipList", shipList.getShipList());
		String params = Param.getParam("005006", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
	
	/**
	 * 忘记密码
	 */
	public String forgotPassword(String mobile,String password){
		JSONObject model = new JSONObject();
		model.put("mobile", mobile);
		model.put("password", MD5.MD5Encode(password));
		String params = Param.getParam("006003", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
}
