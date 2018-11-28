package com.ymkj.smi.manager.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.Customer;
import com.ymkj.smi.manager.common.entity.CustomerShip;
import com.ymkj.smi.manager.common.entity.WorkShip;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.service.CustomerService;
import com.ymkj.smi.manager.service.CustomerShipService;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;

/**
* CustomerController
* <p/>
* Author: 
* Date: 2017-07-24 18:31:45
* Mail: 
*/
@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	@RequestMapping(value="/creatCustomerShip")
	@ResponseBody
	public ResultVo creatCustomerShip(Customer customer,HttpServletRequest request){
		Response resp = null;
//		String mmsiCode = StrUtils.isNotBlank(request.getParameter("mmsiCode"))?request.getParameter("mmsiCode"):"";
//		String sName = StrUtils.isNotBlank(request.getParameter("sName"))?request.getParameter("sName"):"";
//		String address = StrUtils.isNotBlank(request.getParameter("address"))?request.getParameter("address"):"";
//		String sNature = StrUtils.isNotBlank(request.getParameter("sNature"))?request.getParameter("sNature"):"";
//		String cusName = StrUtils.isNotBlank(request.getParameter("cusName"))?request.getParameter("cusName"):"";
//		int phone = Integer.parseInt(StrUtils.isNotBlank(request.getParameter("phone"))?request.getParameter("phone"):"");
//		CustomerShip customerShip = new CustomerShip();
		HttpSession session = request.getSession();
		if(session!=null){
			AdminUser adminUser  = (AdminUser) session.getAttribute(Constants.SYS_LOGIN_USR);
			customer.setCreater(adminUser.getName());
		}
		resp = customerService.creatCustomer(customer);
		
		return  ResultVo.returnMsg(resp);
		
	}
	
	
	
	/**
	 * 注销客户
	 * @param customerShip
	 * @return
	 * ResultVo
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping(value="/updateCustomerShip")
	@ResponseBody
	public ResultVo VoupdateCustomerShip(Customer customer){
		Response resp = null;
		
		resp = customerService.updateShip(customer);
		
		return  ResultVo.returnMsg(resp);
	}
	/**
	 * 重置密码
	 * @param customerShip
	 * @return
	 * ResultVo
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping(value="/cancleCustomerShip")
	@ResponseBody
	public ResultVo cancleCustomerShip(Customer customer){
		Response resp = null;
		
		resp = customerService.cancleShipPwd(customer);
		
		return  ResultVo.returnMsg(resp);
	}
	
	
	
	/**
	 * 查询客户船
	 * @return
	 * ModelAndView
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping("/index")
	public ModelAndView applyFileView(){
		ModelAndView modelAndView = new ModelAndView("busi/customer_manager");
		return modelAndView;
	}
	@RequestMapping("/getCustomerManager")
	@ResponseBody 
	public ResultVo getCustomerManager(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "order", defaultValue = "desc") String order,
			HttpServletRequest request){
		String sName = StrUtils.isNotBlank(request.getParameter("sName"))?request.getParameter("sName"):"";
		String sNature = StrUtils.isNotBlank(request.getParameter("sNature"))?request.getParameter("sNature"):"";
		
		System.out.println(sName);
		System.out.println(sNature);
		
		PageInfo<Customer> pageInfo = new PageInfo<Customer>();
		Customer customer = new Customer();
		
		customer.setSName(sName);
		customer.setSNature(sNature);
		
		pageInfo.setPageNo(page);
		pageInfo.setPageSize(rows);
		pageInfo.setQueryParam(customer);
		
		pageInfo = customerService.getFileManagementPage(pageInfo);
		System.out.println(pageInfo);
		return ResultVo.returnPage(pageInfo);
	}
	
	
	
	
	
	
}