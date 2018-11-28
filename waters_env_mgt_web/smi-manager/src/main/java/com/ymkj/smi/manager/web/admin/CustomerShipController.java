package com.ymkj.smi.manager.web.admin;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import com.ymkj.smi.manager.common.untils.CreateExcelUtil;
import com.ymkj.smi.manager.common.untils.FileUtil;
import com.ymkj.smi.manager.common.vo.ExcelBo;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.service.CustomerShipService;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;

import lombok.extern.log4j.Log4j;

/**
* CustomerShipController
* <p/>
* Author: 
* Date: 2017-07-24 18:31:49
* Mail: 
*/
@Log4j
@Controller
@RequestMapping("/admin/customerShip")
public class CustomerShipController {

@Autowired CustomerShipService customerShipService;
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
		
		PageInfo<CustomerShip> pageInfo = new PageInfo<CustomerShip>();
		CustomerShip customerShip = new CustomerShip();
		
		customerShip.setSName(sName);
		customerShip.setSNature(sNature);
		
		pageInfo.setPageNo(page);
		pageInfo.setPageSize(rows);
		pageInfo.setQueryParam(customerShip);
		
		pageInfo = customerShipService.getFileManagementPage(pageInfo);
		System.out.println(pageInfo);
		return ResultVo.returnPage(pageInfo);
	}
	/**
	 * 新增客户船
	 * @param customerShip
	 * @return
	 * ResultVo
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
/*	@RequestMapping(value="/creatCustomerShip")
	@ResponseBody
	public ResultVo creatCustomerShip(CustomerShip customerShip,HttpServletRequest request){
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
			customerShip.setCreater(adminUser.getName());
		}
		resp = customerShipService.creatShip(customerShip);
		
		return  ResultVo.returnMsg(resp);
		
	}*/
	/**
	 * 注销客户船
	 * @param customerShip
	 * @return
	 * ResultVo
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	/*@RequestMapping(value="/updateCustomerShip")
	@ResponseBody
	public ResultVo VoupdateCustomerShip(CustomerShip customerShip){
		Response resp = null;
		
		resp = customerShipService.updateShip(customerShip);
		
		return  ResultVo.returnMsg(resp);
	}*/
	/**
	 * 重置密码
	 * @param customerShip
	 * @return
	 * ResultVo
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	/*@RequestMapping(value="/cancleCustomerShip")
	@ResponseBody
	public ResultVo cancleCustomerShip(CustomerShip customerShip){
		Response resp = null;
		
		resp = customerShipService.cancleShipPwd(customerShip);
		
		return  ResultVo.returnMsg(resp);
	}*/
	
	
	/**
	 * 功能描述：excel文件下载
	 * 输入参数：
	 * @param workShip
	 * @param resp
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年8月7日
	 */
	@RequestMapping(value="/customerShipXlsDownload",method = RequestMethod.GET)
	@ResponseBody 
	public Response customerShipXlsDownload(HttpServletRequest request,HttpServletResponse resp){
		Response response = Response.success();
		try {
			String sName = StrUtils.isNotBlank(request.getParameter("sName"))?request.getParameter("sName"):"";
			String sNature = StrUtils.isNotBlank(request.getParameter("sNature"))?request.getParameter("sNature"):"";
			CustomerShip customerShip = new CustomerShip();
			customerShip.setSName(sName);
			customerShip.setSNature(sNature);
			List<CustomerShip> list = customerShipService.getFilemanagementList(customerShip);
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String temp = sf.format(new Date()); 
	        
			String[] headArr = new String[]{"MMSI码","用户名","船名/单位","单位地址","客户性质","联络人","联络人电话"};
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(sName)){
				map.put("船名/单位", sName);
			}
			if(StringUtils.isNotBlank(sNature)){
				map.put("客户性质", sNature);
			}
			ExcelBo bo = new ExcelBo();
			bo.setFileName("客户信息_"+temp);
			bo.setTitle("客户信息");
			bo.setParams(map);
			bo.setSubTitle("客户信息");
			bo.setThead(headArr);
			bo.setData(transfer(list));
			SXSSFWorkbook workbook = CreateExcelUtil.createExcel(bo);
			FileUtil.downloadXls(bo.getFileName(), workbook, resp);
		} catch (Exception e) {
			log.error("下载excel失败！",e);
			response.setCode(Constants.CODE_FAILURE);
			response.setMsg("excel下载失败!");
		}
		return response;
	}
	
	/**
	 * 功能描述：封装excel数据
	 * 输入参数：
	 * @param list
	 * @return
	 * 返回类型：List<Map<Integer,Object>>
	 * 创建人：tianx
	 * 日期：2017年8月7日
	 */
	public List<Map<Integer, Object>> transfer(List<CustomerShip> list){
		List<Map<Integer, Object>> data = new ArrayList<Map<Integer, Object>>();
		if(null != list && list.size()>0){
			for(CustomerShip ws : list){
				Map<Integer, Object> map = new HashMap<Integer, Object>();
				map.put(0, ws.getMmsiCode());
				map.put(1, ws.getUserName());
				map.put(2, ws.getSName());
				map.put(3, ws.getAddress());
				map.put(4, Constants.CUSTOMER_SHIP_NATURE.get(ws.getSNature()));
				map.put(5, ws.getCusName());
				map.put(6, ws.getPhone());
				data.add(map);
			}
		}
		return data;
	}
}