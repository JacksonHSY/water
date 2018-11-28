package com.ymkj.smi.web.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ymkj.smi.web.service.OrdersService;
import com.ymkj.smi.web.utils.Constants;
import com.ymkj.smi.web.utils.Response;
import com.ymkj.smi.web.vo.Customer;
import com.ymkj.smi.web.vo.OrdersVo;

/**
 * 
 * @author liangj@yuminsoft.com
 *
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping("/createOrder")
	public String createOrder(Model model,HttpSession httpSession) {
		return "order/createOrder";
	}
	
	@RequestMapping("/cancelOrder")
	public String cancelOrder(String taskCode,Model model,HttpSession httpSession) {
		String resultString =  ordersService.cancelOrder(taskCode);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				return "redirect:/index";
			}
		}
		return resultString;
	}
	
	@RequestMapping("/doneOrder")
	public String doneOrder(String taskCode,Model model,HttpSession httpSession) {
		String resultString =  ordersService.queryOrderDetail(taskCode);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				model.addAttribute("msg", resultJson.get("msgEx"));
			}
		}
		return "order/doneOrder";
	}
	
	@RequestMapping("/judgeOrder")
	public String judgeOrder(String taskCode,Model model,HttpSession httpSession) {
		String resultString =  ordersService.queryOrderDetail(taskCode);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				model.addAttribute("msg", resultJson.get("msgEx"));
			}
		}
		return "order/judgeOrder";
	}
	
	@RequestMapping(value = "/judgeOrders", method = RequestMethod.POST)
	public String judgeOrders(String taskCode,String judge,Model model,HttpSession httpSession){
		String resultString =  ordersService.judgeOrder(taskCode,judge);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				return "redirect:/index";
			}
		}
		
		return resultString;
	}
	
	
	@RequestMapping("/orderDeal")
	public String waitDeal(String taskCode,Model model,HttpSession httpSession) {
		String resultString =  ordersService.queryOrderDetail(taskCode);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				model.addAttribute("msg", resultJson.get("msgEx"));
			}
		}
		return "order/orderDeal";
	}
	
	@RequestMapping(value = "/createOrders", method = RequestMethod.POST)
	@ResponseBody
	public Response createOrders(OrdersVo ordersVo,Model model,HttpSession httpSession){
		Response res = Response.success();
		Customer username = (Customer) httpSession.getAttribute(Constants.WEB_LOGIN_USR);  
		Long id = username.getId();
		ordersVo.setId(id);
		ordersVo.setUserType("1");
		String resultString =  ordersService.createOrders(ordersVo);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				res.setData(resultJson.get("msgEx"));
				return res;
			}else{
				res.setCode(Constants.CODE_FAILURE);
		    	res.setMsg("");
		    	return res;
			}
		}
		
		return res;
	}
	
	@RequestMapping("/updateOrder")
	public String updateOrder(String taskCode,Model model,HttpSession httpSession) {
		String resultString =  ordersService.queryOrderDetail(taskCode);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				model.addAttribute("msg", resultJson.get("msgEx"));
			}
		}
		return "order/updateOrder";
	}
	
	@RequestMapping(value = "/updateOrders", method = RequestMethod.POST)
	@ResponseBody
	public Response updateOrders(OrdersVo ordersVo,Model model,HttpSession httpSession){
		Response res = Response.success();
		String resultString =  ordersService.updateOrders(ordersVo);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				res.setData(resultJson.get("msgEx"));
				return res;
			}else{
				res.setCode(Constants.CODE_FAILURE);
		    	res.setMsg("");
		    	return res;
			}
		}
		
		return res;
	}
	
	@RequestMapping(value = "/dictionaryList", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray dictionaryList(String dataType,HttpServletRequest req, HttpServletResponse  res){
		String resultString =  ordersService.queryDictionary(dataType);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				String resultJson1 = JSONObject.toJSONString(resultJson.get("msgEx"));
				JSONObject object = JSONObject.parseObject(resultJson1);
				if(null != object){
					JSONArray jsonArray = JSONArray.fromObject(object.get("infos"));
					return jsonArray;
				}
			}
		}
		return  null;
	}
	
	@RequestMapping(value = "/shipList", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray shipList(HttpSession httpSession,HttpServletRequest req, HttpServletResponse  res){
		Customer username = (Customer) httpSession.getAttribute(Constants.WEB_LOGIN_USR);  
		Long id = username.getId();
		String userType = "1";
		String resultString =  ordersService.queryShips(id,userType);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				String resultJson1 = JSONObject.toJSONString(resultJson.get("msgEx"));
				JSONObject object = JSONObject.parseObject(resultJson1);
				if(null != object){
					String resultJson2 = JSONObject.toJSONString(object.get("infos"));
					JSONObject object1 = JSONObject.parseObject(resultJson2);
					if(null != object1){						
						JSONArray jsonArray = JSONArray.fromObject(object1.get("customerShipList"));
						return jsonArray;
					}
				}
			}
		}
		return  null;
	}
}
