package com.ymkj.smi.manager.web.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.Orders;
import com.ymkj.smi.manager.common.untils.CreateExcelUtil;
import com.ymkj.smi.manager.common.untils.DateUtil;
import com.ymkj.smi.manager.common.untils.FileUtil;
import com.ymkj.smi.manager.common.vo.ExcelBo;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.service.OrdersService;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;

import lombok.extern.log4j.Log4j;

/**
* StatisticalInformationController
* <p/>
* Author: liangj@yuminsoft.com
* Date: 2017-07-31 16:18:38
* Mail: 
*/
@Log4j
@Controller
@RequestMapping("/admin/statistic")
public class StatisticalInformationController {
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping("daily")
	public String DailyStatisticList() {
		return "statistic/daily_statistic";
	}
	
	@RequestMapping("month")
	public String MonthStatisticList() {
		return "statistic/month_statistic";
	}
	
	
	/**
	 * 单日统计表分页
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param request
	 * @return
	 * ResultVo
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("dailyList")
	@ResponseBody
	public ResultVo dailyStatisticPage(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "order", defaultValue = "desc") String order,
			HttpServletRequest request) {
		
			String worId = StrUtils.isNotBlank(request.getParameter("worId"))?request.getParameter("worId"):"";
			String customerShipId  = StrUtils.isNotBlank(request.getParameter("customerShipId"))?request.getParameter("customerShipId"):"";
			String tollCollector = StrUtils.isNotBlank(request.getParameter("tollCollector"))?request.getParameter("tollCollector"):"";
			String workDate = StrUtils.isNotBlank(request.getParameter("workDate"))?request.getParameter("workDate"):"";
			String adress = StrUtils.isNotBlank(request.getParameter("adress"))?request.getParameter("adress"):"";
			
		
		
			PageInfo<Orders> requestbody = new PageInfo<Orders>();
			Orders order1 = new Orders();
			if(StringUtils.isNotBlank(worId)){
				order1.setWorId(Long.parseLong(worId));
			}
			if(StringUtils.isNotBlank(customerShipId)){
				order1.setCustomerShipId(Long.parseLong(customerShipId));
			}			
			order1.setTollCollector(tollCollector);
			order1.setWorkDateStr(workDate);;
			order1.setAdress(adress);
			
			requestbody.setPageNo(page);
			requestbody.setPageSize(rows);
			requestbody.setQueryParam(order1);
			requestbody = ordersService.getDailyStatisticPage(requestbody);
			return ResultVo.returnPage(requestbody);	
	}

	/**
	 * 单月统计表分页
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param request
	 * @return
	 * ResultVo
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("monthList")
	@ResponseBody
	public ResultVo monthStatisticPage(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "order", defaultValue = "desc") String order,
			HttpServletRequest request) {
		
			
			String statisticDimension  = StrUtils.isNotBlank(request.getParameter("statisticDimension"))?request.getParameter("statisticDimension"):"";
			String workDate = StrUtils.isNotBlank(request.getParameter("workDate"))?request.getParameter("workDate"):"";
		
		
			PageInfo<Orders> requestbody = new PageInfo<Orders>();
			Orders order1 = new Orders();
			if(StringUtils.isNotBlank(statisticDimension)){
				order1.setStatisticDimension(statisticDimension);
			}
			if(StringUtils.isNotBlank(workDate)){
				workDate = workDate +"-01";
				Date date = DateUtil.strToDate(workDate, DateUtil.DATAFORMAT_YYYY_MM_DD);
				String dateBegin = DateUtil.getFirstDayOfTheMonth(date, DateUtil.DATAFORMAT_YYYY_MM_DD);//当月第一天
				String dateEnd = DateUtil.format(DateUtil.getEndDayOfTheMonth(date), DateUtil.DATAFORMAT_YYYY_MM_DD);//当月最后一天
				order1.setDateBegin(dateBegin);
				order1.setDateEnd(dateEnd);
			}
			order1.setWorkDateStr(workDate);
			
			requestbody.setPageNo(page);
			requestbody.setPageSize(rows);
			requestbody.setQueryParam(order1);
			requestbody = ordersService.getMonthStatisticPage(requestbody);
			return ResultVo.returnPage(requestbody);	
	}
	
	@RequestMapping(value="/dailyListXlsDownload",method = RequestMethod.GET)
	@ResponseBody 
	public Response workShipXlsDownload(HttpServletRequest request,HttpServletResponse resp){
		Response response = Response.success();
		try {
			String worId = StrUtils.isNotBlank(request.getParameter("worId"))?request.getParameter("worId"):"";
			String customerShipId  = StrUtils.isNotBlank(request.getParameter("customerShipId"))?request.getParameter("customerShipId"):"";
			String tollCollector = StrUtils.isNotBlank(request.getParameter("tollCollector"))?request.getParameter("tollCollector"):"";
			String workDate = StrUtils.isNotBlank(request.getParameter("workDate"))?request.getParameter("workDate"):"";
			String adress = StrUtils.isNotBlank(request.getParameter("adress"))?request.getParameter("adress"):"";
			
		
		
			Orders order1 = new Orders();
			if(StringUtils.isNotBlank(worId)){
				order1.setWorId(Long.parseLong(worId));
			}
			if(StringUtils.isNotBlank(customerShipId)){
				order1.setCustomerShipId(Long.parseLong(customerShipId));
			}			
			order1.setTollCollector(tollCollector);
			order1.setWorkDateStr(workDate);;
			order1.setAdress(adress);
			List<Orders> list = ordersService.getDailyStatisticPageList(order1);
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String temp = sf.format(new Date()); 
	        
			String[] headArr = new String[]{"作业船","客户船名/单位","地点","是否已分类","收费金额","垃圾量合计","生活垃圾","扫舱垃圾","食品废弃物","焚烧炉灰渣"
					,"塑料","生活污水","油污量","所属海事辖区","服务完成时间","拒绝原因","联络员","备注"};
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(worId)){
				map.put("作业船", adress);
			}
			if(StringUtils.isNotBlank(customerShipId)){
				map.put("客户船名/单位", customerShipId);
			}
			if(StringUtils.isNotBlank(tollCollector)){
				map.put("联络员", tollCollector);
			}
			if(StringUtils.isNotBlank(workDate)){
				map.put("日期", workDate);
			}
			if(StringUtils.isNotBlank(adress)){
				map.put("地点", adress);
			}
			
			ExcelBo bo = new ExcelBo();
			bo.setFileName("单日统计报表_"+temp);
			bo.setTitle("单日统计报表");
			bo.setParams(map);
			bo.setSubTitle("单日统计报表");
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
	public List<Map<Integer, Object>> transfer(List<Orders> list){
		List<Map<Integer, Object>> data = new ArrayList<Map<Integer, Object>>();
		if(null != list && list.size()>0){
			for(Orders ws : list){
				Map<Integer, Object> map = new HashMap<Integer, Object>();
				map.put(0, ws.getWorkShipName());
				map.put(1, ws.getCustomerShipName());
				map.put(2, ws.getAdress());
				map.put(3, getIfTypeStr(ws.getIfType()));
				map.put(4, ws.getChargeAmount());
				map.put(5, ws.getAmountGarbage());
				map.put(6, ws.getFLife());
				map.put(7, ws.getFSweeping());
				map.put(8, ws.getFFood());
				map.put(9, ws.getFBurnCinder());
				map.put(10, ws.getFPlastic());
				map.put(11, ws.getFWater());
				map.put(12, ws.getFGungo());
				map.put(13, "");
				map.put(14, ws.getWorkDateStr());
				map.put(15, ws.getReson());
				map.put(16, ws.getTollCollectorName());
				map.put(17, ws.getMemo());
				data.add(map);
			}
		}
		return data;
	}
	
	private String getIfTypeStr(String type){
		if("0".equals(type)){
			return "是";
		}else{
			return "否";
		}
	}
}
