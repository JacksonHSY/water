package com.ymkj.smi.web.service;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ymkj.smi.web.network.HttpClientUtil;
import com.ymkj.smi.web.network.Param;
/**
 * 客户船service
 * @author 
 *
 */
@Service
public class CustomerShipService {
	/**
	 * 删除船
	 */
	public String deleteShip(Long id){
		JSONObject model = new JSONObject();
		model.put("id", id);
		String params = Param.getParam("006004", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	/**
	 * 查找有任务的船
	 * 
	 */
	public String findordersShip(Long id){
		JSONObject model = new JSONObject();
		model.put("id", id);
		String params = Param.getParam("006005", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	} 
}
