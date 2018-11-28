package com.ymkj.smi.web.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ymkj.smi.web.network.HttpClientUtil;
import com.ymkj.smi.web.network.Param;
import com.ymkj.smi.web.vo.OrdersVo;

/**
 * 
 * @author liangj@yuminsoft.com
 *
 */
@Service
public class OrdersService {

	public String queryOrderPage(HttpServletRequest request,Integer page,Long id){
		JSONObject model = new JSONObject();
		model.put("id", id);
		model.put("pageNo", page);
		model.put("pageSize", 5);
		String params = Param.getParam("003002", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
	public String queryOrderDetail(String taskCode){
		JSONObject model = new JSONObject();
		model.put("taskCode", taskCode);
		String params = Param.getParam("003003", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
	public String createOrders(OrdersVo ordersVo){
		JSONObject model = new JSONObject();
		model.put("userType", ordersVo.getUserType());
		model.put("id", ordersVo.getId());
		model.put("customerShipId", ordersVo.getCustomerShipId());
		model.put("taskType", 0);
		model.put("taskStatus", 0);
		model.put("adress", ordersVo.getAdress());
		model.put("ifType", ordersVo.getIfType());
		model.put("eLife", ordersVo.getELife());
		model.put("eSweeping", ordersVo.getESweeping());
		model.put("eFood", ordersVo.getEFood());
		model.put("eBurnCinder", ordersVo.getEBurnCinder());
		model.put("ePlastic", ordersVo.getEPlastic());
		model.put("eWater", ordersVo.getEWater());
		model.put("eGungo", ordersVo.getEGungo());
		model.put("memo", ordersVo.getMemo());
		if(StringUtils.isNotBlank(ordersVo.getWorkDate())){
			String workDate = ordersVo.getWorkDate()+":00";
			model.put("workDate", workDate);
		}
		String params = Param.getParam("003001", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
	public String updateOrders(OrdersVo ordersVo){
		JSONObject model = new JSONObject();
		model.put("userType", 1);
		model.put("id", 1);
		model.put("taskType", 1);
		model.put("taskStatus", 0);
		model.put("adress", ordersVo.getAdress());
		model.put("taskCode", ordersVo.getTaskCode());
		model.put("ifType", ordersVo.getIfType());
		model.put("eLife", ordersVo.getELife());
		model.put("eSweeping", ordersVo.getESweeping());
		model.put("eFood", ordersVo.getEFood());
		model.put("eBurnCinder", ordersVo.getEBurnCinder());
		model.put("ePlastic", ordersVo.getEPlastic());
		model.put("eWater", ordersVo.getEWater());
		model.put("eGungo", ordersVo.getEGungo());
		model.put("memo",  ordersVo.getMemo());
		if(StringUtils.isNotBlank(ordersVo.getWorkDate())){
			String workDate = ordersVo.getWorkDate()+":00";
			model.put("workDate", workDate);
		}
		String params = Param.getParam("003005", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
	public String cancelOrder(String taskCode){
		JSONObject model = new JSONObject();
		model.put("taskCode", taskCode);
		model.put("userType",1);
		String params = Param.getParam("003006", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
	public String judgeOrder(String taskCode,String judge){
		JSONObject model = new JSONObject();
		model.put("taskCode", taskCode);
		model.put("judge", judge);
		String params = Param.getParam("003004", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
	public String queryDictionary(String dataType){
		JSONObject model = new JSONObject();
		model.put("dataType", dataType);
		String params = Param.getParam("005001", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
	public String queryShips(Long id,String userType){
		JSONObject model = new JSONObject();
		model.put("id", id);
		model.put("userType", userType);
		String params = Param.getParam("005002", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	
}
