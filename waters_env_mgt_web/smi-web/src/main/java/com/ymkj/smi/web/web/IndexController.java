package com.ymkj.smi.web.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ymkj.smi.web.service.AnnouncementService;
import com.ymkj.smi.web.service.CustomerService;
import com.ymkj.smi.web.service.CustomerShipService;
import com.ymkj.smi.web.service.OrdersService;
import com.ymkj.smi.web.utils.Constants;
import com.ymkj.smi.web.utils.PageNavUtil;
import com.ymkj.smi.web.utils.Response;
import com.ymkj.smi.web.utils.StrUtils;
import com.ymkj.smi.web.vo.Customer;
import com.ymkj.smi.web.vo.CustomerShipVo;
import com.ymkj.smi.web.vo.ShipVo;

/**
 * 首页
 * 
 * @author longjw@yuminsoft.com
 */
@Controller
@RequestMapping("index")
public class IndexController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private AnnouncementService announcementService;
	
	@Autowired
	private CustomerShipService customerShipService;
	
	@RequestMapping("/download")
	public String download(Model model,HttpSession httpSession){
		return "download";
	}
	@RequestMapping("/ourservices")
	public String ourservices(Model model,HttpSession httpSession){
		return "ourservices";
	}
	@RequestMapping("/contactus")
	public String contactus(Model model,HttpSession httpSession){
		return "contactus";
	}
	@RequestMapping("/aboutus")
	public String aboutus(Model model,HttpSession httpSession){
		return "aboutus";
	}
	@RequestMapping("/forgotPassword")
	public String forgotPassword(Model model,HttpSession httpSession){
		return "personal/forgotPassword";
	}
	@RequestMapping("/userInfo")
	public String userInfo(Model model,HttpSession httpSession){
		Customer customer = (Customer) httpSession.getAttribute(Constants.WEB_LOGIN_USR);
		String phone = customer.getUserName();
		
		String resultString = customerService.selectCustomer(phone);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				model.addAttribute("msg", resultJson.get("msgEx"));
			}
		}
		return "personal/userInfo";
	}
	@RequestMapping("/userInfoEdit")
	public String userInfoEdit(Model model,HttpSession httpSession){
		Customer customer = (Customer) httpSession.getAttribute(Constants.WEB_LOGIN_USR);
		String phone = customer.getUserName();
		
		String resultString = customerService.selectCustomer(phone);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				model.addAttribute("msg", resultJson.get("msgEx"));
			}
		}
		return "personal/userInfoEdit";
	}
	
	@RequestMapping(value ="/userupdateEdit", method = {RequestMethod.POST })
	@ResponseBody  
	public Response userupdateEdit(String ids,CustomerShipVo customerShip,HttpSession httpSession,Model model){
		Customer cus = (Customer) httpSession.getAttribute(Constants.WEB_LOGIN_USR); 
//		String password = customerShip.getPassword();
		Response res = Response.success(); 
		//验证密码
//     	if(!password.equals(cus.getPassword())){
//     		res.setMsg("密码错误");
//     		res.setCode(Constants.CODE_FAILURE);
//     		return res;
//		}
		//删除
		if(StrUtils.isNotEmpty(ids)){
			String [] s = ids.split(",");
			for(String i:s){
				Long id1 = Long.parseLong(i);
				String resultString = customerShipService.deleteShip(id1);
				JSONObject resultJson = JSONObject.parseObject(resultString);
				JSONObject msg = JSONObject.parseObject(resultJson.getString("msgEx"));
				if(null != resultJson && !"0".equals(msg.getString("status"))){
					model.addAttribute("msg", resultJson.get("msgEx"));
					res.setMsg(msg.getString("respDesc"));
		     		res.setCode(msg.getString("status"));
		     		return res;
				}else{
		     		res.setCode(msg.getString("status"));
				}
			}
		}
		List<ShipVo> list = customerShip.getShipList();
		if(list!=null){
			List<ShipVo> listTemp = new ArrayList<ShipVo>();
			Customer customer = new Customer();
			String cusName=customerShip.getCusName();
			Long id = customer.getId();
			for(ShipVo sv:list){
				if(StrUtils.isNotEmpty(sv.getName())){
					listTemp.add(sv);
				}
			}
			customerShip.setShipList(listTemp);
			String resultString = customerService.updateCustomer(cus.getId(),	cusName, id,customerShip);
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson){
				JSONObject msg = JSONObject.parseObject(resultJson.getString("msgEx"));
				model.addAttribute("resp", resultJson.get("msgEx"));
				res.setCode(msg.getString("status"));
				res.setMsg(msg.getString("respDesc"));
	     		res.setCode(msg.getString("status"));
			}
		
		}else{
			res.setMsg("所属船只/单位已全部删除");
     		res.setCode(Constants.CODE_SUCCESS);
		}
        return res;
	}
	
	@RequestMapping("/modifyPassword")
	public String modifyPassword(Model model,HttpSession httpSession){
		return "personal/modifyPassword";
	}
	@RequestMapping("")
	public String showOrders(@RequestParam(value = "page", defaultValue = "1") int page,HttpServletRequest request, Model model,HttpSession httpSession) {
		Customer username = (Customer) httpSession.getAttribute(Constants.WEB_LOGIN_USR);  
		Long id = username.getId();
		String resultString = ordersService.queryOrderPage(request,page,id);
		if(StringUtils.isNotBlank(resultString)){
			JSONObject resultJson = JSONObject.parseObject(resultString);
			if(null != resultJson && resultJson.get("code").equals("0000")){
				model.addAttribute("resp", resultJson.get("msgEx"));
				JSONObject info = JSONObject.parseObject(resultJson.getString("msgEx"));
				JSONObject pageInfo = JSONObject.parseObject(info.getString("infos"));
			    model.addAttribute("currentPage", 	pageInfo.get("pageNo"));
			    model.addAttribute("totalCount",pageInfo.get("totalRecord"));
			    model.addAttribute("totalPage",pageInfo.get("totalPage"));
			    pageInfo.getIntValue("pageSize");
			    model.addAttribute("pageNav", PageNavUtil.getPageNavHtml(page, pageInfo.getIntValue("pageSize"),pageInfo.getIntValue("totalRecord"),pageInfo.getIntValue("pageSize") ));
			}
		}
		//公告
		String s=announcementService.getAnnouncementList(1,5);
		JSONObject resultJson = JSONObject.parseObject(s);
		if(null != resultJson && resultJson.get("code").equals("0000")){
			model.addAttribute("msg", resultJson.get("msgEx"));
		}
		return "index";
	}
	
	@RequestMapping("/logout")
	public String logout(Model model,HttpServletRequest request){
		HttpSession session = request.getSession(true);
		session.removeAttribute(Constants.WEB_LOGIN_USR);
		return "login";
	}
}
